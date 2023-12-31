import sys
from heapq import heappush, heappop


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def update_dir(self, d, nd):
        if d[0] == nd:
            return (d[0], d[1]+1)
        else:
            return (nd, 0)

    def process(self, content):
        grid = [list(map(int, line.strip())) for line in content]

        seen = set()
        # heat loss, row, col, dir_row, dir_col, conseq steps
        pq = [(0, 0, 0, 0, 0, 0)]

        while pq:
            hl, r, c, dr, dc, n = heappop(pq)

            if r == len(grid) - 1 and c == len(grid[0]) - 1:
                return hl

            if (r, c, dr, dc, n) in seen:
                continue

            seen.add((r, c, dr, dc, n))

            if n < 3 and (dr, dc) != (0, 0):
                nr = r + dr
                nc = c + dc
                if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]):
                    heappush(pq, (hl + grid[nr][nc], nr, nc, dr, dc, n + 1))

            for ndr, ndc in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
                if (ndr, ndc) != (dr, dc) and (ndr, ndc) != (-dr, -dc):
                    nr = r + ndr
                    nc = c + ndc
                    if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]):
                        heappush(pq, (hl + grid[nr][nc], nr, nc, ndr, ndc, 1))

    def process_2(self, content):
        grid = [list(map(int, line.strip())) for line in content]

        seen = set()
        pq = [(0, 0, 0, 0, 0, 0)]

        while pq:
            hl, r, c, dr, dc, n = heappop(pq)

            if r == len(grid) - 1 and c == len(grid[0]) - 1 and n >= 4:
                return hl

            if (r, c, dr, dc, n) in seen:
                continue

            seen.add((r, c, dr, dc, n))

            if n < 10 and (dr, dc) != (0, 0):
                nr = r + dr
                nc = c + dc
                if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]):
                    heappush(pq, (hl + grid[nr][nc], nr, nc, dr, dc, n + 1))

            if n >= 4 or (dr, dc) == (0, 0):
                for ndr, ndc in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
                    if (ndr, ndc) != (dr, dc) and (ndr, ndc) != (-dr, -dc):
                        nr = r + ndr
                        nc = c + ndc
                        if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]):
                            heappush(pq, (hl + grid[nr][nc], nr, nc, ndr, ndc, 1))

    def wrap(self, x):
        return (x - 1) % 9 + 1

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
