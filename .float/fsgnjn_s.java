package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.not;

public class fsgnjn_s {
    public static boolean[] module(boolean[] rs1, boolean[] rs2) {
        boolean[] result = new boolean[64];
        result[0] = rs1[0]; result[1] = rs1[1]; result[2] = rs1[2]; result[3] = rs1[3];
        result[4] = rs1[4]; result[5] = rs1[5]; result[6] = rs1[6]; result[7] = rs1[7];
        result[8] = rs1[8]; result[9] = rs1[9]; result[10] = rs1[10]; result[11] = rs1[11];
        result[12] = rs1[12]; result[13] = rs1[13]; result[14] = rs1[14]; result[15] = rs1[15];
        result[16] = rs1[16]; result[17] = rs1[17]; result[18] = rs1[18]; result[19] = rs1[19];
        result[20] = rs1[20]; result[21] = rs1[21]; result[22] = rs1[22]; result[23] = rs1[23];
        result[24] = rs1[24]; result[25] = rs1[25]; result[26] = rs1[26]; result[27] = rs1[27];
        result[28] = rs1[28]; result[29] = rs1[29]; result[30] = rs1[30];
        result[31] = not.gate(rs2[31]);
        result[32] = false; result[33] = false; result[34] = false; result[35] = false;
        result[36] = false; result[37] = false; result[38] = false; result[39] = false;
        result[40] = false; result[41] = false; result[42] = false; result[43] = false;
        result[44] = false; result[45] = false; result[46] = false; result[47] = false;
        result[48] = false; result[49] = false; result[50] = false; result[51] = false;
        result[52] = false; result[53] = false; result[54] = false; result[55] = false;
        result[56] = false; result[57] = false; result[58] = false; result[59] = false;
        result[60] = false; result[61] = false; result[62] = false; result[63] = false;
        return result;
    }
}
