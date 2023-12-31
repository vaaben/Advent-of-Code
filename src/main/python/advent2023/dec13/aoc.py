import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        all_lines = [x.strip() for x in content]
        patterns = []
        pat = []
        for l in all_lines:
            if l == '':
                patterns.append(pat)
                pat = []
            else:
                pat.append(l)
        patterns.append(pat)
        return patterns


    def process(self, content):
        sum = 0
        for pat in content:
            a, b = self.solve(pat)

            if len(a) > 0:
                sum += a.pop()
            else:
                sum += 100 * b.pop()

        return sum

    def process_2(self, content):
        sum = 0
        for p_num, pat in enumerate(content):
            org_reflect = self.solve(pat)
            new_reflect = org_reflect
            rows = len(pat)
            cols = len(pat[0])
            found = False
            for i in range(rows):
                if found:
                    break
                for j in range(cols):
                    if pat[i][j] == '.':
                        a = '#'
                    else:
                        a = '.'
                    org_row = pat[i]
                    pat[i] = org_row[0:j] + a + org_row[j+1:]
                    new_reflect = self.solve(pat)
                    pat[i] = org_row
                    if new_reflect != (set(), set()) and new_reflect != org_reflect:
                        found = True
                        break

            if found:
                new_reflect = (new_reflect[0].difference(org_reflect[0]), new_reflect[1].difference(org_reflect[1]))
            print(f'{p_num} - {org_reflect} -> {new_reflect}')

            if len(new_reflect[0]) > 0:
                sum += new_reflect[0].pop()
            else:
                #elif org_reflect[1] != new_reflect[1]:
                sum += 100 * new_reflect[1].pop()

        return sum

    def solve(self, pat):
        # vertical
        rows = len(pat)
        cols = len(pat[0])
        v_candidates = set(range(1, cols))
        h_candidates = set(range(1, rows))

        for row in pat:
            remove = set()
            for col in v_candidates:
                cut_l = min(col, cols-col)
                if row[col-cut_l:col] != row[col:col+cut_l][::-1]:
                    remove.add(col)
            v_candidates = v_candidates.difference(remove)

        for col in range(cols):
            col_txt = ''.join(map(lambda x: x[col], pat))
            remove = set()
            for row in h_candidates:
                cut_l = min(row, rows-row)
                if col_txt[row-cut_l:row] != col_txt[row:row+cut_l][::-1]:
                    remove.add(row)
            h_candidates = h_candidates.difference(remove)

        reflections = (set(), set())
        if len(v_candidates) > 0:
            reflections = (v_candidates, reflections[1])
        if len(h_candidates) > 0:
            reflections = (reflections[0], h_candidates)

        return reflections

    def part1(self, filename):
        return self.process(self.read_input(filename))

    def part2(self, filename):
        return self.process_2(self.read_input(filename))


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
        # 41861 -- too high
    else:
        print(f'sum: {AoC().part2(args[1])}')
        # 16759 -- too low
        # 18259 -- too low
        # 41859 -- too high
