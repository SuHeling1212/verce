package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.not;

public class fsub32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] negB = negateFloat32(b);
        return fadd32B.module(a, negB);
    }
    
    private static boolean[] negateFloat32(boolean[] b) {
        boolean[] result = new boolean[32];
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
        result[31] = not.gate(b[31]);
        return result;
    }
}
