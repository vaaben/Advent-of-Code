import sys
from collections import deque

class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, grid):
        for sr, row in enumerate(grid):
            sc = row.find('S')
            if sc > -1:
                break

        loop = {(sr, sc)}
        queue = deque([(sr, sc)])

        while queue:
            r, c = queue.popleft()
            ch = grid[r][c]

            if r > 0 and ch in "S|JL" and grid[r - 1][c] in "|7F" and (r - 1, c) not in loop:
                loop.add((r - 1, c))
                queue.append((r - 1, c))

            if r < len(grid) - 1 and ch in "S|7F" and grid[r + 1][c] in "|JL" and (r + 1, c) not in loop:
                loop.add((r + 1, c))
                queue.append((r + 1, c))

            if c > 0 and ch in "S-J7" and grid[r][c - 1] in "-LF" and (r, c - 1) not in loop:
                loop.add((r, c - 1))
                queue.append((r, c - 1))

            if c < len(grid[r]) - 1 and ch in "S-LF" and grid[r][c + 1] in "-J7" and (r, c + 1) not in loop:
                loop.add((r, c + 1))
                queue.append((r, c + 1))

        return len(loop) // 2

    def process_2(self, grid):
        for sr, row in enumerate(grid):
            sc = row.find('S')
            if sc > -1:
                break

        loop = {(sr, sc)}
        queue = deque([(sr, sc)])

        maybe_s = {"|", "-", "J", "L", "7", "F"}

        while queue:
            r, c = queue.popleft()
            ch = grid[r][c]

            if r > 0 and ch in "S|JL" and grid[r - 1][c] in "|7F" and (r - 1, c) not in loop:
                loop.add((r - 1, c))
                queue.append((r - 1, c))
                if ch == "S":
                    maybe_s &= {"|", "J", "L"}

            if r < len(grid) - 1 and ch in "S|7F" and grid[r + 1][c] in "|JL" and (r + 1, c) not in loop:
                loop.add((r + 1, c))
                queue.append((r + 1, c))
                if ch == "S":
                    maybe_s &= {"|", "7", "F"}

            if c > 0 and ch in "S-J7" and grid[r][c - 1] in "-LF" and (r, c - 1) not in loop:
                loop.add((r, c - 1))
                queue.append((r, c - 1))
                if ch == "S":
                    maybe_s &= {"-", "J", "7"}

            if c < len(grid[r]) - 1 and ch in "S-LF" and grid[r][c + 1] in "-J7" and (r, c + 1) not in loop:
                loop.add((r, c + 1))
                queue.append((r, c + 1))
                if ch == "S":
                    maybe_s &= {"-", "L", "F"}

        (S,) = maybe_s

        grid = [row.replace("S", S) for row in grid]
        grid = ["".join(ch if (r, c) in loop else "." for c, ch in enumerate(row)) for r, row in enumerate(grid)]

        outside = set()

        for r, row in enumerate(grid):
            within = False
            up = None
            for c, ch in enumerate(row):
                if ch == "|":
                    assert up is None
                    within = not within
                elif ch == "-":
                    assert up is not None
                elif ch in "LF":
                    assert up is None
                    up = ch == "L"
                elif ch in "7J":
                    assert up is not None
                    if ch != ("J" if up else "7"):
                        within = not within
                    up = None
                elif ch == ".":
                    pass
                else:
                    raise RuntimeError(f"unexpected character (horizontal): {ch}")
                if not within:
                    outside.add((r, c))

        return len(grid) * len(grid[0]) - len(outside | loop)

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
