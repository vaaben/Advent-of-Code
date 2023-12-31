import sys
from itertools import combinations


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, part):
        containers = [int(x) for x in content]

        solutions = []
        for i in range(len(containers)):
            for c in combinations(containers, i):
                if sum(c) == 150:
                    solutions.append(c)

        if part == 1:
            return len(solutions)
        else:
            min_containers = len(solutions[0])
            count = 0
            while len(solutions[count]) == min_containers:
                count += 1

            return count

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
