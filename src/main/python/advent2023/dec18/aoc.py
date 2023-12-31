import sys
import re

class AoC:

    inst_exp = re.compile('(\w) (\d+) \(#\w+\)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        points = [(0, 0)]
        dirs = {"U": (-1, 0), "D": (1, 0), "L": (0, -1), "R": (0, 1)}

        b = 0

        for line in content:
            d, n, _ = line.split()
            dr, dc = dirs[d]
            n = int(n)
            b += n
            r, c = points[-1]
            points.append((r + dr * n, c + dc * n))

        A = abs(sum(points[i][0] * (points[i - 1][1] - points[(i + 1) % len(points)][1]) for i in range(len(points)))) // 2
        i = A - b // 2 + 1

        return i + b

    def process_2(self, content):
        points = [(0, 0)]
        dirs = {"U": (-1, 0), "D": (1, 0), "L": (0, -1), "R": (0, 1)}

        b = 0

        for line in content:
            _, _, x = line.split()
            x = x[2:-1]
            dr, dc = dirs["RDLU"[int(x[-1])]]
            n = int(x[:-1], 16)
            b += n
            r, c = points[-1]
            points.append((r + dr * n, c + dc * n))

        A = abs(sum(points[i][0] * (points[i - 1][1] - points[(i + 1) % len(points)][1]) for i in range(len(points)))) // 2
        i = A - b // 2 + 1

        return i + b

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
