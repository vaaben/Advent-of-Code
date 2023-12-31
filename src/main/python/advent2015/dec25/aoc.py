import sys


class AoC:

    FIRST = 20151125

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]


    def find_exp(self, r, c):
        return sum(range(r+c-1)) + c - 1

    def solve(self, base, exp, mod):
        res = 1
        while exp:
            if exp % 2: res = res * base % mod
            exp //= 2
            base = base**2 % mod
        return res * self.FIRST % mod

    def process(self, content):
        BASE = 252533
        MOD = 33554393

        ROW = 2978
        COL = 3083

        return self.solve(BASE, self.find_exp(ROW, COL), MOD)

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
