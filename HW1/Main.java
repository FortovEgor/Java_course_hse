public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        factorial(3);
    }

    static void factorial(int num) {
        // функция печатает факториал аргумента
        int result = 1;
        for (int i = 2; i <= num; ++i) {
            result *= i;
        }
        System.out.println(result);
    }
}