package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class isNaN_d {
    public static boolean module(boolean[] bits) {
        boolean expAllOnes = allOnes11From52.module(bits);
        boolean manNotAllZeros = not.gate(allZeros52From0.module(bits));
        return and.gate(expAllOnes, manNotAllZeros);
    }
}
