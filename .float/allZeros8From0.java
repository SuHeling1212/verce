package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class allZeros8From0 {
    public static boolean module(boolean[] bits) {
        boolean z0 = not.gate(bits[0]);
        boolean z1 = not.gate(bits[1]);
        boolean z2 = not.gate(bits[2]);
        boolean z3 = not.gate(bits[3]);
        boolean z4 = not.gate(bits[4]);
        boolean z5 = not.gate(bits[5]);
        boolean z6 = not.gate(bits[6]);
        boolean z7 = not.gate(bits[7]);
        
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
