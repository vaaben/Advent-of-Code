import sys
import re

class AoC:

    flaf = re.compile('(\d+) (red|green|blue)')

    max_color = {'red': 12,
           'green': 13,
           'blue': 14}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        sum = 0
        for l in content:
            g, c = l.split(':')
            game = int(g[5:])
            possible = True
            draws = c.strip().split(';')
            for d in draws:
                dice = d.strip().split(',')
                for x in dice:
                    color, num = self.flaf.match(x.strip()).group(2, 1)
                    possible &= self.max_color[color] >= int(num)
            if possible:
                sum += game
        return sum

    def process_part2(self, content):
        sum = 0
        for l in content:
            g, c = l.split(':')
            min_cubes = {'red': 0, 'green': 0, 'blue': 0}
            draws = c.strip().split(';')
            for d in draws:
                dice = d.strip().split(',')
                for x in dice:
                    color, num = self.flaf.match(x.strip()).group(2, 1)
                    min_cubes[color] = max(min_cubes[color], int(num))
            sum += min_cubes['red']*min_cubes['green']*min_cubes['blue']
        return sum

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_part2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
