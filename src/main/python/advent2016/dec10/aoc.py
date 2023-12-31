import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def process(self, instructions):
        from collections import defaultdict

        initial = [line.split() for line in instructions if line.startswith('value')]
        commands = [line.split() for line in instructions if not line.startswith('value')]

        connections = {}
        for line in commands:
            name, lower, higher = line[1], line[5:7], line[-2:]
            connections[name] = (lower, higher)

        bots = defaultdict(list)
        outputs = defaultdict(list)
        stack = []

        def add_value(name, value):
            bots[name].append(value)
            if len(bots[name]) == 2:
                stack.append(name)

        def send_value(connection, value):
            out_type, out_name = connection
            if out_type == 'bot':
                add_value(out_name, value)
            else:
                outputs[out_name].append(value)

        for line in initial:
            value, name = int(line[1]), line[-1]
            add_value(name, value)

        while stack:
            name = stack.pop()
            low_value, high_value = sorted(bots[name])
            if low_value == 17 and high_value == 61:
                wanted_bot = name
            lower_connection, higher_connection = connections[name]
            send_value(lower_connection, low_value)
            send_value(higher_connection, high_value)

        a, b, c = (outputs[i][0] for i in '012')

        return (wanted_bot, a*b*c)

    def part1(self, filename):
        return self.process(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'part1, part2: {AoC().part1(args[1])}')
    else:
        print(f'part1, part2: {AoC().part1(args[1])}')
