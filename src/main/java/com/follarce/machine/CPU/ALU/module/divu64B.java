package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.*;

public class divu64B {

    public static boolean[] module(boolean[] dividend, boolean[] divisor) {
        boolean[] R = zeros64();
        boolean[] Q = copy64(dividend);

        boolean[] qBit = new boolean[64];

        boolean[][] stepResult;
        
        stepResult = step(R, Q, divisor, 0);
        R = stepResult[0];
        qBit[0] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[0]);

        stepResult = step(R, Q, divisor, 1);
        R = stepResult[0];
        qBit[1] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[1]);

        stepResult = step(R, Q, divisor, 2);
        R = stepResult[0];
        qBit[2] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[2]);

        stepResult = step(R, Q, divisor, 3);
        R = stepResult[0];
        qBit[3] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[3]);

        stepResult = step(R, Q, divisor, 4);
        R = stepResult[0];
        qBit[4] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[4]);

        stepResult = step(R, Q, divisor, 5);
        R = stepResult[0];
        qBit[5] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[5]);

        stepResult = step(R, Q, divisor, 6);
        R = stepResult[0];
        qBit[6] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[6]);

        stepResult = step(R, Q, divisor, 7);
        R = stepResult[0];
        qBit[7] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[7]);

        stepResult = step(R, Q, divisor, 8);
        R = stepResult[0];
        qBit[8] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[8]);

        stepResult = step(R, Q, divisor, 9);
        R = stepResult[0];
        qBit[9] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[9]);

        stepResult = step(R, Q, divisor, 10);
        R = stepResult[0];
        qBit[10] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[10]);

        stepResult = step(R, Q, divisor, 11);
        R = stepResult[0];
        qBit[11] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[11]);

        stepResult = step(R, Q, divisor, 12);
        R = stepResult[0];
        qBit[12] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[12]);

        stepResult = step(R, Q, divisor, 13);
        R = stepResult[0];
        qBit[13] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[13]);

        stepResult = step(R, Q, divisor, 14);
        R = stepResult[0];
        qBit[14] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[14]);

        stepResult = step(R, Q, divisor, 15);
        R = stepResult[0];
        qBit[15] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[15]);

        stepResult = step(R, Q, divisor, 16);
        R = stepResult[0];
        qBit[16] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[16]);

        stepResult = step(R, Q, divisor, 17);
        R = stepResult[0];
        qBit[17] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[17]);

        stepResult = step(R, Q, divisor, 18);
        R = stepResult[0];
        qBit[18] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[18]);

        stepResult = step(R, Q, divisor, 19);
        R = stepResult[0];
        qBit[19] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[19]);

        stepResult = step(R, Q, divisor, 20);
        R = stepResult[0];
        qBit[20] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[20]);

        stepResult = step(R, Q, divisor, 21);
        R = stepResult[0];
        qBit[21] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[21]);

        stepResult = step(R, Q, divisor, 22);
        R = stepResult[0];
        qBit[22] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[22]);

        stepResult = step(R, Q, divisor, 23);
        R = stepResult[0];
        qBit[23] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[23]);

        stepResult = step(R, Q, divisor, 24);
        R = stepResult[0];
        qBit[24] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[24]);

        stepResult = step(R, Q, divisor, 25);
        R = stepResult[0];
        qBit[25] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[25]);

        stepResult = step(R, Q, divisor, 26);
        R = stepResult[0];
        qBit[26] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[26]);

        stepResult = step(R, Q, divisor, 27);
        R = stepResult[0];
        qBit[27] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[27]);

        stepResult = step(R, Q, divisor, 28);
        R = stepResult[0];
        qBit[28] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[28]);

        stepResult = step(R, Q, divisor, 29);
        R = stepResult[0];
        qBit[29] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[29]);

        stepResult = step(R, Q, divisor, 30);
        R = stepResult[0];
        qBit[30] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[30]);

        stepResult = step(R, Q, divisor, 31);
        R = stepResult[0];
        qBit[31] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[31]);

        stepResult = step(R, Q, divisor, 32);
        R = stepResult[0];
        qBit[32] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[32]);

        stepResult = step(R, Q, divisor, 33);
        R = stepResult[0];
        qBit[33] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[33]);

        stepResult = step(R, Q, divisor, 34);
        R = stepResult[0];
        qBit[34] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[34]);

        stepResult = step(R, Q, divisor, 35);
        R = stepResult[0];
        qBit[35] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[35]);

        stepResult = step(R, Q, divisor, 36);
        R = stepResult[0];
        qBit[36] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[36]);

        stepResult = step(R, Q, divisor, 37);
        R = stepResult[0];
        qBit[37] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[37]);

        stepResult = step(R, Q, divisor, 38);
        R = stepResult[0];
        qBit[38] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[38]);

        stepResult = step(R, Q, divisor, 39);
        R = stepResult[0];
        qBit[39] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[39]);

        stepResult = step(R, Q, divisor, 40);
        R = stepResult[0];
        qBit[40] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[40]);

        stepResult = step(R, Q, divisor, 41);
        R = stepResult[0];
        qBit[41] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[41]);

        stepResult = step(R, Q, divisor, 42);
        R = stepResult[0];
        qBit[42] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[42]);

        stepResult = step(R, Q, divisor, 43);
        R = stepResult[0];
        qBit[43] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[43]);

        stepResult = step(R, Q, divisor, 44);
        R = stepResult[0];
        qBit[44] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[44]);

        stepResult = step(R, Q, divisor, 45);
        R = stepResult[0];
        qBit[45] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[45]);

        stepResult = step(R, Q, divisor, 46);
        R = stepResult[0];
        qBit[46] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[46]);

        stepResult = step(R, Q, divisor, 47);
        R = stepResult[0];
        qBit[47] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[47]);

        stepResult = step(R, Q, divisor, 48);
        R = stepResult[0];
        qBit[48] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[48]);

        stepResult = step(R, Q, divisor, 49);
        R = stepResult[0];
        qBit[49] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[49]);

        stepResult = step(R, Q, divisor, 50);
        R = stepResult[0];
        qBit[50] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[50]);

        stepResult = step(R, Q, divisor, 51);
        R = stepResult[0];
        qBit[51] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[51]);

        stepResult = step(R, Q, divisor, 52);
        R = stepResult[0];
        qBit[52] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[52]);

        stepResult = step(R, Q, divisor, 53);
        R = stepResult[0];
        qBit[53] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[53]);

        stepResult = step(R, Q, divisor, 54);
        R = stepResult[0];
        qBit[54] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[54]);

        stepResult = step(R, Q, divisor, 55);
        R = stepResult[0];
        qBit[55] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[55]);

        stepResult = step(R, Q, divisor, 56);
        R = stepResult[0];
        qBit[56] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[56]);

        stepResult = step(R, Q, divisor, 57);
        R = stepResult[0];
        qBit[57] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[57]);

        stepResult = step(R, Q, divisor, 58);
        R = stepResult[0];
        qBit[58] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[58]);

        stepResult = step(R, Q, divisor, 59);
        R = stepResult[0];
        qBit[59] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[59]);

        stepResult = step(R, Q, divisor, 60);
        R = stepResult[0];
        qBit[60] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[60]);

        stepResult = step(R, Q, divisor, 61);
        R = stepResult[0];
        qBit[61] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[61]);

        stepResult = step(R, Q, divisor, 62);
        R = stepResult[0];
        qBit[62] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[62]);

        stepResult = step(R, Q, divisor, 63);
        R = stepResult[0];
        qBit[63] = stepResult[1][0];
        Q = shiftLeftQ(Q, qBit[63]);

        boolean[] result = new boolean[128];
        result[0] = Q[0]; result[1] = Q[1]; result[2] = Q[2]; result[3] = Q[3];
        result[4] = Q[4]; result[5] = Q[5]; result[6] = Q[6]; result[7] = Q[7];
        result[8] = Q[8]; result[9] = Q[9]; result[10] = Q[10]; result[11] = Q[11];
        result[12] = Q[12]; result[13] = Q[13]; result[14] = Q[14]; result[15] = Q[15];
        result[16] = Q[16]; result[17] = Q[17]; result[18] = Q[18]; result[19] = Q[19];
        result[20] = Q[20]; result[21] = Q[21]; result[22] = Q[22]; result[23] = Q[23];
        result[24] = Q[24]; result[25] = Q[25]; result[26] = Q[26]; result[27] = Q[27];
        result[28] = Q[28]; result[29] = Q[29]; result[30] = Q[30]; result[31] = Q[31];
        result[32] = Q[32]; result[33] = Q[33]; result[34] = Q[34]; result[35] = Q[35];
        result[36] = Q[36]; result[37] = Q[37]; result[38] = Q[38]; result[39] = Q[39];
        result[40] = Q[40]; result[41] = Q[41]; result[42] = Q[42]; result[43] = Q[43];
        result[44] = Q[44]; result[45] = Q[45]; result[46] = Q[46]; result[47] = Q[47];
        result[48] = Q[48]; result[49] = Q[49]; result[50] = Q[50]; result[51] = Q[51];
        result[52] = Q[52]; result[53] = Q[53]; result[54] = Q[54]; result[55] = Q[55];
        result[56] = Q[56]; result[57] = Q[57]; result[58] = Q[58]; result[59] = Q[59];
        result[60] = Q[60]; result[61] = Q[61]; result[62] = Q[62]; result[63] = Q[63];
        result[64] = R[0]; result[65] = R[1]; result[66] = R[2]; result[67] = R[3];
        result[68] = R[4]; result[69] = R[5]; result[70] = R[6]; result[71] = R[7];
        result[72] = R[8]; result[73] = R[9]; result[74] = R[10]; result[75] = R[11];
        result[76] = R[12]; result[77] = R[13]; result[78] = R[14]; result[79] = R[15];
        result[80] = R[16]; result[81] = R[17]; result[82] = R[18]; result[83] = R[19];
        result[84] = R[20]; result[85] = R[21]; result[86] = R[22]; result[87] = R[23];
        result[88] = R[24]; result[89] = R[25]; result[90] = R[26]; result[91] = R[27];
        result[92] = R[28]; result[93] = R[29]; result[94] = R[30]; result[95] = R[31];
        result[96] = R[32]; result[97] = R[33]; result[98] = R[34]; result[99] = R[35];
        result[100] = R[36]; result[101] = R[37]; result[102] = R[38]; result[103] = R[39];
        result[104] = R[40]; result[105] = R[41]; result[106] = R[42]; result[107] = R[43];
        result[108] = R[44]; result[109] = R[45]; result[110] = R[46]; result[111] = R[47];
        result[112] = R[48]; result[113] = R[49]; result[114] = R[50]; result[115] = R[51];
        result[116] = R[52]; result[117] = R[53]; result[118] = R[54]; result[119] = R[55];
        result[120] = R[56]; result[121] = R[57]; result[122] = R[58]; result[123] = R[59];
        result[124] = R[60]; result[125] = R[61]; result[126] = R[62]; result[127] = R[63];

        return result;
    }

    private static boolean[][] step(boolean[] R, boolean[] Q, boolean[] divisor, int stepNum) {
        boolean[] shiftedR = shiftLeftR(R, Q[63]);
        boolean[] diff = subtractor64B.module(shiftedR, divisor);
        boolean canSubtract = diff[64];
        boolean[] newR = mux64(shiftedR, diff, canSubtract);
        boolean[] qBit = new boolean[1];
        qBit[0] = canSubtract;
        return new boolean[][] { newR, qBit };
    }

    private static boolean[] shiftLeftR(boolean[] R, boolean newBit) {
        boolean[] result = new boolean[64];
        result[0] = newBit;
        result[1] = R[0]; result[2] = R[1]; result[3] = R[2]; result[4] = R[3];
        result[5] = R[4]; result[6] = R[5]; result[7] = R[6]; result[8] = R[7];
        result[9] = R[8]; result[10] = R[9]; result[11] = R[10]; result[12] = R[11];
        result[13] = R[12]; result[14] = R[13]; result[15] = R[14]; result[16] = R[15];
        result[17] = R[16]; result[18] = R[17]; result[19] = R[18]; result[20] = R[19];
        result[21] = R[20]; result[22] = R[21]; result[23] = R[22]; result[24] = R[23];
        result[25] = R[24]; result[26] = R[25]; result[27] = R[26]; result[28] = R[27];
        result[29] = R[28]; result[30] = R[29]; result[31] = R[30]; result[32] = R[31];
        result[33] = R[32]; result[34] = R[33]; result[35] = R[34]; result[36] = R[35];
        result[37] = R[36]; result[38] = R[37]; result[39] = R[38]; result[40] = R[39];
        result[41] = R[40]; result[42] = R[41]; result[43] = R[42]; result[44] = R[43];
        result[45] = R[44]; result[46] = R[45]; result[47] = R[46]; result[48] = R[47];
        result[49] = R[48]; result[50] = R[49]; result[51] = R[50]; result[52] = R[51];
        result[53] = R[52]; result[54] = R[53]; result[55] = R[54]; result[56] = R[55];
        result[57] = R[56]; result[58] = R[57]; result[59] = R[58]; result[60] = R[59];
        result[61] = R[60]; result[62] = R[61]; result[63] = R[62];
        return result;
    }

    private static boolean[] shiftLeftQ(boolean[] Q, boolean newBit) {
        boolean[] result = new boolean[64];
        result[0] = newBit;
        result[1] = Q[0]; result[2] = Q[1]; result[3] = Q[2]; result[4] = Q[3];
        result[5] = Q[4]; result[6] = Q[5]; result[7] = Q[6]; result[8] = Q[7];
        result[9] = Q[8]; result[10] = Q[9]; result[11] = Q[10]; result[12] = Q[11];
        result[13] = Q[12]; result[14] = Q[13]; result[15] = Q[14]; result[16] = Q[15];
        result[17] = Q[16]; result[18] = Q[17]; result[19] = Q[18]; result[20] = Q[19];
        result[21] = Q[20]; result[22] = Q[21]; result[23] = Q[22]; result[24] = Q[23];
        result[25] = Q[24]; result[26] = Q[25]; result[27] = Q[26]; result[28] = Q[27];
        result[29] = Q[28]; result[30] = Q[29]; result[31] = Q[30]; result[32] = Q[31];
        result[33] = Q[32]; result[34] = Q[33]; result[35] = Q[34]; result[36] = Q[35];
        result[37] = Q[36]; result[38] = Q[37]; result[39] = Q[38]; result[40] = Q[39];
        result[41] = Q[40]; result[42] = Q[41]; result[43] = Q[42]; result[44] = Q[43];
        result[45] = Q[44]; result[46] = Q[45]; result[47] = Q[46]; result[48] = Q[47];
        result[49] = Q[48]; result[50] = Q[49]; result[51] = Q[50]; result[52] = Q[51];
        result[53] = Q[52]; result[54] = Q[53]; result[55] = Q[54]; result[56] = Q[55];
        result[57] = Q[56]; result[58] = Q[57]; result[59] = Q[58]; result[60] = Q[59];
        result[61] = Q[60]; result[62] = Q[61]; result[63] = Q[62];
        return result;
    }

    private static boolean[] mux64(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[64];
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
        result[32] = mux2to1.module(a[32], b[32], sel);
        result[33] = mux2to1.module(a[33], b[33], sel);
        result[34] = mux2to1.module(a[34], b[34], sel);
        result[35] = mux2to1.module(a[35], b[35], sel);
        result[36] = mux2to1.module(a[36], b[36], sel);
        result[37] = mux2to1.module(a[37], b[37], sel);
        result[38] = mux2to1.module(a[38], b[38], sel);
        result[39] = mux2to1.module(a[39], b[39], sel);
        result[40] = mux2to1.module(a[40], b[40], sel);
        result[41] = mux2to1.module(a[41], b[41], sel);
        result[42] = mux2to1.module(a[42], b[42], sel);
        result[43] = mux2to1.module(a[43], b[43], sel);
        result[44] = mux2to1.module(a[44], b[44], sel);
        result[45] = mux2to1.module(a[45], b[45], sel);
        result[46] = mux2to1.module(a[46], b[46], sel);
        result[47] = mux2to1.module(a[47], b[47], sel);
        result[48] = mux2to1.module(a[48], b[48], sel);
        result[49] = mux2to1.module(a[49], b[49], sel);
        result[50] = mux2to1.module(a[50], b[50], sel);
        result[51] = mux2to1.module(a[51], b[51], sel);
        result[52] = mux2to1.module(a[52], b[52], sel);
        result[53] = mux2to1.module(a[53], b[53], sel);
        result[54] = mux2to1.module(a[54], b[54], sel);
        result[55] = mux2to1.module(a[55], b[55], sel);
        result[56] = mux2to1.module(a[56], b[56], sel);
        result[57] = mux2to1.module(a[57], b[57], sel);
        result[58] = mux2to1.module(a[58], b[58], sel);
        result[59] = mux2to1.module(a[59], b[59], sel);
        result[60] = mux2to1.module(a[60], b[60], sel);
        result[61] = mux2to1.module(a[61], b[61], sel);
        result[62] = mux2to1.module(a[62], b[62], sel);
        result[63] = mux2to1.module(a[63], b[63], sel);
        return result;
    }

    private static boolean[] zeros64() {
        return new boolean[64];
    }

    private static boolean[] copy64(boolean[] src) {
        boolean[] dst = new boolean[64];
        dst[0] = src[0]; dst[1] = src[1]; dst[2] = src[2]; dst[3] = src[3];
        dst[4] = src[4]; dst[5] = src[5]; dst[6] = src[6]; dst[7] = src[7];
        dst[8] = src[8]; dst[9] = src[9]; dst[10] = src[10]; dst[11] = src[11];
        dst[12] = src[12]; dst[13] = src[13]; dst[14] = src[14]; dst[15] = src[15];
        dst[16] = src[16]; dst[17] = src[17]; dst[18] = src[18]; dst[19] = src[19];
        dst[20] = src[20]; dst[21] = src[21]; dst[22] = src[22]; dst[23] = src[23];
        dst[24] = src[24]; dst[25] = src[25]; dst[26] = src[26]; dst[27] = src[27];
        dst[28] = src[28]; dst[29] = src[29]; dst[30] = src[30]; dst[31] = src[31];
        dst[32] = src[32]; dst[33] = src[33]; dst[34] = src[34]; dst[35] = src[35];
        dst[36] = src[36]; dst[37] = src[37]; dst[38] = src[38]; dst[39] = src[39];
        dst[40] = src[40]; dst[41] = src[41]; dst[42] = src[42]; dst[43] = src[43];
        dst[44] = src[44]; dst[45] = src[45]; dst[46] = src[46]; dst[47] = src[47];
        dst[48] = src[48]; dst[49] = src[49]; dst[50] = src[50]; dst[51] = src[51];
        dst[52] = src[52]; dst[53] = src[53]; dst[54] = src[54]; dst[55] = src[55];
        dst[56] = src[56]; dst[57] = src[57]; dst[58] = src[58]; dst[59] = src[59];
        dst[60] = src[60]; dst[61] = src[61]; dst[62] = src[62]; dst[63] = src[63];
        return dst;
    }
}
