import sys
import re

class AoC:

    rule_exp = re.compile('(\w+)\{(.*)\}')
    term_exp = re.compile('([xmas])([<>])(\d+):(\w+)')

    rules = {}
    parts = []

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def eval(self, part, inst):
        # print(f'##{part}')
        # print(f'>>{inst}')
        if inst == 'A' or inst == 'R':
            return inst
        else:
            terms = self.rules[inst]
            for term in terms:
                if len(term) > 1 and term[1] == '<':
                    if part[term[0]] < int(term[2]):
                        return self.eval(part, term[3])
                elif len(term) > 1 and term[1] == '>':
                    if part[term[0]] > int(term[2]):
                        return self.eval(part, term[3])
                else:
                    return self.eval(part, term)

    def process(self, content, sol):
        reading_rules = True
        for line in content:
            if line == '':
                reading_rules = False
            elif reading_rules:
                m = self.rule_exp.match(line)
                flaf = m.group(2).split(",")
                exps = []
                for t in flaf:
                    m_t = self.term_exp.match(t)
                    if m_t:
                        exps.append(m_t.groups())
                    else:
                        exps.append(t)

                self.rules[m.group(1)] = exps
            else:
                part_dict = (line.replace('=', ': ')
                             .replace('x', "'x'")
                             .replace('m', "'m'")
                             .replace('a', "'a'")
                             .replace('s', "'s'"))
                self.parts.append(eval(part_dict))

        if sol == 1:
            s = 0
            for p in self.parts:
                if self.eval(p, 'in') == 'A':
                    s += sum(p.values())
            return s
        else:
            return self.count({key: (1, 4000) for key in "xmas"})

    def count(self, ranges, name="in"):
        if name == "R":
            return 0
        if name == "A":
            product = 1
            for lo, hi in ranges.values():
                product *= hi - lo + 1
            return product

        terms = self.rules[name]
        total = 0
        for term in terms:
            #print(term)
            if isinstance(term, tuple):
                key = term[0]
                n = int(term[2])
                lo, hi = ranges[key]
                if term[1] == "<":
                    T = (lo, n - 1)
                    F = (n, hi)
                else:
                    T = (n + 1, hi)
                    F = (lo, n)
                if T[0] <= T[1]:
                    copy = dict(ranges)
                    copy[key] = T
                    total += self.count(copy, term[3])
                if F[0] <= F[1]:
                    ranges = dict(ranges)
                    ranges[key] = F
                else:
                    break
            else:
                total += self.count(ranges, term)

        return total

    def part1(self, filename):
        return self.process(self.read_input(filename), 1)

    def part2(self, filename):
        return self.process(self.read_input(filename), 2)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
