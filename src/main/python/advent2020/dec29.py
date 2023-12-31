
def _int_tuple(*i):
    return tuple(int(_) for _ in i)

print(_int_tuple(0, 2,3))

n = 2344234
print(n & 0xff)

small_trailing = [0] * 256
for j in range(1,8):
    small_trailing[1<<j::1<<(j+1)] = [j] * (1<<(7-j))
    
print(small_trailing)
j = 1
#print(1<<(j+1))

print(pow())

from sympy.ntheory.primetest import isprime

