"""
--- Day 4: The Ideal Stocking Stuffer ---
Santa needs help mining some AdventCoins (very similar to bitcoins)
to use as gifts for all the economically forward-thinking little girls and boys.

To do this, he needs to find MD5 hashes which, in hexadecimal,
start with at least five zeroes.
The input to the MD5 hash is some secret key (your puzzle input, given below)
followed by a number in decimal.
To mine AdventCoins, you must find Santa the lowest positive number
(no leading zeroes: 1, 2, 3, ...) that produces such a hash.

For example:

If your secret key is abcdef, the answer is 609043, because the MD5 hash
of abcdef609043 starts with five zeroes (000001dbbfa...),
and it is the lowest such number to do so.

If your secret key is pqrstuv, the lowest number it combines with to make an
MD5 hash starting with five zeroes is 1048970;
that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....

Your puzzle answer was 282749.

--- Part Two ---
Now find one that starts with six zeroes.

Your puzzle answer was 9962624.
"""

import hashlib
import time
from itertools import count
import multiprocessing
from multiprocessing import Queue, Process

# part zero
input = "yzbqklnj"

flaf = False

def findNLeadingZeros_opt(n, offset, step):
    global flaf
    for k in count(start=offset, step=step):
        md5_hash = hashlib.md5(str.encode(input + str(k)))
        hex = md5_hash.hexdigest()
    
        if flaf:
            exit(0)
    
        if hex[:n] == "0"*n:
            print(k)
            flaf = True
            exit(0)

# part one
# print("found postfix: " + str(findNLeadingZeros_opt(5,0,1)))

#flaf = False
# part two
if __name__ == '__main__':
    t0 = time.time()
    processes = []
    for i in range(0,10):
        p = multiprocessing.Process(target=findNLeadingZeros_opt, args=(6,i,10))
        processes.append(p)
        p.start()

    for process in processes:
        process.join()

    #print("found postfix: " + str(findNLeadingZeros_opt(6,0,1)))
    t1 = time.time()
    print("time taken " + str(t1-t0) + "s")
