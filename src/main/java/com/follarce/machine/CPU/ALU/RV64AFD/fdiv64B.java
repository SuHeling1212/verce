package com.follarce.machine.CPU.ALU.RV64AFD;

public class fdiv64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        if (futils64B.isNaN(a) || futils64B.isNaN(b)) {
            return futils64B.getNaN();
        }
        
        if (futils64B.isZero(b)) {
            boolean sign = futils64B.getSign(a) ^ futils64B.getSign(b);
            if (futils64B.isZero(a)) {
                return futils64B.getNaN();
            } else if (sign) {
                return futils64B.getNegativeInfinity();
            } else {
                return futils64B.getPositiveInfinity();
            }
        }
        
        if (futils64B.isZero(a)) {
            boolean[] zero = new boolean[64];
            zero[0] = futils64B.getSign(a) ^ futils64B.getSign(b);
            return zero;
        }
        
        if (futils64B.isInfinity(a)) {
            boolean sign = futils64B.getSign(a) ^ futils64B.getSign(b);
            if (sign) {
                return futils64B.getNegativeInfinity();
            } else {
                return futils64B.getPositiveInfinity();
            }
        }
        
        if (futils64B.isInfinity(b)) {
            boolean[] zero = new boolean[64];
            zero[0] = futils64B.getSign(a) ^ futils64B.getSign(b);
            return zero;
        }
        
        boolean resultSign = futils64B.getSign(a) ^ futils64B.getSign(b);
        boolean[] expA = futils64B.getExponent(a);
        boolean[] expB = futils64B.getExponent(b);
        boolean[] mantA = futils64B.getMantissa(a);
        boolean[] mantB = futils64B.getMantissa(b);
        
        int expAInt = futils64B.exponentToInt(expA);
        int expBInt = futils64B.exponentToInt(expB);
        
        int resultExp = expAInt - expBInt + 1023;
        
        boolean[] mantissaAWithOne = new boolean[53];
        mantissaAWithOne[0] = true;
        for (int i = 0; i < 52; i++) {
            mantissaAWithOne[i + 1] = mantA[i];
        }
        
        boolean[] mantissaBWithOne = new boolean[53];
        mantissaBWithOne[0] = true;
        for (int i = 0; i < 52; i++) {
            mantissaBWithOne[i + 1] = mantB[i];
        }
        
        boolean[] quotient = divide53(mantissaAWithOne, mantissaBWithOne);
        
        boolean[] finalMantissa = new boolean[52];
        for (int i = 0; i < 52; i++) {
            if (i + 1 < 53) {
                finalMantissa[i] = quotient[i + 1];
            } else {
                finalMantissa[i] = false;
            }
        }
        
        if (resultExp > 2047) {
            if (resultSign) {
                return futils64B.getNegativeInfinity();
            } else {
                return futils64B.getPositiveInfinity();
            }
        } else if (resultExp < 0) {
            boolean[] zero = new boolean[64];
            zero[0] = resultSign;
            return zero;
        }
        
        boolean[] finalExpBits = futils64B.intToExponent(resultExp);
        return futils64B.createDouble(resultSign, finalExpBits, finalMantissa);
    }
    
    private static boolean[] divide53(boolean[] dividend, boolean[] divisor) {
        boolean[] quotient = new boolean[53];
        boolean[] remainder = new boolean[54];
        
        for (int i = 0; i < 53; i++) {
            for (int j = 53; j > 0; j--) {
                remainder[j] = remainder[j - 1];
            }
            remainder[0] = dividend[i];
            
            if (compare54(remainder, divisor) >= 0) {
                quotient[i] = true;
                remainder = subtract54(remainder, divisor);
            } else {
                quotient[i] = false;
            }
        }
        
        return quotient;
    }
    
    private static int compare54(boolean[] a, boolean[] b) {
        for (int i = 0; i < 53; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }
    
    private static boolean[] subtract54(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[54];
        boolean borrow = false;
        for (int i = 53; i >= 0; i--) {
            boolean bitB = (i < 53) ? b[i] : false;
            boolean sub = a[i] ^ bitB ^ borrow;
            borrow = (!a[i] && (bitB || borrow)) || (bitB && borrow);
            result[i] = sub;
        }
        return result;
    }
}