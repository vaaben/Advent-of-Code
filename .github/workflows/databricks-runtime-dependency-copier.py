from subprocess import check_output
import os
from os import listdir, getcwd
from os.path import isfile, join
import re
import shutil
import logging

# logging.basicConfig(format='[%(levelname)s] %(message)s', level=logging.INFO)
logging.basicConfig(format='%(message)s', level=logging.INFO)

pattern = re.compile(r'(?P<artifact>spark-[\w-]+)_[\d\.]+-(?P<version>[\d\.]+)-SNAPSHOT\.jar')
path = check_output(["databricks-connect", "get-jar-dir"]).strip().decode("utf-8") 

spark_replace = []

target_dir = join(getcwd(), "analytics", "lib")
if os.path.exists(target_dir) and os.path.isdir(target_dir):
  logging.warning("Folder %s already exist, emptying.", target_dir)
  shutil.rmtree(target_dir)
os.makedirs(target_dir, exist_ok=True)

for file in listdir(path):
  if not isfile(join(path, file)):
    continue
  m = pattern.match(file)
  if m:
    spark_replace.append(m.groupdict())
  else:
    original = join(path, file)
    target = join(target_dir, file)
    shutil.copyfile(original, target)
logging.info("Copied managed dependencies to %s", target_dir)

module_ids = []
blacklist = ["spark-sql-dw-integration-tests"]
for package in spark_replace:
  if package["artifact"] in blacklist:
    continue
  module_ids.append("\"org.apache.spark\" %% \"{}\" % \"{}\" % Provided".format(package["artifact"], package["version"]))

deps_file = join(getcwd(), "project", "DatabricksDeps.scala")
file = open(deps_file, "w")
file.write("""import sbt._

object DatabricksDeps {{

  val deps: Seq[ModuleID] = Seq(
    {}
  )

}}
""".format(",\n    ".join(module_ids)))
logging.info("Wrote Maven dependencies to %s", deps_file)
