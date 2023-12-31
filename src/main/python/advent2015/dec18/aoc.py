import sys


class AoC:

    grid = []
    rows = 0
    cols = 0

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def count_neighbours(self, r, c, typ):
        count = 0
        for dr, dc in [(-1, -1), (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1)]:
            nr = r + dr
            nc = c + dc
            if 0 <= nr < self.rows and 0 <= nc < self.cols and self.grid[nr][nc] == typ:
                count += 1
        return count

    def count_on(self):
        count = 0
        for r in range(self.rows):
            for c in range(self.cols):
                if self.grid[r][c] == '#':
                    count += 1
        return count

    def fix_corners(self):
        self.grid[0][0] = '#'
        self.grid[self.rows-1][0] = '#'
        self.grid[0][self.cols-1] = '#'
        self.grid[self.rows-1][self.cols-1] = '#'

    def evolve(self):
        new_grid = []
        for r in range(self.rows):
            new_row = []
            for c in range(self.cols):
                if self.grid[r][c] == '#':
                    # on -> ?
                    if 2 <= self.count_neighbours(r, c, '#') <= 3:
                        new_row.append('#')
                    else:
                        new_row.append('.')
                else:
                    if self.count_neighbours(r, c, '#') == 3:
                        new_row.append('#')
                    else:
                        new_row.append('.')
            new_grid.append(new_row)

        self.grid = new_grid

    def print_grid(self):
        for l in self.grid:
            print(''.join(l))

    def process(self, content, n, part):
        self.grid = [list(x) for x in content]
        self.cols = len(self.grid[0])
        self.rows = len(self.grid)

        if part == 1:
            for i in range(n):
                self.evolve()

            return self.count_on()
        else:
            self.fix_corners()
            for i in range(n):
                self.evolve()
                self.fix_corners()

            return self.count_on()

    def part1(self, filename):
        return self.process(self.read_input(filename), 100, 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 100, 2)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
