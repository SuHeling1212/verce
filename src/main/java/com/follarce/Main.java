package com.follarce;

import com.follarce.machine.CPU.ALU.module.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== ALU Module Test =====\n");

        testHalfAdder();
        testFullAdder();
        testAdder64B();
        testAdder128B();
        testSubtractor64B();
        testComparator64B();
        testShifter64B();
        testMux2to1();
        testMultiplier64B();
        testDivider64B();

        System.out.println("\n===== All tests completed =====");
    }

    static void testHalfAdder() {
        System.out.println("--- halfAdder test ---");
        boolean[] result = halfAdder.module(false, false);
        System.out.println("0 + 0 = " + (result[0] ? 1 : 0) + ", carry = " + (result[1] ? 1 : 0));

        result = halfAdder.module(true, false);
        System.out.println("1 + 0 = " + (result[0] ? 1 : 0) + ", carry = " + (result[1] ? 1 : 0));

        result = halfAdder.module(true, true);
        System.out.println("1 + 1 = " + (result[0] ? 1 : 0) + ", carry = " + (result[1] ? 1 : 0));
        System.out.println();
    }

    static void testFullAdder() {
        System.out.println("--- fullAdder test ---");
        boolean[] result = fullAdder.module(false, false, false);
        System.out.println("0 + 0 + 0 = " + (result[0] ? 1 : 0) + ", carry = " + (result[1] ? 1 : 0));

        result = fullAdder.module(true, true, false);
        System.out.println("1 + 1 + 0 = " + (result[0] ? 1 : 0) + ", carry = " + (result[1] ? 1 : 0));

        result = fullAdder.module(true, true, true);
        System.out.println("1 + 1 + 1 = " + (result[0] ? 1 : 0) + ", carry = " + (result[1] ? 1 : 0));
        System.out.println();
    }

    static void testAdder64B() {
        System.out.println("--- adder64B test ---");
        boolean[] a = new boolean[64];
        boolean[] b = new boolean[64];
        a[0] = true;
        b[0] = true;
        boolean[] result = adder64B.module(a, b);
        System.out.println("1 + 1 (64-bit) = bit0:" + (result[0] ? 1 : 0) + " bit1:" + (result[1] ? 1 : 0) + " carry:" + result[64]);

        a = new boolean[64];
        b = new boolean[64];
        a[0] = true;
        b[1] = true;
        result = adder64B.module(a, b);
        System.out.println("1 + 2 (64-bit) = bit0:" + (result[0] ? 1 : 0) + " bit1:" + (result[1] ? 1 : 0) + " carry:" + result[64]);
        System.out.println();
    }

    static void testAdder128B() {
        System.out.println("--- adder128B test ---");
        boolean[] a = new boolean[128];
        boolean[] b = new boolean[128];
        a[0] = true;
        b[0] = true;
        boolean[] result = adder128B.module(a, b);
        System.out.println("1 + 1 (128-bit) = bit0:" + (result[0] ? 1 : 0) + " bit1:" + (result[1] ? 1 : 0) + " bit64:" + result[64] + " carry:" + result[128]);

        a = new boolean[128];
        b = new boolean[128];
        for (int i = 0; i < 64; i++) {
            a[i] = true;
            b[i] = true;
        }
        result = adder128B.module(a, b);
        System.out.println("2^64-1 + 2^64-1 (128-bit) = bit0:" + (result[0] ? 1 : 0) + " bit1:" + (result[1] ? 1 : 0) + " bit63:" + (result[63] ? 1 : 0) + " bit64:" + result[64] + " carry:" + result[128]);
        System.out.println();
    }

    static void testSubtractor64B() {
        System.out.println("--- subtractor64B test ---");
        boolean[] a = new boolean[64];
        boolean[] b = new boolean[64];
        a[1] = true;
        b[0] = true;
        boolean[] result = subtractor64B.module(a, b);
        System.out.println("2 - 1 (64-bit) = bit0:" + (result[0] ? 1 : 0) + " bit1:" + (result[1] ? 1 : 0));
        System.out.println();
    }

    static void testComparator64B() {
        System.out.println("--- comparator64B test ---");
        boolean[] a = new boolean[64];
        boolean[] b = new boolean[64];
        a[1] = true;
        b[0] = true;
        System.out.println("unsigned: 2 < 1? " + unsignedComparator64B.module(a, b));
        System.out.println("signed: 2 < 1? " + signedComparator64B.module(a, b));

        a = new boolean[64];
        b = new boolean[64];
        a[0] = true;
        b[1] = true;
        System.out.println("unsigned: 1 < 2? " + unsignedComparator64B.module(a, b));
        System.out.println("signed: 1 < 2? " + signedComparator64B.module(a, b));
        System.out.println();
    }

    static void testShifter64B() {
        System.out.println("--- shifter64B test ---");
        boolean[] in = new boolean[64];
        in[0] = true;
        boolean[] shift = new boolean[64];
        shift[0] = true;
        boolean[] result = shifter64B.module(in, shift);
        System.out.println("shift 1 left by 1 = bit1:" + (result[1] ? 1 : 0));

        shift = new boolean[64];
        shift[1] = true;
        result = shifter64B.module(in, shift);
        System.out.println("shift 1 left by 2 = bit2:" + (result[2] ? 1 : 0));
        System.out.println();
    }

    static void testMux2to1() {
        System.out.println("--- mux2to1 test ---");
        System.out.println("sel=0, a=false, b=true  -> " + mux2to1.module(false, true, false));
        System.out.println("sel=1, a=false, b=true  -> " + mux2to1.module(false, true, true));
        System.out.println("sel=0, a=true, b=false  -> " + mux2to1.module(true, false, false));
        System.out.println("sel=1, a=true, b=false  -> " + mux2to1.module(true, false, true));
        System.out.println();
    }

    static void testMultiplier64B() {
        System.out.println("--- multiplier64B test ---");
        boolean[] a = new boolean[64];
        boolean[] b = new boolean[64];
        a[0] = true;
        b[1] = true;
        boolean[] result = multiplier64B.module(a, b);
        System.out.println("1 * 2 (64-bit) = bit0:" + (result[0] ? 1 : 0) + " bit1:" + (result[1] ? 1 : 0));
        System.out.println();
    }

    static void testDivider64B() {
        System.out.println("--- divider64B test ---");
        
        // Test 6 / 2 = 3
        boolean[] dividend = new boolean[64];
        boolean[] divisor = new boolean[64];
        dividend[1] = true; dividend[2] = true; // 6 = 110b
        divisor[1] = true; // 2 = 10b
        boolean[] result = divider64B.divu(dividend, divisor);
        System.out.println("6 / 2 (64-bit): quotient=" + bitsToInt(result, 0, 64) + " remainder=" + bitsToInt(result, 64, 64));

        // Test 10 / 3 = 3 R 1
        dividend = new boolean[64];
        divisor = new boolean[64];
        dividend[1] = true; dividend[3] = true; // 10 = 1010b
        divisor[0] = true; divisor[1] = true; // 3 = 11b
        result = divider64B.divu(dividend, divisor);
        System.out.println("10 / 3 (64-bit): quotient=" + bitsToInt(result, 0, 64) + " remainder=" + bitsToInt(result, 64, 64));

        // Test 15 / 5 = 3 R 0
        dividend = new boolean[64];
        divisor = new boolean[64];
        dividend[0] = true; dividend[1] = true; dividend[2] = true; dividend[3] = true; // 15 = 1111b
        divisor[0] = true; divisor[2] = true; // 5 = 101b
        result = divider64B.divu(dividend, divisor);
        System.out.println("15 / 5 (64-bit): quotient=" + bitsToInt(result, 0, 64) + " remainder=" + bitsToInt(result, 64, 64));

        // Test 100 / 7 = 14 R 2
        dividend = new boolean[64];
        divisor = new boolean[64];
        dividend[2] = true; dividend[5] = true; dividend[6] = true; // 100 = 1100100b
        divisor[0] = true; divisor[1] = true; divisor[2] = true; // 7 = 111b
        result = divider64B.divu(dividend, divisor);
        System.out.println("100 / 7 (64-bit): quotient=" + bitsToInt(result, 0, 64) + " remainder=" + bitsToInt(result, 64, 64));
        
        System.out.println();
    }

    static long bitsToInt(boolean[] bits, int offset, int len) {
        long result = 0;
        for (int i = 0; i < len && i < 64; i++) {
            if (bits[offset + i]) {
                result |= (1L << i);
            }
        }
        return result;
    }
}
