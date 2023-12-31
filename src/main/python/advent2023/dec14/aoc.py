import sys


class AoC:

    cache = {}

    rows = 0
    cols = 0

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        layout = []
        for line in content:
            layout.append([*line])

        self.rows = len(layout)
        self.cols = len(layout[0])

        layout = self.tilt_north(layout)
        for line in layout:
            print(line)

        return self.calculate_load(layout)

    def process_2(self, content):
        layout = []
        for line in content:
            layout.append([*line])

        self.rows = len(layout)
        self.cols = len(layout[0])

        self.cache[tuple(map(tuple, layout))] = 0

        turns = 1000000000
        cycle_offset = 0
        cycle_length = 0
        for i in range(turns):
            self.cycle(layout)
            #print(f'>>> {self.calculate_load(layout)}')
            key = tuple(map(tuple, layout))
            if key in self.cache:
                cycle_offset = self.cache[key]
                cycle_length = i - cycle_offset
                break
            self.cache[key] = i

        cycle_repeats = (turns - cycle_offset) // cycle_length
        cycle_remainder = (turns - cycle_offset) % cycle_length
        min_turns = (cycle_remainder - 1)
        if min_turns < 0:
            min_turns += cycle_length

        print(f'offset: {cycle_offset}')
        print(f'length: {cycle_length}')
        print(f'repeats: {cycle_repeats}')
        print(f'mod: {cycle_remainder}')
        print(f'min turns: {min_turns}')

        for i in range(min_turns):
            self.cycle(layout)

        #print(cycle_length)
        #for line in layout:
        #    print(''.join(line))

        return self.calculate_load(layout)

    def cycle(self, layout):
        self.tilt_north(layout)
        self.tilt_west(layout)
        self.tilt_south(layout)
        self.tilt_east(layout)

    def tilt_north(self, content):
        for x in range(self.rows):
            for col in range(self.cols):
                if content[x][col] == '.':
                    for y in range(x+1, self.rows):
                        if content[y][col] == 'O':
                            content[x][col] = 'O'
                            content[y][col] = '.'
                            break
                        elif content[y][col] == '#':
                            break
        return content

    def tilt_south(self, content):
        for x in range(self.rows-1, 0, -1):
            for col in range(self.cols):
                if content[x][col] == '.':
                    for y in range(x-1, -1, -1):
                        if content[y][col] == 'O':
                            content[x][col] = 'O'
                            content[y][col] = '.'
                            break
                        elif content[y][col] == '#':
                            break
        return content

    def tilt_west(self, content):
        for x in range(self.cols):
            for row in range(self.rows):
                if content[row][x] == '.':
                    for y in range(x+1, self.cols):
                        if content[row][y] == 'O':
                            content[row][x] = 'O'
                            content[row][y] = '.'
                            break
                        elif content[row][y] == '#':
                            break
        return content

    def tilt_east(self, content):
        for x in range(self.cols-1, 0, -1):
            for row in range(self.rows):
                if content[row][x] == '.':
                    for y in range(x-1, -1, -1):
                        if content[row][y] == 'O':
                            content[row][x] = 'O'
                            content[row][y] = '.'
                            break
                        elif content[row][y] == '#':
                            break
        return content

    def calculate_load(self, content):
        sum = 0
        for n, line in enumerate(content):
            sum += line.count('O') * (len(content) - n)
        return sum

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
        # 102498 - too low
        # 102505 - too low
