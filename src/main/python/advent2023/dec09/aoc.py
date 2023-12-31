import sys
import re


class AoC:

    num_reg = re.compile('(-?\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def diff_seq(self, s):
        return [s[i+1]-s[i] for i in range(len(s)-1)]

    def all_zero(self, s):
        return not any(filter(lambda x: x != 0, s))

    def process(self, content):
        sum = 0
        for line in content:
            seq = [int(x) for x in self.num_reg.findall(line)]
            sum += self.extend_series(seq)[-1]
        return sum

    def process_2(self, content):
        sum = 0
        for line in content:
            seq = [int(x) for x in self.num_reg.findall(line)]
            sum += self.extend_series(seq)[0]
        return sum

    def extend_series(self, s):
        if self.all_zero(s):
            s.append(0)
            return [0] + s
        else:
            ext_seq = self.extend_series(self.diff_seq(s))
            s.append(s[-1] + ext_seq[-1])
            return [s[0]-ext_seq[0]] + s

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 656231570 - too low
    else:
        print(f'sum: {AoC().part2(args[1])}')
