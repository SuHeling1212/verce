package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;

public class fmax_s {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean[] ltResult = flt_s.module(a, b);
        boolean aLessThanB = ltResult[0];
        boolean eitherNaN = or.gate(isNaN_s.module(a), isNaN_s.module(b));
        
        boolean[] nanResult = makeQuietNaN32();
        boolean[] normalResult = mux32B(a, b, aLessThanB);
        
        return mux32B(normalResult, nanResult, eitherNaN);
    }
    
    private static boolean[] mux32B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[32];
        for (int i = 0; i < 32; i++) {
            result[i] = mux2to1.module(a[i], b[i], sel);
        }
        return result;
    }
    
    private static boolean[] makeQuietNaN32() {
        boolean[] nan = new boolean[32];
        nan[22] = true;
        nan[30] = true;
        nan[31] = true;
        return nan;
    }
}
