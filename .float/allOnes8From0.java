package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;

public class allOnes8From0 {
    public static boolean module(boolean[] bits) {
        boolean s0 = and.gate(bits[0], bits[1]);
        boolean s1 = and.gate(s0, bits[2]);
        boolean s2 = and.gate(s1, bits[3]);
        boolean s3 = and.gate(s2, bits[4]);
        boolean s4 = and.gate(s3, bits[5]);
        boolean s5 = and.gate(s4, bits[6]);
        boolean s6 = and.gate(s5, bits[7]);
        
        return s6;
    }
}
