import sys

class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def hash(self, txt):
        value = 0
        for c in txt:
            value = ((value + ord(c))*17) % 256
        return value

    def process(self, content):
        return sum(map(self.hash, content[0].split(',')))

    def process_2(self, content):
        boxes = []
        for i in range(256):
            boxes.append([])
        inst_list = content[0].split(',')
        for inst in inst_list:
            if '=' in inst:
                label, focal = inst.split('=')
                box_inx = self.hash(label)
                box = boxes[box_inx]
                found = False
                for replace_inx, lens in enumerate(box):
                    if lens[0] == label:
                        box[replace_inx] = (label, focal)
                        found = True
                        break
                if not found:
                    boxes[box_inx].append((label, focal))

            else:
                label, _ = inst.split('-')
                box_inx = self.hash(label)
                box = boxes[box_inx]
                for remove_inx, lens in enumerate(box):
                    if lens[0] == label:
                        del box[remove_inx]

        sum = 0
        for f, box in enumerate(boxes):
            for i, lens in enumerate(box):
                sum += (f+1) * (i+1) * int(lens[1])

        return sum

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
