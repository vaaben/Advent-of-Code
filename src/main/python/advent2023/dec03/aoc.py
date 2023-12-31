import sys
import re


class AoC:

    flaf = re.compile('(\d+)')

    width = 0
    height = 0

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        inp = [x.strip() for x in content]

        self.width = len(inp[0])
        self.height = len(inp)

        return inp

    def process(self, content):
        sum = 0
        for i in range(len(content)):
            seek_offset = 0
            numbers_in_line = self.flaf.findall(content[i])
            for num in numbers_in_line:
                j = content[i].find(num, seek_offset)
                seek_offset = j + len(num)
                if self.check_halo(i, j, len(num), content):
                    sum += int(num)

        return sum

    def check_halo(self, i, j, l, content):
        tag = False
        for x in range(j-1, j+l+1):
            for y in range(i-1, i+2):
                if not (x < 0 or y < 0 or x >= self.width or y >= self.height):
                    c = content[y][x]
                    tag |= (not c.isdigit() and not c == '.')
        return tag

    def process_2(self, content):
        sum = 0
        gear_list = []
        for i in range(len(content)):
            seek_offset = 0
            numbers_in_line = self.flaf.findall(content[i])
            for num in numbers_in_line:
                j = content[i].find(num, seek_offset)
                seek_offset = j + len(num)
                asterix = self.check_gear(i, j, len(num), content)
                for g in asterix:
                    gear_list.append((g[0], g[1], int(num)))

        for k in range(len(gear_list)):
            a = gear_list[k]
            for l in range(k+1, len(gear_list)):
                b = gear_list[l]
                if a[0] == b[0] and a[1] == b[1]:
                    sum += a[2]*b[2]

        return sum

    def check_gear(self, i, j, l, content):
        asterix = []
        for x in range(j-1, j+l+1):
            for y in range(i-1, i+2):
                if not (x < 0 or y < 0 or x >= self.width or y >= self.height):
                    c = content[y][x]
                    if not c.isdigit() and c == '*':
                        asterix.append((y, x))
        return asterix

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 514953 too low
        # 514960 too low
    else:
        print(f'sum: {AoC().part2(args[1])}')
