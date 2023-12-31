import sys
import re

class AoC:

    code_reg = re.compile('\((\d+)x(\d+)\)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        sum = 0
        for line in content:
            #print(line)
            done = False
            decomp = line
            offset = 0
            while not done:
                iter = self.code_reg.finditer(decomp, offset)
                try:
                    code = next(iter)
                    #print(code)
                    #print(code.groups())
                    grp, mult = [int(x) for x in code.groups()]
                    #print(f'{grp},{mult}')
                    decomp = decomp[:code.start()] + decomp[code.end():code.end()+grp] * mult + decomp[code.end()+grp:]
                    offset = code.start() + grp * mult - 1
                    #print(f'{decomp}, {offset}')
                except StopIteration as e:
                    done = True
                    pass
            print(f'{decomp}, {len(decomp)}')
            sum += len(decomp)

        return sum

    def process_2(self, line):
        sum = 0
        #print(f'>> {line}')
        done = False
        decomp = line
        offset = 0
        expand = 0
        while not done:
            iter = self.code_reg.finditer(decomp, offset)
            try:
                code = next(iter)
                #print(code)
                #print(code.groups())
                grp, mult = [int(x) for x in code.groups()]
                #print(f'{grp},{mult}')
                expand += self.process_2(decomp[code.end():code.end()+grp])*mult
                decomp = decomp[:code.start()] + decomp[code.end()+grp:]
                offset = code.start()
                #print(f'{decomp}, {offset}')
            except StopIteration as e:
                done = True
                pass
        #print(f'{decomp}, {len(decomp)}, {expand}')
        sum += len(decomp) + expand

        return sum

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename)[0])


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 26285 - too low
    else:
        print(f'sum: {AoC().part2(args[1])}')
