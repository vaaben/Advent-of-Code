import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        return self.parse(eval(content[0]))

    def parse(self, x):
        if x is None:
            return 0
        elif isinstance(x, int):
            return x
        elif isinstance(x, list):
            return sum([self.parse(y) for y in x])
        elif isinstance(x, dict):
            return sum([self.parse(y) for y in x.values()])
        else:
            return 0

    def process_2(self, content):
        return self.parse_2(eval(content[0]))

    def parse_2(self, x):
        if x is None:
            return 0
        elif isinstance(x, int):
            return x
        elif isinstance(x, list):
            return sum([self.parse_2(y) for y in x])
        elif isinstance(x, dict):
            if 'red' not in x.values():
                return sum([self.parse_2(y) for y in x.values()])
            else:
                return 0
        else:
            return 0

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
