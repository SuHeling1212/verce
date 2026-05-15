package com.follarce.machine.CPU.ALU.RV64AFD;

public class futils64B {
    public static boolean getSign(boolean[] doubleBits) {
        return doubleBits[0];
    }

    public static boolean[] getExponent(boolean[] doubleBits) {
        boolean[] exp = new boolean[11];
        for (int i = 0; i < 11; i++) {
            exp[i] = doubleBits[i + 1];
        }
        return exp;
    }

    public static boolean[] getMantissa(boolean[] doubleBits) {
        boolean[] mant = new boolean[52];
        for (int i = 0; i < 52; i++) {
            mant[i] = doubleBits[i + 12];
        }
        return mant;
    }

    public static boolean[] createDouble(boolean sign, boolean[] exponent, boolean[] mantissa) {
        boolean[] result = new boolean[64];
        result[0] = sign;
        for (int i = 0; i < 11; i++) {
            result[i + 1] = exponent[i];
        }
        for (int i = 0; i < 52; i++) {
            result[i + 12] = mantissa[i];
        }
        return result;
    }

    public static int exponentToInt(boolean[] exponent) {
        int result = 0;
        for (int i = 0; i < 11; i++) {
            if (exponent[i]) {
                result += (1 << (10 - i));
            }
        }
        return result;
    }

    public static boolean[] intToExponent(int value) {
        boolean[] result = new boolean[11];
        for (int i = 0; i < 11; i++) {
            result[i] = (value & (1 << (10 - i))) != 0;
        }
        return result;
    }

    public static boolean[] copy(boolean[] source) {
        boolean[] result = new boolean[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    public static boolean isZero(boolean[] doubleBits) {
        for (int i = 1; i < 64; i++) {
            if (doubleBits[i]) return false;
        }
        return true;
    }

    public static boolean isNaN(boolean[] doubleBits) {
        boolean[] exp = getExponent(doubleBits);
        boolean[] mant = getMantissa(doubleBits);
        
        boolean expAllOne = true;
        for (int i = 0; i < 11; i++) {
            if (!exp[i]) {
                expAllOne = false;
                break;
            }
        }
        
        boolean mantNonZero = false;
        for (int i = 0; i < 52; i++) {
            if (mant[i]) {
                mantNonZero = true;
                break;
            }
        }
        
        return expAllOne && mantNonZero;
    }

    public static boolean isInfinity(boolean[] doubleBits) {
        boolean[] exp = getExponent(doubleBits);
        boolean[] mant = getMantissa(doubleBits);
        
        boolean expAllOne = true;
        for (int i = 0; i < 11; i++) {
            if (!exp[i]) {
                expAllOne = false;
                break;
            }
        }
        
        boolean mantZero = true;
        for (int i = 0; i < 52; i++) {
            if (mant[i]) {
                mantZero = false;
                break;
            }
        }
        
        return expAllOne && mantZero;
    }

    public static boolean[] getNaN() {
        boolean[] result = new boolean[64];
        result[0] = false;
        for (int i = 1; i <= 11; i++) {
            result[i] = true;
        }
        result[12] = true;
        return result;
    }

    public static boolean[] getPositiveInfinity() {
        boolean[] result = new boolean[64];
        result[0] = false;
        for (int i = 1; i <= 11; i++) {
            result[i] = true;
        }
        for (int i = 12; i < 64; i++) {
            result[i] = false;
        }
        return result;
    }

    public static boolean[] getNegativeInfinity() {
        boolean[] result = new boolean[64];
        result[0] = true;
        for (int i = 1; i <= 11; i++) {
            result[i] = true;
        }
        for (int i = 12; i < 64; i++) {
            result[i] = false;
        }
        return result;
    }

    public static boolean[] negate(boolean[] doubleBits) {
        boolean[] result = copy(doubleBits);
        result[0] = !result[0];
        return result;
    }
}