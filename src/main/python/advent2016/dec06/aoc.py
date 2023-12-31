import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, f):
        line_len = len(content[0])
        charpos =[]
        for i in range(line_len):
            charpos.append({})

        for line in content:
            for i in range(line_len):
                c = line[i]
                if c in charpos[i]:
                    charpos[i][c] += 1
                else:
                    charpos[i][c] = 1

        text = ''
        for freq_count in charpos:
            c = f(freq_count.items(), key=lambda x: x[1])
            text += c[0]

        return text

    def part1(self, filename):
        return self.process(self.read_input(filename), max)

    def part2(self, filename):
        return self.process(self.read_input(filename), min)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
