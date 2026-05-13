package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.not;

public class fsub64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] negB = negateFloat64(b);
        return fadd64B.module(a, negB);
    }
    
    private static boolean[] negateFloat64(boolean[] b) {
        boolean[] result = new boolean[64];
        result[0] = b[0];
        result[1] = b[1];
        result[2] = b[2];
        result[3] = b[3];
        result[4] = b[4];
        result[5] = b[5];
        result[6] = b[6];
        result[7] = b[7];
        result[8] = b[8];
        result[9] = b[9];
        result[10] = b[10];
        result[11] = b[11];
        result[12] = b[12];
        result[13] = b[13];
        result[14] = b[14];
        result[15] = b[15];
        result[16] = b[16];
        result[17] = b[17];
        result[18] = b[18];
        result[19] = b[19];
        result[20] = b[20];
        result[21] = b[21];
        result[22] = b[22];
        result[23] = b[23];
        result[24] = b[24];
        result[25] = b[25];
        result[26] = b[26];
        result[27] = b[27];
        result[28] = b[28];
        result[29] = b[29];
        result[30] = b[30];
        result[31] = b[31];
        result[32] = b[32];
        result[33] = b[33];
        result[34] = b[34];
        result[35] = b[35];
        result[36] = b[36];
        result[37] = b[37];
        result[38] = b[38];
        result[39] = b[39];
        result[40] = b[40];
        result[41] = b[41];
        result[42] = b[42];
        result[43] = b[43];
        result[44] = b[44];
        result[45] = b[45];
        result[46] = b[46];
        result[47] = b[47];
        result[48] = b[48];
        result[49] = b[49];
        result[50] = b[50];
        result[51] = b[51];
        result[52] = b[52];
        result[53] = b[53];
        result[54] = b[54];
        result[55] = b[55];
        result[56] = b[56];
        result[57] = b[57];
        result[58] = b[58];
        result[59] = b[59];
        result[60] = b[60];
        result[61] = b[61];
        result[62] = b[62];
        result[63] = not.gate(b[63]);
        return result;
    }
}
