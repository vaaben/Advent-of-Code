import sys
from collections import deque

class AoC:

    grid = []

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def fill(self, sr, sc, ss):
        ans = set()
        seen = {(sr, sc)}
        q = deque([(sr, sc, ss)])

        while q:
            r, c, s = q.popleft()

            if s % 2 == 0:
                ans.add((r, c))
            if s == 0:
                continue

            for nr, nc in [(r + 1, c), (r - 1, c), (r, c + 1), (r, c - 1)]:
                if (nr < 0 or nr >= len(self.grid) or
                        nc < 0 or nc >= len(self.grid[0]) or
                        self.grid[nr][nc] == "#" or (nr, nc) in seen):
                    continue
                seen.add((nr, nc))
                q.append((nr, nc, s - 1))

        return len(ans)

    def process(self, content, steps, part):
        for i, line in enumerate(content):
            self.grid.append([*line])
            j = line.find('S')
            if j > -1:
                start_r = i
                start_c = j

        size = len(self.grid)

        if part == 1:
            que = deque([(start_r, start_c)])
            while steps > 0:
                next_steps = set()
                while que:
                    p_r, p_c = que.popleft()
                    for d_r, d_c in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                        new_r = p_r + d_r
                        new_c = p_c + d_c
                        if self.grid[new_r][new_c] != '#':
                            next_steps.add((new_r, new_c))
                que = deque(list(next_steps))
                steps -= 1

            return len(que)
        else:
            grid_width = steps // size - 1

            odd = (grid_width // 2 * 2 + 1) ** 2
            even = ((grid_width + 1) // 2 * 2) ** 2

            # fill from start pos
            odd_points = self.fill(start_r, start_c, size * 2 + 1)
            even_points = self.fill(start_r, start_c, size * 2)

            # fill grid from side t(op), r(ight)...
            corner_t = self.fill(size - 1, start_c, size - 1)
            corner_r = self.fill(start_r, 0, size - 1)
            corner_b = self.fill(0, start_c, size - 1)
            corner_l = self.fill(start_r, size - 1, size - 1)

            # fill grid from corner
            small_tr = self.fill(size - 1, 0, size // 2 - 1)
            small_tl = self.fill(size - 1, size - 1, size // 2 - 1)
            small_br = self.fill(0, 0, size // 2 - 1)
            small_bl = self.fill(0, size - 1, size // 2 - 1)

            large_tr = self.fill(size - 1, 0, size * 3 // 2 - 1)
            large_tl = self.fill(size - 1, size - 1, size * 3 // 2 - 1)
            large_br = self.fill(0, 0, size * 3 // 2 - 1)
            large_bl = self.fill(0, size - 1, size * 3 // 2 - 1)

            return (
                odd * odd_points +
                even * even_points +
                corner_t + corner_r + corner_b + corner_l +
                (grid_width + 1) * (small_tr + small_tl + small_br + small_bl) +
                grid_width * (large_tr + large_tl + large_br + large_bl)
            )

    def part1(self, filename):
        return self.process(self.read_input(filename), 64, 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 26501365, 2)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
