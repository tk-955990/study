public class kuku {
    public static void main(String[] args) {

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                String str = String.format("%2d", i * j);

                System.out.print(str);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}