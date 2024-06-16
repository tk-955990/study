
def check(i):
    if i % 15 == 0:
        print("FizzBuzz!!", end=" ")
    elif i % 5 == 0:
        print("fizz", end=" ")
    elif i % 3 == 0:
        print("buzz", end=" ")
    else:
        print(i, end=" ")


def roop():
    for i in range(1, 51):
        if i % 10 == 0:
            check(i)
            print("\n")
        else:
            check(i)
