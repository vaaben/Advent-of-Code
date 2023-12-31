import copy
import sys


class AoC:

    grid = []
    rows = 0
    cols = 0

    seen = {}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def print_grid(self):
        for r in self.grid:
            print(''.join(r))

    def count_adj(self, row, col, typ):
        count = 0
        for t in [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)]:
            t_r = row + t[0]
            t_c = col + t[1]
            if 0 <= t_c < self.cols and 0 <= t_r < self.rows and self.grid[t_r][t_c] == typ:
                count += 1
        return count

    def count(self, typ):
        sum = 0
        for r in range(self.rows):
            for c in range(self.cols):
                if self.grid[r][c] == typ:
                    sum += 1
        return sum

    def to_key(self, grid):
        key = ''
        for line in grid:
            key += ''.join(line)
        return key

    def evolve(self, i):
        new_grid = []
        for r in range(self.rows):
            line = []
            for c in range(self.cols):
                a = self.grid[r][c]
                if a == '.' and self.count_adj(r, c, '|') >= 3:
                    line.append('|')
                elif a == '|' and self.count_adj(r, c, '#') >= 3:
                    line.append('#')
                elif a == '#' and (self.count_adj(r, c, '#') == 0 or self.count_adj(r, c, '|') == 0):
                    line.append('.')
                else:
                    line.append(a)
            new_grid.append(line)
        key = self.to_key(new_grid)
        self.grid = new_grid

        if key in self.seen:
            return self.seen[key]
        self.seen[key] = i

    def process(self, content, n):
        self.grid = [list(x) for x in content]
        self.rows = len(self.grid)
        self.cols = len(self.grid[0])
        for i in range(n):
            flaf = self.evolve(i)
            if flaf:
                break

        if i != n-1:
            # calculate from i,n and flaf
            print(f'{i}, {flaf}')
            period = i - flaf
            remaining = (n - flaf) % period - 1

            for j in range(remaining):
                self.evolve(j)
                #print(self.count('|') * self.count('#'))

        return self.count('|') * self.count('#')

    def part1(self, filename):
        return self.process(self.read_input(filename), 10)

    def part2(self, filename):
        return self.process(self.read_input(filename), 1000000000)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
        # 224553 - too high
        # 208603 - too high
        # 201348 - correct
