package com.follarce;

import com.follarce.machine.CPU.ALU.RV64IMC.*;
import java.math.BigInteger;

public class Main {

    public static boolean[] longToBits(long value) {
        boolean[] bits = new boolean[64];
        for (int i = 0; i < 64; i++) {
            bits[i] = ((value >> i) & 1) == 1;
        }
        return bits;
    }

    public static long bitsToSignedLong(boolean[] bits) {
        long result = 0;
        for (int i = 0; i < 64; i++) {
            if (bits[i]) {
                result |= (1L << i);
            }
        }
        return result;
    }

    public static BigInteger bitsToUnsignedBigInt(boolean[] bits) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                result = result.setBit(i);
            }
        }
        return result;
    }

    public static String bitsToHex(boolean[] bits) {
        return bitsToUnsignedBigInt(bits).toString(16);
    }

    // ========== 有符号除法 ==========
    public static void testDivide(long dividend, long divisor) {
        System.out.println("----------------------------------------");
        System.out.println("有符号除法: " + dividend + " ÷ " + divisor);

        boolean[] result = div64B.module(longToBits(dividend), longToBits(divisor));
        long actual = bitsToSignedLong(result);

        long expected;
        if (divisor == 0) {
            expected = -1;
        } else if (dividend == Long.MIN_VALUE && divisor == -1) {
            expected = Long.MIN_VALUE;
        } else {
            expected = dividend / divisor;
        }

        System.out.println("预期商: " + expected + " (0x" + Long.toHexString(expected) + ")");
        System.out.println("实际商: " + actual + " (0x" + Long.toHexString(actual) + ")");

        if (expected == actual) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    // ========== 无符号除法 ==========
    public static void testDivideUnsigned(long dividend, long divisor) {
        System.out.println("----------------------------------------");
        BigInteger udiv = bitsToUnsignedBigInt(longToBits(dividend));
        BigInteger udivisor = bitsToUnsignedBigInt(longToBits(divisor));
        System.out.println("无符号除法: " + udiv + " ÷ " + udivisor);

        boolean[] result128 = divu64B.module(longToBits(dividend), longToBits(divisor));
        boolean[] quotient = new boolean[64];
        for (int i = 0; i < 64; i++) quotient[i] = result128[i];
        long actualQ = bitsToSignedLong(quotient);

        BigInteger expectedQ;
        if (divisor == 0) {
            expectedQ = BigInteger.ONE.shiftLeft(64).subtract(BigInteger.ONE);
        } else {
            expectedQ = udiv.divide(udivisor);
        }

        System.out.println("预期商: " + expectedQ);
        System.out.println("实际商: " + bitsToUnsignedBigInt(quotient));

        if (expectedQ.equals(bitsToUnsignedBigInt(quotient))) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    // ========== 有符号余数 ==========
    public static void testRemainder(long dividend, long divisor) {
        System.out.println("----------------------------------------");
        System.out.println("有符号余数: " + dividend + " % " + divisor);

        boolean[] result = rem64B.module(longToBits(dividend), longToBits(divisor));
        long actual = bitsToSignedLong(result);

        long expected;
        if (divisor == 0) {
            expected = dividend;
        } else if (dividend == Long.MIN_VALUE && divisor == -1) {
            expected = 0;
        } else {
            expected = dividend % divisor;
        }

        System.out.println("预期余数: " + expected + " (0x" + Long.toHexString(expected) + ")");
        System.out.println("实际余数: " + actual + " (0x" + Long.toHexString(actual) + ")");

        if (expected == actual) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    // ========== 无符号余数 ==========
    public static void testRemainderUnsigned(long dividend, long divisor) {
        System.out.println("----------------------------------------");
        BigInteger udiv = bitsToUnsignedBigInt(longToBits(dividend));
        BigInteger udivisor = bitsToUnsignedBigInt(longToBits(divisor));
        System.out.println("无符号余数: " + udiv + " % " + udivisor);

        boolean[] result = remu64B.module(longToBits(dividend), longToBits(divisor));
        long actual = bitsToSignedLong(result);

        BigInteger expected;
        if (divisor == 0) {
            expected = udiv;
        } else {
            expected = udiv.remainder(udivisor);
        }

        System.out.println("预期余数: " + expected);
        System.out.println("实际余数: " + bitsToUnsignedBigInt(result));

        if (expected.equals(bitsToUnsignedBigInt(result))) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    // ========== 64位加法 ==========
    public static void testAdd(long a, long b) {
        System.out.println("----------------------------------------");
        System.out.println("加法: " + a + " + " + b);

        boolean[] sum65 = adder64B.module(longToBits(a), longToBits(b));
        boolean[] low64 = new boolean[64];
        for (int i = 0; i < 64; i++) low64[i] = sum65[i];
        long actual = bitsToSignedLong(low64);
        long expected = a + b;

        System.out.println("预期: " + expected + " (0x" + Long.toHexString(expected) + ")");
        System.out.println("实际: " + actual + " (0x" + Long.toHexString(actual) + ")");
        System.out.println("进位: " + sum65[64]);

        if (expected == actual) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    // ========== 64位减法 ==========
    public static void testSub(long a, long b) {
        System.out.println("----------------------------------------");
        System.out.println("减法: " + a + " - " + b);

        boolean[] diff65 = subtractor64B.module(longToBits(a), longToBits(b));
        boolean[] low64 = new boolean[64];
        for (int i = 0; i < 64; i++) low64[i] = diff65[i];
        long actual = bitsToSignedLong(low64);
        long expected = a - b;

        System.out.println("预期: " + expected + " (0x" + Long.toHexString(expected) + ")");
        System.out.println("实际: " + actual + " (0x" + Long.toHexString(actual) + ")");
        System.out.println("借位: " + diff65[64]);

        if (expected == actual) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    // ========== 64位乘法 ==========
    public static void testMultiply(long a, long b) {
        System.out.println("----------------------------------------");
        System.out.println("乘法: " + a + " × " + b);

        boolean[] result = multiplier64B.module(longToBits(a), longToBits(b));
        long actualLow64 = 0;
        for (int i = 0; i < 64; i++) {
            if (result[i]) {
                actualLow64 |= (1L << i);
            }
        }
        long expectedLow64 = a * b;

        System.out.println("预期低64位: " + expectedLow64 + " (0x" + Long.toHexString(expectedLow64) + ")");
        System.out.println("实际低64位: " + actualLow64 + " (0x" + Long.toHexString(actualLow64) + ")");

        if (expectedLow64 == actualLow64) {
            System.out.println("✅ 通过");
        } else {
            System.out.println("❌ 失败");
        }
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    RV64 门电路模块测试");
        System.out.println("========================================\n");

        // ========== 有符号除法 ==========
        System.out.println("========== 有符号除法 (div64B) ==========\n");
        testDivide(0, 1);
        testDivide(1, 1);
        testDivide(10, 3);
        testDivide(100, 7);
        testDivide(-10, 3);
        testDivide(10, -3);
        testDivide(-10, -3);
        testDivide(-1, 1);
        testDivide(1, -1);
        testDivide(0x7FFFFFFFFFFFFFFFL, 2);
        testDivide(0x7FFFFFFFFFFFFFFFL, -1);
        testDivide(Long.MIN_VALUE, 1);
        testDivide(Long.MIN_VALUE, -1);
        testDivide(42, 0);
        testDivide(0, 0);

        // ========== 无符号除法 ==========
        System.out.println("\n========== 无符号除法 (divu64B) ==========\n");
        testDivideUnsigned(100, 7);
        testDivideUnsigned(0xFFFFFFFFFFFFFFFFL, 2);
        testDivideUnsigned(0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL);
        testDivideUnsigned(1L << 60, 3);
        testDivideUnsigned(42, 0);
        testDivideUnsigned(0, 0);

        // ========== 有符号余数 ==========
        System.out.println("\n========== 有符号余数 (rem64B) ==========\n");
        testRemainder(10, 3);
        testRemainder(100, 7);
        testRemainder(-10, 3);
        testRemainder(10, -3);
        testRemainder(-10, -3);
        testRemainder(17, 5);
        testRemainder(42, 0);
        testRemainder(Long.MIN_VALUE, -1);

        // ========== 无符号余数 ==========
        System.out.println("\n========== 无符号余数 (remu64B) ==========\n");
        testRemainderUnsigned(100, 7);
        testRemainderUnsigned(0xFFFFFFFFFFFFFFFFL, 3);
        testRemainderUnsigned(1L << 60, 7);
        testRemainderUnsigned(42, 0);

        // ========== 加法 ==========
        System.out.println("\n========== 64位加法 (adder64B) ==========\n");
        testAdd(0, 0);
        testAdd(1, 1);
        testAdd(100, 200);
        testAdd(-1, 1);
        testAdd(-100, 200);
        testAdd(Long.MAX_VALUE, 1);
        testAdd(Long.MIN_VALUE, -1);
        testAdd(0xFFFFFFFFFFFFFFFFL, 1);

        // ========== 减法 ==========
        System.out.println("\n========== 64位减法 (subtractor64B) ==========\n");
        testSub(10, 3);
        testSub(100, 200);
        testSub(0, 1);
        testSub(-1, -1);
        testSub(Long.MIN_VALUE, 1);
        testSub(Long.MAX_VALUE, -1);
        testSub(0, Long.MIN_VALUE);

        // ========== 乘法 ==========
        System.out.println("\n========== 64位乘法 (multiplier64B) ==========\n");
        testMultiply(0, 0);
        testMultiply(1, 1);
        testMultiply(2, 3);
        testMultiply(5, 7);
        testMultiply(1, -1);
        testMultiply(-1, -1);
        testMultiply(0x7FFFFFFFFFFFFFFFL, 2);
        testMultiply(1L << 30, 1L << 30);

        // ========== 随机测试 ==========
        System.out.println("\n========== 随机测试 ==========\n");
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < 10; i++) {
            long a = rand.nextLong();
            long b = rand.nextLong();
            testDivide(a, b != 0 ? b : 1);
        }
        for (int i = 0; i < 5; i++) {
            long a = rand.nextLong();
            long b = rand.nextLong();
            testAdd(a, b);
        }
        for (int i = 0; i < 5; i++) {
            long a = rand.nextLong();
            long b = rand.nextLong();
            testMultiply(a, b);
        }

        System.out.println("\n========================================");
        System.out.println("    测试完成");
        System.out.println("========================================");
    }
}