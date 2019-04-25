package com.factorial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class a calculate factorial
 * with using CompletableFuture framework.
 **/
class ThreadsFactorialExecutor {
    /**
     * Count of threads.
     **/
    private Integer threads;
    /**
     * ThreadsFactorialExecutor constructor.
     * @param countOfThreads count of threads;
    * */
    ThreadsFactorialExecutor(final Integer countOfThreads) {
        this.threads = countOfThreads;
    }
    /**
     * Create a completable future tasks and execute that.
     * @param digit factorial digit;
     * @return factorial result;
     * */
    BigInteger calculate(final Integer digit) {
        List<CompletableFuture<BigInteger>> futuresPartsOfFactorial
                = new ArrayList<>();

        int increment = digit / threads;
        int next = increment;
        int start = 1;

        for (int i = 0; i < threads; i++) {
            if ((threads + next) > digit) {
                next = digit;
            }
            futuresPartsOfFactorial.add(createThreadCalculator(start, next));
            start = next;
            next = next + increment;
        }

        CompletableFuture<BigInteger> result =
                executeCalculation(futuresPartsOfFactorial);

        return result.join();
    }
    /**
     * Create a completable future task.
     * @param startDigit start digit for factorial part;
     * @param endDigit end digit for factorial part;
     * @return completable future task;
     * */
    private CompletableFuture<BigInteger> createThreadCalculator(
            final int startDigit, final int endDigit) {
        return CompletableFuture.supplyAsync(() -> new FactorialCalculation()
                .calculateClassically(startDigit, endDigit));
    }
    /**
     * Execute futures tasks.
     * @param futuresPartsOfFactorial list of completable futures;
     * @return completable future result;
     * */
    private CompletableFuture<BigInteger> executeCalculation(
            final List<CompletableFuture<BigInteger>> futuresPartsOfFactorial) {
        return  CompletableFuture.allOf(futuresPartsOfFactorial
                .toArray(new CompletableFuture[threads]))
                .thenApply(r -> futuresPartsOfFactorial.stream()
                        .map(CompletableFuture::join)
                        .reduce(BigInteger::multiply)
                        .orElse(BigInteger.ONE));
    }

}
