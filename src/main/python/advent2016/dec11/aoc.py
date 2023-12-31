from collections import deque
import sys
import re

class AoC:

    micro_exp = re.compile('(\w+)-compatible microchip')
    gen_exp = re.compile('(\w+) generator')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def is_invalid(self, m, g, e):
        if e < 0 or e > 3:
            return True
        for micro, floor in m.items():
            if g[micro] != floor and any(floor == gen for gen in g.values()):
                #print(f'{m} {g}')
                return True
        return False

    def is_solution(self, m, g):
        m_sol = len(list(filter(lambda x: x != 3, m.values()))) == 0
        g_sol = len(list(filter(lambda x: x != 3, g.values()))) == 0
        #if m_sol and g_sol:
        #    print(f'sol: {m}{m_sol} {g}{g_sol}')
        return m_sol and g_sol

    def make_hash(self, chips, gens, ele):
        hash = ''
        for i in chips:
            hash += str(chips[i]) + str(gens[i])
        return hash + str(ele)

    def process(self, content):
        microchips = {}
        generators = {}
        for i, line in enumerate(content):
            for x in self.micro_exp.findall(line):
                microchips[x] = i
            for x in self.gen_exp.findall(line):
                generators[x] = i

        return self.solve(microchips, generators)

    def solve(self, micros, gens):
        seen = set()
        que = deque([(micros, gens, 0, 0)])
        while que:
            m, g, e, steps = que.popleft()
            #print(f'{m}, {g}, {e}, {steps}')
            hash = self.make_hash(m, g, e)
            if hash in seen or self.is_invalid(m, g, e):
                continue

            seen.add(hash)
            #print(f'{hash} + {e} + {steps}')

            if self.is_solution(m, g):
                return steps

            microchips_at_elevator = list(filter(lambda k: k[1] == e, m.items()))
            #print(microchips_at_elevator)
            generators_at_elevator = list(filter(lambda k: k[1] == e, g.items()))
            #print(generators_at_elevator)
            for i, a in enumerate(microchips_at_elevator):
                # try moving chips alone
                #print(f'move one microchip {a}')
                m_tag = dict(m)
                m_tag[a[0]] = e+1
                que.append((m_tag, g, e+1, steps+1))
                m_tag = dict(m)
                m_tag[a[0]] = e-1
                que.append((m_tag, g, e-1, steps+1))

                for b in microchips_at_elevator[i+1:]:
                    # try moving two chips
                    #print(f'move two microchips {a} {b}')
                    m_tag = dict(m)
                    m_tag[a[0]] = e+1
                    m_tag[b[0]] = e+1
                    que.append((m_tag, g, e+1, steps+1))
                    m_tag = dict(m)
                    m_tag[a[0]] = e-1
                    m_tag[b[0]] = e-1
                    que.append((m_tag, g, e-1, steps+1))

                for b in generators_at_elevator:
                    # try moving chips and gen
                    #print(f'move microchip {a} generator {b}')
                    m_tag = dict(m)
                    g_tag = dict(g)
                    m_tag[a[0]] = e+1
                    g_tag[b[0]] = e+1
                    que.append((m_tag, g_tag, e+1, steps+1))
                    m_tag = dict(m)
                    g_tag = dict(g)
                    m_tag[a[0]] = e-1
                    g_tag[b[0]] = e-1
                    que.append((m_tag, g_tag, e-1, steps+1))

            for i, a in enumerate(generators_at_elevator):
                # try moving chips alone
                #print(f'move one generator {a}')
                g_tag = dict(g)
                g_tag[a[0]] = e+1
                que.append((m, g_tag, e+1, steps+1))
                g_tag = dict(g)
                g_tag[a[0]] = e-1
                que.append((m, g_tag, e-1, steps+1))

                for b in generators_at_elevator[i+1:]:
                    # try moving two generators
                    #print(f'move two generators {a} {b}')
                    g_tag = dict(g)
                    g_tag[a[0]] = e+1
                    g_tag[b[0]] = e+1
                    que.append((m, g_tag, e+1, steps+1))
                    g_tag = dict(g)
                    g_tag[a[0]] = e-1
                    g_tag[b[0]] = e-1
                    que.append((m, g_tag, e-1, steps+1))

                for b in microchips_at_elevator:
                    # try moving gen and chips
                    #print(f'move generator {a} microchip {b}')
                    m_tag = dict(m)
                    g_tag = dict(g)
                    g_tag[a[0]] = e+1
                    m_tag[b[0]] = e+1
                    que.append((m_tag, g_tag, e+1, steps+1))
                    m_tag = dict(m)
                    g_tag = dict(g)
                    g_tag[a[0]] = e-1
                    m_tag[b[0]] = e-1
                    que.append((m_tag, g_tag, e-1, steps+1))

            #print(que)
            #break

            #print(list())
            #print(g)
            #print(e)
            #print(steps)

        pass

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
