package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;
import com.follarce.machine.logic.gate.xor;

public class flt_d {
    public static boolean[] module(boolean[] rs1, boolean[] rs2) {
        boolean is1NaN = isNaN_d.module(rs1);
        boolean is2NaN = isNaN_d.module(rs2);
        boolean anyNaN = or.gate(is1NaN, is2NaN);
        
        boolean sign1 = rs1[63];
        boolean sign2 = rs2[63];
        
        boolean is1Zero = isZero_d.module(rs1);
        boolean is2Zero = isZero_d.module(rs2);
        boolean bothZero = and.gate(is1Zero, is2Zero);
        
        boolean signsDiffer = xor.gate(sign1, sign2);
        
        boolean aLessThanB = compareLess(rs1, rs2);
        
        boolean case1 = and.gate(sign1, not.gate(sign2));
        boolean case2 = and.gate(and.gate(not.gate(sign1), not.gate(sign2)), aLessThanB);
        boolean case3 = and.gate(and.gate(sign1, sign2), not.gate(aLessThanB));
        
        boolean result = or.gate(or.gate(case1, case2), case3);
        result = and.gate(result, not.gate(anyNaN));
        result = and.gate(result, not.gate(bothZero));
        
        return createOne(result);
    }
    
    private static boolean compareLess(boolean[] a, boolean[] b) {
        boolean[] expA = new boolean[11];
        expA[0] = a[52]; expA[1] = a[53]; expA[2] = a[54]; expA[3] = a[55];
        expA[4] = a[56]; expA[5] = a[57]; expA[6] = a[58]; expA[7] = a[59];
        expA[8] = a[60]; expA[9] = a[61]; expA[10] = a[62];
        
        boolean[] expB = new boolean[11];
        expB[0] = b[52]; expB[1] = b[53]; expB[2] = b[54]; expB[3] = b[55];
        expB[4] = b[56]; expB[5] = b[57]; expB[6] = b[58]; expB[7] = b[59];
        expB[8] = b[60]; expB[9] = b[61]; expB[10] = b[62];
        
        boolean expLess = compare11Less(expA, expB);
        boolean expEqual = compare11Equal(expA, expB);
        boolean manLess = compareManLess(a, b);
        
        return or.gate(expLess, and.gate(expEqual, manLess));
    }
    
    private static boolean compare11Less(boolean[] a, boolean[] b) {
        boolean borrow0 = and.gate(not.gate(a[0]), b[0]);
        boolean borrow1 = or.gate(and.gate(not.gate(a[1]), b[1]), and.gate(or.gate(not.gate(a[1]), b[1]), borrow0));
        boolean borrow2 = or.gate(and.gate(not.gate(a[2]), b[2]), and.gate(or.gate(not.gate(a[2]), b[2]), borrow1));
        boolean borrow3 = or.gate(and.gate(not.gate(a[3]), b[3]), and.gate(or.gate(not.gate(a[3]), b[3]), borrow2));
        boolean borrow4 = or.gate(and.gate(not.gate(a[4]), b[4]), and.gate(or.gate(not.gate(a[4]), b[4]), borrow3));
        boolean borrow5 = or.gate(and.gate(not.gate(a[5]), b[5]), and.gate(or.gate(not.gate(a[5]), b[5]), borrow4));
        boolean borrow6 = or.gate(and.gate(not.gate(a[6]), b[6]), and.gate(or.gate(not.gate(a[6]), b[6]), borrow5));
        boolean borrow7 = or.gate(and.gate(not.gate(a[7]), b[7]), and.gate(or.gate(not.gate(a[7]), b[7]), borrow6));
        boolean borrow8 = or.gate(and.gate(not.gate(a[8]), b[8]), and.gate(or.gate(not.gate(a[8]), b[8]), borrow7));
        boolean borrow9 = or.gate(and.gate(not.gate(a[9]), b[9]), and.gate(or.gate(not.gate(a[9]), b[9]), borrow8));
        boolean borrow10 = or.gate(and.gate(not.gate(a[10]), b[10]), and.gate(or.gate(not.gate(a[10]), b[10]), borrow9));
        
        return borrow10;
    }
    
    private static boolean compare11Equal(boolean[] a, boolean[] b) {
        boolean eq0 = not.gate(xor.gate(a[0], b[0]));
        boolean eq1 = not.gate(xor.gate(a[1], b[1]));
        boolean eq2 = not.gate(xor.gate(a[2], b[2]));
        boolean eq3 = not.gate(xor.gate(a[3], b[3]));
        boolean eq4 = not.gate(xor.gate(a[4], b[4]));
        boolean eq5 = not.gate(xor.gate(a[5], b[5]));
        boolean eq6 = not.gate(xor.gate(a[6], b[6]));
        boolean eq7 = not.gate(xor.gate(a[7], b[7]));
        boolean eq8 = not.gate(xor.gate(a[8], b[8]));
        boolean eq9 = not.gate(xor.gate(a[9], b[9]));
        boolean eq10 = not.gate(xor.gate(a[10], b[10]));
        
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(eq0, eq1), eq2), eq3), eq4), eq5), eq6), eq7), eq8), eq9), eq10);
    }
    
    private static boolean compareManLess(boolean[] a, boolean[] b) {
        boolean borrow0 = and.gate(not.gate(a[0]), b[0]);
        boolean borrow1 = or.gate(and.gate(not.gate(a[1]), b[1]), and.gate(or.gate(not.gate(a[1]), b[1]), borrow0));
        boolean borrow2 = or.gate(and.gate(not.gate(a[2]), b[2]), and.gate(or.gate(not.gate(a[2]), b[2]), borrow1));
        boolean borrow3 = or.gate(and.gate(not.gate(a[3]), b[3]), and.gate(or.gate(not.gate(a[3]), b[3]), borrow2));
        boolean borrow4 = or.gate(and.gate(not.gate(a[4]), b[4]), and.gate(or.gate(not.gate(a[4]), b[4]), borrow3));
        boolean borrow5 = or.gate(and.gate(not.gate(a[5]), b[5]), and.gate(or.gate(not.gate(a[5]), b[5]), borrow4));
        boolean borrow6 = or.gate(and.gate(not.gate(a[6]), b[6]), and.gate(or.gate(not.gate(a[6]), b[6]), borrow5));
        boolean borrow7 = or.gate(and.gate(not.gate(a[7]), b[7]), and.gate(or.gate(not.gate(a[7]), b[7]), borrow6));
        boolean borrow8 = or.gate(and.gate(not.gate(a[8]), b[8]), and.gate(or.gate(not.gate(a[8]), b[8]), borrow7));
        boolean borrow9 = or.gate(and.gate(not.gate(a[9]), b[9]), and.gate(or.gate(not.gate(a[9]), b[9]), borrow8));
        boolean borrow10 = or.gate(and.gate(not.gate(a[10]), b[10]), and.gate(or.gate(not.gate(a[10]), b[10]), borrow9));
        boolean borrow11 = or.gate(and.gate(not.gate(a[11]), b[11]), and.gate(or.gate(not.gate(a[11]), b[11]), borrow10));
        boolean borrow12 = or.gate(and.gate(not.gate(a[12]), b[12]), and.gate(or.gate(not.gate(a[12]), b[12]), borrow11));
        boolean borrow13 = or.gate(and.gate(not.gate(a[13]), b[13]), and.gate(or.gate(not.gate(a[13]), b[13]), borrow12));
        boolean borrow14 = or.gate(and.gate(not.gate(a[14]), b[14]), and.gate(or.gate(not.gate(a[14]), b[14]), borrow13));
        boolean borrow15 = or.gate(and.gate(not.gate(a[15]), b[15]), and.gate(or.gate(not.gate(a[15]), b[15]), borrow14));
        boolean borrow16 = or.gate(and.gate(not.gate(a[16]), b[16]), and.gate(or.gate(not.gate(a[16]), b[16]), borrow15));
        boolean borrow17 = or.gate(and.gate(not.gate(a[17]), b[17]), and.gate(or.gate(not.gate(a[17]), b[17]), borrow16));
        boolean borrow18 = or.gate(and.gate(not.gate(a[18]), b[18]), and.gate(or.gate(not.gate(a[18]), b[18]), borrow17));
        boolean borrow19 = or.gate(and.gate(not.gate(a[19]), b[19]), and.gate(or.gate(not.gate(a[19]), b[19]), borrow18));
        boolean borrow20 = or.gate(and.gate(not.gate(a[20]), b[20]), and.gate(or.gate(not.gate(a[20]), b[20]), borrow19));
        boolean borrow21 = or.gate(and.gate(not.gate(a[21]), b[21]), and.gate(or.gate(not.gate(a[21]), b[21]), borrow20));
        boolean borrow22 = or.gate(and.gate(not.gate(a[22]), b[22]), and.gate(or.gate(not.gate(a[22]), b[22]), borrow21));
        boolean borrow23 = or.gate(and.gate(not.gate(a[23]), b[23]), and.gate(or.gate(not.gate(a[23]), b[23]), borrow22));
        boolean borrow24 = or.gate(and.gate(not.gate(a[24]), b[24]), and.gate(or.gate(not.gate(a[24]), b[24]), borrow23));
        boolean borrow25 = or.gate(and.gate(not.gate(a[25]), b[25]), and.gate(or.gate(not.gate(a[25]), b[25]), borrow24));
        boolean borrow26 = or.gate(and.gate(not.gate(a[26]), b[26]), and.gate(or.gate(not.gate(a[26]), b[26]), borrow25));
        boolean borrow27 = or.gate(and.gate(not.gate(a[27]), b[27]), and.gate(or.gate(not.gate(a[27]), b[27]), borrow26));
        boolean borrow28 = or.gate(and.gate(not.gate(a[28]), b[28]), and.gate(or.gate(not.gate(a[28]), b[28]), borrow27));
        boolean borrow29 = or.gate(and.gate(not.gate(a[29]), b[29]), and.gate(or.gate(not.gate(a[29]), b[29]), borrow28));
        boolean borrow30 = or.gate(and.gate(not.gate(a[30]), b[30]), and.gate(or.gate(not.gate(a[30]), b[30]), borrow29));
        boolean borrow31 = or.gate(and.gate(not.gate(a[31]), b[31]), and.gate(or.gate(not.gate(a[31]), b[31]), borrow30));
        boolean borrow32 = or.gate(and.gate(not.gate(a[32]), b[32]), and.gate(or.gate(not.gate(a[32]), b[32]), borrow31));
        boolean borrow33 = or.gate(and.gate(not.gate(a[33]), b[33]), and.gate(or.gate(not.gate(a[33]), b[33]), borrow32));
        boolean borrow34 = or.gate(and.gate(not.gate(a[34]), b[34]), and.gate(or.gate(not.gate(a[34]), b[34]), borrow33));
        boolean borrow35 = or.gate(and.gate(not.gate(a[35]), b[35]), and.gate(or.gate(not.gate(a[35]), b[35]), borrow34));
        boolean borrow36 = or.gate(and.gate(not.gate(a[36]), b[36]), and.gate(or.gate(not.gate(a[36]), b[36]), borrow35));
        boolean borrow37 = or.gate(and.gate(not.gate(a[37]), b[37]), and.gate(or.gate(not.gate(a[37]), b[37]), borrow36));
        boolean borrow38 = or.gate(and.gate(not.gate(a[38]), b[38]), and.gate(or.gate(not.gate(a[38]), b[38]), borrow37));
        boolean borrow39 = or.gate(and.gate(not.gate(a[39]), b[39]), and.gate(or.gate(not.gate(a[39]), b[39]), borrow38));
        boolean borrow40 = or.gate(and.gate(not.gate(a[40]), b[40]), and.gate(or.gate(not.gate(a[40]), b[40]), borrow39));
        boolean borrow41 = or.gate(and.gate(not.gate(a[41]), b[41]), and.gate(or.gate(not.gate(a[41]), b[41]), borrow40));
        boolean borrow42 = or.gate(and.gate(not.gate(a[42]), b[42]), and.gate(or.gate(not.gate(a[42]), b[42]), borrow41));
        boolean borrow43 = or.gate(and.gate(not.gate(a[43]), b[43]), and.gate(or.gate(not.gate(a[43]), b[43]), borrow42));
        boolean borrow44 = or.gate(and.gate(not.gate(a[44]), b[44]), and.gate(or.gate(not.gate(a[44]), b[44]), borrow43));
        boolean borrow45 = or.gate(and.gate(not.gate(a[45]), b[45]), and.gate(or.gate(not.gate(a[45]), b[45]), borrow44));
        boolean borrow46 = or.gate(and.gate(not.gate(a[46]), b[46]), and.gate(or.gate(not.gate(a[46]), b[46]), borrow45));
        boolean borrow47 = or.gate(and.gate(not.gate(a[47]), b[47]), and.gate(or.gate(not.gate(a[47]), b[47]), borrow46));
        boolean borrow48 = or.gate(and.gate(not.gate(a[48]), b[48]), and.gate(or.gate(not.gate(a[48]), b[48]), borrow47));
        boolean borrow49 = or.gate(and.gate(not.gate(a[49]), b[49]), and.gate(or.gate(not.gate(a[49]), b[49]), borrow48));
        boolean borrow50 = or.gate(and.gate(not.gate(a[50]), b[50]), and.gate(or.gate(not.gate(a[50]), b[50]), borrow49));
        boolean borrow51 = or.gate(and.gate(not.gate(a[51]), b[51]), and.gate(or.gate(not.gate(a[51]), b[51]), borrow50));
        
        return borrow51;
    }
    
    private static boolean[] createOne(boolean value) {
        boolean[] result = new boolean[64];
        result[0] = value;
        return result;
    }
}
