package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;
import com.follarce.machine.logic.gate.xor;

public class flt_s {
    public static boolean[] module(boolean[] rs1, boolean[] rs2) {
        boolean is1NaN = isNaN_s.module(rs1);
        boolean is2NaN = isNaN_s.module(rs2);
        boolean anyNaN = or.gate(is1NaN, is2NaN);
        
        boolean sign1 = rs1[31];
        boolean sign2 = rs2[31];
        
        boolean is1Zero = isZero_s.module(rs1);
        boolean is2Zero = isZero_s.module(rs2);
        boolean bothZero = and.gate(is1Zero, is2Zero);
        
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
        boolean[] expA = new boolean[8];
        expA[0] = a[23]; expA[1] = a[24]; expA[2] = a[25]; expA[3] = a[26];
        expA[4] = a[27]; expA[5] = a[28]; expA[6] = a[29]; expA[7] = a[30];
        
        boolean[] expB = new boolean[8];
        expB[0] = b[23]; expB[1] = b[24]; expB[2] = b[25]; expB[3] = b[26];
        expB[4] = b[27]; expB[5] = b[28]; expB[6] = b[29]; expB[7] = b[30];
        
        boolean expLess = compare8Less(expA, expB);
        boolean expEqual = compare8Equal(expA, expB);
        boolean manLess = compareManLess(a, b);
        
        return or.gate(expLess, and.gate(expEqual, manLess));
    }
    
    private static boolean compare8Less(boolean[] a, boolean[] b) {
        boolean borrow0 = and.gate(not.gate(a[0]), b[0]);
        boolean borrow1 = or.gate(and.gate(not.gate(a[1]), b[1]), and.gate(or.gate(not.gate(a[1]), b[1]), borrow0));
        boolean borrow2 = or.gate(and.gate(not.gate(a[2]), b[2]), and.gate(or.gate(not.gate(a[2]), b[2]), borrow1));
        boolean borrow3 = or.gate(and.gate(not.gate(a[3]), b[3]), and.gate(or.gate(not.gate(a[3]), b[3]), borrow2));
        boolean borrow4 = or.gate(and.gate(not.gate(a[4]), b[4]), and.gate(or.gate(not.gate(a[4]), b[4]), borrow3));
        boolean borrow5 = or.gate(and.gate(not.gate(a[5]), b[5]), and.gate(or.gate(not.gate(a[5]), b[5]), borrow4));
        boolean borrow6 = or.gate(and.gate(not.gate(a[6]), b[6]), and.gate(or.gate(not.gate(a[6]), b[6]), borrow5));
        boolean borrow7 = or.gate(and.gate(not.gate(a[7]), b[7]), and.gate(or.gate(not.gate(a[7]), b[7]), borrow6));
        
        return borrow7;
    }
    
    private static boolean compare8Equal(boolean[] a, boolean[] b) {
        boolean eq0 = not.gate(xor.gate(a[0], b[0]));
        boolean eq1 = not.gate(xor.gate(a[1], b[1]));
        boolean eq2 = not.gate(xor.gate(a[2], b[2]));
        boolean eq3 = not.gate(xor.gate(a[3], b[3]));
        boolean eq4 = not.gate(xor.gate(a[4], b[4]));
        boolean eq5 = not.gate(xor.gate(a[5], b[5]));
        boolean eq6 = not.gate(xor.gate(a[6], b[6]));
        boolean eq7 = not.gate(xor.gate(a[7], b[7]));
        
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(eq0, eq1), eq2), eq3), eq4), eq5), eq6), eq7);
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
        
        return borrow22;
    }
    
    private static boolean[] createOne(boolean value) {
        boolean[] result = new boolean[64];
        result[0] = value;
        return result;
    }
}
