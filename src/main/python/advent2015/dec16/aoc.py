import sys
import re


class AoC:

    pattern = re.compile(r"(\w+): (\d+)")

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, part):

        ticker = {
            "children": 3,
            "cats": 7,
            "samoyeds": 2,
            "pomeranians": 3,
            "akitas": 0,
            "vizslas": 0,
            "goldfish": 5,
            "trees": 3,
            "cars": 2,
            "perfumes": 1
        }

        parse_line = lambda line: [(n, int(v)) for n, v in re.findall(self.pattern, line)]

        check_1 = lambda name, val: ticker[name] == val
        check_2 = lambda name, val: (
            val > ticker[name] if name in ("cats", "trees") else
            val < ticker[name] if name in ("pomeranians", "goldfish") else
            val == ticker[name])

        solve = lambda data, func: next(i for i, line in enumerate(data, 1)
                                        if all(func(*p) for p in line))

        data = list(map(parse_line, content))

        if part == 1:
            return solve(data, check_1)
        else:
            return solve(data, check_2)

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 2)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
