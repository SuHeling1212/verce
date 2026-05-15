package com.follarce.machine.CPU.ALU.RV64AFD;

public class fadd32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        if (futils32B.isNaN(a) || futils32B.isNaN(b)) {
            return futils32B.getNaN();
        }
        
        if (futils32B.isInfinity(a) && futils32B.isInfinity(b)) {
            if (futils32B.getSign(a) == futils32B.getSign(b)) {
                return futils32B.copy(a);
            } else {
                return futils32B.getNaN();
            }
        }
        
        if (futils32B.isInfinity(a)) {
            return futils32B.copy(a);
        }
        
        if (futils32B.isInfinity(b)) {
            return futils32B.copy(b);
        }
        
        if (futils32B.isZero(a)) {
            return futils32B.copy(b);
        }
        
        if (futils32B.isZero(b)) {
            return futils32B.copy(a);
        }
        
        boolean signA = futils32B.getSign(a);
        boolean signB = futils32B.getSign(b);
        boolean[] expA = futils32B.getExponent(a);
        boolean[] expB = futils32B.getExponent(b);
        boolean[] mantA = futils32B.getMantissa(a);
        boolean[] mantB = futils32B.getMantissa(b);
        
        int expAInt = futils32B.exponentToInt(expA);
        int expBInt = futils32B.exponentToInt(expB);
        
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
        
        int expDiff = expAInt - expBInt;
        boolean[] largerMantissa;
        boolean[] smallerMantissa;
        int largerExp;
        boolean resultSign;
        
        if (expDiff > 0) {
            largerMantissa = mantissaAWithOne;
            smallerMantissa = shiftRight24(mantissaBWithOne, expDiff);
            largerExp = expAInt;
            resultSign = signA;
        } else if (expDiff < 0) {
            largerMantissa = mantissaBWithOne;
            smallerMantissa = shiftRight24(mantissaAWithOne, -expDiff);
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
            sumMantissa = add24(largerMantissa, smallerMantissa);
        } else {
            int compare = compare24(largerMantissa, smallerMantissa);
            if (compare > 0) {
                sumMantissa = subtract24(largerMantissa, smallerMantissa);
            } else if (compare < 0) {
                sumMantissa = subtract24(smallerMantissa, largerMantissa);
                resultSign = !resultSign;
            } else {
                boolean[] zero = new boolean[32];
                return zero;
            }
        }
        
        int finalExp = largerExp;
        boolean[] finalMantissa = new boolean[23];
        
        if (sumMantissa[0]) {
            for (int i = 0; i < 23; i++) {
                finalMantissa[i] = sumMantissa[i + 1];
            }
            finalExp++;
        } else {
            int shift = 0;
            while (shift < 23 && !sumMantissa[shift + 1]) {
                shift++;
            }
            if (shift == 23) {
                boolean[] zero = new boolean[32];
                return zero;
            }
            for (int i = 0; i < 23; i++) {
                if (i + shift + 1 < 24) {
                    finalMantissa[i] = sumMantissa[i + shift + 1];
                } else {
                    finalMantissa[i] = false;
                }
            }
            finalExp -= shift;
        }
        
        if (finalExp > 255) {
            if (resultSign) {
                return futils32B.getNegativeInfinity();
            } else {
                return futils32B.getPositiveInfinity();
            }
        } else if (finalExp < 0) {
            boolean[] zero = new boolean[32];
            zero[0] = resultSign;
            return zero;
        }
        
        boolean[] finalExpBits = futils32B.intToExponent(finalExp);
        return futils32B.createFloat(resultSign, finalExpBits, finalMantissa);
    }
    
    private static boolean[] shiftRight24(boolean[] bits, int shifts) {
        boolean[] result = new boolean[24];
        for (int i = 0; i < 24; i++) {
            if (i + shifts < 24) {
                result[i] = bits[i + shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }
    
    private static boolean[] add24(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[24];
        boolean carry = false;
        for (int i = 23; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }
    
    private static boolean[] subtract24(boolean[] a, boolean[] b) {
        boolean[] bComplement = twosComplement24(b);
        return add24(a, bComplement);
    }
    
    private static boolean[] twosComplement24(boolean[] bits) {
        boolean[] result = new boolean[24];
        for (int i = 0; i < 24; i++) {
            result[i] = !bits[i];
        }
        
        boolean carry = true;
        for (int i = 23; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }
    
    private static int compare24(boolean[] a, boolean[] b) {
        for (int i = 0; i < 24; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }
}