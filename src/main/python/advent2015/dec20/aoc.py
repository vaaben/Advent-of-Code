import sys
from functools import reduce

class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def factors(self, n):
        return set(reduce(list.__add__,
                          ([i, n//i] for i in range(1, int(n**0.5) + 1) if n % i == 0)))

    def process(self, content):
        min_presents = int(content[0])

        house = 1
        while True:
            facs = self.factors(house)
            presents = 10 * sum(facs)
            if presents > min_presents:
                break
            house += 1
        return house

    def process_2(self, content):
        min_presents = int(content[0])

        house = 1
        while True:
            facs = self.factors(house)
            presents = 11 * sum(filter(lambda x: house <= x * 50, facs))
            if presents > min_presents:
                break
            house += 1
        return house

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
