import sys
import re


class Mover:

    position = (0, 0)

    movement = {'N': (0, 1),
                'E': (1, 0),
                'S': (0, -1),
                'W': (-1, 0)}

    rotation = {('N', 'R'): 'E',
                ('E', 'R'): 'S',
                ('S', 'R'): 'W',
                ('W', 'R'): 'N',
                ('N', 'L'): 'W',
                ('E', 'L'): 'N',
                ('S', 'L'): 'E',
                ('W', 'L'): 'S'}

    heading = 'N'

    def update(self, d, s):
        self.heading = self.rotation[(self.heading, d)]
        m = self.movement[self.heading]
        self.position = (self.position[0] + m[0] * s, self.position[1] + m[1] * s)

    def update_visited(self, d, s):
        self.heading = self.rotation[(self.heading, d)]
        m = self.movement[self.heading]
        visited = []
        for i in range(s):
            visited.append((self.position[0] + m[0] * i, self.position[1] + m[1] * i))
        self.position = (self.position[0] + m[0] * s, self.position[1] + m[1] * s)
        return visited


class AoC:

    regexp = re.compile('(L|R)(\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content][0]

    def process(self, content):
        m = Mover()
        for (a, b) in self.regexp.findall(content):
            m.update(a, int(b))

        return abs(m.position[0]) + abs(m.position[1])

    def process_2(self, content):
        m = Mover()
        visited = set()
        crossing = None
        for (a, b) in self.regexp.findall(content):
            new_pos = m.update_visited(a, int(b))
            for p in new_pos:
                if p in visited and crossing is None:
                    crossing = p
                else:
                    visited.add(p)

        return abs(crossing[0]) + abs(crossing[1])

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
