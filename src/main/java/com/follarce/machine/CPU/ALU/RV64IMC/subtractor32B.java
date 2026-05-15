package com.follarce.machine.CPU.ALU.RV64IMC;

import com.follarce.machine.logic.gate.not;

public class subtractor32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] notB = new boolean[32];
        notB[0] = not.gate(b[0]);
        notB[1] = not.gate(b[1]);
        notB[2] = not.gate(b[2]);
        notB[3] = not.gate(b[3]);
        notB[4] = not.gate(b[4]);
        notB[5] = not.gate(b[5]);
        notB[6] = not.gate(b[6]);
        notB[7] = not.gate(b[7]);
        notB[8] = not.gate(b[8]);
        notB[9] = not.gate(b[9]);
        notB[10] = not.gate(b[10]);
        notB[11] = not.gate(b[11]);
        notB[12] = not.gate(b[12]);
        notB[13] = not.gate(b[13]);
        notB[14] = not.gate(b[14]);
        notB[15] = not.gate(b[15]);
        notB[16] = not.gate(b[16]);
        notB[17] = not.gate(b[17]);
        notB[18] = not.gate(b[18]);
        notB[19] = not.gate(b[19]);
        notB[20] = not.gate(b[20]);
        notB[21] = not.gate(b[21]);
        notB[22] = not.gate(b[22]);
        notB[23] = not.gate(b[23]);
        notB[24] = not.gate(b[24]);
        notB[25] = not.gate(b[25]);
        notB[26] = not.gate(b[26]);
        notB[27] = not.gate(b[27]);
        notB[28] = not.gate(b[28]);
        notB[29] = not.gate(b[29]);
        notB[30] = not.gate(b[30]);
        notB[31] = not.gate(b[31]);
        
        boolean[] one = new boolean[32];
        one[0] = true;
        one[1] = false;
        one[2] = false;
        one[3] = false;
        one[4] = false;
        one[5] = false;
        one[6] = false;
        one[7] = false;
        one[8] = false;
        one[9] = false;
        one[10] = false;
        one[11] = false;
        one[12] = false;
        one[13] = false;
        one[14] = false;
        one[15] = false;
        one[16] = false;
        one[17] = false;
        one[18] = false;
        one[19] = false;
        one[20] = false;
        one[21] = false;
        one[22] = false;
        one[23] = false;
        one[24] = false;
        one[25] = false;
        one[26] = false;
        one[27] = false;
        one[28] = false;
        one[29] = false;
        one[30] = false;
        one[31] = false;
        
        boolean[] notBPlusOne = adder32B.module(notB, one);
        return adder32B.module(a, notBPlusOne);
    }
}
