import sys
import re


class AoC:

    transitions = {}

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, part):
        rules = True
        start = ''
        for line in content:
            if line == '':
                rules = False
                continue

            if rules:
                f, t = line.split(' => ')
                if f in self.transitions:
                    self.transitions[f] = self.transitions[f] + [t]
                else:
                    self.transitions[f] = [t]
            else:
                start = line

        if part == 1:
            molecules = set([start])
            new_molecules = set()
            for k, r in self.transitions.items():
                for i in r:
                    for m in molecules:
                        replace_inx = [x.start() for x in re.finditer(k, m)]
                        for j in replace_inx:
                            new_molecules.add(m[0:j]+i+m[j+len(k):])
            molecules = new_molecules

            return len(molecules)
        else:
            #print(start)
            molecules = set(['e'])
            counter = 0
            while True:
                new_molecules = set()
                for k, r in self.transitions.items():
                    for i in r:
                        for m in molecules:
                            replace_inx = [x.start() for x in re.finditer(k, m)]
                            for j in replace_inx:
                                new_m = m[0:j]+i+m[j+len(k):]
                                if new_m == start:
                                    #print("###")
                                    return counter + 1
                                else:
                                    new_molecules.add(new_m)
                molecules = new_molecules
                #print(molecules)
                counter += 1
                print(counter)

            return len(molecules)

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 2)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 674 - too high
    else:
        print(f'sum: {AoC().part2(args[1])}')
