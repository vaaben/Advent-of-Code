import sys
import hashlib
from collections import deque

class AoC:

    vault = (3, 3)

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, part):
        solutions = []
        salt = content[0]

        que = deque([((0, 0), '')])
        while que:
            pos, path = que.popleft()

            if pos == self.vault:
                solutions.append(path)
                continue

            u, d, l, r = hashlib.md5((salt+path).encode()).hexdigest()[:4]

            if u > 'a' and pos[1] > 0:
                que.append(((pos[0], pos[1]-1), path+'U'))
            if d > 'a' and pos[1] < 3:
                que.append(((pos[0], pos[1]+1), path+'D'))
            if l > 'a' and pos[0] > 0:
                que.append(((pos[0]-1, pos[1]), path+'L'))
            if r > 'a' and pos[0] < 3:
                que.append(((pos[0]+1, pos[1]), path+'R'))

        if part == 1:
            return solutions[0]
        else:
            return len(solutions[-1])

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 2)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
