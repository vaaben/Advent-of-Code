import sys
import re
from itertools import combinations_with_replacement as combinations, permutations

class AoC:

    total = 100

    ing_exp = re.compile('(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, target=None):
        ingredients = []
        for line in content:
            m = self.ing_exp.match(line)
            if m:
                ingredients.append((int(m.group(2)), int(m.group(3)), int(m.group(4)), int(m.group(5)), int(m.group(6))))

        max_prod = 0

        for combo in combinations(range(101), len(ingredients)):
            if sum(combo) == self.total:

                for perm in permutations(combo):
                    p = 1

                    cal = 0
                    for i, amount in enumerate(perm):
                        cal += amount * ingredients[i][len(ingredients[0])-1]

                    if target is None or cal == target:
                        for j in range(len(ingredients[0])-1):
                            s = 0
                            for i, amount in enumerate(perm):
                                s += amount * ingredients[i][j]
                            p *= max(0, s)

                        max_prod = max(p, max_prod)

        return max_prod

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process(self.read_input(filename), 500)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
        # 11162880 - too low
