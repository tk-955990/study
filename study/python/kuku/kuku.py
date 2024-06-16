
def check(j):
    if (j % 9 == 0):
        print("\n")


def roop():
    for i in range(1, 10):
        for j in range(1, 10):
            print('{:2}'.format(i*j), end=" ")

          #  print(i * j, end=" ")
            check(j)


roop()
