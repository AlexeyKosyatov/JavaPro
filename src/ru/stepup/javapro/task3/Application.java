package src.ru.stepup.javapro.task3;

import java.math.BigInteger;

public class Application {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(3);

        for (int i = 0; i < 10; i++) {
            R1 r = new R1(i);
            threadPool.execute(r);
        }

        threadPool.shutdown();
        threadPool.awaitTermination();
    }


    static class R1 implements Runnable {
        private int number;
        public R1(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            BigInteger a = BigInteger.valueOf(0);
            for(int i = 0; i < 10000; i++) {
                for(int j = 0; j < 10000; j++) {
                    a = a.add(BigInteger.valueOf(i * (long)j));
                }
            }
            System.out.println("Задача " + number + " " + a + " завершена: " + Thread.currentThread().getName());
        }
    }
}
