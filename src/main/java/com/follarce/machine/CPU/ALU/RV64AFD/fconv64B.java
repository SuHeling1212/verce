package com.follarce.machine.CPU.ALU.RV64AFD;

public class fconv64B {
    public static boolean[] int32ToDouble(boolean[] a) {
        boolean sign = a[0];
        boolean[] magnitude = new boolean[32];
        for (int i = 0; i < 32; i++) {
            magnitude[i] = a[i];
        }
        
        if (sign) {
            magnitude = twosComplement32(a);
        }
        
        int leadingZeroes = countLeadingZeroes32(magnitude);
        int exp = 1023 + 31 - leadingZeroes;
        
        boolean[] mant = new boolean[52];
        int shift = leadingZeroes + 1;
        for (int i = 0; i < 52; i++) {
            if (i + shift < 32) {
                mant[i] = magnitude[i + shift];
            } else {
                mant[i] = false;
            }
        }
        
        boolean[] expBits = futils64B.intToExponent(exp);
        return futils64B.createDouble(sign, expBits, mant);
    }

    public static boolean[] uint32ToDouble(boolean[] a) {
        int leadingZeroes = countLeadingZeroes32(a);
        int exp = 1023 + 31 - leadingZeroes;
        
        boolean[] mant = new boolean[52];
        int shift = leadingZeroes + 1;
        for (int i = 0; i < 52; i++) {
            if (i + shift < 32) {
                mant[i] = a[i + shift];
            } else {
                mant[i] = false;
            }
        }
        
        boolean[] expBits = futils64B.intToExponent(exp);
        return futils64B.createDouble(false, expBits, mant);
    }

    public static boolean[] int64ToDouble(boolean[] a) {
        boolean sign = a[0];
        boolean[] magnitude = new boolean[64];
        for (int i = 0; i < 64; i++) {
            magnitude[i] = a[i];
        }
        
        if (sign) {
            magnitude = twosComplement64(a);
        }
        
        int leadingZeroes = countLeadingZeroes64(magnitude);
        int exp = 1023 + 63 - leadingZeroes;
        
        boolean[] mant = new boolean[52];
        int shift = leadingZeroes + 1;
        for (int i = 0; i < 52; i++) {
            if (i + shift < 64) {
                mant[i] = magnitude[i + shift];
            } else {
                mant[i] = false;
            }
        }
        
        boolean[] expBits = futils64B.intToExponent(exp);
        return futils64B.createDouble(sign, expBits, mant);
    }

    public static boolean[] uint64ToDouble(boolean[] a) {
        int leadingZeroes = countLeadingZeroes64(a);
        int exp = 1023 + 63 - leadingZeroes;
        
        boolean[] mant = new boolean[52];
        int shift = leadingZeroes + 1;
        for (int i = 0; i < 52; i++) {
            if (i + shift < 64) {
                mant[i] = a[i + shift];
            } else {
                mant[i] = false;
            }
        }
        
        boolean[] expBits = futils64B.intToExponent(exp);
        return futils64B.createDouble(false, expBits, mant);
    }

    public static boolean[] doubleToInt32(boolean[] a) {
        boolean sign = futils64B.getSign(a);
        boolean[] exp = futils64B.getExponent(a);
        boolean[] mant = futils64B.getMantissa(a);
        
        int expInt = futils64B.exponentToInt(exp);
        
        if (expInt == 0) {
            return new boolean[32];
        }
        
        int shift = expInt - 1023;
        
        boolean[] result = new boolean[32];
        result[0] = false;
        result[1] = true;
        for (int i = 0; i < 52; i++) {
            if (i + 2 < 32) {
                result[i + 2] = mant[i];
            }
        }
        
        if (shift > 30) {
            for (int i = 0; i < 32; i++) {
                result[i] = false;
            }
            result[0] = sign;
            return result;
        }
        
        if (shift >= 0) {
            result = shiftLeft32(result, shift);
        } else {
            result = shiftRight32(result, -shift);
        }
        
        if (sign) {
            result = twosComplement32(result);
        }
        
        return result;
    }

    public static boolean[] doubleToUint32(boolean[] a) {
        boolean[] signedResult = doubleToInt32(a);
        if (!signedResult[0]) {
            return signedResult;
        }
        
        boolean[] result = new boolean[32];
        for (int i = 0; i < 32; i++) {
            result[i] = signedResult[i];
        }
        return result;
    }

    public static boolean[] doubleToInt64(boolean[] a) {
        boolean sign = futils64B.getSign(a);
        boolean[] exp = futils64B.getExponent(a);
        boolean[] mant = futils64B.getMantissa(a);
        
        int expInt = futils64B.exponentToInt(exp);
        
        if (expInt == 0) {
            return new boolean[64];
        }
        
        int shift = expInt - 1023;
        
        boolean[] result = new boolean[64];
        result[0] = false;
        result[1] = true;
        for (int i = 0; i < 52; i++) {
            if (i + 2 < 64) {
                result[i + 2] = mant[i];
            }
        }
        
        if (shift > 62) {
            for (int i = 0; i < 64; i++) {
                result[i] = false;
            }
            result[0] = sign;
            return result;
        }
        
        if (shift >= 0) {
            result = shiftLeft64(result, shift);
        } else {
            result = shiftRight64(result, -shift);
        }
        
        if (sign) {
            result = twosComplement64(result);
        }
        
        return result;
    }

    public static boolean[] doubleToUint64(boolean[] a) {
        boolean[] signedResult = doubleToInt64(a);
        if (!signedResult[0]) {
            return signedResult;
        }
        
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            result[i] = signedResult[i];
        }
        return result;
    }

    public static boolean[] doubleToFloat(boolean[] a) {
        boolean sign = futils64B.getSign(a);
        boolean[] exp = futils64B.getExponent(a);
        boolean[] mant = futils64B.getMantissa(a);
        
        int expInt = futils64B.exponentToInt(exp);
        
        int newExp = expInt - 1023 + 127;
        
        boolean[] newExpBits = futils32B.intToExponent(newExp);
        
        boolean[] newMant = new boolean[23];
        for (int i = 0; i < 23; i++) {
            newMant[i] = mant[i];
        }
        
        return futils32B.createFloat(sign, newExpBits, newMant);
    }

    private static boolean[] twosComplement32(boolean[] bits) {
        boolean[] result = new boolean[32];
        for (int i = 0; i < 32; i++) {
            result[i] = !bits[i];
        }
        
        boolean carry = true;
        for (int i = 31; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }

    private static boolean[] twosComplement64(boolean[] bits) {
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            result[i] = !bits[i];
        }
        
        boolean carry = true;
        for (int i = 63; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }

    private static int countLeadingZeroes32(boolean[] bits) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if (!bits[i]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private static int countLeadingZeroes64(boolean[] bits) {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            if (!bits[i]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private static boolean[] shiftLeft32(boolean[] bits, int shifts) {
        boolean[] result = new boolean[32];
        for (int i = 0; i < 32; i++) {
            if (i - shifts >= 0) {
                result[i] = bits[i - shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }

    private static boolean[] shiftRight32(boolean[] bits, int shifts) {
        boolean[] result = new boolean[32];
        for (int i = 0; i < 32; i++) {
            if (i + shifts < 32) {
                result[i] = bits[i + shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }

    private static boolean[] shiftLeft64(boolean[] bits, int shifts) {
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            if (i - shifts >= 0) {
                result[i] = bits[i - shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }

    private static boolean[] shiftRight64(boolean[] bits, int shifts) {
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            if (i + shifts < 64) {
                result[i] = bits[i + shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }
}