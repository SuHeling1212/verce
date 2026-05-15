package com.follarce.machine.CPU.ALU.RV64AFD;

public class fcmp32B {
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
        if (futils32B.isNaN(a) || futils32B.isNaN(b)) {
            return 0;
        }
        
        if (futils32B.isZero(a) && futils32B.isZero(b)) {
            return 0;
        }
        
        boolean signA = futils32B.getSign(a);
        boolean signB = futils32B.getSign(b);
        
        if (signA && !signB) {
            return -1;
        }
        
        if (!signA && signB) {
            return 1;
        }
        
        boolean[] expA = futils32B.getExponent(a);
        boolean[] expB = futils32B.getExponent(b);
        boolean[] mantA = futils32B.getMantissa(a);
        boolean[] mantB = futils32B.getMantissa(b);
        
        int expAInt = futils32B.exponentToInt(expA);
        int expBInt = futils32B.exponentToInt(expB);
        
        if (expAInt > expBInt) {
            return signA ? -1 : 1;
        }
        
        if (expAInt < expBInt) {
            return signA ? 1 : -1;
        }
        
        for (int i = 0; i < 23; i++) {
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