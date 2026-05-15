package com.follarce.machine.CPU.ALU.RV64IMC;

import com.follarce.machine.logic.gate.*;

public class rem64B {
    
    public static boolean[] module(boolean[] dividend, boolean[] divisor) {
        boolean dividendSign = dividend[63];
        
        boolean[] absDividend = abs(dividend);
        boolean[] absDivisor = abs(divisor);
        
        boolean[] divuResult = divu64B.module(absDividend, absDivisor);
        boolean[] remainder = extractRemainder(divuResult);
        
        remainder = conditionalNegate(remainder, dividendSign);
        
        return remainder;
    }
    
    private static boolean[] abs(boolean[] value) {
        boolean isNegative = value[63];
        boolean[] negated = negate(value);
        return mux64B.module(value, negated, isNegative);
    }
    
    private static boolean[] negate(boolean[] value) {
        boolean[] notValue = not64B.module(value);
        boolean[] one = createOne();
        return adder64B.module(notValue, one);
    }
    
    private static boolean[] conditionalNegate(boolean[] value, boolean condition) {
        boolean[] negated = negate(value);
        return mux64B.module(value, negated, condition);
    }
    
    private static boolean[] extractRemainder(boolean[] divuResult) {
        boolean[] remainder = new boolean[64];
        remainder[0] = divuResult[64]; remainder[1] = divuResult[65]; remainder[2] = divuResult[66]; remainder[3] = divuResult[67];
        remainder[4] = divuResult[68]; remainder[5] = divuResult[69]; remainder[6] = divuResult[70]; remainder[7] = divuResult[71];
        remainder[8] = divuResult[72]; remainder[9] = divuResult[73]; remainder[10] = divuResult[74]; remainder[11] = divuResult[75];
        remainder[12] = divuResult[76]; remainder[13] = divuResult[77]; remainder[14] = divuResult[78]; remainder[15] = divuResult[79];
        remainder[16] = divuResult[80]; remainder[17] = divuResult[81]; remainder[18] = divuResult[82]; remainder[19] = divuResult[83];
        remainder[20] = divuResult[84]; remainder[21] = divuResult[85]; remainder[22] = divuResult[86]; remainder[23] = divuResult[87];
        remainder[24] = divuResult[88]; remainder[25] = divuResult[89]; remainder[26] = divuResult[90]; remainder[27] = divuResult[91];
        remainder[28] = divuResult[92]; remainder[29] = divuResult[93]; remainder[30] = divuResult[94]; remainder[31] = divuResult[95];
        remainder[32] = divuResult[96]; remainder[33] = divuResult[97]; remainder[34] = divuResult[98]; remainder[35] = divuResult[99];
        remainder[36] = divuResult[100]; remainder[37] = divuResult[101]; remainder[38] = divuResult[102]; remainder[39] = divuResult[103];
        remainder[40] = divuResult[104]; remainder[41] = divuResult[105]; remainder[42] = divuResult[106]; remainder[43] = divuResult[107];
        remainder[44] = divuResult[108]; remainder[45] = divuResult[109]; remainder[46] = divuResult[110]; remainder[47] = divuResult[111];
        remainder[48] = divuResult[112]; remainder[49] = divuResult[113]; remainder[50] = divuResult[114]; remainder[51] = divuResult[115];
        remainder[52] = divuResult[116]; remainder[53] = divuResult[117]; remainder[54] = divuResult[118]; remainder[55] = divuResult[119];
        remainder[56] = divuResult[120]; remainder[57] = divuResult[121]; remainder[58] = divuResult[122]; remainder[59] = divuResult[123];
        remainder[60] = divuResult[124]; remainder[61] = divuResult[125]; remainder[62] = divuResult[126]; remainder[63] = divuResult[127];
        return remainder;
    }
    
    private static boolean[] createOne() {
        boolean[] one = new boolean[64];
        one[0] = true;
        return one;
    }
}
