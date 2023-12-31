"""
--- Day 5: Doesn't He Have Intern-Elves For This? ---
Santa needs help figuring out which strings in his text
file are naughty or nice.

A nice string is one with all of the following properties:

It contains at least three vowels (aeiou only),
like aei, xazegov, or aeiouaeiouaeiou.
It contains at least one letter that appears twice in a row, like xx,
abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
It does not contain the strings ab, cd, pq, or xy,
even if they are part of one of the other requirements.
For example:

ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...),
a double letter (...dd...), and none of the disallowed substrings.
aaa is nice because it has at least three vowels and a double letter,
even though the letters used by different rules overlap.
jchzalrnumimnmhp is naughty because it has no double letter.
haegwjzuvuyypxyu is naughty because it contains the string xy.
dvszwmarrgswjxmb is naughty because it contains only one vowel.
How many strings are nice?

Your puzzle answer was 258.

--- Part Two ---
Realizing the error of his ways, Santa has switched to a better model of
determining whether a string is naughty or nice. None of the old rules apply,
as they are all clearly ridiculous.

Now, a nice string is one with all of the following properties:

It contains a pair of any two letters that appears at least twice in the
string without overlapping, like xyxy (xy) or aabcdefgaa (aa),
but not like aaa (aa, but it overlaps).
It contains at least one letter which repeats with exactly one letter
between them, like xyx, abcdefeghi (efe), or even aaa.
For example:

qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj)
and a letter that repeats with exactly one letter between them (zxz).
xxyxx is nice because it has a pair that appears twice and a letter that
repeats with one between, even though the letters used by each rule overlap.
uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a
single letter between them.
ieodomkazucvgmuy is naughty because it has a repeating letter with one between
 (odo), but no pair that appears twice.
How many strings are nice under these new rules?

Your puzzle answer was 53.
"""

# part zero
with open("data/dec5.txt") as f:
    content = f.readlines()


vowelList = ['a', 'e', 'i', 'o', 'u']
badWords = ["ab", "cd", "pq", "xy"]


def count_vowels(s):
    vowels = 0
    for c in s:
        if c in vowelList:
            vowels += 1
    return vowels


def contains_duplet(s):
    for i in range(0, len(s)-1):
        if s[i] == s[i+1]:
            return True
    return False


def contains_bad(s):
    for i in range(0, len(s)-1):
        #print(s[i:i+2])
        if s[i:i+2] in badWords:
            return True
    return False


def contains_pair_dublet(s):
    for i in range(0, len(s)-3):
        p1 = s[i:i+2]
        for j in range(i+2, len(s)-1):
            if s[j:j+2] == p1:
                return True
    return False


def contains_skip_pair(s):
    for i in range(0, len(s)-2):
        p1 = s[i:i+3]
        if  p1[0] == p1[2]:
            return True
    return False

# part ichi
good = 0
for s in content:
    if (count_vowels(s) >= 3) and (contains_duplet(s)) and (not contains_bad(s)):
        good += 1


print(good)

# part ni
good = 0
for s in content:
    if (contains_pair_dublet(s)) and (contains_skip_pair(s)):
        good += 1


print(good)