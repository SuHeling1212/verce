package com.follarce.machine.CPU.ALU.RV64AFD;

public class futils32B {
    public static boolean getSign(boolean[] floatBits) {
        return floatBits[0];
    }

    public static boolean[] getExponent(boolean[] floatBits) {
        boolean[] exp = new boolean[8];
        for (int i = 0; i < 8; i++) {
            exp[i] = floatBits[i + 1];
        }
        return exp;
    }

    public static boolean[] getMantissa(boolean[] floatBits) {
        boolean[] mant = new boolean[23];
        for (int i = 0; i < 23; i++) {
            mant[i] = floatBits[i + 9];
        }
        return mant;
    }

    public static boolean[] setSign(boolean[] floatBits, boolean sign) {
        boolean[] result = copy(floatBits);
        result[0] = sign;
        return result;
    }

    public static boolean[] setExponent(boolean[] floatBits, boolean[] exponent) {
        boolean[] result = copy(floatBits);
        for (int i = 0; i < 8; i++) {
            result[i + 1] = exponent[i];
        }
        return result;
    }

    public static boolean[] setMantissa(boolean[] floatBits, boolean[] mantissa) {
        boolean[] result = copy(floatBits);
        for (int i = 0; i < 23; i++) {
            result[i + 9] = mantissa[i];
        }
        return result;
    }

    public static boolean[] createFloat(boolean sign, boolean[] exponent, boolean[] mantissa) {
        boolean[] result = new boolean[32];
        result[0] = sign;
        for (int i = 0; i < 8; i++) {
            result[i + 1] = exponent[i];
        }
        for (int i = 0; i < 23; i++) {
            result[i + 9] = mantissa[i];
        }
        return result;
    }

    public static int exponentToInt(boolean[] exponent) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            if (exponent[i]) {
                result += (1 << (7 - i));
            }
        }
        return result;
    }

    public static boolean[] intToExponent(int value) {
        boolean[] result = new boolean[8];
        for (int i = 0; i < 8; i++) {
            result[i] = (value & (1 << (7 - i))) != 0;
        }
        return result;
    }

    public static boolean[] incrementExponent(boolean[] exponent) {
        int exp = exponentToInt(exponent);
        exp++;
        return intToExponent(exp);
    }

    public static boolean[] decrementExponent(boolean[] exponent) {
        int exp = exponentToInt(exponent);
        exp--;
        return intToExponent(exp);
    }

    public static boolean[] shiftRightMantissa(boolean[] mantissa, int shifts) {
        boolean[] result = new boolean[23];
        for (int i = 0; i < 23; i++) {
            if (i + shifts < 23) {
                result[i] = mantissa[i + shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }

    public static boolean[] shiftLeftMantissa(boolean[] mantissa, int shifts) {
        boolean[] result = new boolean[23];
        for (int i = 0; i < 23; i++) {
            if (i - shifts >= 0) {
                result[i] = mantissa[i - shifts];
            } else {
                result[i] = false;
            }
        }
        return result;
    }

    public static boolean[] addMantissas(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[24];
        boolean carry = false;
        for (int i = 22; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i + 1] = sum;
        }
        result[0] = carry;
        return result;
    }

    public static boolean[] subtractMantissas(boolean[] a, boolean[] b) {
        boolean[] bComplement = twosComplement24(b);
        return addMantissas(a, bComplement);
    }

    public static boolean[] twosComplement24(boolean[] bits) {
        boolean[] result = new boolean[24];
        for (int i = 0; i < 23; i++) {
            result[i + 1] = !bits[i];
        }
        result[0] = false;
        
        boolean carry = true;
        for (int i = 23; i >= 0; i--) {
            boolean temp = result[i];
            result[i] = result[i] ^ carry;
            carry = temp & carry;
        }
        return result;
    }

    public static boolean[] multiplyMantissas(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[47];
        for (int i = 0; i < 23; i++) {
            if (b[i]) {
                boolean[] shifted = new boolean[47];
                for (int j = 0; j < 23; j++) {
                    shifted[j + i] = a[j];
                }
                result = add47(result, shifted);
            }
        }
        return result;
    }

    public static boolean[] add47(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[47];
        boolean carry = false;
        for (int i = 46; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }

    public static boolean[] divideMantissas(boolean[] dividend, boolean[] divisor) {
        boolean[] quotient = new boolean[23];
        boolean[] remainder = new boolean[24];
        
        for (int i = 22; i >= 0; i--) {
            remainder = shiftLeft24(remainder);
            remainder[23] = dividend[i];
            
            if (compare24(remainder, divisor) >= 0) {
                quotient[i] = true;
                remainder = subtract24(remainder, divisor);
            } else {
                quotient[i] = false;
            }
        }
        return quotient;
    }

    public static boolean[] shiftLeft24(boolean[] bits) {
        boolean[] result = new boolean[24];
        for (int i = 0; i < 23; i++) {
            result[i] = bits[i + 1];
        }
        result[23] = false;
        return result;
    }

    public static boolean[] subtract24(boolean[] a, boolean[] b) {
        boolean[] bComplement = twosComplement24(b);
        return add24(a, bComplement);
    }

    public static boolean[] add24(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[24];
        boolean carry = false;
        for (int i = 23; i >= 0; i--) {
            boolean sum = a[i] ^ b[i] ^ carry;
            carry = (a[i] & b[i]) | (a[i] & carry) | (b[i] & carry);
            result[i] = sum;
        }
        return result;
    }

    public static int compare24(boolean[] a, boolean[] b) {
        for (int i = 0; i < 24; i++) {
            if (a[i] && !b[i]) return 1;
            if (!a[i] && b[i]) return -1;
        }
        return 0;
    }

    public static boolean[] normalizeMantissa(boolean[] mantissaWithCarry, boolean[] exponent) {
        boolean[] result = new boolean[23];
        int exp = exponentToInt(exponent);
        
        if (mantissaWithCarry[0]) {
            for (int i = 0; i < 23; i++) {
                result[i] = mantissaWithCarry[i + 1];
            }
            exp++;
        } else {
            int shift = 0;
            while (shift < 23 && !mantissaWithCarry[shift + 1]) {
                shift++;
            }
            if (shift == 23) {
                exp = 0;
                for (int i = 0; i < 23; i++) {
                    result[i] = false;
                }
            } else {
                for (int i = 0; i < 23; i++) {
                    if (i + shift + 1 < 24) {
                        result[i] = mantissaWithCarry[i + shift + 1];
                    } else {
                        result[i] = false;
                    }
                }
                exp -= shift;
            }
        }
        
        if (exp > 255) {
            exp = 255;
            for (int i = 0; i < 23; i++) {
                result[i] = false;
            }
        } else if (exp < 0) {
            exp = 0;
            for (int i = 0; i < 23; i++) {
                result[i] = false;
            }
        }
        
        return result;
    }

    public static int normalizeExponent(boolean[] mantissaWithCarry, int exponent) {
        if (mantissaWithCarry[0]) {
            return exponent + 1;
        } else {
            int shift = 0;
            while (shift < 23 && !mantissaWithCarry[shift + 1]) {
                shift++;
            }
            if (shift == 23) {
                return 0;
            }
            return exponent - shift;
        }
    }

    public static boolean[] copy(boolean[] source) {
        boolean[] result = new boolean[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    public static boolean isZero(boolean[] floatBits) {
        for (int i = 1; i < 32; i++) {
            if (floatBits[i]) return false;
        }
        return true;
    }

    public static boolean isNaN(boolean[] floatBits) {
        boolean[] exp = getExponent(floatBits);
        boolean[] mant = getMantissa(floatBits);
        
        boolean expAllOne = true;
        for (int i = 0; i < 8; i++) {
            if (!exp[i]) {
                expAllOne = false;
                break;
            }
        }
        
        boolean mantNonZero = false;
        for (int i = 0; i < 23; i++) {
            if (mant[i]) {
                mantNonZero = true;
                break;
            }
        }
        
        return expAllOne && mantNonZero;
    }

    public static boolean isInfinity(boolean[] floatBits) {
        boolean[] exp = getExponent(floatBits);
        boolean[] mant = getMantissa(floatBits);
        
        boolean expAllOne = true;
        for (int i = 0; i < 8; i++) {
            if (!exp[i]) {
                expAllOne = false;
                break;
            }
        }
        
        boolean mantZero = true;
        for (int i = 0; i < 23; i++) {
            if (mant[i]) {
                mantZero = false;
                break;
            }
        }
        
        return expAllOne && mantZero;
    }

    public static boolean[] getNaN() {
        boolean[] result = new boolean[32];
        result[0] = false;
        for (int i = 1; i <= 8; i++) {
            result[i] = true;
        }
        result[9] = true;
        return result;
    }

    public static boolean[] getPositiveInfinity() {
        boolean[] result = new boolean[32];
        result[0] = false;
        for (int i = 1; i <= 8; i++) {
            result[i] = true;
        }
        for (int i = 9; i < 32; i++) {
            result[i] = false;
        }
        return result;
    }

    public static boolean[] getNegativeInfinity() {
        boolean[] result = new boolean[32];
        result[0] = true;
        for (int i = 1; i <= 8; i++) {
            result[i] = true;
        }
        for (int i = 9; i < 32; i++) {
            result[i] = false;
        }
        return result;
    }

    public static boolean[] negate(boolean[] floatBits) {
        boolean[] result = copy(floatBits);
        result[0] = !result[0];
        return result;
    }
}