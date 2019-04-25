package com.factorial;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * This class run factorial calculation by different types
 * and output the time costs to console.
* */
public final class Application {
    /**
     * Scanner for reading from the console.
     * */
    private static Scanner scannerInt = new Scanner(System.in);
    /**
     * Count of Thread for thread factorial calculation.
     * */
    private static final Integer COUNT_OF_THREADS =
            Runtime.getRuntime().availableProcessors();

    /**
     * The private Application constructor.
     * */
    private Application() {
    }

    /**
     * Run factorial execution by each methods
     * and output results to the console.
     * */
    public static void main(final String[] args) {

        ThreadsFactorialExecutor calculation =
                new ThreadsFactorialExecutor(COUNT_OF_THREADS);
        BigInteger factorial;
        int digit = getNumber();

            long startTime = System.nanoTime();
            factorial = calculation.calculate(digit);
            long timeThreads = System.nanoTime() - startTime;
            System.out.println(factorial);

            startTime = System.nanoTime();
            FactorialCalculation calculator = new FactorialCalculation();
            factorial = calculator.calculateClassically(1, digit);
            long timeClassic = System.nanoTime() - startTime;
            System.out.println(factorial);

            startTime = System.nanoTime();
            FactorialCalculation calculatorStream = new FactorialCalculation();
            factorial = calculatorStream.calculateParallel(digit);
            long timeStream = System.nanoTime() - startTime;
            System.out.println(factorial + "\n");

            System.out.println(
                    "Classic Factorial FactorialCalculation Time:          "
                    + timeClassic);
            System.out.println(
                    "Threads Factorial FactorialCalculation Time:          "
                    + timeThreads);
            System.out.println(
                    "Parallel Streams Factorial FactorialCalculation Time: "
                    + timeStream);
    }

    /**
     * Read factorial number from the console.
     * @return factorial digit;
    **/
    private static int getNumber() {
        System.out.println("Enter the number");
        return scannerInt.nextInt();
    }
}
