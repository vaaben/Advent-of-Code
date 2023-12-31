import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, f):
        expand_rows = []
        expand_cols = set()

        for c, char in enumerate(content[0]):
            expand_cols.add(c)

        for r, row in enumerate(content):
            if row.count('.') == len(row):
                expand_rows.append(r)
            else:
                for c, char in enumerate(row):
                    if char == '#' and c in expand_cols:
                        expand_cols.remove(c)

        expand_cols = sorted(list(expand_cols))
        print(expand_rows)
        print(expand_cols)

        galaxies = set()
        delta_r = 0
        for r, row in enumerate(content):
            delta_c = 0
            if r in expand_rows:
                delta_r += f-1
            else:
                for c, char in enumerate(row):
                    if c in expand_cols:
                        delta_c += f-1
                    if char == '#':
                        galaxies.add((r + delta_r, c + delta_c))

        sum = 0
        galaxies = list(galaxies)
        for i, a in enumerate(galaxies):
            for b in galaxies[i:]:
                sum += abs(b[0]-a[0]) + abs(b[1]-a[1])

        return sum

    def part1(self, filename):
        return self.process(self.read_input(filename), 2)

    def part2(self, filename):
        return self.process(self.read_input(filename), 1000000)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
