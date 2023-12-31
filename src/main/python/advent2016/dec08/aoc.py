import sys
import re

class AoC:

    screen = []

    w = 50
    h = 6

    rect_exp = re.compile('rect (\d+)x(\d+)')
    rotate_col_exp = re.compile('rotate column x=(\d+) by (\d+)')
    rotate_row_exp = re.compile('rotate row y=(\d+) by (\d+)')

    def __init__(self):
        for i in range(self.h):
            self.screen.append('.'*self.w)

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def draw_rect(self, i, j):
        for y in range(j):
            self.screen[y] = '#'*i + self.screen[y][i:]

    def rotate_column(self, i, j):
        cur_col = [x[i] for x in self.screen]
        for y in range(self.h):
            self.screen[y] = self.screen[y][0:i] + cur_col[(y - j) % self.h] + self.screen[y][i+1:]

    def rotate_row(self, i, j):
        self.screen[i] = self.screen[i][-j:] + self.screen[i][:-j]

    def process(self, content):
        for line in content:
            m = self.rect_exp.match(line)
            if m:
                self.draw_rect(int(m.groups()[0]), int(m.groups()[1]))
            m = self.rotate_col_exp.match(line)
            if m:
                self.rotate_column(int(m.groups()[0]), int(m.groups()[1]))
            m = self.rotate_row_exp.match(line)
            if m:
                self.rotate_row(int(m.groups()[0]), int(m.groups()[1]))

        lit = 0
        for l in self.screen:
            lit += l.count('#')
        return (lit, self.screen)

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])[0]}')
    else:
        for l in AoC().part2(args[1])[1]:
            print(l)
        #print(f'sum: {AoC().part2(args[1])}')
