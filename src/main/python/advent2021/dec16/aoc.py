import sys


class AoC:

    pyint = int

    def int(self, x, y = 10):
        return self.pyint("".join(x), y)

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content):
        k = list("".join(bin(int(c, 16))[2:].zfill(4) for c in content[0]))

        q = self.parse(k)
        return self.vsum(q)

    def process_2(self, content):
        k = list("".join(bin(int(c, 16))[2:].zfill(4) for c in content[0]))

        q = self.parse(k)
        return self.vsum_2(q)

    def parse(self, k):
        version = int(''.join(k[:3]), 2)
        k[:] = k[3:]
        typeid = int(''.join(k[:3]), 2)
        k[:] = k[3:]
        if typeid == 4:
            data = []
            while True:
                cont = k.pop(0)
                data += k[:4]
                k[:] = k[4:]
                if cont == "0":
                    break
            data = int(''.join(data), 2)
            return (version, typeid, data)
        else:
            packets = []
            if k.pop(0) == "0":
                length = int(''.join(k[:15]), 2)
                k[:] = k[15:]
                d = k[:length]
                k[:] = k[length:]
                while d:
                    packets.append(self.parse(d))
            else:
                num = int(''.join(k[:11]), 2)
                k[:] = k[11:]
                for _ in range(num):
                    packets.append(self.parse(k))
            return (version, typeid, packets)

    def vsum(self, k):
        t = k[0]
        if k[1] == 4:
            return t
        else:
            return t + sum(map(self.vsum, k[2]))

    def vsum_2(self, k):
        if k[1] == 0:
            return sum(map(self.vsum_2, k[2]))
        elif k[1] == 1:
            t = 1
            for q in k[2]:
                t *= self.vsum_2(q)
            return t
        elif k[1] == 2:
            return min(map(self.vsum_2, k[2]))
        elif k[1] == 3:
            return max(map(self.vsum_2, k[2]))
        elif k[1] == 4:
            return k[2]
        elif k[1] == 5:
            return self.vsum_2(k[2][0]) > self.vsum_2(k[2][1])
        elif k[1] == 6:
            return self.vsum_2(k[2][0]) < self.vsum_2(k[2][1])
        elif k[1] == 7:
            return self.vsum_2(k[2][0]) == self.vsum_2(k[2][1])

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
