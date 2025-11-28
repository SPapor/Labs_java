import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class lab_7 {

    @FunctionalInterface
    interface PrimeChecker extends IntPredicate {}

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2 || n == 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        return IntStream.rangeClosed(5, (int) Math.sqrt(n))
                .filter(i -> i % 6 == 1 || i % 6 == 5)
                .noneMatch(i -> n % i == 0);
    }

    private static int reverse(int n) {
        String normal = String.valueOf(n);
        String reversed = new StringBuilder(normal).reverse().toString();
        return Integer.parseInt(reversed);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть натуральне число: ");
        int num = sc.nextInt();

        if (num > 1000 || num < 1) {
            System.out.println("Число має бути від 1 до 1000!");
            return;
        }

        PrimeChecker checker = lab_7::isPrime;

        boolean isEmirp = checker.test(num) &&
                num != reverse(num) &&
                checker.test(reverse(num));

        System.out.println(isEmirp
                ? num + " є надпростим числом!"
                : num + " Не є надпростим числом.");
    }
}