package com.follarce.machine.CPU.ALU.RV64AFD;

public class fmul32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        if (futils32B.isNaN(a) || futils32B.isNaN(b)) {
            return futils32B.getNaN();
        }
        
        if (futils32B.isZero(a) || futils32B.isZero(b)) {
            boolean[] zero = new boolean[32];
            zero[0] = futils32B.getSign(a) ^ futils32B.getSign(b);
            return zero;
        }
        
        if (futils32B.isInfinity(a) || futils32B.isInfinity(b)) {
            if (futils32B.isZero(a) || futils32B.isZero(b)) {
                return futils32B.getNaN();
            }
            boolean sign = futils32B.getSign(a) ^ futils32B.getSign(b);
            if (sign) {
                return futils32B.getNegativeInfinity();
            } else {
                return futils32B.getPositiveInfinity();
            }
        }
        
        boolean resultSign = futils32B.getSign(a) ^ futils32B.getSign(b);
        boolean[] expA = futils32B.getExponent(a);
        boolean[] expB = futils32B.getExponent(b);
        boolean[] mantA = futils32B.getMantissa(a);
        boolean[] mantB = futils32B.getMantissa(b);
        
        int expAInt = futils32B.exponentToInt(expA);
        int expBInt = futils32B.exponentToInt(expB);
        
        int resultExp = expAInt + expBInt - 127;
        
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
        
        boolean[] product = multiply48(mantissaAWithOne, mantissaBWithOne);
        
        boolean[] finalMantissa = new boolean[23];
        if (product[0]) {
            for (int i = 0; i < 23; i++) {
                finalMantissa[i] = product[i + 1];
            }
            resultExp++;
        } else {
            for (int i = 0; i < 23; i++) {
                finalMantissa[i] = product[i + 2];
            }
        }
        
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
    
    private static boolean[] multiply48(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[48];
        for (int i = 0; i < 24; i++) {
            if (b[i]) {
                boolean[] shifted = new boolean[48];
                for (int j = 0; j < 24; j++) {
                    shifted[j + i] = a[j];
                }
                result = add48(result, shifted);
            }
        }
        return result;
    }
    
    private static boolean[] add48(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[48];
        boolean carry = false;
        for (int i = 47; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }
}