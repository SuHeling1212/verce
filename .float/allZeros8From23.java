package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class allZeros8From23 {
    public static boolean module(boolean[] bits) {
        boolean z0 = not.gate(bits[23]);
        boolean z1 = not.gate(bits[24]);
        boolean z2 = not.gate(bits[25]);
        boolean z3 = not.gate(bits[26]);
        boolean z4 = not.gate(bits[27]);
        boolean z5 = not.gate(bits[28]);
        boolean z6 = not.gate(bits[29]);
        boolean z7 = not.gate(bits[30]);
        
        boolean s0 = and.gate(z0, z1);
        boolean s1 = and.gate(s0, z2);
        boolean s2 = and.gate(s1, z3);
        boolean s3 = and.gate(s2, z4);
        boolean s4 = and.gate(s3, z5);
        boolean s5 = and.gate(s4, z6);
        boolean s6 = and.gate(s5, z7);
        
        return s6;
    }
}
