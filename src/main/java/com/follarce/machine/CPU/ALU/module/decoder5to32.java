package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;

public class decoder5to32 {
    public static boolean[] module(boolean[] in) {
        boolean[] result = new boolean[32];
        boolean not0 = not.gate(in[0]);
        boolean not1 = not.gate(in[1]);
        boolean not2 = not.gate(in[2]);
        boolean not3 = not.gate(in[3]);
        boolean not4 = not.gate(in[4]);
        
        result[0] = and.gate(and.gate(and.gate(and.gate(not4, not3), not2), not1), not0);
        result[1] = and.gate(and.gate(and.gate(and.gate(not4, not3), not2), not1), in[0]);
        result[2] = and.gate(and.gate(and.gate(and.gate(not4, not3), not2), in[1]), not0);
        result[3] = and.gate(and.gate(and.gate(and.gate(not4, not3), not2), in[1]), in[0]);
        result[4] = and.gate(and.gate(and.gate(and.gate(not4, not3), in[2]), not1), not0);
        result[5] = and.gate(and.gate(and.gate(and.gate(not4, not3), in[2]), not1), in[0]);
        result[6] = and.gate(and.gate(and.gate(and.gate(not4, not3), in[2]), in[1]), not0);
        result[7] = and.gate(and.gate(and.gate(and.gate(not4, not3), in[2]), in[1]), in[0]);
        result[8] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), not2), not1), not0);
        result[9] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), not2), not1), in[0]);
        result[10] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), not2), in[1]), not0);
        result[11] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), not2), in[1]), in[0]);
        result[12] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), in[2]), not1), not0);
        result[13] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), in[2]), not1), in[0]);
        result[14] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), in[2]), in[1]), not0);
        result[15] = and.gate(and.gate(and.gate(and.gate(not4, in[3]), in[2]), in[1]), in[0]);
        result[16] = and.gate(and.gate(and.gate(and.gate(in[4], not3), not2), not1), not0);
        result[17] = and.gate(and.gate(and.gate(and.gate(in[4], not3), not2), not1), in[0]);
        result[18] = and.gate(and.gate(and.gate(and.gate(in[4], not3), not2), in[1]), not0);
        result[19] = and.gate(and.gate(and.gate(and.gate(in[4], not3), not2), in[1]), in[0]);
        result[20] = and.gate(and.gate(and.gate(and.gate(in[4], not3), in[2]), not1), not0);
        result[21] = and.gate(and.gate(and.gate(and.gate(in[4], not3), in[2]), not1), in[0]);
        result[22] = and.gate(and.gate(and.gate(and.gate(in[4], not3), in[2]), in[1]), not0);
        result[23] = and.gate(and.gate(and.gate(and.gate(in[4], not3), in[2]), in[1]), in[0]);
        result[24] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), not2), not1), not0);
        result[25] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), not2), not1), in[0]);
        result[26] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), not2), in[1]), not0);
        result[27] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), not2), in[1]), in[0]);
        result[28] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), in[2]), not1), not0);
        result[29] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), in[2]), not1), in[0]);
        result[30] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), in[2]), in[1]), not0);
        result[31] = and.gate(and.gate(and.gate(and.gate(in[4], in[3]), in[2]), in[1]), in[0]);
        
        return result;
    }
}
