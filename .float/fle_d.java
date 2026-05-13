package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;

public class fle_d {
    public static boolean[] module(boolean[] rs1, boolean[] rs2) {
        boolean[] eqResult = feq_d.module(rs1, rs2);
        boolean[] ltResult = flt_d.module(rs1, rs2);
        
        boolean eq = eqResult[0];
        boolean lt = ltResult[0];
        
        boolean result = or.gate(eq, lt);
        
        return createOne(result);
    }
    
    private static boolean[] createOne(boolean value) {
        boolean[] result = new boolean[64];
        result[0] = value;
        return result;
    }
}
