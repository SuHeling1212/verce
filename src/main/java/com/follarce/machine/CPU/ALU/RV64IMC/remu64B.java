package com.follarce.machine.CPU.ALU.RV64IMC;

import com.follarce.machine.logic.gate.*;

public class remu64B {

    public static boolean[] module(boolean[] dividend, boolean[] divisor) {
        // 计算无符号除法，得到商
        boolean[] quotient = divu64B.module(dividend, divisor);
         
        // 计算 product = quotient * divisor (128位)
        boolean[] product128 = multiplier64B.module(quotient, divisor);

        // 提取低64位乘积
        boolean[] productLow64 = extractLow64(product128);

        // 计算余数 = dividend - productLow64
        boolean[] remainder = extractLow64(subtractor64B.module(dividend, productLow64));

        return remainder;
    }

    private static boolean[] extractLow64(boolean[] in128) {
        boolean[] result = new boolean[64];
        result[0] = in128[0];
        result[1] = in128[1];
        result[2] = in128[2];
        result[3] = in128[3];
        result[4] = in128[4];
        result[5] = in128[5];
        result[6] = in128[6];
        result[7] = in128[7];
        result[8] = in128[8];
        result[9] = in128[9];
        result[10] = in128[10];
        result[11] = in128[11];
        result[12] = in128[12];
        result[13] = in128[13];
        result[14] = in128[14];
        result[15] = in128[15];
        result[16] = in128[16];
        result[17] = in128[17];
        result[18] = in128[18];
        result[19] = in128[19];
        result[20] = in128[20];
        result[21] = in128[21];
        result[22] = in128[22];
        result[23] = in128[23];
        result[24] = in128[24];
        result[25] = in128[25];
        result[26] = in128[26];
        result[27] = in128[27];
        result[28] = in128[28];
        result[29] = in128[29];
        result[30] = in128[30];
        result[31] = in128[31];
        result[32] = in128[32];
        result[33] = in128[33];
        result[34] = in128[34];
        result[35] = in128[35];
        result[36] = in128[36];
        result[37] = in128[37];
        result[38] = in128[38];
        result[39] = in128[39];
        result[40] = in128[40];
        result[41] = in128[41];
        result[42] = in128[42];
        result[43] = in128[43];
        result[44] = in128[44];
        result[45] = in128[45];
        result[46] = in128[46];
        result[47] = in128[47];
        result[48] = in128[48];
        result[49] = in128[49];
        result[50] = in128[50];
        result[51] = in128[51];
        result[52] = in128[52];
        result[53] = in128[53];
        result[54] = in128[54];
        result[55] = in128[55];
        result[56] = in128[56];
        result[57] = in128[57];
        result[58] = in128[58];
        result[59] = in128[59];
        result[60] = in128[60];
        result[61] = in128[61];
        result[62] = in128[62];
        result[63] = in128[63];
        return result;
    }
}