package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;

public class feq_d {
    public static boolean[] module(boolean[] rs1, boolean[] rs2) {
        boolean is1NaN = isNaN_d.module(rs1);
        boolean is2NaN = isNaN_d.module(rs2);
        boolean anyNaN = or.gate(is1NaN, is2NaN);
        
        boolean is1Zero = isZero_d.module(rs1);
        boolean is2Zero = isZero_d.module(rs2);
        boolean bothZero = and.gate(is1Zero, is2Zero);
        
        boolean bitsEqual = compare64.module(rs1, rs2);
        boolean bothZeroOrBitsEqual = or.gate(bothZero, bitsEqual);
        
        boolean result = and.gate(bothZeroOrBitsEqual, not.gate(anyNaN));
        
        return createOne(result);
    }
    
    private static boolean[] createOne(boolean value) {
        boolean[] result = new boolean[64];
        result[0] = value;
        return result;
    }
}
