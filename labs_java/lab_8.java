import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class lab_8 {

    private static final int size = 1_000_000;
    private static final int THRESHOLD = 20;

    public static void main(String[] args) {

        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(101);
        }

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.currentTimeMillis();
        long sum = pool.invoke(new SumTask(array, 0, size));
        long end = System.currentTimeMillis();

        System.out.println("Сума елементів: " + sum);
        System.out.println("Час виконання: " + (end - start) + " ms");
    }

    static class SumTask extends RecursiveTask<Long> {

        private final int[] array;
        private final int start;
        private final int end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;

            if (length <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            }

            int mid = start + length / 2;

            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);

            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();

            return leftResult + rightResult;
        }
    }
}
