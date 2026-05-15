package com.follarce.machine.CPU.ALU.RV64AFD;

public class fsqrt32B {
    public static boolean[] module(boolean[] a) {
        if (futils32B.isNaN(a)) {
            return futils32B.getNaN();
        }
        
        if (futils32B.isZero(a)) {
            return futils32B.copy(a);
        }
        
        if (futils32B.isInfinity(a)) {
            return futils32B.copy(a);
        }
        
        boolean sign = futils32B.getSign(a);
        if (sign) {
            return futils32B.getNaN();
        }
        
        boolean[] exp = futils32B.getExponent(a);
        boolean[] mant = futils32B.getMantissa(a);
        
        int expInt = futils32B.exponentToInt(exp);
        
        boolean isExpOdd = (expInt & 1) != 0;
        
        int resultExp = expInt / 2 + 63;
        
        boolean[] mantissaWithOne = new boolean[24];
        mantissaWithOne[0] = true;
        for (int i = 0; i < 23; i++) {
            mantissaWithOne[i + 1] = mant[i];
        }
        
        boolean[] shiftedMantissa = new boolean[25];
        if (isExpOdd) {
            shiftedMantissa[0] = mantissaWithOne[0];
            shiftedMantissa[1] = mantissaWithOne[1];
            for (int i = 2; i < 25; i++) {
                shiftedMantissa[i] = mantissaWithOne[i - 1];
            }
        } else {
            shiftedMantissa[0] = false;
            for (int i = 0; i < 24; i++) {
                shiftedMantissa[i + 1] = mantissaWithOne[i];
            }
        }
        
        boolean[] sqrtMantissa = sqrt25(shiftedMantissa);
        
        boolean[] finalMantissa = new boolean[23];
        for (int i = 0; i < 23; i++) {
            finalMantissa[i] = sqrtMantissa[i + 1];
        }
        
        boolean[] finalExpBits = futils32B.intToExponent(resultExp);
        return futils32B.createFloat(false, finalExpBits, finalMantissa);
    }
    
    private static boolean[] sqrt25(boolean[] radicand) {
        boolean[] result = new boolean[25];
        boolean[] remainder = new boolean[26];
        
        for (int i = 0; i < 25; i++) {
            remainder = shiftLeft26(remainder);
            remainder[25] = radicand[i];
            
            boolean[] temp = new boolean[26];
            for (int j = 0; j < 24; j++) {
                temp[j + 1] = result[j];
            }
            temp[25] = true;
            
            if (compare26(remainder, temp) >= 0) {
                result[i] = true;
                remainder = subtract26(remainder, temp);
            } else {
                result[i] = false;
            }
        }
        
        return result;
    }
    
    private static boolean[] shiftLeft26(boolean[] bits) {
        boolean[] result = new boolean[26];
        for (int i = 0; i < 25; i++) {
            result[i] = bits[i + 1];
        }
        result[25] = false;
        return result;
    }
    
    private static boolean[] subtract26(boolean[] a, boolean[] b) {
        boolean[] bComplement = twosComplement26(b);
        return add26(a, bComplement);
    }
    
    private static boolean[] twosComplement26(boolean[] bits) {
        boolean[] result = new boolean[26];
        for (int i = 0; i < 26; i++) {
            result[i] = !bits[i];
        }
        
        boolean carry = true;
        for (int i = 25; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }
    
    private static boolean[] add26(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[26];
        boolean carry = false;
        for (int i = 25; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }
    
    private static int compare26(boolean[] a, boolean[] b) {
        for (int i = 0; i < 26; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }
}