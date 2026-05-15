package com.follarce.machine.CPU.ALU.RV64AFD;

public class fsqrt64B {
    public static boolean[] module(boolean[] a) {
        if (futils64B.isNaN(a)) {
            return futils64B.getNaN();
        }
        
        if (futils64B.isInfinity(a)) {
            if (futils64B.getSign(a)) {
                return futils64B.getNaN();
            } else {
                return futils64B.copy(a);
            }
        }
        
        if (futils64B.isZero(a)) {
            return futils64B.copy(a);
        }
        
        if (futils64B.getSign(a)) {
            return futils64B.getNaN();
        }
        
        boolean[] expA = futils64B.getExponent(a);
        boolean[] mantA = futils64B.getMantissa(a);
        
        int expAInt = futils64B.exponentToInt(expA);
        
        boolean isOddExp = (expAInt % 2) != 0;
        int resultExp = expAInt / 2 + 512;
        
        boolean[] mantissaWithOne = new boolean[53];
        mantissaWithOne[0] = true;
        for (int i = 0; i < 52; i++) {
            mantissaWithOne[i + 1] = mantA[i];
        }
        
        boolean[] workingMantissa;
        if (isOddExp) {
            workingMantissa = shiftRight53(mantissaWithOne, 1);
            for (int i = 51; i >= 0; i--) {
                workingMantissa[i + 1] = workingMantissa[i];
            }
            workingMantissa[0] = true;
        } else {
            workingMantissa = mantissaWithOne;
        }
        
        boolean[] sqrtMantissa = sqrt53(workingMantissa);
        
        boolean[] finalMantissa = new boolean[52];
        for (int i = 0; i < 52; i++) {
            finalMantissa[i] = sqrtMantissa[i + 1];
        }
        
        boolean[] finalExpBits = futils64B.intToExponent(resultExp);
        return futils64B.createDouble(false, finalExpBits, finalMantissa);
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
    
    private static boolean[] sqrt53(boolean[] a) {
        boolean[] result = new boolean[53];
        boolean[] remainder = new boolean[106];
        
        for (int i = 0; i < 53; i++) {
            remainder[i] = a[i];
        }
        
        for (int i = 0; i < 53; i++) {
            boolean guessBit = false;
            boolean[] temp = new boolean[106];
            
            for (int j = 0; j < 53; j++) {
                temp[j * 2 + 1] = result[j];
            }
            
            temp[104] = true;
            
            if (compare106(remainder, temp) >= 0) {
                guessBit = true;
                remainder = subtract106(remainder, temp);
            }
            
            result[i] = guessBit;
            
            for (int j = 104; j >= 0; j--) {
                remainder[j + 1] = remainder[j];
            }
            remainder[0] = false;
        }
        
        return result;
    }
    
    private static int compare106(boolean[] a, boolean[] b) {
        for (int i = 0; i < 106; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }
    
    private static boolean[] subtract106(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[106];
        boolean borrow = false;
        for (int i = 105; i >= 0; i--) {
            boolean sub = a[i] ^ b[i] ^ borrow;
            borrow = (!a[i] && (b[i] || borrow)) || (b[i] && borrow);
            result[i] = sub;
        }
        return result;
    }
}