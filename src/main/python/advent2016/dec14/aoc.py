import sys
import hashlib

class AoC:

    cache = {}
    salt = ''
    hash_loops = 0

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def has_group_of_3(self, l):
        for i in range(len(l)-2):
            if l[i] == l[i+1] == l[i+2]:
                return True, l[i]
        return False, ''

    def has_group_of_5(self, hex, c):
        return c*5 in hex

    def _md5(self, i):
        if i in self.cache:
            return self.cache[i]
        txt = hashlib.md5((self.salt + str(i)).encode()).hexdigest()
        for j in range(self.hash_loops):
            txt = hashlib.md5(txt.encode()).hexdigest()
        self.cache[i] = txt
        #print(f'caching: {i}')
        return txt

    def is_key(self, i, c):
        for j in range(i+1, i+1001):
            if self.has_group_of_5(self._md5(j), c):
                return True
        return False

    def process(self, content):
        i = 0
        self.salt = content[0]
        keys = []
        while True:
            md5 = self._md5(i)
            test, c = self.has_group_of_3(md5)
            if test and self.is_key(i, c):
                keys.append((i, md5))
                print(i)
                if len(keys) == 64:
                    break
            i += 1

        #for i in keys:
        #    print(i)
        return i


    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        self.hash_loops = 2016
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
