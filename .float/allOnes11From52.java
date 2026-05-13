package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;

public class allOnes11From52 {
    public static boolean module(boolean[] bits) {
        boolean s0 = and.gate(bits[52], bits[53]);
        boolean s1 = and.gate(s0, bits[54]);
        boolean s2 = and.gate(s1, bits[55]);
        boolean s3 = and.gate(s2, bits[56]);
        boolean s4 = and.gate(s3, bits[57]);
        boolean s5 = and.gate(s4, bits[58]);
        boolean s6 = and.gate(s5, bits[59]);
        boolean s7 = and.gate(s6, bits[60]);
        boolean s8 = and.gate(s7, bits[61]);
        boolean s9 = and.gate(s8, bits[62]);
        
        return s9;
    }
}
