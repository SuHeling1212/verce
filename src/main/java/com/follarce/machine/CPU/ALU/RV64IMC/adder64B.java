package com.follarce.machine.CPU.ALU.RV64IMC;

public class adder64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        return module(a, b, false);
    }

    public static boolean[] module(boolean[] a, boolean[] b, boolean carryIn) {
        boolean[] result = new boolean[65];

        boolean c0 = carryIn;
        boolean c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22,
                c23, c24, c25, c26, c27, c28, c29, c30, c31, c32, c33, c34, c35, c36, c37, c38, c39, c40, c41, c42, c43,
                c44, c45, c46, c47, c48, c49, c50, c51, c52, c53, c54, c55, c56, c57, c58, c59, c60, c61, c62, c63, c64;

        boolean[] fa0 = fullAdder.module(a[0], b[0], c0);
        result[0] = fa0[0];
        c1 = fa0[1];

        boolean[] fa1 = fullAdder.module(a[1], b[1], c1);
        result[1] = fa1[0];
        c2 = fa1[1];

        boolean[] fa2 = fullAdder.module(a[2], b[2], c2);
        result[2] = fa2[0];
        c3 = fa2[1];

        boolean[] fa3 = fullAdder.module(a[3], b[3], c3);
        result[3] = fa3[0];
        c4 = fa3[1];

        boolean[] fa4 = fullAdder.module(a[4], b[4], c4);
        result[4] = fa4[0];
        c5 = fa4[1];

        boolean[] fa5 = fullAdder.module(a[5], b[5], c5);
        result[5] = fa5[0];
        c6 = fa5[1];

        boolean[] fa6 = fullAdder.module(a[6], b[6], c6);
        result[6] = fa6[0];
        c7 = fa6[1];

        boolean[] fa7 = fullAdder.module(a[7], b[7], c7);
        result[7] = fa7[0];
        c8 = fa7[1];

        boolean[] fa8 = fullAdder.module(a[8], b[8], c8);
        result[8] = fa8[0];
        c9 = fa8[1];

        boolean[] fa9 = fullAdder.module(a[9], b[9], c9);
        result[9] = fa9[0];
        c10 = fa9[1];

        boolean[] fa10 = fullAdder.module(a[10], b[10], c10);
        result[10] = fa10[0];
        c11 = fa10[1];

        boolean[] fa11 = fullAdder.module(a[11], b[11], c11);
        result[11] = fa11[0];
        c12 = fa11[1];

        boolean[] fa12 = fullAdder.module(a[12], b[12], c12);
        result[12] = fa12[0];
        c13 = fa12[1];

        boolean[] fa13 = fullAdder.module(a[13], b[13], c13);
        result[13] = fa13[0];
        c14 = fa13[1];

        boolean[] fa14 = fullAdder.module(a[14], b[14], c14);
        result[14] = fa14[0];
        c15 = fa14[1];

        boolean[] fa15 = fullAdder.module(a[15], b[15], c15);
        result[15] = fa15[0];
        c16 = fa15[1];

        boolean[] fa16 = fullAdder.module(a[16], b[16], c16);
        result[16] = fa16[0];
        c17 = fa16[1];

        boolean[] fa17 = fullAdder.module(a[17], b[17], c17);
        result[17] = fa17[0];
        c18 = fa17[1];

        boolean[] fa18 = fullAdder.module(a[18], b[18], c18);
        result[18] = fa18[0];
        c19 = fa18[1];

        boolean[] fa19 = fullAdder.module(a[19], b[19], c19);
        result[19] = fa19[0];
        c20 = fa19[1];

        boolean[] fa20 = fullAdder.module(a[20], b[20], c20);
        result[20] = fa20[0];
        c21 = fa20[1];

        boolean[] fa21 = fullAdder.module(a[21], b[21], c21);
        result[21] = fa21[0];
        c22 = fa21[1];

        boolean[] fa22 = fullAdder.module(a[22], b[22], c22);
        result[22] = fa22[0];
        c23 = fa22[1];

        boolean[] fa23 = fullAdder.module(a[23], b[23], c23);
        result[23] = fa23[0];
        c24 = fa23[1];

        boolean[] fa24 = fullAdder.module(a[24], b[24], c24);
        result[24] = fa24[0];
        c25 = fa24[1];

        boolean[] fa25 = fullAdder.module(a[25], b[25], c25);
        result[25] = fa25[0];
        c26 = fa25[1];

        boolean[] fa26 = fullAdder.module(a[26], b[26], c26);
        result[26] = fa26[0];
        c27 = fa26[1];

        boolean[] fa27 = fullAdder.module(a[27], b[27], c27);
        result[27] = fa27[0];
        c28 = fa27[1];

        boolean[] fa28 = fullAdder.module(a[28], b[28], c28);
        result[28] = fa28[0];
        c29 = fa28[1];

        boolean[] fa29 = fullAdder.module(a[29], b[29], c29);
        result[29] = fa29[0];
        c30 = fa29[1];

        boolean[] fa30 = fullAdder.module(a[30], b[30], c30);
        result[30] = fa30[0];
        c31 = fa30[1];

        boolean[] fa31 = fullAdder.module(a[31], b[31], c31);
        result[31] = fa31[0];
        c32 = fa31[1];

        boolean[] fa32 = fullAdder.module(a[32], b[32], c32);
        result[32] = fa32[0];
        c33 = fa32[1];

        boolean[] fa33 = fullAdder.module(a[33], b[33], c33);
        result[33] = fa33[0];
        c34 = fa33[1];

        boolean[] fa34 = fullAdder.module(a[34], b[34], c34);
        result[34] = fa34[0];
        c35 = fa34[1];

        boolean[] fa35 = fullAdder.module(a[35], b[35], c35);
        result[35] = fa35[0];
        c36 = fa35[1];

        boolean[] fa36 = fullAdder.module(a[36], b[36], c36);
        result[36] = fa36[0];
        c37 = fa36[1];

        boolean[] fa37 = fullAdder.module(a[37], b[37], c37);
        result[37] = fa37[0];
        c38 = fa37[1];

        boolean[] fa38 = fullAdder.module(a[38], b[38], c38);
        result[38] = fa38[0];
        c39 = fa38[1];

        boolean[] fa39 = fullAdder.module(a[39], b[39], c39);
        result[39] = fa39[0];
        c40 = fa39[1];

        boolean[] fa40 = fullAdder.module(a[40], b[40], c40);
        result[40] = fa40[0];
        c41 = fa40[1];

        boolean[] fa41 = fullAdder.module(a[41], b[41], c41);
        result[41] = fa41[0];
        c42 = fa41[1];

        boolean[] fa42 = fullAdder.module(a[42], b[42], c42);
        result[42] = fa42[0];
        c43 = fa42[1];

        boolean[] fa43 = fullAdder.module(a[43], b[43], c43);
        result[43] = fa43[0];
        c44 = fa43[1];

        boolean[] fa44 = fullAdder.module(a[44], b[44], c44);
        result[44] = fa44[0];
        c45 = fa44[1];

        boolean[] fa45 = fullAdder.module(a[45], b[45], c45);
        result[45] = fa45[0];
        c46 = fa45[1];

        boolean[] fa46 = fullAdder.module(a[46], b[46], c46);
        result[46] = fa46[0];
        c47 = fa46[1];

        boolean[] fa47 = fullAdder.module(a[47], b[47], c47);
        result[47] = fa47[0];
        c48 = fa47[1];

        boolean[] fa48 = fullAdder.module(a[48], b[48], c48);
        result[48] = fa48[0];
        c49 = fa48[1];

        boolean[] fa49 = fullAdder.module(a[49], b[49], c49);
        result[49] = fa49[0];
        c50 = fa49[1];

        boolean[] fa50 = fullAdder.module(a[50], b[50], c50);
        result[50] = fa50[0];
        c51 = fa50[1];

        boolean[] fa51 = fullAdder.module(a[51], b[51], c51);
        result[51] = fa51[0];
        c52 = fa51[1];

        boolean[] fa52 = fullAdder.module(a[52], b[52], c52);
        result[52] = fa52[0];
        c53 = fa52[1];

        boolean[] fa53 = fullAdder.module(a[53], b[53], c53);
        result[53] = fa53[0];
        c54 = fa53[1];

        boolean[] fa54 = fullAdder.module(a[54], b[54], c54);
        result[54] = fa54[0];
        c55 = fa54[1];

        boolean[] fa55 = fullAdder.module(a[55], b[55], c55);
        result[55] = fa55[0];
        c56 = fa55[1];

        boolean[] fa56 = fullAdder.module(a[56], b[56], c56);
        result[56] = fa56[0];
        c57 = fa56[1];

        boolean[] fa57 = fullAdder.module(a[57], b[57], c57);
        result[57] = fa57[0];
        c58 = fa57[1];

        boolean[] fa58 = fullAdder.module(a[58], b[58], c58);
        result[58] = fa58[0];
        c59 = fa58[1];

        boolean[] fa59 = fullAdder.module(a[59], b[59], c59);
        result[59] = fa59[0];
        c60 = fa59[1];

        boolean[] fa60 = fullAdder.module(a[60], b[60], c60);
        result[60] = fa60[0];
        c61 = fa60[1];

        boolean[] fa61 = fullAdder.module(a[61], b[61], c61);
        result[61] = fa61[0];
        c62 = fa61[1];

        boolean[] fa62 = fullAdder.module(a[62], b[62], c62);
        result[62] = fa62[0];
        c63 = fa62[1];

        boolean[] fa63 = fullAdder.module(a[63], b[63], c63);
        result[63] = fa63[0];
        c64 = fa63[1];

        result[64] = c64;

        return result;
    }
}