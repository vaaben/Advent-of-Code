import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        start = list(map(int, list(content[0])))
        to_fill = int(content[1])
        return ''.join(map(str, self.check_sum(self.dragon_extend(start, to_fill))))

    def dragon_extend(self, pat, length):
        if len(pat) >= length:
            return pat[:length]
        else:
            rev_inv = list(map(lambda x: (x+1) % 2, reversed(pat)))
            return self.dragon_extend(pat + [0] + rev_inv, length)

    def check_sum(self, pat):
        if len(pat) % 2 == 1:
            return pat
        else:
            return self.check_sum([(pat[i]+pat[i+1]+1) % 2 for i in range(0, len(pat), 2)])

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
