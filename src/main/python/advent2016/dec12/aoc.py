import sys
import re

class AoC:

    cpy_cmd_i = re.compile('cpy (\d+) ([abcd])')
    cpy_cmd_r = re.compile('cpy ([abcd]) ([abcd])')
    inc_cmd = re.compile('inc ([abcd])')
    dec_cmd = re.compile('dec ([abcd])')
    jnz_cmd_i = re.compile('jnz (\d+) (-?\d+)')
    jnz_cmd_r = re.compile('jnz ([abcd]) (-?\d+)')

    registers = {'a': 0, 'b': 0, 'c': 0, 'd': 0}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        inst_ptr = 0
        while inst_ptr < len(content):
            #print(content[inst_ptr])
            m = self.cpy_cmd_i.match(content[inst_ptr])
            if m:
                self.registers[m.group(2)] = int(m.group(1))

            m = self.cpy_cmd_r.match(content[inst_ptr])
            if m:
                self.registers[m.group(2)] = self.registers[m.group(1)]

            m = self.inc_cmd.match(content[inst_ptr])
            if m:
                self.registers[m.group(1)] += 1

            m = self.dec_cmd.match(content[inst_ptr])
            if m:
                self.registers[m.group(1)] -= 1

            m = self.jnz_cmd_i.match(content[inst_ptr])
            if m and m.group(1) != 0:
                inst_ptr += int(m.group(2)) - 1

            m = self.jnz_cmd_r.match(content[inst_ptr])
            if m and self.registers[m.group(1)] != 0:
                inst_ptr += int(m.group(2)) - 1

            inst_ptr += 1

        return self.registers['a']

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        self.registers['c'] = 1
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
