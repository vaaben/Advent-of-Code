import sys
import re

class AoC:

    num_exp = re.compile('(\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        time_dist = zip([int(x) for x in self.num_exp.findall(content[0])], [int(x) for x in self.num_exp.findall(content[1])])

        product = 1
        for i in time_dist:
            options = 0
            for j in range(i[0]):
                dist = (i[0]-j) * j
                if dist > i[1]:
                    options += 1
            product *= options
        return product

    def process_2(self, content):
        race_time = int(''.join(self.num_exp.findall(content[0])))
        race_dist = int(''.join(self.num_exp.findall(content[1])))

        options = 0
        for j in range(race_time):
            dist = (race_time-j) * j
            if dist > race_dist:
                options += 1
        return options

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
