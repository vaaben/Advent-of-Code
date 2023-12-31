"""
--- Day 7: Some Assembly Required ---
This year, Santa brought little Bobby Tables a set of wires and bitwise
logic gates! Unfortunately, little Bobby is a little under the recommended
age range, and he needs help assembling the circuit.

Each wire has an identifier (some lowercase letters) and can carry a 16-bit
signal (a number from 0 to 65535). A signal is provided to each wire by a gate,
another wire, or some specific value.
Each wire can only get a signal from one source, but can provide its signal to
multiple destinations.
A gate provides no signal until all of its inputs have a signal.

The included instructions booklet describes how to connect the parts together:
x AND y -> z means to connect wires x and y to an AND gate, and then
connect its output to wire z.

For example:

123 -> x means that the signal 123 is provided to wire x.
x AND y -> z means that the bitwise AND of wire x and wire y is provided
to wire z.
p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and
then provided to wire q.
NOT e -> f means that the bitwise complement of the value from wire e is
provided to wire f.
Other possible gates include OR (bitwise OR) and RSHIFT (right-shift).
If, for some reason, you'd like to emulate the circuit instead, almost all
programming languages (for example, C, JavaScript, or Python) provide
operators for these gates.

For example, here is a simple circuit:

123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i
After it is run, these are the signals on the wires:

d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
In little Bobby's kit's instructions booklet (provided as your puzzle input),
what signal is ultimately provided to wire a?

Your puzzle answer was 956.

--- Part Two ---
Now, take the signal you got on wire a, override wire b to that signal,
and reset the other wires (including wire a).
What new signal is ultimately provided to wire a?

Your puzzle answer was 40149.
"""

# part zero
import re

with open("data/dec7.txt") as f:
    content = [x.strip() for x in f.readlines()]


index = {l.split()[-1]: l for l in content}


# part ichi
vals = {}


def f(r):
    if r in vals:
        return vals[r]
    l = index[r]
    w = l.split()
    if w[1] == '->':
        ret = int(w[0]) if w[0].isdigit() else f(w[0])
    elif w[0] == 'NOT':
        ret = ~f(w[1])
    elif w[1] == 'AND':
        v1 = int(w[0]) if w[0].isdigit() else f(w[0])
        ret = v1 & f(w[2])
    elif w[1] == 'OR':
        ret = f(w[0]) | f(w[2])
    elif w[1] == 'RSHIFT':
        ret = f(w[0]) >> int(w[2])
    elif w[1] == 'LSHIFT':
        ret = f(w[0]) << int(w[2])
    vals[r] = ret
    return ret


result = f('a')
print(result)


# part ni
vals = {}
def f(r):
    if r == 'b':
        return result
    if r in vals:
        return vals[r]
    l = index[r]
    w = l.split()
    if w[1] == '->':
        ret = int(w[0]) if w[0].isdigit() else f(w[0])
    elif w[0] == 'NOT':
        ret = ~f(w[1])
    elif w[1] == 'AND':
        v1 = int(w[0]) if w[0].isdigit() else f(w[0])
        ret = v1 & f(w[2])
    elif w[1] == 'OR':
        ret = f(w[0]) | f(w[2])
    elif w[1] == 'RSHIFT':
        ret = f(w[0]) >> int(w[2])
    elif w[1] == 'LSHIFT':
        ret = f(w[0]) << int(w[2])
    vals[r] = ret
    return ret


print(f('a'))
