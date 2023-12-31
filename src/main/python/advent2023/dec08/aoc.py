import sys
import re
from math import lcm

class Node:

    def __init__(self, name, l, r):
        self._name = name
        self._left = l
        self._right = r


class AoC:

    node_exp = re.compile('(\w+) = \((\w+), (\w+)\)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        lr_instr = content[0]
        steps = 0
        nodes = {}
        for i in range(2, len(content)):
            m = self.node_exp.match(content[i])
            if len(m.groups()) == 3:
                name, l, r = m.groups()[0:]
                nodes[name] = Node(name, l, r)

        current_node = 'AAA'
        while current_node != 'ZZZ':
            inst = lr_instr[steps % len(lr_instr)]
            if inst == 'L':
                current_node = nodes[current_node]._left
            else:
                current_node = nodes[current_node]._right
            steps += 1

        return steps

    def process_2(self, content):
        lr_instr = content[0]
        steps = []
        nodes = {}
        for i in range(2, len(content)):
            m = self.node_exp.match(content[i])
            if len(m.groups()) == 3:
                name, l, r = m.groups()[0:]
                nodes[name] = Node(name, l, r)

        current_node = list(filter(lambda x: x[-1] == 'A', nodes))
        for i in range(len(current_node)):
        #while any(filter(lambda x: x[-1] != 'Z', current_node)):
            step = 0
            while current_node[i][-1] != 'Z':
                inst = lr_instr[step % len(lr_instr)]
                if inst == 'L':
                    current_node[i] = nodes[current_node[i]]._left
                else:
                    current_node[i] = nodes[current_node[i]]._right
                step += 1
            steps.append(step)

        flaf = lcm(*steps)

        return flaf

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'steps: {AoC().part1(args[1])}')
    else:
        print(f'steps: {AoC().part2(args[1])}')
