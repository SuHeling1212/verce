package com.follarce.machine.CPU.ALU.module;

public class instructionDecoder {
    public static boolean[] opcode(boolean[] inst) {
        boolean[] result = new boolean[7];
        result[0] = inst[0];
        result[1] = inst[1];
        result[2] = inst[2];
        result[3] = inst[3];
        result[4] = inst[4];
        result[5] = inst[5];
        result[6] = inst[6];
        return result;
    }

    public static boolean[] rd(boolean[] inst) {
        boolean[] result = new boolean[5];
        result[0] = inst[7];
        result[1] = inst[8];
        result[2] = inst[9];
        result[3] = inst[10];
        result[4] = inst[11];
        return result;
    }

    public static boolean[] funct3(boolean[] inst) {
        boolean[] result = new boolean[3];
        result[0] = inst[12];
        result[1] = inst[13];
        result[2] = inst[14];
        return result;
    }

    public static boolean[] rs1(boolean[] inst) {
        boolean[] result = new boolean[5];
        result[0] = inst[15];
        result[1] = inst[16];
        result[2] = inst[17];
        result[3] = inst[18];
        result[4] = inst[19];
        return result;
    }

    public static boolean[] rs2(boolean[] inst) {
        boolean[] result = new boolean[5];
        result[0] = inst[20];
        result[1] = inst[21];
        result[2] = inst[22];
        result[3] = inst[23];
        result[4] = inst[24];
        return result;
    }

    public static boolean[] funct7(boolean[] inst) {
        boolean[] result = new boolean[7];
        result[0] = inst[25];
        result[1] = inst[26];
        result[2] = inst[27];
        result[3] = inst[28];
        result[4] = inst[29];
        result[5] = inst[30];
        result[6] = inst[31];
        return result;
    }

    public static boolean[] immI(boolean[] inst) {
        boolean[] result = new boolean[64];
        result[0] = inst[20];
        result[1] = inst[21];
        result[2] = inst[22];
        result[3] = inst[23];
        result[4] = inst[24];
        result[5] = inst[25];
        result[6] = inst[26];
        result[7] = inst[27];
        result[8] = inst[28];
        result[9] = inst[29];
        result[10] = inst[30];
        result[11] = inst[31];
        result[12] = inst[31];
        result[13] = inst[31];
        result[14] = inst[31];
        result[15] = inst[31];
        result[16] = inst[31];
        result[17] = inst[31];
        result[18] = inst[31];
        result[19] = inst[31];
        result[20] = inst[31];
        result[21] = inst[31];
        result[22] = inst[31];
        result[23] = inst[31];
        result[24] = inst[31];
        result[25] = inst[31];
        result[26] = inst[31];
        result[27] = inst[31];
        result[28] = inst[31];
        result[29] = inst[31];
        result[30] = inst[31];
        result[31] = inst[31];
        result[32] = inst[31];
        result[33] = inst[31];
        result[34] = inst[31];
        result[35] = inst[31];
        result[36] = inst[31];
        result[37] = inst[31];
        result[38] = inst[31];
        result[39] = inst[31];
        result[40] = inst[31];
        result[41] = inst[31];
        result[42] = inst[31];
        result[43] = inst[31];
        result[44] = inst[31];
        result[45] = inst[31];
        result[46] = inst[31];
        result[47] = inst[31];
        result[48] = inst[31];
        result[49] = inst[31];
        result[50] = inst[31];
        result[51] = inst[31];
        result[52] = inst[31];
        result[53] = inst[31];
        result[54] = inst[31];
        result[55] = inst[31];
        result[56] = inst[31];
        result[57] = inst[31];
        result[58] = inst[31];
        result[59] = inst[31];
        result[60] = inst[31];
        result[61] = inst[31];
        result[62] = inst[31];
        result[63] = inst[31];
        return result;
    }

    public static boolean[] immU(boolean[] inst) {
        boolean[] result = new boolean[64];
        result[0] = false;
        result[1] = false;
        result[2] = false;
        result[3] = false;
        result[4] = false;
        result[5] = false;
        result[6] = false;
        result[7] = false;
        result[8] = false;
        result[9] = false;
        result[10] = false;
        result[11] = false;
        result[12] = inst[12];
        result[13] = inst[13];
        result[14] = inst[14];
        result[15] = inst[15];
        result[16] = inst[16];
        result[17] = inst[17];
        result[18] = inst[18];
        result[19] = inst[19];
        result[20] = inst[20];
        result[21] = inst[21];
        result[22] = inst[22];
        result[23] = inst[23];
        result[24] = inst[24];
        result[25] = inst[25];
        result[26] = inst[26];
        result[27] = inst[27];
        result[28] = inst[28];
        result[29] = inst[29];
        result[30] = inst[30];
        result[31] = inst[31];
        result[32] = inst[31];
        result[33] = inst[31];
        result[34] = inst[31];
        result[35] = inst[31];
        result[36] = inst[31];
        result[37] = inst[31];
        result[38] = inst[31];
        result[39] = inst[31];
        result[40] = inst[31];
        result[41] = inst[31];
        result[42] = inst[31];
        result[43] = inst[31];
        result[44] = inst[31];
        result[45] = inst[31];
        result[46] = inst[31];
        result[47] = inst[31];
        result[48] = inst[31];
        result[49] = inst[31];
        result[50] = inst[31];
        result[51] = inst[31];
        result[52] = inst[31];
        result[53] = inst[31];
        result[54] = inst[31];
        result[55] = inst[31];
        result[56] = inst[31];
        result[57] = inst[31];
        result[58] = inst[31];
        result[59] = inst[31];
        result[60] = inst[31];
        result[61] = inst[31];
        result[62] = inst[31];
        result[63] = inst[31];
        return result;
    }

    public static boolean[] shamt(boolean[] inst) {
        boolean[] result = new boolean[6];
        result[0] = inst[20];
        result[1] = inst[21];
        result[2] = inst[22];
        result[3] = inst[23];
        result[4] = inst[24];
        result[5] = inst[25];
        return result;
    }

    public static boolean[] shamtW(boolean[] inst) {
        boolean[] result = new boolean[5];
        result[0] = inst[20];
        result[1] = inst[21];
        result[2] = inst[22];
        result[3] = inst[23];
        result[4] = inst[24];
        return result;
    }
}
