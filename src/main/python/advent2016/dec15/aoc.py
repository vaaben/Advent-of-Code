import sys
import re

class AoC:

    disc_exp = re.compile('Disc #(\d+) has (\d+) positions; at time=(\d+), it is at position (\d+).')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, extra=()):
        discs = {}
        for line in content:
            g = self.disc_exp.match(line)
            if g:
                discs[int(g.group(1))] = (int(g.group(2)), int(g.group(4)))

        discs[max(discs)+1] = extra

        t = 0
        while True:
            sum = 0
            for d in discs:
                sum += (t+d+discs[d][1]) % discs[d][0]
            if sum == 0:
                return t
            t += 1

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process(self.read_input(filename), (11, 0))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
