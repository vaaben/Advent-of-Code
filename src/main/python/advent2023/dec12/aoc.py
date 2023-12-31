import sys


class AoC:

    cache = {}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, n):
        sum = 0
        for line in content:
            pattern, grps = line.split(' ')
            groups = tuple([int(x.strip()) for x in grps.split(',')])
            pattern = '?'.join([pattern]*n)

            sum += self.solve('', groups * n, pattern)

        return sum

    def flaf(self, a, b):
        if a == '?':
            return True
        else:
            return a == b

    def matches(self, ref, test):
        possible = True
        for p in zip(ref, test):
            possible &= self.flaf(p[0], p[1])
        return possible

    def solve(self, prefix, groups, pattern):

        pattern_len = len(pattern)
        if len(groups) == 0:
            test = prefix + '.' * (pattern_len - len(prefix))
            if self.matches(pattern, test):
                #solutions.append(test)
                return 1
            else:
                return 0

        key = (pattern[len(prefix):], groups)
        if key in self.cache:
            return self.cache[key]

        count = 0
        max_pattern_start = pattern_len - len(prefix) - (sum(groups) + len(groups) - 1) + 1
        for i in range(max_pattern_start):
            test = prefix + '.' * i + '#' * groups[0] + '.'
            if self.matches(pattern[:len(test)], test):
                count += self.solve(test, groups[1:], pattern)

        # return solutions
        self.cache[key] = count
        return count

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 5)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 3904 - too low
    else:
        print(f'sum: {AoC().part2(args[1])}')
