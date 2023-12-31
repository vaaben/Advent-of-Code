import sys
import networkx as nx

class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def connections(self, a, b):
        conns = set()
        for c in a:
            for c_c in a[c]:
                if c_c in b:
                    conns.add((c, c_c))
                    #conns.add((c_c, c))
        for c in b:
            for c_c in b[c]:
                if c_c in a:
                    conns.add((c, c_c))
                    #conns.add((c_c, c))
        return conns

    def process(self, content):
        g = nx.Graph()

        for line in content:
            left, right = line.split(":")
            for node in right.strip().split():
                g.add_edge(left, node)
                g.add_edge(node, left)

        g.remove_edges_from(nx.minimum_edge_cut(g))
        a, b = nx.connected_components(g)

        return len(a) * len(b)

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
