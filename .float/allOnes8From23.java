package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;

public class allOnes8From23 {
    public static boolean module(boolean[] bits) {
        boolean s0 = and.gate(bits[23], bits[24]);
        boolean s1 = and.gate(s0, bits[25]);
        boolean s2 = and.gate(s1, bits[26]);
        boolean s3 = and.gate(s2, bits[27]);
        boolean s4 = and.gate(s3, bits[28]);
        boolean s5 = and.gate(s4, bits[29]);
        boolean s6 = and.gate(s5, bits[30]);
        
        return s6;
    }
}
