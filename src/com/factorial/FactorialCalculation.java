package com.factorial;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
* This class contain logic for
 * multiplication factorial digits.
* */
class FactorialCalculation {

    /**
     * Calculate factorial product by using classical approach.
     * @param start start digit in factorial part;
     * @param end end digit in factorial part;
     * @return factorial product;
     * */
    final BigInteger calculateClassically(int start, final int end) {
        BigInteger product = BigInteger.ONE;

        while (start < end) {
            start++;
            product = product.multiply(BigInteger.valueOf(start));
        }

        return product;
    }
    /**
     * Calculate factorial product by using parallel stream and reduce.
     * @param end end digit in factorial part;
     * @return factorial product;
    * */
    final BigInteger calculateParallel(final int end) {
        return LongStream.rangeClosed(1, end)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger::multiply)
                .orElse(BigInteger.ONE);
    }

}
