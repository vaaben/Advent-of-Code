import sys
import re

class AoC:

    num_reg = re.compile('(\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def is_valid(self, a, b, c):
        valid = ((a+b) > c) and ((a+c) > b) and ((b+c) > a)
        return valid

    def process(self, content):
        count_valid = 0
        for line in content:
            if self.is_valid(*[int(x) for x in self.num_reg.findall(line)]):
                count_valid += 1

        return count_valid

    def process_2(self, content):
        count_valid = 0
        for inx in range(0, len(content), 3):
            l1 = [int(x) for x in self.num_reg.findall(content[inx])]
            l2 = [int(x) for x in self.num_reg.findall(content[inx+1])]
            l3 = [int(x) for x in self.num_reg.findall(content[inx+2])]
            for j in [0, 1, 2]:
                if self.is_valid(l1[j], l2[j], l3[j]):
                    count_valid += 1

        return count_valid

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'num: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
