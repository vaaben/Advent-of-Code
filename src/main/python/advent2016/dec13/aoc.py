import sys
from collections import deque


class AoC:

    cache = {}
    visited = set()

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def _repr(self, b):
        if b:
            return '.'
        else:
            return '#'

    def process(self, content):
        n = int(content[0])
        end_p = tuple(map(int, content[1].split(',')))
        print(end_p)

        return self.solve((1, 1), 0, end_p, n)

    def process_2(self, content):
        n = int(content[0])
        end_p = tuple(map(int, content[1].split(',')))
        print(end_p)

        return self.solve_2((1, 1), 0, end_p, n)

    # depth first approach may not give the shortest path
    def solve_df(self, p, step, end_p, f):
        if p == end_p:
            return step

        self.visited.add(p)

        next_move = filter(lambda x: x not in self.visited and self.is_open(x, f),
                           [(p[0]-1, p[1]),
                            (p[0]+1, p[1]),
                            (p[0], p[1]-1),
                            (p[0], p[1]+1)])
        flaf = list(map(lambda x: self.solve(x, step+1, end_p, f), next_move))
        print(flaf)
        return sum(flaf)

    def solve(self, start, step, end_p, f):
        que = deque([(start, step)])

        while que:
            p, steps = que.popleft()

            if p == end_p:
                return steps

            self.visited.add(p)

            next_move = filter(lambda x: x not in self.visited and self.is_open(x, f),
                               [(p[0]-1, p[1]),
                                (p[0]+1, p[1]),
                                (p[0], p[1]-1),
                                (p[0], p[1]+1)])
            for m in next_move:
                que.append((m, steps+1))

    def solve_2(self, start, step, end_p, f):
        que = deque([(start, step)])

        while que:
            p, steps = que.popleft()

            if steps > 50:
                return len(self.visited)

            self.visited.add(p)

            next_move = filter(lambda x: x not in self.visited and self.is_open(x, f),
                               [(p[0]-1, p[1]),
                                (p[0]+1, p[1]),
                                (p[0], p[1]-1),
                                (p[0], p[1]+1)])
            for m in next_move:
                que.append((m, steps+1))

    def is_open(self, p, f):
        if p in self.cache:
            return self.cache[p]

        res = False
        if p[0] >= 0 and p[1] >= 0:
            res = (f + p[0]*p[0] + 3*p[0] + 2*p[0]*p[1] + p[1] + p[1]*p[1]).bit_count() % 2 == 0
        self.cache[p] = res
        return res

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
