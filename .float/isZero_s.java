package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;

public class isZero_s {
    public static boolean module(boolean[] bits) {
        boolean expAllZeros = allZeros8From23.module(bits);
        boolean manAllZeros = allZeros23From0.module(bits);
        return and.gate(expAllZeros, manAllZeros);
    }
}
