import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def is_trap(self, l, c, r):
        if l == c == '^' and r == '.':
            return True
        elif l == '.' and c == r == '^':
            return True
        elif l == '^' and c == r == '.':
            return True
        elif l == c == '.' and r == '^':
            return True
        else:
            return False

    def evolve(self, s):
        new_s = ''
        flaf = '.' + s + '.'
        for i in range(1, len(s)+1):
            if self.is_trap(flaf[i-1], flaf[i], flaf[i+1]):
                new_s += '^'
            else:
                new_s += '.'

        return new_s

    def process(self, content, n):
        state = content[0]

        print(state)
        safe = state.count('.')
        for i in range(n-1):
            state = self.evolve(state)
            safe += state.count('.')

        return safe

    def part1(self, filename):
        return self.process(self.read_input(filename), 40)

    def part2(self, filename):
        return self.process(self.read_input(filename), 400000)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
