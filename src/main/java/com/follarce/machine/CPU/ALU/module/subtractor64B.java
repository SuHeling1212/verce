package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.*;

public class subtractor64B {
    
    // A - B
    public static boolean[] module(boolean[] a, boolean[] b) {
        // ~B
        boolean[] notB = new boolean[64];
        notB[0]  = not.gate(b[0]);
        notB[1]  = not.gate(b[1]);
        notB[2]  = not.gate(b[2]);
        notB[3]  = not.gate(b[3]);
        notB[4]  = not.gate(b[4]);
        notB[5]  = not.gate(b[5]);
        notB[6]  = not.gate(b[6]);
        notB[7]  = not.gate(b[7]);
        notB[8]  = not.gate(b[8]);
        notB[9]  = not.gate(b[9]);
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
        notB[32] = not.gate(b[32]);
        notB[33] = not.gate(b[33]);
        notB[34] = not.gate(b[34]);
        notB[35] = not.gate(b[35]);
        notB[36] = not.gate(b[36]);
        notB[37] = not.gate(b[37]);
        notB[38] = not.gate(b[38]);
        notB[39] = not.gate(b[39]);
        notB[40] = not.gate(b[40]);
        notB[41] = not.gate(b[41]);
        notB[42] = not.gate(b[42]);
        notB[43] = not.gate(b[43]);
        notB[44] = not.gate(b[44]);
        notB[45] = not.gate(b[45]);
        notB[46] = not.gate(b[46]);
        notB[47] = not.gate(b[47]);
        notB[48] = not.gate(b[48]);
        notB[49] = not.gate(b[49]);
        notB[50] = not.gate(b[50]);
        notB[51] = not.gate(b[51]);
        notB[52] = not.gate(b[52]);
        notB[53] = not.gate(b[53]);
        notB[54] = not.gate(b[54]);
        notB[55] = not.gate(b[55]);
        notB[56] = not.gate(b[56]);
        notB[57] = not.gate(b[57]);
        notB[58] = not.gate(b[58]);
        notB[59] = not.gate(b[59]);
        notB[60] = not.gate(b[60]);
        notB[61] = not.gate(b[61]);
        notB[62] = not.gate(b[62]);
        notB[63] = not.gate(b[63]);
        
        // A + (~B) + 1
        return adder64B.module(a, notB, true);
    }
}