import sys
import re

class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def is_abba(self, s):
        abba = False
        for i in range(len(s)-3):
            abba |= ((s[i] == s[i+3]) and (s[i+1] == s[i+2]) and (s[i] != s[i+1]))
        #print(f'{s} - {abba}')
        return abba

    def is_aba(self, a, b):
        aba = False
        for i in range(len(a)-2):
            if a[i] == a[i+2] and a[i] != a[i+1] and not aba:
                for j in range(len(b)-2):
                    if b[j] == a[i+1] and b[j+1] == a[i] and b[j+2] == b[j]:
                        aba = True
                        break
        #print(f'{s} - {abba}')
        return aba

    def process(self, content):
        count = 0
        for l in content:
            address = re.split('\[|\]', l)
            net_abba = False
            hyper_abba = False
            for i in range(len(address)):
                if i % 2 == 0:
                    net_abba |= self.is_abba(address[i])
                else:
                    hyper_abba |= self.is_abba(address[i])

            if net_abba and not hyper_abba:
                #print(l)
                count += 1

        return count

    def process_2(self, content):
        count = 0
        for l in content:
            address = re.split('\[|\]', l)
            super = ''
            hyper = ''
            for i in range(len(address)):
                if i % 2 == 0:
                    super += address[i]+'  '
                else:
                    hyper += address[i]+'  '

            #print(super)
            #print(hyper)
            if self.is_aba(super, hyper):
                count += 1

        return count

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # not 85, 82, 118 too high
    else:
        print(f'sum: {AoC().part2(args[1])}')
