package com.follarce.machine.CPU.ALU.RV64IMC;
import com.follarce.machine.logic.gate.*;
public class unsignedComparator64B {
    public static boolean module(boolean[] a, boolean[] b) {
        boolean[] diff = subtractor64B.module(a, b);
        return not.gate(diff[64]);
    }
}