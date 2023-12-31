import sys
import hashlib

class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        txt = content[0]
        i = 0
        code = []
        while len(code) < 8:
            md5 = hashlib.md5((txt+str(i)).encode()).hexdigest()
            if md5[0:5] == '00000':
                print('*')
                code.append(md5[5])
            i += 1

        return code

    def process_2(self, content):
        txt = content[0]
        i = 0
        inserted = 0
        code = ['', '', '', '', '', '', '', '']
        while inserted < 8:
            md5 = hashlib.md5((txt+str(i)).encode()).hexdigest()
            if md5[0:5] == '00000':
                inx = md5[5]
                if inx.isdigit() and int(inx) < len(code) and code[int(inx)] == '':
                    print('*')
                    code[int(inx)] = md5[6]
                    inserted += 1
            i += 1

        return code

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'code: {"".join(AoC().part1(args[1]))}')
    else:
        print(f'code: {"".join(AoC().part2(args[1]))}')
