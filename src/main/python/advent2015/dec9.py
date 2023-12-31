"""
--- Day 9: All in a Single Night ---
Every year, Santa manages to deliver all of his presents in a single night.

This year, however, he has some new locations to visit; his elves have provided
him the distances between every pair of locations.
He can start and end at any two (different) locations he wants, but he must
visit each location exactly once.
What is the shortest distance he can travel to achieve this?

For example, given the following distances:

London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
The possible routes are therefore:

Dublin -> London -> Belfast = 982
London -> Dublin -> Belfast = 605
London -> Belfast -> Dublin = 659
Dublin -> Belfast -> London = 659
Belfast -> Dublin -> London = 605
Belfast -> London -> Dublin = 982
The shortest of these is London -> Dublin -> Belfast = 605, and so the answer
is 605 in this example.

What is the distance of the shortest route?

--- Part Two ---
The next year, just to show off, Santa decides to take the route with the longest distance instead.

He can still start and end at any two (different) locations he wants, and he still must visit each location exactly once.

For example, given the distances above, the longest route would be 982 via (for example) Dublin -> London -> Belfast.

What is the distance of the longest route?

"""

# part zero
import re
import itertools
import sys
with open("data/dec9.txt") as f:
    input = [x.strip() for x in f.readlines()]


def parseLine(line):
    m = re.search("^(.*) to (.*) = (\\d+)$", line)
    return m.group(1), m.group(2), m.group(3)


def route_length(r, d_map):
    d = 0
    for t in range(0, len(r)-1):
        d += d_map[(r[t], r[t+1])]
    return d


# part one
dist_map = dict()
cities = set()
for x in input:
    a, b, dist = parseLine(x)
    cities.add(a)
    cities.add(b)
    dist_map[(a, b)] = dist_map[(b, a)] = int(dist)

shortest_route = sys.maxsize
route = set()
for p in list(itertools.permutations(cities)):
    d = route_length(p, dist_map)
    if d < shortest_route:
        shortest_route = d
        route = p

print(route)
print(shortest_route)


# part two
longest_route = 0
route = set()
for p in list(itertools.permutations(cities)):
    d = route_length(p, dist_map)
    if d > longest_route:
        longest_route = d
        route = p


print(route)
print(longest_route)
