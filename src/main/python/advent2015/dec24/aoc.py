import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def solve(self, data, groups):
        def aux(presents, score, used, qe):
            if score == goal:
                possible_solutions.add((used, qe))
            elif score < goal and presents and used < 6:
                aux(presents[1:], score, used, qe)
                aux(presents[1:], score+presents[0], used+1, qe*presents[0])

        possible_solutions = set()
        goal = sum(data) // groups
        aux(data, 0, 0, 1)
        return min(possible_solutions)[1]

    def process(self, content, part):
        data = sorted(map(int, content), reverse=True)

        if part == 1:
            print(self.solve(data, 3))
        else:
            print(self.solve(data, 4))

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