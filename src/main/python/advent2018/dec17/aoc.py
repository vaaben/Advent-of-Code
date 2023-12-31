import sys
import re
import numpy as np

class AoC:

    num_exp = re.compile('-?\d+')

    max_y = 0

    clay = set()
    water = set()
    flowing = set()

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def print_grid(grid):
        print("********* BEGINNING *********")
        int_to_char = {0: " ", 1: "#", 2: "|", 3: "~", 10: "o"}
        for line in grid:
            for c in line[494:508]:
                print(int_to_char[c], end="")
            print()
        print("************ END ***********")
        print()

    def process(self, content, part):
        clays = []
        min_x = float("inf")
        max_x = float("-inf")
        min_y = float("inf")
        max_y = float("-inf")

        for line in content:
            first, second = line.split(", ")
            width_pos = int(first[2:])
            length_dir = second[0]
            length_from, length_to = second[2:].split("..")
            length_from = int(length_from)
            length_to = int(length_to)
            clays.append((width_pos, length_dir, length_from, length_to))
            if length_dir == "x":
                max_x = max(max_x, length_from, length_to)
                min_x = min(min_x, length_from, length_to)
                max_y = max(max_y, width_pos)
                min_y = min(min_y, width_pos)
            else:
                max_y = max(max_y, length_from, length_to)
                max_x = max(max_x, width_pos)
                min_y = min(min_y, length_from, length_to)
                min_x = min(min_x, width_pos)

        grid = np.zeros((max_y + 2, max_x + 2))
        for width_pos, length_dir, length_from, length_to in clays:
            if length_dir == "x":
                grid[width_pos, length_from:length_to + 1] = 1
            else:
                grid[length_from:length_to + 1, width_pos] = 1

        y, x = 0, 500
        stack = [(y, x)]
        while len(stack):
            y, x = stack.pop()
            if y > max_y:
                continue
            if grid[y + 1][x] in [0, 2]:
                grid[y][x] = 2
                stack.append((y + 1, x))
                continue
            else:
                fill_left = x
                while grid[y][fill_left] in [0, 2] and grid[y + 1][fill_left] in [1, 3]:
                    grid[y][fill_left] = 2
                    fill_left -= 1
                fill_right = x
                while grid[y][fill_right] in [0, 2] and grid[y + 1][fill_right] in [
                    1,
                    3,
                ]:
                    grid[y][fill_right] = 2
                    fill_right += 1
                if grid[y][fill_left] == 1 and grid[y][fill_right] == 1:
                    grid[y][fill_left + 1:fill_right] = 3
                    stack.append((y - 1, x))
                    continue
                if grid[y + 1][fill_left] == 0:
                    stack.append((y, fill_left))
                if grid[y + 1][fill_right] == 0:
                    stack.append((y, fill_right))

        # import matplotlib.pyplot as plt
        # plt.imsave("test.png", grid)

        if part == 1:
            return np.sum(grid[min_y:max_y + 1, :] >= 2)
        else:
            return np.sum(grid[min_y : max_y + 1, :] == 3)


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
