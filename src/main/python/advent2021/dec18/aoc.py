import sys


class Num:
    def __init__(self, val):
        self.val = val
        self.leftn = None
        self.rightn = None
        self.leftmost = self
        self.rightmost = self
        self.parent = None
    def update_n(self):
        pass
    def __repr__(self):
        return str(self.val)


class Pair:
    def __init__(self, left, right):
        self.parent = None
        self.side = None
        self.left = left
        self.right = right
        self.leftmost = left.leftmost
        self.rightmost = right.rightmost
        self.setleft(left)
        self.setright(right)
    def setleft(self, left):
        self.left = left
        self.left.parent = self
        self.left.side = 0
        self.leftmost = self.left.leftmost
        self.left.rightmost.rightn = self.right.leftmost
    def setright(self, right):
        self.right = right
        self.right.parent = self
        self.right.side = 1
        self.rightmost = self.right.rightmost
        self.right.leftmost.leftn = self.left.rightmost
    def update_n(self):
        self.left.update_n()
        self.right.update_n()
        self.leftmost = self.left.leftmost
        self.rightmost = self.right.rightmost
        self.left.rightmost.rightn = self.right.leftmost
        self.right.leftmost.leftn = self.left.rightmost
    def __repr__(self):
        return f"[{self.left}, {self.right}]"


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def convert(self, e):
        if isinstance(e, list):
            return Pair(self.convert(e[0]), self.convert(e[1]))
        else:
            return Num(e)

    def test_explode(self, v, k, d = 0):
        if isinstance(k, Num):
            return
        if isinstance(k.left, Num) and isinstance(k.right, Num) and d >= 4:
            if k.left.leftn: k.left.leftn.val += k.left.val
            if k.right.rightn: k.right.rightn.val += k.right.val
            if k.side == 0:
                k.parent.setleft(Num(0))
            else:
                k.parent.setright(Num(0))
            v.update_n()
            return True
        return self.test_explode(v, k.left, d + 1) or self.test_explode(v, k.right, d + 1)

    def test_split(self, v, k):
        if isinstance(k, Num):
            if k.val >= 10:
                if k.side == 0:
                    k.parent.setleft(Pair(Num(k.val // 2), Num(-(-k.val // 2))))
                else:
                    k.parent.setright(Pair(Num(k.val // 2), Num(-(-k.val // 2))))
                v.update_n()
                return True
            else:
                return False
        return self.test_split(v, k.left) or self.test_split(v, k.right)

    def reduce(self, v):
        while True:
            if self.test_explode(v, v):
                pass
            elif self.test_split(v, v):
                pass
            else:
                break

    def mag(self, v):
        if isinstance(v, Num):
            return v.val
        else:
            return 3 * self.mag(v.left) + 2 * self.mag(v.right)

    def copy(self, v):
        if isinstance(v, Pair):
            return Pair(self.copy(v.left), self.copy(v.right))
        return Num(v.val)

    def process(self, content, part):
        if part == 1:
            d = []

            for line in content:
                if d == []:
                    d = self.convert(eval(line))
                else:
                    d = Pair(d, self.convert(eval(line)))
                    self.reduce(d)

            return self.mag(d)

        else:
            d = [self.convert(eval(line)) for line in content]

            mv = -float("inf")

            for i in range(len(d)):
                for j in range(len(d)):
                    if i == j:
                        continue
                    v = Pair(self.copy(d[i]), self.copy(d[j]))
                    self.reduce(v)
                    mv = max(mv, self.mag(v))

            return mv

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


