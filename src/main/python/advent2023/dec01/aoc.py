import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        sum = 0
        for line in content:
            a, b = None, None
            for i in line:
                if i.isdigit():
                    if a is None:
                        a = int(i)
                        b = a
                    else:
                        b = int(i)
            sum += 10*a + b

        return sum

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):

        # replace with pre- and postfix to fix eightwo -> 82
        replace_map = {'one': 'o1e', 'two': 't2o', 'three': 't3e', 'four': 'f4r', 'five': 'f5e',
                       'six': 's6x', 'seven': 's7n', 'eight': 'e8t', 'nine': 'n9e'}

        content = []
        for line in self.read_input(filename):
            for r in replace_map.items():
                line = line.replace(r[0], r[1])
            content.append(line)

        return self.process(content)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
