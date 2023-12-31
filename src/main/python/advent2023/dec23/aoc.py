import sys
from collections import deque

class AoC:

    grid = []
    rows = 0
    cols = 0

    longest = 0
    solution = []

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def print_path(self, solution):
        for r, l in enumerate(self.grid):
            line = ''
            for c, x in enumerate(l):
                if (r, c) in solution:
                    line += 'O'
                else:
                    line += x
            print(line)

    def go_deep(self, pr, pc, seen):
        if pr == self.rows-1:
            if len(seen) > self.longest:
                self.solution = seen
                self.longest = len(seen)
                print(self.longest)
        else:
            for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                new_pr = pr+dr
                new_pc = pc+dc
                if 0 <= new_pr < self.rows and 0 <= new_pc < self.cols and (new_pr, new_pc) not in seen and self.grid[new_pr][new_pc] != '#':
                    new_seen = set(seen)
                    new_seen.add((new_pr, new_pc))
                    self.go_deep(new_pr, new_pc, new_seen)


    def process(self, content, part):
        self.grid = [list(x) for x in content]
        self.rows = len(self.grid)
        self.cols = len(self.grid[0])

        if part == 1:
            que = deque([(0, 1, set())])

            solutions = []
            longest = 0

            while que:
                pr, pc, seen = que.popleft()

                if pr == self.rows-1:
                    if len(seen) > longest:
                        longest = len(seen)
                        solutions.append(seen)
                        print(longest)
                    continue
                elif part == 1 and self.grid[pr][pc] == '^':
                    if (pr-1, pc) in seen:
                        continue
                    seen.add((pr-1, pc))
                    que.append((pr-1, pc, seen))
                elif part == 1 and self.grid[pr][pc] == 'v':
                    if (pr+1, pc) in seen:
                        continue
                    seen.add((pr+1, pc))
                    que.append((pr+1, pc, seen))
                elif part == 1 and self.grid[pr][pc] == '<':
                    if (pr, pc-1) in seen:
                        continue
                    seen.add((pr, pc-1))
                    que.append((pr, pc-1, seen))
                elif part == 1 and self.grid[pr][pc] == '>':
                    if (pr, pc+1) in seen:
                        continue
                    seen.add((pr, pc+1))
                    que.append((pr, pc+1, seen))
                else:
                    for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                        new_pr = pr+dr
                        new_pc = pc+dc
                        if 0 <= new_pr < self.rows and 0 <= new_pc < self.cols and (new_pr, new_pc) not in seen and self.grid[new_pr][new_pc] != '#':
                            new_seen = set(seen)
                            new_seen.add((new_pr, new_pc))
                            que.append((new_pr, new_pc, new_seen))

            lon = max(solutions, key=len)

            # self.print_path(longest)

            return len(lon)
        else:
            self.go_deep(1,0, set())

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 2)


if __name__ == '__main__':

    sys.setrecursionlimit(10000)

    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
