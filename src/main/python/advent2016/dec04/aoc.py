import sys
import re

class AoC:

    checksum = re.compile('([a-z]+)')
    num = re.compile('(\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        sum = 0
        for line in content:
            freq = {}
            split_at = line.rfind("-")
            room_name = line[:split_at]
            section_id = self.num.findall(line[split_at+1:])[0]
            checksum = self.checksum.findall(line[split_at+1:])[0]
            for c in room_name:
                if c in freq:
                    freq[c] += 1
                else:
                    freq[c] = 1
            if '-' in freq:
                freq.pop('-')

            sorted_freq = sorted(freq.items(), key=lambda kv: (-kv[1], kv[0]))
            valid = False
            if len(sorted_freq) > 4:
                flaf = ''.join([x[0] for x in sorted_freq[0:5]])
                valid = (flaf == checksum)

            if valid:
                sum += int(section_id)

        return sum

    def process_2(self, content):
        sum = 0
        for line in content:
            freq = {}
            split_at = line.rfind("-")
            room_name = line[:split_at]
            section_id = int(self.num.findall(line[split_at+1:])[0])
            checksum = self.checksum.findall(line[split_at+1:])[0]
            for c in room_name:
                if c in freq:
                    freq[c] += 1
                else:
                    freq[c] = 1
            if '-' in freq:
                freq.pop('-')

            sorted_freq = sorted(freq.items(), key=lambda kv: (-kv[1], kv[0]))
            valid = False
            if len(sorted_freq) > 4:
                flaf = ''.join([x[0] for x in sorted_freq[0:5]])
                valid = (flaf == checksum)

            if valid:
                decrypt = ''
                for c in room_name:
                    if c == '-':
                        decrypt += ' '
                    else:
                        decrypt += chr((ord(c) - 97 + section_id) % 26 + 97)
                if decrypt.find('north') >= 0:
                    print(f'{section_id} - {decrypt}')

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
