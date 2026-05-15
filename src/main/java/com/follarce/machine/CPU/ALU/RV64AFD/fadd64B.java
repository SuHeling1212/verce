package com.follarce.machine.CPU.ALU.RV64AFD;

public class fadd64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        if (futils64B.isNaN(a) || futils64B.isNaN(b)) {
            return futils64B.getNaN();
        }
        
        if (futils64B.isInfinity(a) && futils64B.isInfinity(b)) {
            if (futils64B.getSign(a) == futils64B.getSign(b)) {
                return futils64B.copy(a);
            } else {
                return futils64B.getNaN();
            }
        }
        
        if (futils64B.isInfinity(a)) {
            return futils64B.copy(a);
        }
        
        if (futils64B.isInfinity(b)) {
            return futils64B.copy(b);
        }
        
        if (futils64B.isZero(a)) {
            return futils64B.copy(b);
        }
        
        if (futils64B.isZero(b)) {
            return futils64B.copy(a);
        }
        
        boolean signA = futils64B.getSign(a);
        boolean signB = futils64B.getSign(b);
        boolean[] expA = futils64B.getExponent(a);
        boolean[] expB = futils64B.getExponent(b);
        boolean[] mantA = futils64B.getMantissa(a);
        boolean[] mantB = futils64B.getMantissa(b);
        
        int expAInt = futils64B.exponentToInt(expA);
        int expBInt = futils64B.exponentToInt(expB);
        
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
        
        int expDiff = expAInt - expBInt;
        boolean[] largerMantissa;
        boolean[] smallerMantissa;
        int largerExp;
        boolean resultSign;
        
        if (expDiff > 0) {
            largerMantissa = mantissaAWithOne;
            smallerMantissa = shiftRight53(mantissaBWithOne, expDiff);
            largerExp = expAInt;
            resultSign = signA;
        } else if (expDiff < 0) {
            largerMantissa = mantissaBWithOne;
            smallerMantissa = shiftRight53(mantissaAWithOne, -expDiff);
            largerExp = expBInt;
            resultSign = signB;
        } else {
            largerMantissa = mantissaAWithOne;
            smallerMantissa = mantissaBWithOne;
            largerExp = expAInt;
            resultSign = signA;
        }
        
        boolean[] sumMantissa;
        
        if (signA == signB) {
            sumMantissa = add53(largerMantissa, smallerMantissa);
        } else {
            int compare = compare53(largerMantissa, smallerMantissa);
            if (compare > 0) {
                sumMantissa = subtract53(largerMantissa, smallerMantissa);
            } else if (compare < 0) {
                sumMantissa = subtract53(smallerMantissa, largerMantissa);
                resultSign = !resultSign;
            } else {
                boolean[] zero = new boolean[64];
                return zero;
            }
        }
        
        int finalExp = largerExp;
        boolean[] finalMantissa = new boolean[52];
        
        if (sumMantissa[0]) {
            for (int i = 0; i < 52; i++) {
                finalMantissa[i] = sumMantissa[i + 1];
            }
            finalExp++;
        } else {
            int shift = 0;
            while (shift < 52 && !sumMantissa[shift + 1]) {
                shift++;
            }
            if (shift == 52) {
                boolean[] zero = new boolean[64];
                return zero;
            }
            for (int i = 0; i < 52; i++) {
                if (i + shift + 1 < 53) {
                    finalMantissa[i] = sumMantissa[i + shift + 1];
                } else {
                    finalMantissa[i] = false;
                }
            }
            finalExp -= shift;
        }
        
        if (finalExp > 2047) {
            if (resultSign) {
                return futils64B.getNegativeInfinity();
            } else {
                return futils64B.getPositiveInfinity();
            }
        } else if (finalExp < 0) {
            boolean[] zero = new boolean[64];
            zero[0] = resultSign;
            return zero;
        }
        
        boolean[] finalExpBits = futils64B.intToExponent(finalExp);
        return futils64B.createDouble(resultSign, finalExpBits, finalMantissa);
    }
    
    private static boolean[] shiftRight53(boolean[] bits, int shifts) {
        boolean[] result = new boolean[53];
        for (int i = 0; i < 53; i++) {
            if (i + shifts < 53) {
                result[i] = bits[i + shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }
    
    private static boolean[] add53(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[53];
        boolean carry = false;
        for (int i = 52; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }
    
    private static boolean[] subtract53(boolean[] a, boolean[] b) {
        boolean[] bComplement = twosComplement53(b);
        return add53(a, bComplement);
    }
    
    private static boolean[] twosComplement53(boolean[] bits) {
        boolean[] result = new boolean[53];
        for (int i = 0; i < 53; i++) {
            result[i] = !bits[i];
        }
        
        boolean carry = true;
        for (int i = 52; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }
    
    private static int compare53(boolean[] a, boolean[] b) {
        for (int i = 0; i < 53; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }
}