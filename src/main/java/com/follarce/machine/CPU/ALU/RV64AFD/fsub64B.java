package com.follarce.machine.CPU.ALU.RV64AFD;

public class fsub64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] negB = futils64B.negate(b);
        return fadd64B.module(a, negB);
    }
}