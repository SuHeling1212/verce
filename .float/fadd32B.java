package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.*;

public class fadd32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean signA = a[31];
        boolean signB = b[31];
        
        boolean[] expA = extractExp32(a);
        boolean[] expB = extractExp32(b);
        
        boolean[] manA = extractMan32(a);
        boolean[] manB = extractMan32(b);
        
        boolean isAllOneExpA = isAllOneExp(expA);
        boolean isAllOneExpB = isAllOneExp(expB);
        boolean isZeroManA = isZeroMan(manA);
        boolean isZeroManB = isZeroMan(manB);
        
        boolean isZeroA = and.gate(isZeroExp(expA), isZeroManA);
        boolean isZeroB = and.gate(isZeroExp(expB), isZeroManB);
        boolean isInfA = and.gate(isAllOneExpA, isZeroManA);
        boolean isInfB = and.gate(isAllOneExpB, isZeroManB);
        boolean isNaNA = and.gate(isAllOneExpA, not.gate(isZeroManA));
        boolean isNaNB = and.gate(isAllOneExpB, not.gate(isZeroManB));
        
        boolean bothZero = and.gate(isZeroA, isZeroB);
        boolean eitherNaN = or.gate(isNaNA, isNaNB);
        boolean bothInf = and.gate(isInfA, isInfB);
        boolean infSignsDiffer = and.gate(bothInf, xor.gate(signA, signB));
        
        boolean[] nanResult = makeQuietNaN32();
        boolean[] infResult = makeInf32(mux2to1.module(signA, signB, isInfB));
        boolean zeroResultSign = and.gate(signA, signB);
        boolean[] zeroResult = makeZero32(zeroResultSign);
        
        boolean isZeroExpA = isZeroExp(expA);
        boolean isZeroExpB = isZeroExp(expB);
        
        boolean[] expAExtended = extendExp8To11(expA);
        boolean[] expBExtended = extendExp8To11(expB);
        
        boolean[] manAWithHidden = addHiddenBit32(manA, isZeroExpA);
        boolean[] manBWithHidden = addHiddenBit32(manB, isZeroExpB);
        
        boolean[] expDiff = sub11B(expAExtended, expBExtended);
        boolean expALarger = not.gate(expDiff[10]);
        
        boolean[] largerExp = mux11B(expAExtended, expBExtended, expALarger);
        
        boolean[] shiftAmount = mux11B(expDiff, negate11(expDiff), expALarger);
        
        boolean[] shiftedMan = shiftRight24(manBWithHidden, shiftAmount);
        boolean[] manALigned = mux24B(manAWithHidden, shiftedMan, expALarger);
        
        boolean[] shiftedManA = shiftRight24(manAWithHidden, shiftAmount);
        boolean[] manBLigned = mux24B(manBWithHidden, shiftedManA, not.gate(expALarger));
        
        boolean[] manAForAdd = mux24B(manALigned, manBLigned, expALarger);
        boolean[] manBForAdd = mux24B(manBLigned, manALigned, expALarger);
        
        boolean signsDiffer = xor.gate(signA, signB);
        
        boolean[] manBInverted = not24B(manBForAdd);
        boolean[] manBForOp = mux24B(manBForAdd, manBInverted, signsDiffer);
        boolean carryIn = signsDiffer;
        
        boolean[] manSum = add25B(manAForAdd, manBForOp, carryIn);
        boolean addCarry = manSum[24];
        
        boolean[] manSubAB = sub25B(manAForAdd, manBForAdd);
        boolean[] manSubBA = sub25B(manBForAdd, manAForAdd);
        boolean subBorrow = not.gate(manSubAB[24]);
        
        boolean[] manSubResult = mux25B(manSubAB, manSubBA, subBorrow);
        boolean subResultZero = isAllZero24(manSubResult);
        
        boolean[] manResult = mux25B(manSum, manSubResult, signsDiffer);
        boolean carryOut = mux2to1.module(addCarry, false, signsDiffer);
        
        boolean aLargerOrEqual = or.gate(expALarger, subBorrow);
        boolean resultSign = mux2to1.module(signB, signA, aLargerOrEqual);
        resultSign = mux2to1.module(resultSign, xor.gate(signA, signB), not.gate(signsDiffer));
        boolean resultZero = and.gate(signsDiffer, subResultZero);
        
        boolean zeroSignSame = and.gate(signA, signB);
        boolean zeroSign = mux2to1.module(false, zeroSignSame, not.gate(signsDiffer));
        resultSign = mux2to1.module(resultSign, zeroSign, resultZero);
        
        boolean[] manAfterCarry = mux25B(manResult, shiftRight25By1(manResult), carryOut);
        boolean[] expAfterCarry = mux11B(largerExp, add11B(largerExp, makeOne11()), carryOut);
        
        boolean needNorm = and.gate(signsDiffer, not.gate(manAfterCarry[23]));
        boolean[] manNorm1 = mux25B(manAfterCarry, shiftLeft25By1(manAfterCarry), needNorm);
        boolean[] expNorm1 = mux11B(expAfterCarry, sub11B(expAfterCarry, makeOne11()), needNorm);
        
        boolean needNorm2 = and.gate(needNorm, not.gate(manNorm1[23]));
        boolean[] manNorm2 = mux25B(manNorm1, shiftLeft25By1(manNorm1), needNorm2);
        boolean[] expNorm2 = mux11B(expNorm1, sub11B(expNorm1, makeOne11()), needNorm2);
        
        boolean needNorm3 = and.gate(needNorm2, not.gate(manNorm2[23]));
        boolean[] normalizedMan = mux25B(manNorm2, shiftLeft25By1(manNorm2), needNorm3);
        boolean[] resultExp = mux11B(expNorm2, sub11B(expNorm2, makeOne11()), needNorm3);
        
        boolean[] zeroExp = new boolean[11];
        boolean[] finalExp = mux11B(resultExp, zeroExp, resultZero);
        boolean[] zeroMan = new boolean[25];
        boolean[] finalMan = mux25B(normalizedMan, zeroMan, resultZero);
        
        boolean[] normalResult = packFloat32(resultSign, finalExp, finalMan);
        
        boolean returnNaN = or.gate(eitherNaN, infSignsDiffer);
        boolean returnInf = and.gate(not.gate(returnNaN), or.gate(isInfA, isInfB));
        boolean returnZero = and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), bothZero);
        boolean returnA = and.gate(and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), isZeroB), not.gate(isZeroA));
        boolean returnB = and.gate(and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), isZeroA), not.gate(isZeroB));
        
        boolean[] result1 = mux32B(normalResult, nanResult, returnNaN);
        boolean[] result2 = mux32B(result1, infResult, returnInf);
        boolean[] result3 = mux32B(result2, zeroResult, returnZero);
        boolean[] result4 = mux32B(result3, a, returnA);
        boolean[] result5 = mux32B(result4, b, returnB);
        
        return result5;
    }
    
    private static boolean[] extractExp32(boolean[] f) {
        boolean[] exp = new boolean[8];
        exp[0] = f[23]; exp[1] = f[24]; exp[2] = f[25]; exp[3] = f[26];
        exp[4] = f[27]; exp[5] = f[28]; exp[6] = f[29]; exp[7] = f[30];
        return exp;
    }
    
    private static boolean[] extractMan32(boolean[] f) {
        boolean[] man = new boolean[23];
        man[0] = f[0]; man[1] = f[1]; man[2] = f[2]; man[3] = f[3];
        man[4] = f[4]; man[5] = f[5]; man[6] = f[6]; man[7] = f[7];
        man[8] = f[8]; man[9] = f[9]; man[10] = f[10]; man[11] = f[11];
        man[12] = f[12]; man[13] = f[13]; man[14] = f[14]; man[15] = f[15];
        man[16] = f[16]; man[17] = f[17]; man[18] = f[18]; man[19] = f[19];
        man[20] = f[20]; man[21] = f[21]; man[22] = f[22];
        return man;
    }
    
    private static boolean isZeroExp(boolean[] exp) {
        boolean not0 = not.gate(exp[0]);
        boolean not1 = not.gate(exp[1]);
        boolean not2 = not.gate(exp[2]);
        boolean not3 = not.gate(exp[3]);
        boolean not4 = not.gate(exp[4]);
        boolean not5 = not.gate(exp[5]);
        boolean not6 = not.gate(exp[6]);
        boolean not7 = not.gate(exp[7]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, not1), not2), not3), not4), not5), not6), not7);
    }
    
    private static boolean isAllOneExp(boolean[] exp) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(exp[0], exp[1]), exp[2]), exp[3]), exp[4]), exp[5]), exp[6]), exp[7]);
    }
    
    private static boolean isZeroMan(boolean[] man) {
        boolean z0 = not.gate(man[0]);
        boolean z1 = not.gate(man[1]);
        boolean z2 = not.gate(man[2]);
        boolean z3 = not.gate(man[3]);
        boolean z4 = not.gate(man[4]);
        boolean z5 = not.gate(man[5]);
        boolean z6 = not.gate(man[6]);
        boolean z7 = not.gate(man[7]);
        boolean z8 = not.gate(man[8]);
        boolean z9 = not.gate(man[9]);
        boolean z10 = not.gate(man[10]);
        boolean z11 = not.gate(man[11]);
        boolean z12 = not.gate(man[12]);
        boolean z13 = not.gate(man[13]);
        boolean z14 = not.gate(man[14]);
        boolean z15 = not.gate(man[15]);
        boolean z16 = not.gate(man[16]);
        boolean z17 = not.gate(man[17]);
        boolean z18 = not.gate(man[18]);
        boolean z19 = not.gate(man[19]);
        boolean z20 = not.gate(man[20]);
        boolean z21 = not.gate(man[21]);
        boolean z22 = not.gate(man[22]);
        boolean s0 = and.gate(z0, z1);
        boolean s1 = and.gate(s0, z2);
        boolean s2 = and.gate(s1, z3);
        boolean s3 = and.gate(s2, z4);
        boolean s4 = and.gate(s3, z5);
        boolean s5 = and.gate(s4, z6);
        boolean s6 = and.gate(s5, z7);
        boolean s7 = and.gate(s6, z8);
        boolean s8 = and.gate(s7, z9);
        boolean s9 = and.gate(s8, z10);
        boolean s10 = and.gate(s9, z11);
        boolean s11 = and.gate(s10, z12);
        boolean s12 = and.gate(s11, z13);
        boolean s13 = and.gate(s12, z14);
        boolean s14 = and.gate(s13, z15);
        boolean s15 = and.gate(s14, z16);
        boolean s16 = and.gate(s15, z17);
        boolean s17 = and.gate(s16, z18);
        boolean s18 = and.gate(s17, z19);
        boolean s19 = and.gate(s18, z20);
        boolean s20 = and.gate(s19, z21);
        boolean s21 = and.gate(s20, z22);
        return s21;
    }
    
    private static boolean[] extendExp8To11(boolean[] exp) {
        boolean[] result = new boolean[11];
        result[0] = exp[0]; result[1] = exp[1]; result[2] = exp[2]; result[3] = exp[3];
        result[4] = exp[4]; result[5] = exp[5]; result[6] = exp[6]; result[7] = exp[7];
        result[8] = false; result[9] = false; result[10] = false;
        return result;
    }
    
    private static boolean[] addHiddenBit32(boolean[] man, boolean isZero) {
        boolean[] result = new boolean[24];
        result[0] = man[0]; result[1] = man[1]; result[2] = man[2]; result[3] = man[3];
        result[4] = man[4]; result[5] = man[5]; result[6] = man[6]; result[7] = man[7];
        result[8] = man[8]; result[9] = man[9]; result[10] = man[10]; result[11] = man[11];
        result[12] = man[12]; result[13] = man[13]; result[14] = man[14]; result[15] = man[15];
        result[16] = man[16]; result[17] = man[17]; result[18] = man[18]; result[19] = man[19];
        result[20] = man[20]; result[21] = man[21]; result[22] = man[22];
        result[23] = not.gate(isZero);
        return result;
    }
    
    private static boolean[] sub11B(boolean[] a, boolean[] b) {
        boolean[] notB = new boolean[11];
        notB[0] = not.gate(b[0]); notB[1] = not.gate(b[1]); notB[2] = not.gate(b[2]); notB[3] = not.gate(b[3]);
        notB[4] = not.gate(b[4]); notB[5] = not.gate(b[5]); notB[6] = not.gate(b[6]); notB[7] = not.gate(b[7]);
        notB[8] = not.gate(b[8]); notB[9] = not.gate(b[9]); notB[10] = not.gate(b[10]);
        return add11BWithCarry(a, notB, true);
    }
    
    private static boolean[] add11B(boolean[] a, boolean[] b) {
        return add11BWithCarry(a, b, false);
    }
    
    private static boolean[] add11BWithCarry(boolean[] a, boolean[] b, boolean carryIn) {
        boolean[] result = new boolean[11];
        boolean c0 = carryIn;
        boolean[] fa0 = fullAdder.module(a[0], b[0], c0);
        result[0] = fa0[0]; boolean c1 = fa0[1];
        boolean[] fa1 = fullAdder.module(a[1], b[1], c1);
        result[1] = fa1[0]; boolean c2 = fa1[1];
        boolean[] fa2 = fullAdder.module(a[2], b[2], c2);
        result[2] = fa2[0]; boolean c3 = fa2[1];
        boolean[] fa3 = fullAdder.module(a[3], b[3], c3);
        result[3] = fa3[0]; boolean c4 = fa3[1];
        boolean[] fa4 = fullAdder.module(a[4], b[4], c4);
        result[4] = fa4[0]; boolean c5 = fa4[1];
        boolean[] fa5 = fullAdder.module(a[5], b[5], c5);
        result[5] = fa5[0]; boolean c6 = fa5[1];
        boolean[] fa6 = fullAdder.module(a[6], b[6], c6);
        result[6] = fa6[0]; boolean c7 = fa6[1];
        boolean[] fa7 = fullAdder.module(a[7], b[7], c7);
        result[7] = fa7[0]; boolean c8 = fa7[1];
        boolean[] fa8 = fullAdder.module(a[8], b[8], c8);
        result[8] = fa8[0]; boolean c9 = fa8[1];
        boolean[] fa9 = fullAdder.module(a[9], b[9], c9);
        result[9] = fa9[0]; boolean c10 = fa9[1];
        boolean[] fa10 = fullAdder.module(a[10], b[10], c10);
        result[10] = fa10[0];
        return result;
    }
    
    private static boolean[] negate11(boolean[] a) {
        boolean[] notA = new boolean[11];
        notA[0] = not.gate(a[0]); notA[1] = not.gate(a[1]); notA[2] = not.gate(a[2]); notA[3] = not.gate(a[3]);
        notA[4] = not.gate(a[4]); notA[5] = not.gate(a[5]); notA[6] = not.gate(a[6]); notA[7] = not.gate(a[7]);
        notA[8] = not.gate(a[8]); notA[9] = not.gate(a[9]); notA[10] = not.gate(a[10]);
        return add11BWithCarry(notA, new boolean[11], true);
    }
    
    private static boolean[] mux11B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[11];
        result[0] = mux2to1.module(a[0], b[0], sel);
        result[1] = mux2to1.module(a[1], b[1], sel);
        result[2] = mux2to1.module(a[2], b[2], sel);
        result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel);
        result[5] = mux2to1.module(a[5], b[5], sel);
        result[6] = mux2to1.module(a[6], b[6], sel);
        result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel);
        result[9] = mux2to1.module(a[9], b[9], sel);
        result[10] = mux2to1.module(a[10], b[10], sel);
        return result;
    }
    
    private static boolean[] mux24B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[24];
        result[0] = mux2to1.module(a[0], b[0], sel);
        result[1] = mux2to1.module(a[1], b[1], sel);
        result[2] = mux2to1.module(a[2], b[2], sel);
        result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel);
        result[5] = mux2to1.module(a[5], b[5], sel);
        result[6] = mux2to1.module(a[6], b[6], sel);
        result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel);
        result[9] = mux2to1.module(a[9], b[9], sel);
        result[10] = mux2to1.module(a[10], b[10], sel);
        result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel);
        result[13] = mux2to1.module(a[13], b[13], sel);
        result[14] = mux2to1.module(a[14], b[14], sel);
        result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel);
        result[17] = mux2to1.module(a[17], b[17], sel);
        result[18] = mux2to1.module(a[18], b[18], sel);
        result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel);
        result[21] = mux2to1.module(a[21], b[21], sel);
        result[22] = mux2to1.module(a[22], b[22], sel);
        result[23] = mux2to1.module(a[23], b[23], sel);
        return result;
    }
    
    private static boolean[] not24B(boolean[] a) {
        boolean[] result = new boolean[24];
        result[0] = not.gate(a[0]); result[1] = not.gate(a[1]); result[2] = not.gate(a[2]); result[3] = not.gate(a[3]);
        result[4] = not.gate(a[4]); result[5] = not.gate(a[5]); result[6] = not.gate(a[6]); result[7] = not.gate(a[7]);
        result[8] = not.gate(a[8]); result[9] = not.gate(a[9]); result[10] = not.gate(a[10]); result[11] = not.gate(a[11]);
        result[12] = not.gate(a[12]); result[13] = not.gate(a[13]); result[14] = not.gate(a[14]); result[15] = not.gate(a[15]);
        result[16] = not.gate(a[16]); result[17] = not.gate(a[17]); result[18] = not.gate(a[18]); result[19] = not.gate(a[19]);
        result[20] = not.gate(a[20]); result[21] = not.gate(a[21]); result[22] = not.gate(a[22]); result[23] = not.gate(a[23]);
        return result;
    }
    
    private static boolean[] add25B(boolean[] a, boolean[] b, boolean carryIn) {
        boolean[] result = new boolean[25];
        boolean c0 = carryIn;
        boolean[] fa0 = fullAdder.module(a[0], b[0], c0);
        result[0] = fa0[0]; boolean c1 = fa0[1];
        boolean[] fa1 = fullAdder.module(a[1], b[1], c1);
        result[1] = fa1[0]; boolean c2 = fa1[1];
        boolean[] fa2 = fullAdder.module(a[2], b[2], c2);
        result[2] = fa2[0]; boolean c3 = fa2[1];
        boolean[] fa3 = fullAdder.module(a[3], b[3], c3);
        result[3] = fa3[0]; boolean c4 = fa3[1];
        boolean[] fa4 = fullAdder.module(a[4], b[4], c4);
        result[4] = fa4[0]; boolean c5 = fa4[1];
        boolean[] fa5 = fullAdder.module(a[5], b[5], c5);
        result[5] = fa5[0]; boolean c6 = fa5[1];
        boolean[] fa6 = fullAdder.module(a[6], b[6], c6);
        result[6] = fa6[0]; boolean c7 = fa6[1];
        boolean[] fa7 = fullAdder.module(a[7], b[7], c7);
        result[7] = fa7[0]; boolean c8 = fa7[1];
        boolean[] fa8 = fullAdder.module(a[8], b[8], c8);
        result[8] = fa8[0]; boolean c9 = fa8[1];
        boolean[] fa9 = fullAdder.module(a[9], b[9], c9);
        result[9] = fa9[0]; boolean c10 = fa9[1];
        boolean[] fa10 = fullAdder.module(a[10], b[10], c10);
        result[10] = fa10[0]; boolean c11 = fa10[1];
        boolean[] fa11 = fullAdder.module(a[11], b[11], c11);
        result[11] = fa11[0]; boolean c12 = fa11[1];
        boolean[] fa12 = fullAdder.module(a[12], b[12], c12);
        result[12] = fa12[0]; boolean c13 = fa12[1];
        boolean[] fa13 = fullAdder.module(a[13], b[13], c13);
        result[13] = fa13[0]; boolean c14 = fa13[1];
        boolean[] fa14 = fullAdder.module(a[14], b[14], c14);
        result[14] = fa14[0]; boolean c15 = fa14[1];
        boolean[] fa15 = fullAdder.module(a[15], b[15], c15);
        result[15] = fa15[0]; boolean c16 = fa15[1];
        boolean[] fa16 = fullAdder.module(a[16], b[16], c16);
        result[16] = fa16[0]; boolean c17 = fa16[1];
        boolean[] fa17 = fullAdder.module(a[17], b[17], c17);
        result[17] = fa17[0]; boolean c18 = fa17[1];
        boolean[] fa18 = fullAdder.module(a[18], b[18], c18);
        result[18] = fa18[0]; boolean c19 = fa18[1];
        boolean[] fa19 = fullAdder.module(a[19], b[19], c19);
        result[19] = fa19[0]; boolean c20 = fa19[1];
        boolean[] fa20 = fullAdder.module(a[20], b[20], c20);
        result[20] = fa20[0]; boolean c21 = fa20[1];
        boolean[] fa21 = fullAdder.module(a[21], b[21], c21);
        result[21] = fa21[0]; boolean c22 = fa21[1];
        boolean[] fa22 = fullAdder.module(a[22], b[22], c22);
        result[22] = fa22[0]; boolean c23 = fa22[1];
        boolean[] fa23 = fullAdder.module(a[23], b[23], c23);
        result[23] = fa23[0]; boolean c24 = fa23[1];
        result[24] = c24;
        return result;
    }
    
    private static boolean[] shiftRight24(boolean[] in, boolean[] shift) {
        boolean[] s0 = shiftRight24By1(in, shift[0]);
        boolean[] s1 = shiftRight24By2(s0, shift[1]);
        boolean[] s2 = shiftRight24By4(s1, shift[2]);
        boolean[] s3 = shiftRight24By8(s2, shift[3]);
        boolean[] s4 = shiftRight24By16(s3, shift[4]);
        return s4;
    }
    
    private static boolean[] shiftRight24By1(boolean[] in, boolean sel) {
        boolean[] out = new boolean[24];
        out[0] = mux2to1.module(in[0], in[1], sel);
        out[1] = mux2to1.module(in[1], in[2], sel);
        out[2] = mux2to1.module(in[2], in[3], sel);
        out[3] = mux2to1.module(in[3], in[4], sel);
        out[4] = mux2to1.module(in[4], in[5], sel);
        out[5] = mux2to1.module(in[5], in[6], sel);
        out[6] = mux2to1.module(in[6], in[7], sel);
        out[7] = mux2to1.module(in[7], in[8], sel);
        out[8] = mux2to1.module(in[8], in[9], sel);
        out[9] = mux2to1.module(in[9], in[10], sel);
        out[10] = mux2to1.module(in[10], in[11], sel);
        out[11] = mux2to1.module(in[11], in[12], sel);
        out[12] = mux2to1.module(in[12], in[13], sel);
        out[13] = mux2to1.module(in[13], in[14], sel);
        out[14] = mux2to1.module(in[14], in[15], sel);
        out[15] = mux2to1.module(in[15], in[16], sel);
        out[16] = mux2to1.module(in[16], in[17], sel);
        out[17] = mux2to1.module(in[17], in[18], sel);
        out[18] = mux2to1.module(in[18], in[19], sel);
        out[19] = mux2to1.module(in[19], in[20], sel);
        out[20] = mux2to1.module(in[20], in[21], sel);
        out[21] = mux2to1.module(in[21], in[22], sel);
        out[22] = mux2to1.module(in[22], in[23], sel);
        out[23] = mux2to1.module(in[23], false, sel);
        return out;
    }
    
    private static boolean[] shiftRight24By2(boolean[] in, boolean sel) {
        boolean[] out = new boolean[24];
        out[0] = mux2to1.module(in[0], in[2], sel);
        out[1] = mux2to1.module(in[1], in[3], sel);
        out[2] = mux2to1.module(in[2], in[4], sel);
        out[3] = mux2to1.module(in[3], in[5], sel);
        out[4] = mux2to1.module(in[4], in[6], sel);
        out[5] = mux2to1.module(in[5], in[7], sel);
        out[6] = mux2to1.module(in[6], in[8], sel);
        out[7] = mux2to1.module(in[7], in[9], sel);
        out[8] = mux2to1.module(in[8], in[10], sel);
        out[9] = mux2to1.module(in[9], in[11], sel);
        out[10] = mux2to1.module(in[10], in[12], sel);
        out[11] = mux2to1.module(in[11], in[13], sel);
        out[12] = mux2to1.module(in[12], in[14], sel);
        out[13] = mux2to1.module(in[13], in[15], sel);
        out[14] = mux2to1.module(in[14], in[16], sel);
        out[15] = mux2to1.module(in[15], in[17], sel);
        out[16] = mux2to1.module(in[16], in[18], sel);
        out[17] = mux2to1.module(in[17], in[19], sel);
        out[18] = mux2to1.module(in[18], in[20], sel);
        out[19] = mux2to1.module(in[19], in[21], sel);
        out[20] = mux2to1.module(in[20], in[22], sel);
        out[21] = mux2to1.module(in[21], in[23], sel);
        out[22] = mux2to1.module(in[22], false, sel);
        out[23] = mux2to1.module(in[23], false, sel);
        return out;
    }
    
    private static boolean[] shiftRight24By4(boolean[] in, boolean sel) {
        boolean[] out = new boolean[24];
        out[0] = mux2to1.module(in[0], in[4], sel);
        out[1] = mux2to1.module(in[1], in[5], sel);
        out[2] = mux2to1.module(in[2], in[6], sel);
        out[3] = mux2to1.module(in[3], in[7], sel);
        out[4] = mux2to1.module(in[4], in[8], sel);
        out[5] = mux2to1.module(in[5], in[9], sel);
        out[6] = mux2to1.module(in[6], in[10], sel);
        out[7] = mux2to1.module(in[7], in[11], sel);
        out[8] = mux2to1.module(in[8], in[12], sel);
        out[9] = mux2to1.module(in[9], in[13], sel);
        out[10] = mux2to1.module(in[10], in[14], sel);
        out[11] = mux2to1.module(in[11], in[15], sel);
        out[12] = mux2to1.module(in[12], in[16], sel);
        out[13] = mux2to1.module(in[13], in[17], sel);
        out[14] = mux2to1.module(in[14], in[18], sel);
        out[15] = mux2to1.module(in[15], in[19], sel);
        out[16] = mux2to1.module(in[16], in[20], sel);
        out[17] = mux2to1.module(in[17], in[21], sel);
        out[18] = mux2to1.module(in[18], in[22], sel);
        out[19] = mux2to1.module(in[19], in[23], sel);
        out[20] = mux2to1.module(in[20], false, sel);
        out[21] = mux2to1.module(in[21], false, sel);
        out[22] = mux2to1.module(in[22], false, sel);
        out[23] = mux2to1.module(in[23], false, sel);
        return out;
    }
    
    private static boolean[] shiftRight24By8(boolean[] in, boolean sel) {
        boolean[] out = new boolean[24];
        out[0] = mux2to1.module(in[0], in[8], sel);
        out[1] = mux2to1.module(in[1], in[9], sel);
        out[2] = mux2to1.module(in[2], in[10], sel);
        out[3] = mux2to1.module(in[3], in[11], sel);
        out[4] = mux2to1.module(in[4], in[12], sel);
        out[5] = mux2to1.module(in[5], in[13], sel);
        out[6] = mux2to1.module(in[6], in[14], sel);
        out[7] = mux2to1.module(in[7], in[15], sel);
        out[8] = mux2to1.module(in[8], in[16], sel);
        out[9] = mux2to1.module(in[9], in[17], sel);
        out[10] = mux2to1.module(in[10], in[18], sel);
        out[11] = mux2to1.module(in[11], in[19], sel);
        out[12] = mux2to1.module(in[12], in[20], sel);
        out[13] = mux2to1.module(in[13], in[21], sel);
        out[14] = mux2to1.module(in[14], in[22], sel);
        out[15] = mux2to1.module(in[15], in[23], sel);
        out[16] = mux2to1.module(in[16], false, sel);
        out[17] = mux2to1.module(in[17], false, sel);
        out[18] = mux2to1.module(in[18], false, sel);
        out[19] = mux2to1.module(in[19], false, sel);
        out[20] = mux2to1.module(in[20], false, sel);
        out[21] = mux2to1.module(in[21], false, sel);
        out[22] = mux2to1.module(in[22], false, sel);
        out[23] = mux2to1.module(in[23], false, sel);
        return out;
    }
    
    private static boolean[] shiftRight24By16(boolean[] in, boolean sel) {
        boolean[] out = new boolean[24];
        out[0] = mux2to1.module(in[0], in[16], sel);
        out[1] = mux2to1.module(in[1], in[17], sel);
        out[2] = mux2to1.module(in[2], in[18], sel);
        out[3] = mux2to1.module(in[3], in[19], sel);
        out[4] = mux2to1.module(in[4], in[20], sel);
        out[5] = mux2to1.module(in[5], in[21], sel);
        out[6] = mux2to1.module(in[6], in[22], sel);
        out[7] = mux2to1.module(in[7], in[23], sel);
        out[8] = mux2to1.module(in[8], false, sel);
        out[9] = mux2to1.module(in[9], false, sel);
        out[10] = mux2to1.module(in[10], false, sel);
        out[11] = mux2to1.module(in[11], false, sel);
        out[12] = mux2to1.module(in[12], false, sel);
        out[13] = mux2to1.module(in[13], false, sel);
        out[14] = mux2to1.module(in[14], false, sel);
        out[15] = mux2to1.module(in[15], false, sel);
        out[16] = mux2to1.module(in[16], false, sel);
        out[17] = mux2to1.module(in[17], false, sel);
        out[18] = mux2to1.module(in[18], false, sel);
        out[19] = mux2to1.module(in[19], false, sel);
        out[20] = mux2to1.module(in[20], false, sel);
        out[21] = mux2to1.module(in[21], false, sel);
        out[22] = mux2to1.module(in[22], false, sel);
        out[23] = mux2to1.module(in[23], false, sel);
        return out;
    }
    
    private static boolean[] normalizeMan32(boolean[] man, boolean overflow, boolean underflow) {
        boolean[] s0 = mux24B(man, shiftRight24By1Simple(man), overflow);
        boolean[] s1 = mux24B(s0, shiftLeft24By1(s0), underflow);
        return s1;
    }
    
    private static boolean[] shiftLeft24By1(boolean[] in) {
        boolean[] out = new boolean[24];
        out[0] = false;
        out[1] = in[0]; out[2] = in[1]; out[3] = in[2]; out[4] = in[3];
        out[5] = in[4]; out[6] = in[5]; out[7] = in[6]; out[8] = in[7];
        out[9] = in[8]; out[10] = in[9]; out[11] = in[10]; out[12] = in[11];
        out[13] = in[12]; out[14] = in[13]; out[15] = in[14]; out[16] = in[15];
        out[17] = in[16]; out[18] = in[17]; out[19] = in[18]; out[20] = in[19];
        out[21] = in[20]; out[22] = in[21]; out[23] = in[22];
        return out;
    }
    
    private static boolean[] shiftRight24By1Simple(boolean[] in) {
        boolean[] out = new boolean[24];
        out[0] = in[1]; out[1] = in[2]; out[2] = in[3]; out[3] = in[4];
        out[4] = in[5]; out[5] = in[6]; out[6] = in[7]; out[7] = in[8];
        out[8] = in[9]; out[9] = in[10]; out[10] = in[11]; out[11] = in[12];
        out[12] = in[13]; out[13] = in[14]; out[14] = in[15]; out[15] = in[16];
        out[16] = in[17]; out[17] = in[18]; out[18] = in[19]; out[19] = in[20];
        out[20] = in[21]; out[21] = in[22]; out[22] = in[23]; out[23] = false;
        return out;
    }
    
    private static boolean[] calcExpAdjust32(boolean overflow, boolean underflow) {
        boolean[] result = new boolean[11];
        result[0] = or.gate(overflow, underflow);
        result[1] = underflow; result[2] = underflow; result[3] = underflow;
        result[4] = underflow; result[5] = underflow; result[6] = underflow;
        result[7] = underflow; result[8] = underflow; result[9] = underflow;
        result[10] = underflow;
        return result;
    }
    
    private static boolean calcResultSign32(boolean signA, boolean signB, boolean signsDiffer, boolean expALarger) {
        boolean signWhenDiff = mux2to1.module(signB, signA, expALarger);
        return mux2to1.module(signA, signWhenDiff, signsDiffer);
    }
    
    private static boolean[] packFloat32(boolean sign, boolean[] exp, boolean[] man) {
        boolean[] result = new boolean[32];
        result[0] = man[0]; result[1] = man[1]; result[2] = man[2]; result[3] = man[3];
        result[4] = man[4]; result[5] = man[5]; result[6] = man[6]; result[7] = man[7];
        result[8] = man[8]; result[9] = man[9]; result[10] = man[10]; result[11] = man[11];
        result[12] = man[12]; result[13] = man[13]; result[14] = man[14]; result[15] = man[15];
        result[16] = man[16]; result[17] = man[17]; result[18] = man[18]; result[19] = man[19];
        result[20] = man[20]; result[21] = man[21]; result[22] = man[22];
        result[23] = exp[0]; result[24] = exp[1]; result[25] = exp[2]; result[26] = exp[3];
        result[27] = exp[4]; result[28] = exp[5]; result[29] = exp[6]; result[30] = exp[7];
        result[31] = sign;
        return result;
    }
    
    private static boolean[] sub25B(boolean[] a, boolean[] b) {
        boolean[] aExt = new boolean[25];
        aExt[0] = a[0]; aExt[1] = a[1]; aExt[2] = a[2]; aExt[3] = a[3];
        aExt[4] = a[4]; aExt[5] = a[5]; aExt[6] = a[6]; aExt[7] = a[7];
        aExt[8] = a[8]; aExt[9] = a[9]; aExt[10] = a[10]; aExt[11] = a[11];
        aExt[12] = a[12]; aExt[13] = a[13]; aExt[14] = a[14]; aExt[15] = a[15];
        aExt[16] = a[16]; aExt[17] = a[17]; aExt[18] = a[18]; aExt[19] = a[19];
        aExt[20] = a[20]; aExt[21] = a[21]; aExt[22] = a[22]; aExt[23] = a[23];
        aExt[24] = false;
        
        boolean[] notB = new boolean[25];
        notB[0] = not.gate(b[0]); notB[1] = not.gate(b[1]); notB[2] = not.gate(b[2]); notB[3] = not.gate(b[3]);
        notB[4] = not.gate(b[4]); notB[5] = not.gate(b[5]); notB[6] = not.gate(b[6]); notB[7] = not.gate(b[7]);
        notB[8] = not.gate(b[8]); notB[9] = not.gate(b[9]); notB[10] = not.gate(b[10]); notB[11] = not.gate(b[11]);
        notB[12] = not.gate(b[12]); notB[13] = not.gate(b[13]); notB[14] = not.gate(b[14]); notB[15] = not.gate(b[15]);
        notB[16] = not.gate(b[16]); notB[17] = not.gate(b[17]); notB[18] = not.gate(b[18]); notB[19] = not.gate(b[19]);
        notB[20] = not.gate(b[20]); notB[21] = not.gate(b[21]); notB[22] = not.gate(b[22]); notB[23] = not.gate(b[23]);
        notB[24] = false;
        return add25B(aExt, notB, true);
    }
    
    private static boolean[] mux25B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[25];
        result[0] = mux2to1.module(a[0], b[0], sel); result[1] = mux2to1.module(a[1], b[1], sel);
        result[2] = mux2to1.module(a[2], b[2], sel); result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel); result[5] = mux2to1.module(a[5], b[5], sel);
        result[6] = mux2to1.module(a[6], b[6], sel); result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel); result[9] = mux2to1.module(a[9], b[9], sel);
        result[10] = mux2to1.module(a[10], b[10], sel); result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel); result[13] = mux2to1.module(a[13], b[13], sel);
        result[14] = mux2to1.module(a[14], b[14], sel); result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel); result[17] = mux2to1.module(a[17], b[17], sel);
        result[18] = mux2to1.module(a[18], b[18], sel); result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel); result[21] = mux2to1.module(a[21], b[21], sel);
        result[22] = mux2to1.module(a[22], b[22], sel); result[23] = mux2to1.module(a[23], b[23], sel);
        result[24] = mux2to1.module(a[24], b[24], sel);
        return result;
    }
    
    private static boolean isAllZero25(boolean[] bits) {
        boolean z0 = not.gate(bits[0]); boolean z1 = not.gate(bits[1]); boolean z2 = not.gate(bits[2]);
        boolean z3 = not.gate(bits[3]); boolean z4 = not.gate(bits[4]); boolean z5 = not.gate(bits[5]);
        boolean z6 = not.gate(bits[6]); boolean z7 = not.gate(bits[7]); boolean z8 = not.gate(bits[8]);
        boolean z9 = not.gate(bits[9]); boolean z10 = not.gate(bits[10]); boolean z11 = not.gate(bits[11]);
        boolean z12 = not.gate(bits[12]); boolean z13 = not.gate(bits[13]); boolean z14 = not.gate(bits[14]);
        boolean z15 = not.gate(bits[15]); boolean z16 = not.gate(bits[16]); boolean z17 = not.gate(bits[17]);
        boolean z18 = not.gate(bits[18]); boolean z19 = not.gate(bits[19]); boolean z20 = not.gate(bits[20]);
        boolean z21 = not.gate(bits[21]); boolean z22 = not.gate(bits[22]); boolean z23 = not.gate(bits[23]);
        boolean z24 = not.gate(bits[24]);
        boolean s0 = and.gate(z0, z1); boolean s1 = and.gate(s0, z2); boolean s2 = and.gate(s1, z3);
        boolean s3 = and.gate(s2, z4); boolean s4 = and.gate(s3, z5); boolean s5 = and.gate(s4, z6);
        boolean s6 = and.gate(s5, z7); boolean s7 = and.gate(s6, z8); boolean s8 = and.gate(s7, z9);
        boolean s9 = and.gate(s8, z10); boolean s10 = and.gate(s9, z11); boolean s11 = and.gate(s10, z12);
        boolean s12 = and.gate(s11, z13); boolean s13 = and.gate(s12, z14); boolean s14 = and.gate(s13, z15);
        boolean s15 = and.gate(s14, z16); boolean s16 = and.gate(s15, z17); boolean s17 = and.gate(s16, z18);
        boolean s18 = and.gate(s17, z19); boolean s19 = and.gate(s18, z20); boolean s20 = and.gate(s19, z21);
        boolean s21 = and.gate(s20, z22); boolean s22 = and.gate(s21, z23); boolean s23 = and.gate(s22, z24);
        return s23;
    }
    
    private static boolean isAllZero24(boolean[] bits) {
        boolean z0 = not.gate(bits[0]); boolean z1 = not.gate(bits[1]); boolean z2 = not.gate(bits[2]);
        boolean z3 = not.gate(bits[3]); boolean z4 = not.gate(bits[4]); boolean z5 = not.gate(bits[5]);
        boolean z6 = not.gate(bits[6]); boolean z7 = not.gate(bits[7]); boolean z8 = not.gate(bits[8]);
        boolean z9 = not.gate(bits[9]); boolean z10 = not.gate(bits[10]); boolean z11 = not.gate(bits[11]);
        boolean z12 = not.gate(bits[12]); boolean z13 = not.gate(bits[13]); boolean z14 = not.gate(bits[14]);
        boolean z15 = not.gate(bits[15]); boolean z16 = not.gate(bits[16]); boolean z17 = not.gate(bits[17]);
        boolean z18 = not.gate(bits[18]); boolean z19 = not.gate(bits[19]); boolean z20 = not.gate(bits[20]);
        boolean z21 = not.gate(bits[21]); boolean z22 = not.gate(bits[22]); boolean z23 = not.gate(bits[23]);
        boolean s0 = and.gate(z0, z1); boolean s1 = and.gate(s0, z2); boolean s2 = and.gate(s1, z3);
        boolean s3 = and.gate(s2, z4); boolean s4 = and.gate(s3, z5); boolean s5 = and.gate(s4, z6);
        boolean s6 = and.gate(s5, z7); boolean s7 = and.gate(s6, z8); boolean s8 = and.gate(s7, z9);
        boolean s9 = and.gate(s8, z10); boolean s10 = and.gate(s9, z11); boolean s11 = and.gate(s10, z12);
        boolean s12 = and.gate(s11, z13); boolean s13 = and.gate(s12, z14); boolean s14 = and.gate(s13, z15);
        boolean s15 = and.gate(s14, z16); boolean s16 = and.gate(s15, z17); boolean s17 = and.gate(s16, z18);
        boolean s18 = and.gate(s17, z19); boolean s19 = and.gate(s18, z20); boolean s20 = and.gate(s19, z21);
        boolean s21 = and.gate(s20, z22); boolean s22 = and.gate(s21, z23);
        return s22;
    }
    
    private static boolean[] shiftRight25By1(boolean[] in) {
        boolean[] result = new boolean[25];
        result[0] = false;
        result[1] = in[0]; result[2] = in[1]; result[3] = in[2]; result[4] = in[3];
        result[5] = in[4]; result[6] = in[5]; result[7] = in[6]; result[8] = in[7];
        result[9] = in[8]; result[10] = in[9]; result[11] = in[10]; result[12] = in[11];
        result[13] = in[12]; result[14] = in[13]; result[15] = in[14]; result[16] = in[15];
        result[17] = in[16]; result[18] = in[17]; result[19] = in[18]; result[20] = in[19];
        result[21] = in[20]; result[22] = in[21]; result[23] = in[22]; result[24] = in[23];
        return result;
    }
    
    private static boolean[] shiftLeft25By1(boolean[] in) {
        boolean[] result = new boolean[25];
        result[0] = in[1]; result[1] = in[2]; result[2] = in[3]; result[3] = in[4];
        result[4] = in[5]; result[5] = in[6]; result[6] = in[7]; result[7] = in[8];
        result[8] = in[9]; result[9] = in[10]; result[10] = in[11]; result[11] = in[12];
        result[12] = in[13]; result[13] = in[14]; result[14] = in[15]; result[15] = in[16];
        result[16] = in[17]; result[17] = in[18]; result[18] = in[19]; result[19] = in[20];
        result[20] = in[21]; result[21] = in[22]; result[22] = in[23]; result[23] = in[24];
        result[24] = false;
        return result;
    }
    
    private static boolean[] makeOne11() {
        boolean[] result = new boolean[11];
        result[0] = true;
        return result;
    }

    private static boolean[] makeQuietNaN32() {
        boolean[] result = new boolean[32];
        result[22] = true;
        result[23] = true; result[24] = true; result[25] = true; result[26] = true;
        result[27] = true; result[28] = true; result[29] = true; result[30] = true;
        return result;
    }
    
    private static boolean[] makeInf32(boolean sign) {
        boolean[] result = new boolean[32];
        result[23] = true; result[24] = true; result[25] = true; result[26] = true;
        result[27] = true; result[28] = true; result[29] = true; result[30] = true;
        result[31] = sign;
        return result;
    }
    
    private static boolean[] makeZero32(boolean sign) {
        boolean[] result = new boolean[32];
        result[31] = sign;
        return result;
    }
    
    private static boolean[] mux32B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[32];
        result[0] = mux2to1.module(a[0], b[0], sel);
        result[1] = mux2to1.module(a[1], b[1], sel);
        result[2] = mux2to1.module(a[2], b[2], sel);
        result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel);
        result[5] = mux2to1.module(a[5], b[5], sel);
        result[6] = mux2to1.module(a[6], b[6], sel);
        result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel);
        result[9] = mux2to1.module(a[9], b[9], sel);
        result[10] = mux2to1.module(a[10], b[10], sel);
        result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel);
        result[13] = mux2to1.module(a[13], b[13], sel);
        result[14] = mux2to1.module(a[14], b[14], sel);
        result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel);
        result[17] = mux2to1.module(a[17], b[17], sel);
        result[18] = mux2to1.module(a[18], b[18], sel);
        result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel);
        result[21] = mux2to1.module(a[21], b[21], sel);
        result[22] = mux2to1.module(a[22], b[22], sel);
        result[23] = mux2to1.module(a[23], b[23], sel);
        result[24] = mux2to1.module(a[24], b[24], sel);
        result[25] = mux2to1.module(a[25], b[25], sel);
        result[26] = mux2to1.module(a[26], b[26], sel);
        result[27] = mux2to1.module(a[27], b[27], sel);
        result[28] = mux2to1.module(a[28], b[28], sel);
        result[29] = mux2to1.module(a[29], b[29], sel);
        result[30] = mux2to1.module(a[30], b[30], sel);
        result[31] = mux2to1.module(a[31], b[31], sel);
        return result;
    }
}
