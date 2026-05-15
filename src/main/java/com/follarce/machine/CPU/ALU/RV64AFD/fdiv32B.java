package com.follarce.machine.CPU.ALU.RV64AFD;

public class fdiv32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        if (futils32B.isNaN(a) || futils32B.isNaN(b)) {
            return futils32B.getNaN();
        }
        
        if (futils32B.isZero(b)) {
            if (futils32B.isZero(a)) {
                return futils32B.getNaN();
            }
            boolean sign = futils32B.getSign(a);
            if (sign) {
                return futils32B.getNegativeInfinity();
            } else {
                return futils32B.getPositiveInfinity();
            }
        }
        
        if (futils32B.isZero(a)) {
            boolean[] zero = new boolean[32];
            zero[0] = futils32B.getSign(a) ^ futils32B.getSign(b);
            return zero;
        }
        
        if (futils32B.isInfinity(a)) {
            boolean sign = futils32B.getSign(a) ^ futils32B.getSign(b);
            if (sign) {
                return futils32B.getNegativeInfinity();
            } else {
                return futils32B.getPositiveInfinity();
            }
        }
        
        if (futils32B.isInfinity(b)) {
            boolean[] zero = new boolean[32];
            zero[0] = futils32B.getSign(a) ^ futils32B.getSign(b);
            return zero;
        }
        
        boolean resultSign = futils32B.getSign(a) ^ futils32B.getSign(b);
        boolean[] expA = futils32B.getExponent(a);
        boolean[] expB = futils32B.getExponent(b);
        boolean[] mantA = futils32B.getMantissa(a);
        boolean[] mantB = futils32B.getMantissa(b);
        
        int expAInt = futils32B.exponentToInt(expA);
        int expBInt = futils32B.exponentToInt(expB);
        
        int resultExp = expAInt - expBInt + 127;
        
        boolean[] mantissaAWithOne = new boolean[24];
        mantissaAWithOne[0] = true;
        for (int i = 0; i < 23; i++) {
            mantissaAWithOne[i + 1] = mantA[i];
        }
        
        boolean[] mantissaBWithOne = new boolean[24];
        mantissaBWithOne[0] = true;
        for (int i = 0; i < 23; i++) {
            mantissaBWithOne[i + 1] = mantB[i];
        }
        
        boolean[] quotient = divide24(mantissaAWithOne, mantissaBWithOne);
        
        int shift = 0;
        while (shift < 23 && !quotient[shift]) {
            shift++;
        }
        
        if (shift == 23) {
            boolean[] zero = new boolean[32];
            zero[0] = resultSign;
            return zero;
        }
        
        boolean[] finalMantissa = new boolean[23];
        for (int i = 0; i < 23; i++) {
            if (i + shift < 23) {
                finalMantissa[i] = quotient[i + shift];
            } else {
                finalMantissa[i] = false;
            }
        }
        
        resultExp -= shift;
        
        if (resultExp > 255) {
            if (resultSign) {
                return futils32B.getNegativeInfinity();
            } else {
                return futils32B.getPositiveInfinity();
            }
        } else if (resultExp < 0) {
            boolean[] zero = new boolean[32];
            zero[0] = resultSign;
            return zero;
        }
        
        boolean[] finalExpBits = futils32B.intToExponent(resultExp);
        return futils32B.createFloat(resultSign, finalExpBits, finalMantissa);
    }
    
    private static boolean[] divide24(boolean[] dividend, boolean[] divisor) {
        boolean[] quotient = new boolean[24];
        boolean[] remainder = new boolean[25];
        
        for (int i = 0; i < 24; i++) {
            remainder = shiftLeft25(remainder);
            remainder[24] = dividend[i];
            
            if (compare25(remainder, divisor) >= 0) {
                quotient[i] = true;
                remainder = subtract25(remainder, divisor);
            } else {
                quotient[i] = false;
            }
        }
        return quotient;
    }
    
    private static boolean[] shiftLeft25(boolean[] bits) {
        boolean[] result = new boolean[25];
        for (int i = 0; i < 24; i++) {
            result[i] = bits[i + 1];
        }
        result[24] = false;
        return result;
    }
    
    private static boolean[] subtract25(boolean[] a, boolean[] b) {
        boolean[] bComplement = twosComplement25(b);
        return add25(a, bComplement);
    }
    
    private static boolean[] twosComplement25(boolean[] bits) {
        boolean[] result = new boolean[25];
        for (int i = 0; i < 24; i++) {
            result[i] = !bits[i];
        }
        result[24] = false;
        
        boolean carry = true;
        for (int i = 24; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }
    
    private static boolean[] add25(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[25];
        boolean carry = false;
        for (int i = 24; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }
    
    private static int compare25(boolean[] a, boolean[] b) {
        for (int i = 0; i < 24; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }
}