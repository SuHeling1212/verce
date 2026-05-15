package com.follarce.machine.CPU.ALU.RV64IMC;

public class mux32B {
    public static boolean[] module(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[32];
        result[0] = mux2to1.module(a[0], b[0], sel);
        result[1] = mux2to1.module(a[1], b[1], sel);
        result[2] = mux2to1.module(a[2], b[2], sel);
        result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel);
        result[5] = mux2to1.module(a[5], b[5], sel);
        result[6] = mux2to1.module(a[6], b[6], sel);
        result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel);
        result[9] = mux2to1.module(a[9], b[9], sel);
        result[10] = mux2to1.module(a[10], b[10], sel);
        result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel);
        result[13] = mux2to1.module(a[13], b[13], sel);
        result[14] = mux2to1.module(a[14], b[14], sel);
        result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel);
        result[17] = mux2to1.module(a[17], b[17], sel);
        result[18] = mux2to1.module(a[18], b[18], sel);
        result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel);
        result[21] = mux2to1.module(a[21], b[21], sel);
        result[22] = mux2to1.module(a[22], b[22], sel);
        result[23] = mux2to1.module(a[23], b[23], sel);
        result[24] = mux2to1.module(a[24], b[24], sel);
        result[25] = mux2to1.module(a[25], b[25], sel);
        result[26] = mux2to1.module(a[26], b[26], sel);
        result[27] = mux2to1.module(a[27], b[27], sel);
        result[28] = mux2to1.module(a[28], b[28], sel);
        result[29] = mux2to1.module(a[29], b[29], sel);
        result[30] = mux2to1.module(a[30], b[30], sel);
        result[31] = mux2to1.module(a[31], b[31], sel);
        return result;
    }
}
