package com.follarce.machine.CPU.ALU.RV64IMC;

public class leftShifter32B {
    public static boolean[] module(boolean[] in, boolean[] shift) {
        boolean[] s0 = shift1(in, shift[0]);
        boolean[] s1 = shift2(s0, shift[1]);
        boolean[] s2 = shift4(s1, shift[2]);
        boolean[] s3 = shift8(s2, shift[3]);
        boolean[] s4 = shift16(s3, shift[4]);
        return s4;
    }

    private static boolean[] shift1(boolean[] in, boolean sel) {
        boolean[] out = new boolean[32];
        out[0] = mux2to1.module(in[0], false, sel);
        out[1] = mux2to1.module(in[1], in[0], sel);
        out[2] = mux2to1.module(in[2], in[1], sel);
        out[3] = mux2to1.module(in[3], in[2], sel);
        out[4] = mux2to1.module(in[4], in[3], sel);
        out[5] = mux2to1.module(in[5], in[4], sel);
        out[6] = mux2to1.module(in[6], in[5], sel);
        out[7] = mux2to1.module(in[7], in[6], sel);
        out[8] = mux2to1.module(in[8], in[7], sel);
        out[9] = mux2to1.module(in[9], in[8], sel);
        out[10] = mux2to1.module(in[10], in[9], sel);
        out[11] = mux2to1.module(in[11], in[10], sel);
        out[12] = mux2to1.module(in[12], in[11], sel);
        out[13] = mux2to1.module(in[13], in[12], sel);
        out[14] = mux2to1.module(in[14], in[13], sel);
        out[15] = mux2to1.module(in[15], in[14], sel);
        out[16] = mux2to1.module(in[16], in[15], sel);
        out[17] = mux2to1.module(in[17], in[16], sel);
        out[18] = mux2to1.module(in[18], in[17], sel);
        out[19] = mux2to1.module(in[19], in[18], sel);
        out[20] = mux2to1.module(in[20], in[19], sel);
        out[21] = mux2to1.module(in[21], in[20], sel);
        out[22] = mux2to1.module(in[22], in[21], sel);
        out[23] = mux2to1.module(in[23], in[22], sel);
        out[24] = mux2to1.module(in[24], in[23], sel);
        out[25] = mux2to1.module(in[25], in[24], sel);
        out[26] = mux2to1.module(in[26], in[25], sel);
        out[27] = mux2to1.module(in[27], in[26], sel);
        out[28] = mux2to1.module(in[28], in[27], sel);
        out[29] = mux2to1.module(in[29], in[28], sel);
        out[30] = mux2to1.module(in[30], in[29], sel);
        out[31] = mux2to1.module(in[31], in[30], sel);
        return out;
    }

    private static boolean[] shift2(boolean[] in, boolean sel) {
        boolean[] out = new boolean[32];
        out[0] = mux2to1.module(in[0], false, sel);
        out[1] = mux2to1.module(in[1], false, sel);
        out[2] = mux2to1.module(in[2], in[0], sel);
        out[3] = mux2to1.module(in[3], in[1], sel);
        out[4] = mux2to1.module(in[4], in[2], sel);
        out[5] = mux2to1.module(in[5], in[3], sel);
        out[6] = mux2to1.module(in[6], in[4], sel);
        out[7] = mux2to1.module(in[7], in[5], sel);
        out[8] = mux2to1.module(in[8], in[6], sel);
        out[9] = mux2to1.module(in[9], in[7], sel);
        out[10] = mux2to1.module(in[10], in[8], sel);
        out[11] = mux2to1.module(in[11], in[9], sel);
        out[12] = mux2to1.module(in[12], in[10], sel);
        out[13] = mux2to1.module(in[13], in[11], sel);
        out[14] = mux2to1.module(in[14], in[12], sel);
        out[15] = mux2to1.module(in[15], in[13], sel);
        out[16] = mux2to1.module(in[16], in[14], sel);
        out[17] = mux2to1.module(in[17], in[15], sel);
        out[18] = mux2to1.module(in[18], in[16], sel);
        out[19] = mux2to1.module(in[19], in[17], sel);
        out[20] = mux2to1.module(in[20], in[18], sel);
        out[21] = mux2to1.module(in[21], in[19], sel);
        out[22] = mux2to1.module(in[22], in[20], sel);
        out[23] = mux2to1.module(in[23], in[21], sel);
        out[24] = mux2to1.module(in[24], in[22], sel);
        out[25] = mux2to1.module(in[25], in[23], sel);
        out[26] = mux2to1.module(in[26], in[24], sel);
        out[27] = mux2to1.module(in[27], in[25], sel);
        out[28] = mux2to1.module(in[28], in[26], sel);
        out[29] = mux2to1.module(in[29], in[27], sel);
        out[30] = mux2to1.module(in[30], in[28], sel);
        out[31] = mux2to1.module(in[31], in[29], sel);
        return out;
    }

    private static boolean[] shift4(boolean[] in, boolean sel) {
        boolean[] out = new boolean[32];
        out[0] = mux2to1.module(in[0], false, sel);
        out[1] = mux2to1.module(in[1], false, sel);
        out[2] = mux2to1.module(in[2], false, sel);
        out[3] = mux2to1.module(in[3], false, sel);
        out[4] = mux2to1.module(in[4], in[0], sel);
        out[5] = mux2to1.module(in[5], in[1], sel);
        out[6] = mux2to1.module(in[6], in[2], sel);
        out[7] = mux2to1.module(in[7], in[3], sel);
        out[8] = mux2to1.module(in[8], in[4], sel);
        out[9] = mux2to1.module(in[9], in[5], sel);
        out[10] = mux2to1.module(in[10], in[6], sel);
        out[11] = mux2to1.module(in[11], in[7], sel);
        out[12] = mux2to1.module(in[12], in[8], sel);
        out[13] = mux2to1.module(in[13], in[9], sel);
        out[14] = mux2to1.module(in[14], in[10], sel);
        out[15] = mux2to1.module(in[15], in[11], sel);
        out[16] = mux2to1.module(in[16], in[12], sel);
        out[17] = mux2to1.module(in[17], in[13], sel);
        out[18] = mux2to1.module(in[18], in[14], sel);
        out[19] = mux2to1.module(in[19], in[15], sel);
        out[20] = mux2to1.module(in[20], in[16], sel);
        out[21] = mux2to1.module(in[21], in[17], sel);
        out[22] = mux2to1.module(in[22], in[18], sel);
        out[23] = mux2to1.module(in[23], in[19], sel);
        out[24] = mux2to1.module(in[24], in[20], sel);
        out[25] = mux2to1.module(in[25], in[21], sel);
        out[26] = mux2to1.module(in[26], in[22], sel);
        out[27] = mux2to1.module(in[27], in[23], sel);
        out[28] = mux2to1.module(in[28], in[24], sel);
        out[29] = mux2to1.module(in[29], in[25], sel);
        out[30] = mux2to1.module(in[30], in[26], sel);
        out[31] = mux2to1.module(in[31], in[27], sel);
        return out;
    }

    private static boolean[] shift8(boolean[] in, boolean sel) {
        boolean[] out = new boolean[32];
        out[0] = mux2to1.module(in[0], false, sel);
        out[1] = mux2to1.module(in[1], false, sel);
        out[2] = mux2to1.module(in[2], false, sel);
        out[3] = mux2to1.module(in[3], false, sel);
        out[4] = mux2to1.module(in[4], false, sel);
        out[5] = mux2to1.module(in[5], false, sel);
        out[6] = mux2to1.module(in[6], false, sel);
        out[7] = mux2to1.module(in[7], false, sel);
        out[8] = mux2to1.module(in[8], in[0], sel);
        out[9] = mux2to1.module(in[9], in[1], sel);
        out[10] = mux2to1.module(in[10], in[2], sel);
        out[11] = mux2to1.module(in[11], in[3], sel);
        out[12] = mux2to1.module(in[12], in[4], sel);
        out[13] = mux2to1.module(in[13], in[5], sel);
        out[14] = mux2to1.module(in[14], in[6], sel);
        out[15] = mux2to1.module(in[15], in[7], sel);
        out[16] = mux2to1.module(in[16], in[8], sel);
        out[17] = mux2to1.module(in[17], in[9], sel);
        out[18] = mux2to1.module(in[18], in[10], sel);
        out[19] = mux2to1.module(in[19], in[11], sel);
        out[20] = mux2to1.module(in[20], in[12], sel);
        out[21] = mux2to1.module(in[21], in[13], sel);
        out[22] = mux2to1.module(in[22], in[14], sel);
        out[23] = mux2to1.module(in[23], in[15], sel);
        out[24] = mux2to1.module(in[24], in[16], sel);
        out[25] = mux2to1.module(in[25], in[17], sel);
        out[26] = mux2to1.module(in[26], in[18], sel);
        out[27] = mux2to1.module(in[27], in[19], sel);
        out[28] = mux2to1.module(in[28], in[20], sel);
        out[29] = mux2to1.module(in[29], in[21], sel);
        out[30] = mux2to1.module(in[30], in[22], sel);
        out[31] = mux2to1.module(in[31], in[23], sel);
        return out;
    }

    private static boolean[] shift16(boolean[] in, boolean sel) {
        boolean[] out = new boolean[32];
        out[0] = mux2to1.module(in[0], false, sel);
        out[1] = mux2to1.module(in[1], false, sel);
        out[2] = mux2to1.module(in[2], false, sel);
        out[3] = mux2to1.module(in[3], false, sel);
        out[4] = mux2to1.module(in[4], false, sel);
        out[5] = mux2to1.module(in[5], false, sel);
        out[6] = mux2to1.module(in[6], false, sel);
        out[7] = mux2to1.module(in[7], false, sel);
        out[8] = mux2to1.module(in[8], false, sel);
        out[9] = mux2to1.module(in[9], false, sel);
        out[10] = mux2to1.module(in[10], false, sel);
        out[11] = mux2to1.module(in[11], false, sel);
        out[12] = mux2to1.module(in[12], false, sel);
        out[13] = mux2to1.module(in[13], false, sel);
        out[14] = mux2to1.module(in[14], false, sel);
        out[15] = mux2to1.module(in[15], false, sel);
        out[16] = mux2to1.module(in[16], in[0], sel);
        out[17] = mux2to1.module(in[17], in[1], sel);
        out[18] = mux2to1.module(in[18], in[2], sel);
        out[19] = mux2to1.module(in[19], in[3], sel);
        out[20] = mux2to1.module(in[20], in[4], sel);
        out[21] = mux2to1.module(in[21], in[5], sel);
        out[22] = mux2to1.module(in[22], in[6], sel);
        out[23] = mux2to1.module(in[23], in[7], sel);
        out[24] = mux2to1.module(in[24], in[8], sel);
        out[25] = mux2to1.module(in[25], in[9], sel);
        out[26] = mux2to1.module(in[26], in[10], sel);
        out[27] = mux2to1.module(in[27], in[11], sel);
        out[28] = mux2to1.module(in[28], in[12], sel);
        out[29] = mux2to1.module(in[29], in[13], sel);
        out[30] = mux2to1.module(in[30], in[14], sel);
        out[31] = mux2to1.module(in[31], in[15], sel);
        return out;
    }
}
