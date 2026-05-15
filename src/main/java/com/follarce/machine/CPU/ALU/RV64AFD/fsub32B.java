package com.follarce.machine.CPU.ALU.RV64AFD;

public class fsub32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] negB = futils32B.negate(b);
        return fadd32B.module(a, negB);
    }
}