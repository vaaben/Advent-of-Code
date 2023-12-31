import sys
import re
from itertools import permutations


class AoC:

    exp = re.compile('(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+).')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, extras):
        placement_map = {}
        for line in content:
            m = self.exp.match(line)
            if m:
                value = int(m.group(3))
                if m.group(2) == 'lose':
                    value = -value
                pair_a = (m.group(1), m.group(4))
                placement_map[pair_a] = value

        people = extras
        for pa, pb in placement_map:
            people.add(pa)
            people.add(pb)

        max_value = 0
        for permut in permutations(people):
            value = 0
            pairs = zip(permut, list(permut[1:]) + [permut[0]])
            for pa, pb in pairs:
                if (pa, pb) in placement_map:
                    value += placement_map[(pa, pb)]
                if (pb, pa) in placement_map:
                    value += placement_map[(pb, pa)]
            if value > max_value:
                max_value = value

        return max_value

    def part1(self, filename):
        return self.process(self.read_input(filename), set())

    def part2(self, filename):
        return self.process(self.read_input(filename), set('me'))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
