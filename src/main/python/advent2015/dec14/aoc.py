import sys
import re

class AoC:

    reindeer_exp = re.compile('(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.')

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def dist(self, speed, run, rest, t):
        cycles = t // (run + rest)
        cycle_remainder = t % (run + rest)
        return speed * run * cycles + min(run, cycle_remainder)*speed

    def process(self, content, t):
        distances = {}
        for line in content:
            reindeer = self.reindeer_exp.match(line)
            if reindeer:
                speed = int(reindeer.group(2))
                run = int(reindeer.group(3))
                rest = int(reindeer.group(4))
                distances[reindeer.group(1)] = self.dist(speed, run, rest, t)

        return max(distances.items(), key=lambda x: x[1])

    def process_2(self, content, t):
        points = {}
        reindeers = {}
        for line in content:
            reindeer = self.reindeer_exp.match(line)
            key = reindeer.group(1)
            reindeers[key] = (int(reindeer.group(2)), int(reindeer.group(3)), int(reindeer.group(4)))
            points[key] = 0

        for i in range(1, t+1):
            distances = {}
            for key, r in reindeers.items():
                distances[key] = self.dist(r[0], r[1], r[2], i)

            max_dist = max(distances.values())
            for s in distances:
                if distances[s] == max_dist:
                    points[s] += 1

            #print(f'{points} - {distances}')

        print(distances)
        print(points)
        return max(points.items(), key=lambda x: x[1])

    def part1(self, filename):
        return self.process(self.read_input(filename), 2503)

    def part2(self, filename):
        return self.process_2(self.read_input(filename), 2503)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
        # 1103 - too high
