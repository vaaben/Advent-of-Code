import sys
from collections import namedtuple
from copy import deepcopy


class AoC:

    Spell = namedtuple('spell', "name, cost, damage, heal, armor, mana, duration")
    spells = {
        Spell('missile',   53, 4, 0, 0,   0, 0),
        Spell('drain',     73, 2, 2, 0,   0, 0),
        Spell('shield',   113, 0, 0, 7,   0, 6),
        Spell('poison',   173, 3, 0, 0,   0, 6),
        Spell('recharge', 229, 0, 0, 0, 101, 5)
    }

    def read_input(self, filename):
        with open(filename) as f:
            content = f.readlines()
        return [x.strip() for x in content]

    def battle(self, hard_mode, boss_hp, boss_dammage):

        def apply_effects(s, boss_turn=False):
            for spell in s['active_spells']:
                s['boss_hp'] -= spell.damage
                s['mana'] += spell.mana
                s['hp'] += spell.heal
                if spell.armor > 0 and boss_turn: s['hp'] += 7
            s['active_spells'] = {spell: dur-1 for spell, dur in s['active_spells'].items() if dur > 1}

        states = [dict(mana=500, hp=50, boss_hp=boss_hp, active_spells=dict(), spent_mana=0)]
        result = 9999

        while states:
            s = states.pop()
            s['hp'] -= hard_mode
            if s['hp'] <= 0:
                continue

            apply_effects(s)
            if s['boss_hp'] <= 0:
                result = min(result, s['spent_mana'])
                continue

            for spell in self.spells:
                if (spell.cost <= s['mana'] and
                        s['spent_mana'] + spell.cost < result and
                        spell not in s['active_spells']):
                    new_s = deepcopy(s)
                    new_s['mana'] -= spell.cost
                    new_s['spent_mana'] += spell.cost
                    new_s['active_spells'][spell] = spell.duration
                    apply_effects(new_s, boss_turn=True)
                    if new_s['boss_hp'] <= 0:
                        result = min(result, new_s['spent_mana'])
                        continue

                    new_s['hp'] -= boss_dammage
                    if new_s['hp'] > 0:
                        states.append(new_s)
        return result

    def process(self, content, mode):
        boss_hp = int(content[0].split(': ')[1])
        boss_dammage = int(content[1].split(': ')[1])
        return self.battle(hard_mode=mode, boss_hp=boss_hp, boss_dammage=boss_dammage)

    def part1(self, filename):
        return self.process(self.read_input(filename), False)

    def part2(self, filename):
        return self.process(self.read_input(filename), True)


if __name__ == '__main__':
    args = sys.argv[1:]
    if args[0] == 'part1':
        print(f'sum: {AoC().part1(args[1])}')
    else:
        print(f'sum: {AoC().part2(args[1])}')
