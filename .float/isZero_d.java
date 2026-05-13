package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;

public class isZero_d {
    public static boolean module(boolean[] bits) {
        boolean expAllZeros = allZeros11From52.module(bits);
        boolean manAllZeros = allZeros52From0.module(bits);
        return and.gate(expAllZeros, manAllZeros);
    }
}
