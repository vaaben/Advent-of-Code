import sys
import re

from multiprocessing import Pool


class Map:

    def __init__(self, a, b, l):
        self.s_start = b
        self.s_end = b + l
        self.d_start = a
        self.d_end = a + l

    def lookup(self, k):
        if self.s_start <= k <= self.s_end:
            return self.d_start + k-self.s_start
        else:
            return k


class AoC:

    num_reg = re.compile('(\d+)')
    seeds = None
    almanac = None

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def lookup(self, n, almanac):
        d = n
        for section in almanac:
            for map in section:
                nd = map.lookup(d)
                if nd != d:
                    d = nd
                    break
        return (n, d)

    def process(self, content):
        seeds = [int(x) for x in self.num_reg.findall(content[0].split(':')[1])]

        print(seeds)

        almanac = []
        for i in range(1, len(content)):
            if content[i] == '':
                pass
            elif content[i][0].isdigit():
                almanac[-1].append(Map(*[int(x) for x in self.num_reg.findall(content[i])]))
            else:
                # label
                almanac.append([])

        # map info
        translation = [self.lookup(x, almanac) for x in seeds]
        print(translation)

        return max(translation, key=lambda x: x[1])

    def pre_process(self, content):
        self.seeds = [int(x) for x in self.num_reg.findall(content[0].split(':')[1])]

        self.almanac = []
        for i in range(1, len(content)):
            if content[i] == '':
                pass
            elif content[i][0].isdigit():
                self.almanac[-1].append(Map(*[int(x) for x in self.num_reg.findall(content[i])]))
            else:
                # label
                self.almanac.append([])

        return (self.seeds, self.almanac)

    def process_2(self, inx):

        # map info
        min_loc = 4100783722
        #for i in range(0, len(seeds), 2):
        print(f'>>> {inx}')
        for s in range(self.seeds[inx], self.seeds[inx]+self.seeds[inx+1]):
            tmp = self.lookup(s, self.almanac)[1]
            if tmp < min_loc:
                min_loc = tmp
                print(tmp)

        return min_loc

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        # 79753136  too high
        #
        aoc = AoC()
        (seeds, almanac) = aoc.pre_process(aoc.read_input(args[1]))

        with Pool(int(len(seeds) / 2)) as pool:
            res = pool.map(aoc.process_2, range(0, len(seeds), 2))

        print(f'sum: {min(res)}')
