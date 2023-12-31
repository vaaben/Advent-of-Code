# --- Day 2: Password Philosophy ---
# Your flight departs in a few days from the coastal airport;
# the easiest way down to the coast from here is via toboggan.
#
# The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day.
# "Something's wrong with our computers; we can't log in!"
# You ask if you can take a look.
#
# Their password database seems to be a little corrupted:
# some of the passwords wouldn't have been allowed by the
# Official Toboggan Corporate Policy that was in effect when they were chosen.
#
# To try to debug the problem, they have created a list (your puzzle input)
# of passwords (according to the corrupted database)
# and the corporate policy when that password was set.
#
# For example, suppose you have the following list:
#
# 1-3 a: abcde
# 1-3 b: cdefg
# 2-9 c: ccccccccc
# Each line gives the password policy and then the password.
# The password policy indicates the lowest and highest number of times
# a given letter must appear for the password to be valid. For example,
# 1-3 a means that the password must contain a at least 1 time
# and at most 3 times.
#
# In the above example, 2 passwords are valid.
# The middle password, cdefg, is not;
# it contains no instances of b, but needs at least 1.
# The first and third passwords are valid: they contain one a or nine c,
# both within the limits of their respective policies.
#
# How many passwords are valid according to their policies?
#
# Your puzzle answer was 398.
#
# --- Part Two ---
# While it appears you validated the passwords correctly, they don't seem
# to be what the Official Toboggan Corporate Authentication System is expecting.
#
# The shopkeeper suddenly realizes that he just accidentally explained
# the password policy rules from his old job at the sled rental place
# down the street!
# The Official Toboggan Corporate Policy actually works a little differently.
#
# Each policy actually describes two positions in the password,
# where 1 means the first character, 2 means the second character,
# and so on.
# (Be careful; Toboggan Corporate Policies have no concept of "index zero"!)
# Exactly one of these positions must contain the given letter.
# Other occurrences of the letter are irrelevant for the purposes
# of policy enforcement.
#
# Given the same example list from above:
#
# 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
# 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
# 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
# How many passwords are valid according to the
# new interpretation of the policies?
#
# Your puzzle answer was 562.
import re

with open("data/dec02.txt") as f:
    content = f.readlines()
content = [x.strip() for x in content]


def extract(s):
    pm = re.search('^(\d+)-(\d+) (.): (.*)$', s)
    
    if pm:
        return pm.groups()


def verify(i, j, c, pwd):
    count = 0
    for x in pwd:
        if c == x:
            count += 1
    
    return i <= count <= j


def verify_new(i, j, c, pwd):
    return (pwd[i-1] == c) ^ (pwd[j-1] == c)
    

def count_valid(l, m):
    valid = 0
    for l in content:
        x = extract(l)
        
        if m(int(x[0]), int(x[1]), x[2], x[3]):
            valid += 1
        
    return valid


print(count_valid(content, verify))
print(count_valid(content, verify_new))
