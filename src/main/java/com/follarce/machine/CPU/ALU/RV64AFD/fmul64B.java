package com.follarce.machine.CPU.ALU.RV64AFD;

public class fmul64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        if (futils64B.isNaN(a) || futils64B.isNaN(b)) {
            return futils64B.getNaN();
        }
        
        if (futils64B.isZero(a) || futils64B.isZero(b)) {
            boolean[] zero = new boolean[64];
            zero[0] = futils64B.getSign(a) ^ futils64B.getSign(b);
            return zero;
        }
        
        if (futils64B.isInfinity(a) || futils64B.isInfinity(b)) {
            boolean sign = futils64B.getSign(a) ^ futils64B.getSign(b);
            if (sign) {
                return futils64B.getNegativeInfinity();
            } else {
                return futils64B.getPositiveInfinity();
            }
        }
        
        boolean resultSign = futils64B.getSign(a) ^ futils64B.getSign(b);
        boolean[] expA = futils64B.getExponent(a);
        boolean[] expB = futils64B.getExponent(b);
        boolean[] mantA = futils64B.getMantissa(a);
        boolean[] mantB = futils64B.getMantissa(b);
        
        int expAInt = futils64B.exponentToInt(expA);
        int expBInt = futils64B.exponentToInt(expB);
        
        int resultExp = expAInt + expBInt - 1023;
        
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
        
        boolean[] product = multiply106(mantissaAWithOne, mantissaBWithOne);
        
        boolean[] finalMantissa = new boolean[52];
        if (product[0]) {
            for (int i = 0; i < 52; i++) {
                finalMantissa[i] = product[i + 1];
            }
            resultExp++;
        } else {
            for (int i = 0; i < 52; i++) {
                finalMantissa[i] = product[i + 2];
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
    
    private static boolean[] multiply106(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[106];
        for (int i = 0; i < 53; i++) {
            if (b[i]) {
                boolean[] shifted = new boolean[106];
                for (int j = 0; j < 53; j++) {
                    shifted[j + i] = a[j];
                }
                result = add106(result, shifted);
            }
        }
        return result;
    }
    
    private static boolean[] add106(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[106];
        boolean carry = false;
        for (int i = 105; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }
}