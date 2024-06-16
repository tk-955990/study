public class fizzbuzz {
    public static void main(String[] args) {
        for (int i = 1; i < 101; i++) {
            if (i % 10 == 0) {
                check(i);
                System.out.println(" ");
            } else {
                check(i);
            }
        }
    }
    static void check(int count) {
        if (count % 15 == 0) {
            System.out.print("FizzBuzz!!  ");
        } else if (count % 5 == 0) {
            System.out.print("fizz  ");
        } else if (count % 3 == 0) {
            System.out.print("buzz  ");
        } else {
            System.out.print(count + "  ");
        }
    }
}
