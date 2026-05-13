package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class allZeros23From0 {
    public static boolean module(boolean[] bits) {
        boolean z0 = not.gate(bits[0]);
        boolean z1 = not.gate(bits[1]);
        boolean z2 = not.gate(bits[2]);
        boolean z3 = not.gate(bits[3]);
        boolean z4 = not.gate(bits[4]);
        boolean z5 = not.gate(bits[5]);
        boolean z6 = not.gate(bits[6]);
        boolean z7 = not.gate(bits[7]);
        boolean z8 = not.gate(bits[8]);
        boolean z9 = not.gate(bits[9]);
        boolean z10 = not.gate(bits[10]);
        boolean z11 = not.gate(bits[11]);
        boolean z12 = not.gate(bits[12]);
        boolean z13 = not.gate(bits[13]);
        boolean z14 = not.gate(bits[14]);
        boolean z15 = not.gate(bits[15]);
        boolean z16 = not.gate(bits[16]);
        boolean z17 = not.gate(bits[17]);
        boolean z18 = not.gate(bits[18]);
        boolean z19 = not.gate(bits[19]);
        boolean z20 = not.gate(bits[20]);
        boolean z21 = not.gate(bits[21]);
        boolean z22 = not.gate(bits[22]);
        
        boolean s0 = and.gate(z0, z1);
        boolean s1 = and.gate(s0, z2);
        boolean s2 = and.gate(s1, z3);
        boolean s3 = and.gate(s2, z4);
        boolean s4 = and.gate(s3, z5);
        boolean s5 = and.gate(s4, z6);
        boolean s6 = and.gate(s5, z7);
        boolean s7 = and.gate(s6, z8);
        boolean s8 = and.gate(s7, z9);
        boolean s9 = and.gate(s8, z10);
        boolean s10 = and.gate(s9, z11);
        boolean s11 = and.gate(s10, z12);
        boolean s12 = and.gate(s11, z13);
        boolean s13 = and.gate(s12, z14);
        boolean s14 = and.gate(s13, z15);
        boolean s15 = and.gate(s14, z16);
        boolean s16 = and.gate(s15, z17);
        boolean s17 = and.gate(s16, z18);
        boolean s18 = and.gate(s17, z19);
        boolean s19 = and.gate(s18, z20);
        boolean s20 = and.gate(s19, z21);
        boolean s21 = and.gate(s20, z22);
        
        return s21;
    }
}
