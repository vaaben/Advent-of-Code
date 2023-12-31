import sys
import re

class AoC:

    num_exp = re.compile('-?\d+')

    x_min = x_max = y_min = y_max = 0

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def in_target(self, p):
        return self.x_min <= p[0] <= self.x_max and self.y_min <= p[1] <= self.y_max

    def process(self, content):
        self.x_min, self.x_max, self.y_min, self.y_max = map(int, self.num_exp.findall(content[0]))

        solutions = []
        for xv in range(1, 100):
            for yv in range(100):
                p = (0, 0)
                s = (xv, yv)
                max_height = 0
                while p[0] <= self.x_max and p[1] >= self.y_min:
                    p = (p[0] + s[0], p[1] + s[1])
                    s = (max(0, s[0]-1), s[1]-1)
                    max_height = max(max_height, p[1])
                    if self.in_target(p):
                        solutions.append((xv, yv, max_height))

        return max(solutions, key=lambda x: x[2])

    def process_2(self, content):
        self.x_min, self.x_max, self.y_min, self.y_max = map(int, self.num_exp.findall(content[0]))

        solutions = set()
        for xv in range(1, 1000):
            for yv in range(-1000, 1000):
                p = (0, 0)
                s = (xv, yv)
                max_height = 0
                while p[0] <= self.x_max and p[1] >= self.y_min:
                    p = (p[0] + s[0], p[1] + s[1])
                    s = (max(0, s[0]-1), s[1]-1)
                    max_height = max(max_height, p[1])
                    if self.in_target(p):
                        solutions.add((xv, yv))

        print(solutions)

        return len(solutions)

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
