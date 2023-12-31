import sys


class AoC:

    weapons = [(8, 4, 0), (10, 5, 0), (25, 6, 0), (40, 7, 0), (74, 8, 0)]
    armors = [(0, 0, 0), (13, 0, 1), (31, 0, 2), (53, 0, 3), (75, 0, 4), (102, 0, 5)]
    rings = [(0, 0, 0), (0, 0, 0), (25, 1, 0), (50, 2, 0), (100, 3, 0), (20, 0, 1), (40, 0, 2), (80, 0, 3)]

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]


    def calc_score(self, w, a, r1, r2):
        cost = w[0] + a[0] + r1[0] + r2[0]
        att = w[1] + a[1] + r1[1] + r2[1]
        deff = w[2] + a[2] + r1[2] + r2[2]
        return (cost, att, deff)

    def would_win(self, opponent, att, deff):
        return max(att - opponent[2], 1) >= max(opponent[1] - deff, 1)

    def solve(self, opponent, part):
        min_cost = 9999
        max_cost = 0
        for w in self.weapons:
            for a in self.armors:
                for i, r1 in enumerate(self.rings):
                    for r2 in self.rings[i+1:]:
                        cost, att, deff = self.calc_score(w, a, r1, r2)
                        if self.would_win(opponent, att, deff):
                            min_cost = min(cost, min_cost)
                        else:
                            max_cost = max(cost, max_cost)
        if part == 1:
            return min_cost
        else:
            return max_cost

    def process(self, content, part):
        opponent = tuple(map(lambda x: int(x.split(': ')[1]), content))

        return self.solve(opponent, part)

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
