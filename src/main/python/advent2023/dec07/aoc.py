import sys


class AoC:

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def card_freq(self, s):
        freq = {}
        for c in s:
            if c in freq:
                freq[c] += 1
            else:
                freq[c] = 1
        return freq

    def hand_type(self, freq):
        max_f = max(freq.items(), key=lambda x: x[1])[1]
        if max_f == 5:
            return 7  # five of a kind
        elif max_f == 4:
            return 6  # four of a kind
        elif max_f == 3 and len(freq) == 2:
            return 5  # full house
        elif max_f == 3:
            return 4  # three of a kind
        elif max_f == 2 and len(freq) == 3:
            return 3  # two pairs
        elif max_f == 2:
            return 2  # one pair
        else:
            return 1

    def hand_type_2(self, freq):

        # patch hand to account for Joker
        if 'J' in freq:
            m = freq['J']
            for c in freq:
                freq[c] += m
            freq.pop('J')

        if len(freq) == 0:
            return 7
        else:
            max_f = max(freq.items(), key=lambda x: x[1])[1]
            if max_f == 5:
                return 7  # five of a kind
            elif max_f == 4:
                return 6  # four of a kind
            elif max_f == 3 and len(freq) == 2:
                return 5  # full house
            elif max_f == 3:
                return 4  # three of a kind
            elif max_f == 2 and len(freq) == 3:
                return 3  # two pairs
            elif max_f == 2:
                return 2  # one pair
            else:
                return 1

    def card_to_value(self, c):
        value = 0
        if c == 'A':
            value = 14
        elif c == 'K':
            value = 13
        elif c == 'Q':
            value = 12
        elif c == 'J':
            value = 11
        elif c == 'T':
            value = 10
        else:
            value = int(c)
        return value

    def card_to_value_2(self, c):
        value = 0
        if c == 'A':
            value = 14
        elif c == 'K':
            value = 13
        elif c == 'Q':
            value = 12
        elif c == 'J':
            value = 1
        elif c == 'T':
            value = 10
        else:
            value = int(c)
        return value

    def process(self, content):
        hands = []
        for hand in content:
            cards, bet = hand.split(' ')
            hands.append((cards, int(bet),
                          self.hand_type(self.card_freq(cards)),
                          [self.card_to_value(c) for c in cards]))

        ranked = sorted(hands, key=lambda x: (x[2], x[3]))

        sum = 0
        for i in range(0, len(ranked)):
            sum += (i+1) * ranked[i][1]

        return sum

    def process_2(self, content):
        hands = []
        for hand in content:
            cards, bet = hand.split(' ')
            hands.append((cards, int(bet),
                          self.hand_type_2(self.card_freq(cards)),
                          [self.card_to_value_2(c) for c in cards]))

        ranked = sorted(hands, key=lambda x: (x[2], x[3]))

        sum = 0
        for i in range(0, len(ranked)):
            sum += (i+1) * ranked[i][1]

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
        #  253995462 - too high
