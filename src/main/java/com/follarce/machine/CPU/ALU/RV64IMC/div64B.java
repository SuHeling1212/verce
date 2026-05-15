package com.follarce.machine.CPU.ALU.RV64IMC;

import com.follarce.machine.logic.gate.*;

public class div64B {
    
    public static boolean[] module(boolean[] dividend, boolean[] divisor) {
        boolean dividendSign = dividend[63];
        boolean divisorSign = divisor[63];
        
        boolean[] absDividend = abs(dividend);
        boolean[] absDivisor = abs(divisor);
        
        boolean[] divuResult = divu64B.module(absDividend, absDivisor);
        boolean[] quotient = extractQuotient(divuResult);
        boolean[] remainder = extractRemainder(divuResult);
        
        boolean signsDiffer = xor.gate(dividendSign, divisorSign);
        quotient = conditionalNegate(quotient, signsDiffer);
        
        return quotient;
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
    
    private static boolean[] extractQuotient(boolean[] divuResult) {
        boolean[] quotient = new boolean[64];
        quotient[0] = divuResult[0]; quotient[1] = divuResult[1]; quotient[2] = divuResult[2]; quotient[3] = divuResult[3];
        quotient[4] = divuResult[4]; quotient[5] = divuResult[5]; quotient[6] = divuResult[6]; quotient[7] = divuResult[7];
        quotient[8] = divuResult[8]; quotient[9] = divuResult[9]; quotient[10] = divuResult[10]; quotient[11] = divuResult[11];
        quotient[12] = divuResult[12]; quotient[13] = divuResult[13]; quotient[14] = divuResult[14]; quotient[15] = divuResult[15];
        quotient[16] = divuResult[16]; quotient[17] = divuResult[17]; quotient[18] = divuResult[18]; quotient[19] = divuResult[19];
        quotient[20] = divuResult[20]; quotient[21] = divuResult[21]; quotient[22] = divuResult[22]; quotient[23] = divuResult[23];
        quotient[24] = divuResult[24]; quotient[25] = divuResult[25]; quotient[26] = divuResult[26]; quotient[27] = divuResult[27];
        quotient[28] = divuResult[28]; quotient[29] = divuResult[29]; quotient[30] = divuResult[30]; quotient[31] = divuResult[31];
        quotient[32] = divuResult[32]; quotient[33] = divuResult[33]; quotient[34] = divuResult[34]; quotient[35] = divuResult[35];
        quotient[36] = divuResult[36]; quotient[37] = divuResult[37]; quotient[38] = divuResult[38]; quotient[39] = divuResult[39];
        quotient[40] = divuResult[40]; quotient[41] = divuResult[41]; quotient[42] = divuResult[42]; quotient[43] = divuResult[43];
        quotient[44] = divuResult[44]; quotient[45] = divuResult[45]; quotient[46] = divuResult[46]; quotient[47] = divuResult[47];
        quotient[48] = divuResult[48]; quotient[49] = divuResult[49]; quotient[50] = divuResult[50]; quotient[51] = divuResult[51];
        quotient[52] = divuResult[52]; quotient[53] = divuResult[53]; quotient[54] = divuResult[54]; quotient[55] = divuResult[55];
        quotient[56] = divuResult[56]; quotient[57] = divuResult[57]; quotient[58] = divuResult[58]; quotient[59] = divuResult[59];
        quotient[60] = divuResult[60]; quotient[61] = divuResult[61]; quotient[62] = divuResult[62]; quotient[63] = divuResult[63];
        return quotient;
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
