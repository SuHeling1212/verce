package com.follarce.machine.CPU.ALU.module;
import com.follarce.machine.logic.gate.*;
public class signedComparator64B {
    public static boolean module(boolean[] a, boolean[] b) {
        boolean signA = a[63];
        boolean signB = b[63];
        boolean signsDiffer = xor.gate(signA, signB);
        
        boolean[] diff = subtractor64B.module(a, b);
        boolean borrow = not.gate(diff[64]);
        
        return mux2to1.module(borrow, signA, signsDiffer);
    }
}