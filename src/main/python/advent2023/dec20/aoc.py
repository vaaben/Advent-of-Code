import sys
from collections import deque
from math import gcd


class Module:
    def __init__(self, name, type, outputs):
        self.name = name
        self.type = type
        self.outputs = outputs

        if type == "%":
            self.memory = "off"
        else:
            self.memory = {}

    def __repr__(self):
        return self.name + "{type=" + self.type + ",outputs=" + ",".join(self.outputs) + ",memory=" + str(self.memory) + "}"


class AoC:

    modules = {}
    broadcast_targets = []

    lo = hi = 0

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, content, part):
        for line in content:
            left, right = line.strip().split(" -> ")
            outputs = right.split(", ")
            if left == "broadcaster":
                self.broadcast_targets = outputs
            else:
                type = left[0]
                name = left[1:]
                self.modules[name] = Module(name, type, outputs)

        for name, module in self.modules.items():
            for output in module.outputs:
                if output in self.modules and self.modules[output].type == "&":
                    self.modules[output].memory[name] = "lo"

        if part == 1:
            for _ in range(1000):
                self.lo += 1  # button pulse
                self.step()

            return self.lo * self.hi
        else:
            (feed,) = [name for name, module in self.modules.items() if "rx" in module.outputs]

            cycle_lengths = {}
            seen = {name: 0 for name, module in self.modules.items() if feed in module.outputs}

            return self.step_2(feed, seen, cycle_lengths)

    def step(self):
        q = deque([("broadcaster", x, "lo") for x in self.broadcast_targets])

        while q:
            origin, target, pulse = q.popleft()

            if pulse == "lo":
                self.lo += 1
            else:
                self.hi += 1

            if target not in self.modules:
                continue

            module = self.modules[target]

            if module.type == "%":
                if pulse == "lo":
                    module.memory = "on" if module.memory == "off" else "off"
                    outgoing = "hi" if module.memory == "on" else "lo"
                    for x in module.outputs:
                        q.append((module.name, x, outgoing))
            else:
                module.memory[origin] = pulse
                outgoing = "lo" if all(x == "hi" for x in module.memory.values()) else "hi"
                for x in module.outputs:
                    q.append((module.name, x, outgoing))

    def step_2(self, feed, seen, cycle_lengths):
        presses = 0
        while True:
            presses += 1
            q = deque([("broadcaster", x, "lo") for x in self.broadcast_targets])

            while q:
                origin, target, pulse = q.popleft()

                if target not in self.modules:
                    continue

                module = self.modules[target]

                if module.name == feed and pulse == "hi":
                    seen[origin] += 1

                    if origin not in cycle_lengths:
                        cycle_lengths[origin] = presses
                    else:
                        assert self.presses == seen[origin] * cycle_lengths[origin]

                    if all(seen.values()):
                        x = 1
                        for cycle_length in cycle_lengths.values():
                            x = x * cycle_length // gcd(x, cycle_length)
                        return x

                if module.type == "%":
                    if pulse == "lo":
                        module.memory = "on" if module.memory == "off" else "off"
                        outgoing = "hi" if module.memory == "on" else "lo"
                        for x in module.outputs:
                            q.append((module.name, x, outgoing))
                else:
                    module.memory[origin] = pulse
                    outgoing = "lo" if all(x == "hi" for x in module.memory.values()) else "hi"
                    for x in module.outputs:
                        q.append((module.name, x, outgoing))

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
