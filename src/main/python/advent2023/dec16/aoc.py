import sys
from collections import deque

class AoC:

    contraption = []
    max_row = 0
    max_col = 0

    dir_map = {'r': (0, 1),
               'd': (1, 0),
               'l': (0, -1),
               'u': (-1, 0)}

    dir_change = {('r', '\\'): ['d'],
                  ('l', '\\'): ['u'],
                  ('u', '\\'): ['l'],
                  ('d', '\\'): ['r'],
                  ('r', '/'): ['u'],
                  ('l', '/'): ['d'],
                  ('u', '/'): ['r'],
                  ('d', '/'): ['l'],
                  ('r', '|'): ['u', 'd'],
                  ('l', '|'): ['u', 'd'],
                  ('d', '-'): ['l', 'r'],
                  ('u', '-'): ['l', 'r']}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def is_valid(self, p):
        return 0 <= p[0] < self.max_row and 0 <= p[1] < self.max_col

    def update_pos(self, p, direction):
        return (p[0] + self.dir_map[direction][0], p[1] + self.dir_map[direction][1])

    def process(self, content):
        self.contraption = [[*x] for x in content]
        self.max_row = len(self.contraption)
        self.max_col = len(self.contraption[0])

        return self.solve(((0, 0), 'r'))

    def process_2(self, content):
        self.contraption = [[*x] for x in content]
        self.max_row = len(self.contraption)
        self.max_col = len(self.contraption[0])

        start_pos = ([((0, i), 'd') for i in range(self.max_col)]
                     + [((self.max_row-1, i), 'u') for i in range(self.max_col)]
                     + [((i, 0), 'r') for i in range(self.max_row)]
                     + [((i, self.max_col-1), 'l') for i in range(self.max_row)])

        return max(map(self.solve, start_pos))

    def solve(self, start_pos):
        seen = set()
        que = deque([start_pos])
        while que:
            pos, direction = que.popleft()
            if (pos, direction) in seen:
                continue

            seen.add((pos, direction))

            if (direction, self.contraption[pos[0]][pos[1]]) in self.dir_change:
                new_dir = self.dir_change[(direction, self.contraption[pos[0]][pos[1]])]
            else:
                new_dir = [direction]

            for d in new_dir:
                new_pos = self.update_pos(pos, d)
                if self.is_valid(new_pos):
                    que.append((new_pos, d))

        visited = set(map(lambda x: x[0], seen))

        return len(visited)


    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 8390 --  too high
    else:
        print(f'sum: {AoC().part2(args[1])}')
