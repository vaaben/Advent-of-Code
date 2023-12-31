import sys
import heapq


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def wrap(self, x):
        return (x - 1) % 9 + 1

    def process(self, content, n):
        _risk = [list(map(int, line)) for line in content]

        risk = [[0] * len(row) * n for row in _risk * n]

        r = len(_risk)
        c = len(_risk[0])

        for i in range(len(risk)):
            for j in range(len(risk[i])):
                risk[i][j] = self.wrap(_risk[i % r][j % c] + i // r + j // c)

        paths = [(0, 0, 0)]

        vis = [[0] * len(row) for row in risk]

        while True:
            rf, x, y = heapq.heappop(paths)
            if vis[x][y]:
                continue
            if (x, y) == (len(risk) - 1, len(risk[x]) - 1):
                return rf
            vis[x][y] = 1
            for nx, ny in [(x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1)]:
                if not len(risk) > nx >= 0 <= ny < len(risk[0]):
                    continue
                if vis[nx][ny]:
                    continue
                heapq.heappush(paths, (rf + risk[nx][ny], nx, ny))

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 5)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
