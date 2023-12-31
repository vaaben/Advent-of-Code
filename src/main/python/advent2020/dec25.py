from sympy.ntheory.residue_ntheory import discrete_log

card_pub = 17115212
door_pub = 3667832
modulus = 20201227

loops = discrete_log(modulus, card_pub, 7)
print(loops)
print(pow(door_pub, loops, modulus))
#15467093