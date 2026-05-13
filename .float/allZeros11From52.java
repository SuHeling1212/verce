package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class allZeros11From52 {
    public static boolean module(boolean[] bits) {
        boolean z0 = not.gate(bits[52]);
        boolean z1 = not.gate(bits[53]);
        boolean z2 = not.gate(bits[54]);
        boolean z3 = not.gate(bits[55]);
        boolean z4 = not.gate(bits[56]);
        boolean z5 = not.gate(bits[57]);
        boolean z6 = not.gate(bits[58]);
        boolean z7 = not.gate(bits[59]);
        boolean z8 = not.gate(bits[60]);
        boolean z9 = not.gate(bits[61]);
        boolean z10 = not.gate(bits[62]);

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

        return s9;
    }
}
