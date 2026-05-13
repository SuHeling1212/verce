package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.*;

public class fmul32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean signA = a[31];
        boolean signB = b[31];
        
        boolean[] expA = extractExp32(a);
        boolean[] expB = extractExp32(b);
        
        boolean[] manA = extractMan32(a);
        boolean[] manB = extractMan32(b);
        
        boolean isZeroExpA = isZeroExp(expA);
        boolean isZeroExpB = isZeroExp(expB);
        boolean isAllOneExpA = isAllOneExp(expA);
        boolean isAllOneExpB = isAllOneExp(expB);
        boolean isZeroManA = isZeroMan(manA);
        boolean isZeroManB = isZeroMan(manB);
        
        boolean isZeroA = and.gate(isZeroExpA, isZeroManA);
        boolean isZeroB = and.gate(isZeroExpB, isZeroManB);
        boolean isInfA = and.gate(isAllOneExpA, isZeroManA);
        boolean isInfB = and.gate(isAllOneExpB, isZeroManB);
        boolean isNaNA = and.gate(isAllOneExpA, not.gate(isZeroManA));
        boolean isNaNB = and.gate(isAllOneExpB, not.gate(isZeroManB));
        
        boolean resultSign = xor.gate(signA, signB);
        
        boolean[] nanResult = makeQuietNaN32();
        boolean[] infResult = makeInf32(resultSign);
        boolean[] zeroResult = makeZero32(resultSign);
        
        boolean eitherNaN = or.gate(isNaNA, isNaNB);
        boolean bothInf = and.gate(isInfA, isInfB);
        boolean bothZero = and.gate(isZeroA, isZeroB);
        boolean infTimesZero = or.gate(and.gate(isInfA, isZeroB), and.gate(isZeroA, isInfB));
        boolean zeroTimesFinite = and.gate(isZeroA, not.gate(isInfB));
        boolean finiteTimesZero = and.gate(isZeroB, not.gate(isInfA));
        
        boolean[] manAWithHidden = addHiddenBit24(manA, isZeroExpA);
        boolean[] manBWithHidden = addHiddenBit24(manB, isZeroExpB);
        
        boolean[] product = multiply24x24(manAWithHidden, manBWithHidden);
        
        boolean[] expAExtended = extendExp8To12(expA);
        boolean[] expBExtended = extendExp8To12(expB);
        
        boolean[] expSum = add12B(expAExtended, expBExtended);
        boolean[] bias = createBias32();
        boolean[] expResult = sub12B(expSum, bias);
        
        boolean needNorm = product[47];
        boolean[] expAdjust = createExpAdjust(needNorm);
        boolean[] finalExp = add12B(expResult, expAdjust);
        
        boolean[] normalizedMan = normalizeMan48(product, needNorm);
        
        boolean[] normalResult = packFloat32(resultSign, finalExp, normalizedMan);
        
        boolean returnNaN = or.gate(or.gate(eitherNaN, infTimesZero), bothInf);
        boolean returnInf = and.gate(not.gate(returnNaN), or.gate(isInfA, isInfB));
        boolean returnZero = and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), or.gate(bothZero, or.gate(zeroTimesFinite, finiteTimesZero)));
        
        boolean[] result1 = mux32B(normalResult, nanResult, returnNaN);
        boolean[] result2 = mux32B(result1, infResult, returnInf);
        boolean[] result3 = mux32B(result2, zeroResult, returnZero);
        
        return result3;
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
        boolean not0 = not.gate(exp[0]); boolean not1 = not.gate(exp[1]);
        boolean not2 = not.gate(exp[2]); boolean not3 = not.gate(exp[3]);
        boolean not4 = not.gate(exp[4]); boolean not5 = not.gate(exp[5]);
        boolean not6 = not.gate(exp[6]); boolean not7 = not.gate(exp[7]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, not1), not2), not3), not4), not5), not6), not7);
    }
    
    private static boolean isInfExp(boolean[] exp) {
        boolean is0 = exp[0]; boolean is1 = exp[1];
        boolean is2 = exp[2]; boolean is3 = exp[3];
        boolean is4 = exp[4]; boolean is5 = exp[5];
        boolean is6 = exp[6]; boolean is7 = exp[7];
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), is3), is4), is5), is6), is7);
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
    
    private static boolean[] addHiddenBit24(boolean[] man, boolean isZero) {
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
    
    private static boolean[] extendExp8To12(boolean[] exp) {
        boolean[] result = new boolean[12];
        result[0] = exp[0]; result[1] = exp[1]; result[2] = exp[2]; result[3] = exp[3];
        result[4] = exp[4]; result[5] = exp[5]; result[6] = exp[6]; result[7] = exp[7];
        result[8] = false; result[9] = false; result[10] = false; result[11] = false;
        return result;
    }
    
    private static boolean[] createBias32() {
        boolean[] bias = new boolean[12];
        bias[0] = false; bias[1] = true; bias[2] = true; bias[3] = true;
        bias[4] = true; bias[5] = true; bias[6] = true; bias[7] = true;
        bias[8] = false; bias[9] = false; bias[10] = false; bias[11] = false;
        return bias;
    }
    
    private static boolean[] createExpAdjust(boolean needNorm) {
        boolean[] result = new boolean[12];
        result[0] = needNorm;
        return result;
    }
    
    private static boolean[] multiply24x24(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[48];
        boolean[] partial0 = createPartial24(a, b[0]);
        boolean[] partial1 = createPartial24(a, b[1]);
        boolean[] partial2 = createPartial24(a, b[2]);
        boolean[] partial3 = createPartial24(a, b[3]);
        boolean[] partial4 = createPartial24(a, b[4]);
        boolean[] partial5 = createPartial24(a, b[5]);
        boolean[] partial6 = createPartial24(a, b[6]);
        boolean[] partial7 = createPartial24(a, b[7]);
        boolean[] partial8 = createPartial24(a, b[8]);
        boolean[] partial9 = createPartial24(a, b[9]);
        boolean[] partial10 = createPartial24(a, b[10]);
        boolean[] partial11 = createPartial24(a, b[11]);
        boolean[] partial12 = createPartial24(a, b[12]);
        boolean[] partial13 = createPartial24(a, b[13]);
        boolean[] partial14 = createPartial24(a, b[14]);
        boolean[] partial15 = createPartial24(a, b[15]);
        boolean[] partial16 = createPartial24(a, b[16]);
        boolean[] partial17 = createPartial24(a, b[17]);
        boolean[] partial18 = createPartial24(a, b[18]);
        boolean[] partial19 = createPartial24(a, b[19]);
        boolean[] partial20 = createPartial24(a, b[20]);
        boolean[] partial21 = createPartial24(a, b[21]);
        boolean[] partial22 = createPartial24(a, b[22]);
        boolean[] partial23 = createPartial24(a, b[23]);
        
        boolean[] sum0 = shiftAndAdd48(partial0, partial1, 1);
        boolean[] sum1 = shiftAndAdd48(sum0, partial2, 2);
        boolean[] sum2 = shiftAndAdd48(sum1, partial3, 3);
        boolean[] sum3 = shiftAndAdd48(sum2, partial4, 4);
        boolean[] sum4 = shiftAndAdd48(sum3, partial5, 5);
        boolean[] sum5 = shiftAndAdd48(sum4, partial6, 6);
        boolean[] sum6 = shiftAndAdd48(sum5, partial7, 7);
        boolean[] sum7 = shiftAndAdd48(sum6, partial8, 8);
        boolean[] sum8 = shiftAndAdd48(sum7, partial9, 9);
        boolean[] sum9 = shiftAndAdd48(sum8, partial10, 10);
        boolean[] sum10 = shiftAndAdd48(sum9, partial11, 11);
        boolean[] sum11 = shiftAndAdd48(sum10, partial12, 12);
        boolean[] sum12 = shiftAndAdd48(sum11, partial13, 13);
        boolean[] sum13 = shiftAndAdd48(sum12, partial14, 14);
        boolean[] sum14 = shiftAndAdd48(sum13, partial15, 15);
        boolean[] sum15 = shiftAndAdd48(sum14, partial16, 16);
        boolean[] sum16 = shiftAndAdd48(sum15, partial17, 17);
        boolean[] sum17 = shiftAndAdd48(sum16, partial18, 18);
        boolean[] sum18 = shiftAndAdd48(sum17, partial19, 19);
        boolean[] sum19 = shiftAndAdd48(sum18, partial20, 20);
        boolean[] sum20 = shiftAndAdd48(sum19, partial21, 21);
        boolean[] sum21 = shiftAndAdd48(sum20, partial22, 22);
        result = shiftAndAdd48(sum21, partial23, 23);
        
        return result;
    }
    
    private static boolean[] createPartial24(boolean[] a, boolean b) {
        boolean[] result = new boolean[48];
        result[0] = and.gate(a[0], b);
        result[1] = and.gate(a[1], b);
        result[2] = and.gate(a[2], b);
        result[3] = and.gate(a[3], b);
        result[4] = and.gate(a[4], b);
        result[5] = and.gate(a[5], b);
        result[6] = and.gate(a[6], b);
        result[7] = and.gate(a[7], b);
        result[8] = and.gate(a[8], b);
        result[9] = and.gate(a[9], b);
        result[10] = and.gate(a[10], b);
        result[11] = and.gate(a[11], b);
        result[12] = and.gate(a[12], b);
        result[13] = and.gate(a[13], b);
        result[14] = and.gate(a[14], b);
        result[15] = and.gate(a[15], b);
        result[16] = and.gate(a[16], b);
        result[17] = and.gate(a[17], b);
        result[18] = and.gate(a[18], b);
        result[19] = and.gate(a[19], b);
        result[20] = and.gate(a[20], b);
        result[21] = and.gate(a[21], b);
        result[22] = and.gate(a[22], b);
        result[23] = and.gate(a[23], b);
        return result;
    }
    
    private static boolean[] shiftAndAdd48(boolean[] a, boolean[] b, int shift) {
        boolean[] shifted = shiftLeft48(b, shift);
        return add48B(a, shifted);
    }
    
    private static boolean[] shiftLeft48(boolean[] a, int shift) {
        boolean[] result = new boolean[48];
        result[0] = false; result[1] = false; result[2] = false; result[3] = false;
        result[4] = false; result[5] = false; result[6] = false; result[7] = false;
        result[8] = false; result[9] = false; result[10] = false; result[11] = false;
        result[12] = false; result[13] = false; result[14] = false; result[15] = false;
        result[16] = false; result[17] = false; result[18] = false; result[19] = false;
        result[20] = false; result[21] = false; result[22] = false; result[23] = false;
        result[24] = a[0]; result[25] = a[1]; result[26] = a[2]; result[27] = a[3];
        result[28] = a[4]; result[29] = a[5]; result[30] = a[6]; result[31] = a[7];
        result[32] = a[8]; result[33] = a[9]; result[34] = a[10]; result[35] = a[11];
        result[36] = a[12]; result[37] = a[13]; result[38] = a[14]; result[39] = a[15];
        result[40] = a[16]; result[41] = a[17]; result[42] = a[18]; result[43] = a[19];
        result[44] = a[20]; result[45] = a[21]; result[46] = a[22]; result[47] = a[23];
        return result;
    }
    
    private static boolean[] add48B(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[48];
        boolean c = false;
        boolean[] fa;
        fa = fullAdder.module(a[0], b[0], c); result[0] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[1], b[1], c); result[1] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[2], b[2], c); result[2] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[3], b[3], c); result[3] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[4], b[4], c); result[4] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[5], b[5], c); result[5] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[6], b[6], c); result[6] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[7], b[7], c); result[7] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[8], b[8], c); result[8] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[9], b[9], c); result[9] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[10], b[10], c); result[10] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[11], b[11], c); result[11] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[12], b[12], c); result[12] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[13], b[13], c); result[13] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[14], b[14], c); result[14] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[15], b[15], c); result[15] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[16], b[16], c); result[16] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[17], b[17], c); result[17] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[18], b[18], c); result[18] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[19], b[19], c); result[19] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[20], b[20], c); result[20] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[21], b[21], c); result[21] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[22], b[22], c); result[22] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[23], b[23], c); result[23] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[24], b[24], c); result[24] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[25], b[25], c); result[25] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[26], b[26], c); result[26] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[27], b[27], c); result[27] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[28], b[28], c); result[28] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[29], b[29], c); result[29] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[30], b[30], c); result[30] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[31], b[31], c); result[31] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[32], b[32], c); result[32] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[33], b[33], c); result[33] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[34], b[34], c); result[34] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[35], b[35], c); result[35] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[36], b[36], c); result[36] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[37], b[37], c); result[37] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[38], b[38], c); result[38] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[39], b[39], c); result[39] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[40], b[40], c); result[40] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[41], b[41], c); result[41] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[42], b[42], c); result[42] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[43], b[43], c); result[43] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[44], b[44], c); result[44] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[45], b[45], c); result[45] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[46], b[46], c); result[46] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[47], b[47], c); result[47] = fa[0];
        return result;
    }
    
    private static boolean[] add12B(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[12];
        boolean c = false;
        boolean[] fa;
        fa = fullAdder.module(a[0], b[0], c); result[0] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[1], b[1], c); result[1] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[2], b[2], c); result[2] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[3], b[3], c); result[3] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[4], b[4], c); result[4] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[5], b[5], c); result[5] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[6], b[6], c); result[6] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[7], b[7], c); result[7] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[8], b[8], c); result[8] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[9], b[9], c); result[9] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[10], b[10], c); result[10] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[11], b[11], c); result[11] = fa[0];
        return result;
    }
    
    private static boolean[] sub12B(boolean[] a, boolean[] b) {
        boolean[] notB = new boolean[12];
        notB[0] = not.gate(b[0]); notB[1] = not.gate(b[1]); notB[2] = not.gate(b[2]); notB[3] = not.gate(b[3]);
        notB[4] = not.gate(b[4]); notB[5] = not.gate(b[5]); notB[6] = not.gate(b[6]); notB[7] = not.gate(b[7]);
        notB[8] = not.gate(b[8]); notB[9] = not.gate(b[9]); notB[10] = not.gate(b[10]); notB[11] = not.gate(b[11]);
        return add12BWithCarry(notB, a, true);
    }
    
    private static boolean[] add12BWithCarry(boolean[] a, boolean[] b, boolean carryIn) {
        boolean[] result = new boolean[12];
        boolean c = carryIn;
        boolean[] fa;
        fa = fullAdder.module(a[0], b[0], c); result[0] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[1], b[1], c); result[1] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[2], b[2], c); result[2] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[3], b[3], c); result[3] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[4], b[4], c); result[4] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[5], b[5], c); result[5] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[6], b[6], c); result[6] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[7], b[7], c); result[7] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[8], b[8], c); result[8] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[9], b[9], c); result[9] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[10], b[10], c); result[10] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[11], b[11], c); result[11] = fa[0];
        return result;
    }
    
    private static boolean[] normalizeMan48(boolean[] product, boolean needNorm) {
        boolean[] result = new boolean[23];
        result[0] = mux2to1.module(product[23], product[24], needNorm);
        result[1] = mux2to1.module(product[24], product[25], needNorm);
        result[2] = mux2to1.module(product[25], product[26], needNorm);
        result[3] = mux2to1.module(product[26], product[27], needNorm);
        result[4] = mux2to1.module(product[27], product[28], needNorm);
        result[5] = mux2to1.module(product[28], product[29], needNorm);
        result[6] = mux2to1.module(product[29], product[30], needNorm);
        result[7] = mux2to1.module(product[30], product[31], needNorm);
        result[8] = mux2to1.module(product[31], product[32], needNorm);
        result[9] = mux2to1.module(product[32], product[33], needNorm);
        result[10] = mux2to1.module(product[33], product[34], needNorm);
        result[11] = mux2to1.module(product[34], product[35], needNorm);
        result[12] = mux2to1.module(product[35], product[36], needNorm);
        result[13] = mux2to1.module(product[36], product[37], needNorm);
        result[14] = mux2to1.module(product[37], product[38], needNorm);
        result[15] = mux2to1.module(product[38], product[39], needNorm);
        result[16] = mux2to1.module(product[39], product[40], needNorm);
        result[17] = mux2to1.module(product[40], product[41], needNorm);
        result[18] = mux2to1.module(product[41], product[42], needNorm);
        result[19] = mux2to1.module(product[42], product[43], needNorm);
        result[20] = mux2to1.module(product[43], product[44], needNorm);
        result[21] = mux2to1.module(product[44], product[45], needNorm);
        result[22] = mux2to1.module(product[45], product[46], needNorm);
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
}
