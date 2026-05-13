package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;

public class multiplier32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[64];
        
        boolean[] p0 = partialProduct(a, b[0]);
        boolean[] p1 = partialProduct(a, b[1]);
        boolean[] p2 = partialProduct(a, b[2]);
        boolean[] p3 = partialProduct(a, b[3]);
        boolean[] p4 = partialProduct(a, b[4]);
        boolean[] p5 = partialProduct(a, b[5]);
        boolean[] p6 = partialProduct(a, b[6]);
        boolean[] p7 = partialProduct(a, b[7]);
        boolean[] p8 = partialProduct(a, b[8]);
        boolean[] p9 = partialProduct(a, b[9]);
        boolean[] p10 = partialProduct(a, b[10]);
        boolean[] p11 = partialProduct(a, b[11]);
        boolean[] p12 = partialProduct(a, b[12]);
        boolean[] p13 = partialProduct(a, b[13]);
        boolean[] p14 = partialProduct(a, b[14]);
        boolean[] p15 = partialProduct(a, b[15]);
        boolean[] p16 = partialProduct(a, b[16]);
        boolean[] p17 = partialProduct(a, b[17]);
        boolean[] p18 = partialProduct(a, b[18]);
        boolean[] p19 = partialProduct(a, b[19]);
        boolean[] p20 = partialProduct(a, b[20]);
        boolean[] p21 = partialProduct(a, b[21]);
        boolean[] p22 = partialProduct(a, b[22]);
        boolean[] p23 = partialProduct(a, b[23]);
        boolean[] p24 = partialProduct(a, b[24]);
        boolean[] p25 = partialProduct(a, b[25]);
        boolean[] p26 = partialProduct(a, b[26]);
        boolean[] p27 = partialProduct(a, b[27]);
        boolean[] p28 = partialProduct(a, b[28]);
        boolean[] p29 = partialProduct(a, b[29]);
        boolean[] p30 = partialProduct(a, b[30]);
        boolean[] p31 = partialProduct(a, b[31]);
        
        boolean[] s0 = adder64B.module(result, p0);
        boolean[] s1 = adder64B.module(s0, shiftLeftBy1(p1));
        boolean[] s2 = adder64B.module(s1, shiftLeftBy2(p2));
        boolean[] s3 = adder64B.module(s2, shiftLeftBy3(p3));
        boolean[] s4 = adder64B.module(s3, shiftLeftBy4(p4));
        boolean[] s5 = adder64B.module(s4, shiftLeftBy5(p5));
        boolean[] s6 = adder64B.module(s5, shiftLeftBy6(p6));
        boolean[] s7 = adder64B.module(s6, shiftLeftBy7(p7));
        boolean[] s8 = adder64B.module(s7, shiftLeftBy8(p8));
        boolean[] s9 = adder64B.module(s8, shiftLeftBy9(p9));
        boolean[] s10 = adder64B.module(s9, shiftLeftBy10(p10));
        boolean[] s11 = adder64B.module(s10, shiftLeftBy11(p11));
        boolean[] s12 = adder64B.module(s11, shiftLeftBy12(p12));
        boolean[] s13 = adder64B.module(s12, shiftLeftBy13(p13));
        boolean[] s14 = adder64B.module(s13, shiftLeftBy14(p14));
        boolean[] s15 = adder64B.module(s14, shiftLeftBy15(p15));
        boolean[] s16 = adder64B.module(s15, shiftLeftBy16(p16));
        boolean[] s17 = adder64B.module(s16, shiftLeftBy17(p17));
        boolean[] s18 = adder64B.module(s17, shiftLeftBy18(p18));
        boolean[] s19 = adder64B.module(s18, shiftLeftBy19(p19));
        boolean[] s20 = adder64B.module(s19, shiftLeftBy20(p20));
        boolean[] s21 = adder64B.module(s20, shiftLeftBy21(p21));
        boolean[] s22 = adder64B.module(s21, shiftLeftBy22(p22));
        boolean[] s23 = adder64B.module(s22, shiftLeftBy23(p23));
        boolean[] s24 = adder64B.module(s23, shiftLeftBy24(p24));
        boolean[] s25 = adder64B.module(s24, shiftLeftBy25(p25));
        boolean[] s26 = adder64B.module(s25, shiftLeftBy26(p26));
        boolean[] s27 = adder64B.module(s26, shiftLeftBy27(p27));
        boolean[] s28 = adder64B.module(s27, shiftLeftBy28(p28));
        boolean[] s29 = adder64B.module(s28, shiftLeftBy29(p29));
        boolean[] s30 = adder64B.module(s29, shiftLeftBy30(p30));
        boolean[] s31 = adder64B.module(s30, shiftLeftBy31(p31));
        
        return s31;
    }
    
    private static boolean[] partialProduct(boolean[] a, boolean b) {
        boolean[] result = new boolean[64];
        result[0] = and.gate(a[0], b);
        result[1] = and.gate(a[1], b);
        result[2] = and.gate(a[2], b);
        result[3] = and.gate(a[3], b);
        result[4] = and.gate(a[4], b);
        result[5] = and.gate(a[5], b);
        result[6] = and.gate(a[6], b);
        result[7] = and.gate(a[7], b);
        result[8] = and.gate(a[8], b);
        result[9] = and.gate(a[9], b);
        result[10] = and.gate(a[10], b);
        result[11] = and.gate(a[11], b);
        result[12] = and.gate(a[12], b);
        result[13] = and.gate(a[13], b);
        result[14] = and.gate(a[14], b);
        result[15] = and.gate(a[15], b);
        result[16] = and.gate(a[16], b);
        result[17] = and.gate(a[17], b);
        result[18] = and.gate(a[18], b);
        result[19] = and.gate(a[19], b);
        result[20] = and.gate(a[20], b);
        result[21] = and.gate(a[21], b);
        result[22] = and.gate(a[22], b);
        result[23] = and.gate(a[23], b);
        result[24] = and.gate(a[24], b);
        result[25] = and.gate(a[25], b);
        result[26] = and.gate(a[26], b);
        result[27] = and.gate(a[27], b);
        result[28] = and.gate(a[28], b);
        result[29] = and.gate(a[29], b);
        result[30] = and.gate(a[30], b);
        result[31] = and.gate(a[31], b);
        result[32] = false;
        result[33] = false;
        result[34] = false;
        result[35] = false;
        result[36] = false;
        result[37] = false;
        result[38] = false;
        result[39] = false;
        result[40] = false;
        result[41] = false;
        result[42] = false;
        result[43] = false;
        result[44] = false;
        result[45] = false;
        result[46] = false;
        result[47] = false;
        result[48] = false;
        result[49] = false;
        result[50] = false;
        result[51] = false;
        result[52] = false;
        result[53] = false;
        result[54] = false;
        result[55] = false;
        result[56] = false;
        result[57] = false;
        result[58] = false;
        result[59] = false;
        result[60] = false;
        result[61] = false;
        result[62] = false;
        result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy1(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false;
        result[1] = in[0];
        result[2] = in[1];
        result[3] = in[2];
        result[4] = in[3];
        result[5] = in[4];
        result[6] = in[5];
        result[7] = in[6];
        result[8] = in[7];
        result[9] = in[8];
        result[10] = in[9];
        result[11] = in[10];
        result[12] = in[11];
        result[13] = in[12];
        result[14] = in[13];
        result[15] = in[14];
        result[16] = in[15];
        result[17] = in[16];
        result[18] = in[17];
        result[19] = in[18];
        result[20] = in[19];
        result[21] = in[20];
        result[22] = in[21];
        result[23] = in[22];
        result[24] = in[23];
        result[25] = in[24];
        result[26] = in[25];
        result[27] = in[26];
        result[28] = in[27];
        result[29] = in[28];
        result[30] = in[29];
        result[31] = in[30];
        result[32] = in[31];
        result[33] = false;
        result[34] = false;
        result[35] = false;
        result[36] = false;
        result[37] = false;
        result[38] = false;
        result[39] = false;
        result[40] = false;
        result[41] = false;
        result[42] = false;
        result[43] = false;
        result[44] = false;
        result[45] = false;
        result[46] = false;
        result[47] = false;
        result[48] = false;
        result[49] = false;
        result[50] = false;
        result[51] = false;
        result[52] = false;
        result[53] = false;
        result[54] = false;
        result[55] = false;
        result[56] = false;
        result[57] = false;
        result[58] = false;
        result[59] = false;
        result[60] = false;
        result[61] = false;
        result[62] = false;
        result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy2(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false;
        result[1] = false;
        result[2] = in[0];
        result[3] = in[1];
        result[4] = in[2];
        result[5] = in[3];
        result[6] = in[4];
        result[7] = in[5];
        result[8] = in[6];
        result[9] = in[7];
        result[10] = in[8];
        result[11] = in[9];
        result[12] = in[10];
        result[13] = in[11];
        result[14] = in[12];
        result[15] = in[13];
        result[16] = in[14];
        result[17] = in[15];
        result[18] = in[16];
        result[19] = in[17];
        result[20] = in[18];
        result[21] = in[19];
        result[22] = in[20];
        result[23] = in[21];
        result[24] = in[22];
        result[25] = in[23];
        result[26] = in[24];
        result[27] = in[25];
        result[28] = in[26];
        result[29] = in[27];
        result[30] = in[28];
        result[31] = in[29];
        result[32] = in[30];
        result[33] = in[31];
        result[34] = false;
        result[35] = false;
        result[36] = false;
        result[37] = false;
        result[38] = false;
        result[39] = false;
        result[40] = false;
        result[41] = false;
        result[42] = false;
        result[43] = false;
        result[44] = false;
        result[45] = false;
        result[46] = false;
        result[47] = false;
        result[48] = false;
        result[49] = false;
        result[50] = false;
        result[51] = false;
        result[52] = false;
        result[53] = false;
        result[54] = false;
        result[55] = false;
        result[56] = false;
        result[57] = false;
        result[58] = false;
        result[59] = false;
        result[60] = false;
        result[61] = false;
        result[62] = false;
        result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy3(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false; result[1] = false; result[2] = false;
        result[3] = in[0]; result[4] = in[1]; result[5] = in[2];
        result[6] = in[3]; result[7] = in[4]; result[8] = in[5];
        result[9] = in[6]; result[10] = in[7]; result[11] = in[8];
        result[12] = in[9]; result[13] = in[10]; result[14] = in[11];
        result[15] = in[12]; result[16] = in[13]; result[17] = in[14];
        result[18] = in[15]; result[19] = in[16]; result[20] = in[17];
        result[21] = in[18]; result[22] = in[19]; result[23] = in[20];
        result[24] = in[21]; result[25] = in[22]; result[26] = in[23];
        result[27] = in[24]; result[28] = in[25]; result[29] = in[26];
        result[30] = in[27]; result[31] = in[28]; result[32] = in[29];
        result[33] = in[30]; result[34] = in[31];
        result[35] = false; result[36] = false; result[37] = false;
        result[38] = false; result[39] = false; result[40] = false;
        result[41] = false; result[42] = false; result[43] = false;
        result[44] = false; result[45] = false; result[46] = false;
        result[47] = false; result[48] = false; result[49] = false;
        result[50] = false; result[51] = false; result[52] = false;
        result[53] = false; result[54] = false; result[55] = false;
        result[56] = false; result[57] = false; result[58] = false;
        result[59] = false; result[60] = false; result[61] = false;
        result[62] = false; result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy4(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false; result[1] = false; result[2] = false; result[3] = false;
        result[4] = in[0]; result[5] = in[1]; result[6] = in[2]; result[7] = in[3];
        result[8] = in[4]; result[9] = in[5]; result[10] = in[6]; result[11] = in[7];
        result[12] = in[8]; result[13] = in[9]; result[14] = in[10]; result[15] = in[11];
        result[16] = in[12]; result[17] = in[13]; result[18] = in[14]; result[19] = in[15];
        result[20] = in[16]; result[21] = in[17]; result[22] = in[18]; result[23] = in[19];
        result[24] = in[20]; result[25] = in[21]; result[26] = in[22]; result[27] = in[23];
        result[28] = in[24]; result[29] = in[25]; result[30] = in[26]; result[31] = in[27];
        result[32] = in[28]; result[33] = in[29]; result[34] = in[30]; result[35] = in[31];
        result[36] = false; result[37] = false; result[38] = false; result[39] = false;
        result[40] = false; result[41] = false; result[42] = false; result[43] = false;
        result[44] = false; result[45] = false; result[46] = false; result[47] = false;
        result[48] = false; result[49] = false; result[50] = false; result[51] = false;
        result[52] = false; result[53] = false; result[54] = false; result[55] = false;
        result[56] = false; result[57] = false; result[58] = false; result[59] = false;
        result[60] = false; result[61] = false; result[62] = false; result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy5(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false; result[1] = false; result[2] = false; result[3] = false; result[4] = false;
        result[5] = in[0]; result[6] = in[1]; result[7] = in[2]; result[8] = in[3]; result[9] = in[4];
        result[10] = in[5]; result[11] = in[6]; result[12] = in[7]; result[13] = in[8]; result[14] = in[9];
        result[15] = in[10]; result[16] = in[11]; result[17] = in[12]; result[18] = in[13]; result[19] = in[14];
        result[20] = in[15]; result[21] = in[16]; result[22] = in[17]; result[23] = in[18]; result[24] = in[19];
        result[25] = in[20]; result[26] = in[21]; result[27] = in[22]; result[28] = in[23]; result[29] = in[24];
        result[30] = in[25]; result[31] = in[26]; result[32] = in[27]; result[33] = in[28]; result[34] = in[29];
        result[35] = in[30]; result[36] = in[31];
        result[37] = false; result[38] = false; result[39] = false; result[40] = false; result[41] = false;
        result[42] = false; result[43] = false; result[44] = false; result[45] = false; result[46] = false;
        result[47] = false; result[48] = false; result[49] = false; result[50] = false; result[51] = false;
        result[52] = false; result[53] = false; result[54] = false; result[55] = false; result[56] = false;
        result[57] = false; result[58] = false; result[59] = false; result[60] = false; result[61] = false;
        result[62] = false; result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy6(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false; result[1] = false; result[2] = false; result[3] = false; result[4] = false; result[5] = false;
        result[6] = in[0]; result[7] = in[1]; result[8] = in[2]; result[9] = in[3]; result[10] = in[4]; result[11] = in[5];
        result[12] = in[6]; result[13] = in[7]; result[14] = in[8]; result[15] = in[9]; result[16] = in[10]; result[17] = in[11];
        result[18] = in[12]; result[19] = in[13]; result[20] = in[14]; result[21] = in[15]; result[22] = in[16]; result[23] = in[17];
        result[24] = in[18]; result[25] = in[19]; result[26] = in[20]; result[27] = in[21]; result[28] = in[22]; result[29] = in[23];
        result[30] = in[24]; result[31] = in[25]; result[32] = in[26]; result[33] = in[27]; result[34] = in[28]; result[35] = in[29];
        result[36] = in[30]; result[37] = in[31];
        result[38] = false; result[39] = false; result[40] = false; result[41] = false; result[42] = false; result[43] = false;
        result[44] = false; result[45] = false; result[46] = false; result[47] = false; result[48] = false; result[49] = false;
        result[50] = false; result[51] = false; result[52] = false; result[53] = false; result[54] = false; result[55] = false;
        result[56] = false; result[57] = false; result[58] = false; result[59] = false; result[60] = false; result[61] = false;
        result[62] = false; result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy7(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false; result[1] = false; result[2] = false; result[3] = false; result[4] = false; result[5] = false; result[6] = false;
        result[7] = in[0]; result[8] = in[1]; result[9] = in[2]; result[10] = in[3]; result[11] = in[4]; result[12] = in[5]; result[13] = in[6];
        result[14] = in[7]; result[15] = in[8]; result[16] = in[9]; result[17] = in[10]; result[18] = in[11]; result[19] = in[12]; result[20] = in[13];
        result[21] = in[14]; result[22] = in[15]; result[23] = in[16]; result[24] = in[17]; result[25] = in[18]; result[26] = in[19]; result[27] = in[20];
        result[28] = in[21]; result[29] = in[22]; result[30] = in[23]; result[31] = in[24]; result[32] = in[25]; result[33] = in[26]; result[34] = in[27];
        result[35] = in[28]; result[36] = in[29]; result[37] = in[30]; result[38] = in[31];
        result[39] = false; result[40] = false; result[41] = false; result[42] = false; result[43] = false; result[44] = false; result[45] = false;
        result[46] = false; result[47] = false; result[48] = false; result[49] = false; result[50] = false; result[51] = false; result[52] = false;
        result[53] = false; result[54] = false; result[55] = false; result[56] = false; result[57] = false; result[58] = false; result[59] = false;
        result[60] = false; result[61] = false; result[62] = false; result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy8(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false; result[1] = false; result[2] = false; result[3] = false;
        result[4] = false; result[5] = false; result[6] = false; result[7] = false;
        result[8] = in[0]; result[9] = in[1]; result[10] = in[2]; result[11] = in[3];
        result[12] = in[4]; result[13] = in[5]; result[14] = in[6]; result[15] = in[7];
        result[16] = in[8]; result[17] = in[9]; result[18] = in[10]; result[19] = in[11];
        result[20] = in[12]; result[21] = in[13]; result[22] = in[14]; result[23] = in[15];
        result[24] = in[16]; result[25] = in[17]; result[26] = in[18]; result[27] = in[19];
        result[28] = in[20]; result[29] = in[21]; result[30] = in[22]; result[31] = in[23];
        result[32] = in[24]; result[33] = in[25]; result[34] = in[26]; result[35] = in[27];
        result[36] = in[28]; result[37] = in[29]; result[38] = in[30]; result[39] = in[31];
        result[40] = false; result[41] = false; result[42] = false; result[43] = false;
        result[44] = false; result[45] = false; result[46] = false; result[47] = false;
        result[48] = false; result[49] = false; result[50] = false; result[51] = false;
        result[52] = false; result[53] = false; result[54] = false; result[55] = false;
        result[56] = false; result[57] = false; result[58] = false; result[59] = false;
        result[60] = false; result[61] = false; result[62] = false; result[63] = false;
        return result;
    }
    
    private static boolean[] shiftLeftBy9(boolean[] in) { return shiftLeft(shiftLeftBy8(in)); }
    private static boolean[] shiftLeftBy10(boolean[] in) { return shiftLeft(shiftLeftBy9(in)); }
    private static boolean[] shiftLeftBy11(boolean[] in) { return shiftLeft(shiftLeftBy10(in)); }
    private static boolean[] shiftLeftBy12(boolean[] in) { return shiftLeft(shiftLeftBy11(in)); }
    private static boolean[] shiftLeftBy13(boolean[] in) { return shiftLeft(shiftLeftBy12(in)); }
    private static boolean[] shiftLeftBy14(boolean[] in) { return shiftLeft(shiftLeftBy13(in)); }
    private static boolean[] shiftLeftBy15(boolean[] in) { return shiftLeft(shiftLeftBy14(in)); }
    private static boolean[] shiftLeftBy16(boolean[] in) { return shiftLeft(shiftLeftBy15(in)); }
    private static boolean[] shiftLeftBy17(boolean[] in) { return shiftLeft(shiftLeftBy16(in)); }
    private static boolean[] shiftLeftBy18(boolean[] in) { return shiftLeft(shiftLeftBy17(in)); }
    private static boolean[] shiftLeftBy19(boolean[] in) { return shiftLeft(shiftLeftBy18(in)); }
    private static boolean[] shiftLeftBy20(boolean[] in) { return shiftLeft(shiftLeftBy19(in)); }
    private static boolean[] shiftLeftBy21(boolean[] in) { return shiftLeft(shiftLeftBy20(in)); }
    private static boolean[] shiftLeftBy22(boolean[] in) { return shiftLeft(shiftLeftBy21(in)); }
    private static boolean[] shiftLeftBy23(boolean[] in) { return shiftLeft(shiftLeftBy22(in)); }
    private static boolean[] shiftLeftBy24(boolean[] in) { return shiftLeft(shiftLeftBy23(in)); }
    private static boolean[] shiftLeftBy25(boolean[] in) { return shiftLeft(shiftLeftBy24(in)); }
    private static boolean[] shiftLeftBy26(boolean[] in) { return shiftLeft(shiftLeftBy25(in)); }
    private static boolean[] shiftLeftBy27(boolean[] in) { return shiftLeft(shiftLeftBy26(in)); }
    private static boolean[] shiftLeftBy28(boolean[] in) { return shiftLeft(shiftLeftBy27(in)); }
    private static boolean[] shiftLeftBy29(boolean[] in) { return shiftLeft(shiftLeftBy28(in)); }
    private static boolean[] shiftLeftBy30(boolean[] in) { return shiftLeft(shiftLeftBy29(in)); }
    private static boolean[] shiftLeftBy31(boolean[] in) { return shiftLeft(shiftLeftBy30(in)); }
    private static boolean[] shiftLeftBy32(boolean[] in) { return shiftLeft(shiftLeftBy31(in)); }
    
    private static boolean[] shiftLeft(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = false;
        result[1] = in[0];
        result[2] = in[1];
        result[3] = in[2];
        result[4] = in[3];
        result[5] = in[4];
        result[6] = in[5];
        result[7] = in[6];
        result[8] = in[7];
        result[9] = in[8];
        result[10] = in[9];
        result[11] = in[10];
        result[12] = in[11];
        result[13] = in[12];
        result[14] = in[13];
        result[15] = in[14];
        result[16] = in[15];
        result[17] = in[16];
        result[18] = in[17];
        result[19] = in[18];
        result[20] = in[19];
        result[21] = in[20];
        result[22] = in[21];
        result[23] = in[22];
        result[24] = in[23];
        result[25] = in[24];
        result[26] = in[25];
        result[27] = in[26];
        result[28] = in[27];
        result[29] = in[28];
        result[30] = in[29];
        result[31] = in[30];
        result[32] = in[31];
        result[33] = in[32];
        result[34] = in[33];
        result[35] = in[34];
        result[36] = in[35];
        result[37] = in[36];
        result[38] = in[37];
        result[39] = in[38];
        result[40] = in[39];
        result[41] = in[40];
        result[42] = in[41];
        result[43] = in[42];
        result[44] = in[43];
        result[45] = in[44];
        result[46] = in[45];
        result[47] = in[46];
        result[48] = in[47];
        result[49] = in[48];
        result[50] = in[49];
        result[51] = in[50];
        result[52] = in[51];
        result[53] = in[52];
        result[54] = in[53];
        result[55] = in[54];
        result[56] = in[55];
        result[57] = in[56];
        result[58] = in[57];
        result[59] = in[58];
        result[60] = in[59];
        result[61] = in[60];
        result[62] = in[61];
        result[63] = in[62];
        return result;
    }
}
