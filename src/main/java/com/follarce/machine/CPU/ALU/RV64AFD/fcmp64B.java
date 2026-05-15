package com.follarce.machine.CPU.ALU.RV64AFD;

public class fcmp64B {
    public static boolean[] lessThan(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[1];
        result[0] = compare(a, b) < 0;
        return result;
    }

    public static boolean[] lessThanOrEqual(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[1];
        result[0] = compare(a, b) <= 0;
        return result;
    }

    public static boolean[] equal(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[1];
        result[0] = compare(a, b) == 0;
        return result;
    }

    public static boolean[] greaterThan(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[1];
        result[0] = compare(a, b) > 0;
        return result;
    }

    public static boolean[] greaterThanOrEqual(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[1];
        result[0] = compare(a, b) >= 0;
        return result;
    }

    private static int compare(boolean[] a, boolean[] b) {
        if (futils64B.isNaN(a) || futils64B.isNaN(b)) {
            return 0;
        }
        
        if (futils64B.isZero(a) && futils64B.isZero(b)) {
            return 0;
        }
        
        boolean signA = futils64B.getSign(a);
        boolean signB = futils64B.getSign(b);
        
        if (signA && !signB) {
            return -1;
        }
        
        if (!signA && signB) {
            return 1;
        }
        
        boolean[] expA = futils64B.getExponent(a);
        boolean[] expB = futils64B.getExponent(b);
        boolean[] mantA = futils64B.getMantissa(a);
        boolean[] mantB = futils64B.getMantissa(b);
        
        int expAInt = futils64B.exponentToInt(expA);
        int expBInt = futils64B.exponentToInt(expB);
        
        if (expAInt > expBInt) {
            return signA ? -1 : 1;
        }
        
        if (expAInt < expBInt) {
            return signA ? 1 : -1;
        }
        
        for (int i = 0; i < 52; i++) {
            if (mantA[i] && !mantB[i]) {
                return signA ? -1 : 1;
            }
            if (!mantA[i] && mantB[i]) {
                return signA ? 1 : -1;
            }
        }
        
        return 0;
    }
}