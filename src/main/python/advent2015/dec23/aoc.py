import sys


class AoC:

    registers = {'a': 0, 'b': 0}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, code):
        ptr = 0
        while ptr < len(code):
            instr = code[ptr]
            if instr.startswith('hlf'):
                self.registers[instr[-1]] /= 2
            elif instr.startswith('tpl'):
                self.registers[instr[-1]] *= 3
            elif instr.startswith('inc'):
                self.registers[instr[-1]] += 1
            elif instr.startswith('jmp'):
                ptr += int(instr[4:]) - 1
            elif instr.startswith('jie'):
                if self.registers[instr[4]] % 2 == 0:
                    ptr += int(instr[7:]) - 1
            elif instr.startswith('jio'):
                if self.registers[instr[4]] == 1:
                    ptr += int(instr[7:]) - 1
            #print(f'{instr} {ptr} {self.registers}')
            ptr += 1

        return self.registers['b']

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        self.registers['a'] = 1
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 20895 - too high
    else:
        print(f'sum: {AoC().part2(args[1])}')
