import sys
import re


class Keypad:

    kp = [['0', '0', '1', '0', '0'],
          ['0', '2', '3', '4', '0'],
          ['5', '6', '7', '8', '9'],
          ['0', 'A', 'B', 'C', '0'],
          ['0', '0', 'D', '0', '0']]

    flaf = {'U': lambda p: (p[0], max(0, p[1]-1)),
            'D': lambda p: (p[0], min(4, p[1]+1)),
            'R': lambda p: (min(4, p[0]+1), p[1]),
            'L': lambda p: (max(0, p[0]-1), p[1])}

    def update(self, p, d):
        new_pos = self.flaf[d](p)
        if self.lookup(new_pos) != '0':
            return new_pos
        else:
            return p

    def lookup(self, p):
        return self.kp[p[1]][p[0]]


class AoC:

    reg_exp = re.compile('(U|D|L|R)')

    flaf = {'U': lambda p: (p[0], max(0, p[1]-1)),
            'D': lambda p: (p[0], min(2, p[1]+1)),
            'R': lambda p: (min(2, p[0]+1), p[1]),
            'L': lambda p: (max(0, p[0]-1), p[1])}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        code = []
        p = (1, 1)
        for line in content:
            for m in self.reg_exp.findall(line):
                p = self.flaf[m](p)
                #print(f'{m} -> {p}')
            #print(3*p[1]+p[0]+1)
            code.append(3*p[1]+p[0]+1)

        return code

    def process_2(self, content):
        code = []
        p = (0, 2)
        keypad = Keypad()
        for line in content:
            for m in self.reg_exp.findall(line):
                p = keypad.update(p, m)
                #print(f'{m} -> {p}')
            code.append(keypad.lookup(p))

        return code

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'code: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
