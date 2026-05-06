package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.*;

public class halfAdder {
    public static boolean[] module(boolean a, boolean b) {
        boolean[] result = new boolean[2];
        result[0] = xor.gate(a, b);
        result[1] = and.gate(a, b);
        return result;
    }
}