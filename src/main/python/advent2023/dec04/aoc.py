import math
import sys
import re

class AoC:

    flaf = re.compile('(\d+)')
    fluf = re.compile('Card *(\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        sum = 0
        for card in content:
            a, C = card.split(':')
            k, l = C.split('|')

            numbers = set(self.flaf.findall(k))
            winners = set(self.flaf.findall(l))

            intersect = numbers.intersection(winners)
            if len(intersect) > 0:
                sum += math.pow(2, len(intersect)-1)

        return int(sum)

    def process_2(self, content):
        card_counter = []
        for c in range(len(content)):
            card_counter.append(1)

        for card in content:
            a, C = card.split(':')
            k, l = C.split('|')

            card_num = int(self.fluf.match(a).group(1))

            numbers = set(self.flaf.findall(k))
            winners = set(self.flaf.findall(l))

            intersect = numbers.intersection(winners)
            for i in range(card_num, card_num+len(intersect)):
                card_counter[i] += card_counter[card_num-1]

        print(card_counter)

        return sum(card_counter)

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
