from dataclasses import dataclass

# Represents a node in a singly linked list.
@dataclass
class Node:
    value: int


def move(current, max_value, index):
    remove = [current.right,
              current.right.right,
              current.right.right.right]
    removed_vals = [c.value for c in remove]
    current.right = remove[-1].right
    destination = current.value - 1
    while destination < 1 or destination in removed_vals:
        destination -= 1
        if destination < 1:
            destination = max_value
    node = index[destination]
    remove[-1].right = node.right
    node.right = remove[0]
    return current.right


def solve(cups, iterations):
    index = dict(zip(cups, map(Node, cups)))
    for c1, c2 in zip(cups, cups[1:] + [cups[0]]):
        index[c1].right = index[c2]
    current = index[cups[0]]
    max_value = len(cups)
    for _ in range(iterations):
        current = move(current, max_value, index)
    return index[1]


# Part one
cups = list(map(int, '925176834'))
node = solve(cups, 100)
for _ in range(8):
    node = node.right
    print(node.value, end='')
print()

# Part two
cups += range(10, 1_000_001)
node = solve(cups, 10_000_000)
print(node.right.value * node.right.right.value)