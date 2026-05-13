package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class isNaN_s {
    public static boolean module(boolean[] bits) {
        boolean expAllOnes = allOnes8From23.module(bits);
        boolean manNotAllZeros = not.gate(allZeros23From0.module(bits));
        return and.gate(expAllOnes, manNotAllZeros);
    }
}
