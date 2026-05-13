package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.*;

public class fmul64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean signA = a[63];
        boolean signB = b[63];
        
        boolean[] expA = extractExp64(a);
        boolean[] expB = extractExp64(b);
        
        boolean[] manA = extractMan64(a);
        boolean[] manB = extractMan64(b);
        
        boolean isZeroExpA = isZeroExp11(expA);
        boolean isZeroExpB = isZeroExp11(expB);
        boolean isAllOneExpA = isAllOne11(expA);
        boolean isAllOneExpB = isAllOne11(expB);
        boolean isZeroManA = isZeroMan52(manA);
        boolean isZeroManB = isZeroMan52(manB);
        
        boolean isZeroA = and.gate(isZeroExpA, isZeroManA);
        boolean isZeroB = and.gate(isZeroExpB, isZeroManB);
        boolean isInfA = and.gate(isAllOneExpA, isZeroManA);
        boolean isInfB = and.gate(isAllOneExpB, isZeroManB);
        boolean isNaNA = and.gate(isAllOneExpA, not.gate(isZeroManA));
        boolean isNaNB = and.gate(isAllOneExpB, not.gate(isZeroManB));
        
        boolean resultSign = xor.gate(signA, signB);
        
        boolean[] nanResult = makeQuietNaN64();
        boolean[] infResult = makeInf64(resultSign);
        boolean[] zeroResult = makeZero64(resultSign);
        
        boolean eitherNaN = or.gate(isNaNA, isNaNB);
        boolean bothInf = and.gate(isInfA, isInfB);
        boolean bothZero = and.gate(isZeroA, isZeroB);
        boolean infTimesZero = or.gate(and.gate(isInfA, isZeroB), and.gate(isZeroA, isInfB));
        boolean zeroTimesFinite = and.gate(isZeroA, not.gate(isInfB));
        boolean finiteTimesZero = and.gate(isZeroB, not.gate(isInfA));
        
        boolean[] manAWithHidden = addHiddenBit53(manA, isZeroExpA);
        boolean[] manBWithHidden = addHiddenBit53(manB, isZeroExpB);
        
        boolean[] expSum = add12B(expA, expB);
        boolean[] bias = createBias64();
        boolean[] expResult = sub12B(expSum, bias);
    
        boolean[] productHigh = multiply53High(manAWithHidden, manBWithHidden);
        
        boolean needNorm = productHigh[52];
        boolean[] expAdjust = createExpAdjust(needNorm);
        boolean[] finalExp = add12B(expResult, expAdjust);
        
        boolean[] normalizedMan = normalizeMan53(productHigh, needNorm);
        
        boolean[] normalResult = packFloat64(resultSign, finalExp, normalizedMan);
        
        boolean returnNaN = or.gate(or.gate(eitherNaN, infTimesZero), bothInf);
        boolean returnInf = and.gate(not.gate(returnNaN), or.gate(isInfA, isInfB));
        boolean returnZero = and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), or.gate(bothZero, or.gate(zeroTimesFinite, finiteTimesZero)));
        
        boolean[] result1 = mux64B(normalResult, nanResult, returnNaN);
        boolean[] result2 = mux64B(result1, infResult, returnInf);
        boolean[] result3 = mux64B(result2, zeroResult, returnZero);
        
        return result3;
    }
    
    private static boolean[] extractExp64(boolean[] f) {
        boolean[] exp = new boolean[11];
        exp[0] = f[52]; exp[1] = f[53]; exp[2] = f[54]; exp[3] = f[55];
        exp[4] = f[56]; exp[5] = f[57]; exp[6] = f[58]; exp[7] = f[59];
        exp[8] = f[60]; exp[9] = f[61]; exp[10] = f[62];
        return exp;
    }
    
    private static boolean[] extractMan64(boolean[] f) {
        boolean[] man = new boolean[52];
        man[0] = f[0]; man[1] = f[1]; man[2] = f[2]; man[3] = f[3];
        man[4] = f[4]; man[5] = f[5]; man[6] = f[6]; man[7] = f[7];
        man[8] = f[8]; man[9] = f[9]; man[10] = f[10]; man[11] = f[11];
        man[12] = f[12]; man[13] = f[13]; man[14] = f[14]; man[15] = f[15];
        man[16] = f[16]; man[17] = f[17]; man[18] = f[18]; man[19] = f[19];
        man[20] = f[20]; man[21] = f[21]; man[22] = f[22]; man[23] = f[23];
        man[24] = f[24]; man[25] = f[25]; man[26] = f[26]; man[27] = f[27];
        man[28] = f[28]; man[29] = f[29]; man[30] = f[30]; man[31] = f[31];
        man[32] = f[32]; man[33] = f[33]; man[34] = f[34]; man[35] = f[35];
        man[36] = f[36]; man[37] = f[37]; man[38] = f[38]; man[39] = f[39];
        man[40] = f[40]; man[41] = f[41]; man[42] = f[42]; man[43] = f[43];
        man[44] = f[44]; man[45] = f[45]; man[46] = f[46]; man[47] = f[47];
        man[48] = f[48]; man[49] = f[49]; man[50] = f[50]; man[51] = f[51];
        return man;
    }
    
    private static boolean isZeroExp11(boolean[] exp) {
        boolean not0 = not.gate(exp[0]); boolean not1 = not.gate(exp[1]);
        boolean not2 = not.gate(exp[2]); boolean not3 = not.gate(exp[3]);
        boolean not4 = not.gate(exp[4]); boolean not5 = not.gate(exp[5]);
        boolean not6 = not.gate(exp[6]); boolean not7 = not.gate(exp[7]);
        boolean not8 = not.gate(exp[8]); boolean not9 = not.gate(exp[9]);
        boolean not10 = not.gate(exp[10]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, not1), not2), not3), not4), not5), not6), not7), not8), not9), not10);
    }
    
    private static boolean[] addHiddenBit53(boolean[] man, boolean isZero) {
        boolean[] result = new boolean[53];
        result[0] = man[0]; result[1] = man[1]; result[2] = man[2]; result[3] = man[3];
        result[4] = man[4]; result[5] = man[5]; result[6] = man[6]; result[7] = man[7];
        result[8] = man[8]; result[9] = man[9]; result[10] = man[10]; result[11] = man[11];
        result[12] = man[12]; result[13] = man[13]; result[14] = man[14]; result[15] = man[15];
        result[16] = man[16]; result[17] = man[17]; result[18] = man[18]; result[19] = man[19];
        result[20] = man[20]; result[21] = man[21]; result[22] = man[22]; result[23] = man[23];
        result[24] = man[24]; result[25] = man[25]; result[26] = man[26]; result[27] = man[27];
        result[28] = man[28]; result[29] = man[29]; result[30] = man[30]; result[31] = man[31];
        result[32] = man[32]; result[33] = man[33]; result[34] = man[34]; result[35] = man[35];
        result[36] = man[36]; result[37] = man[37]; result[38] = man[38]; result[39] = man[39];
        result[40] = man[40]; result[41] = man[41]; result[42] = man[42]; result[43] = man[43];
        result[44] = man[44]; result[45] = man[45]; result[46] = man[46]; result[47] = man[47];
        result[48] = man[48]; result[49] = man[49]; result[50] = man[50]; result[51] = man[51];
        result[52] = not.gate(isZero);
        return result;
    }
    
    private static boolean[] createBias64() {
        boolean[] bias = new boolean[12];
        bias[0] = false; bias[1] = false; bias[2] = false; bias[3] = true;
        bias[4] = false; bias[5] = false; bias[6] = false; bias[7] = false;
        bias[8] = false; bias[9] = false; bias[10] = false; bias[11] = false;
        return bias;
    }
    
    private static boolean[] createExpAdjust(boolean needNorm) {
        boolean[] result = new boolean[12];
        result[0] = needNorm;
        return result;
    }
    
    private static boolean[] multiply53Low(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[53];
        boolean[] partial;
        boolean[] sum = new boolean[53];
        partial = createPartial53(a, b[0]);
        sum = add53B(sum, partial);
        partial = createPartial53(a, b[1]);
        sum = shiftAndAdd53(sum, partial, 1);
        partial = createPartial53(a, b[2]);
        sum = shiftAndAdd53(sum, partial, 2);
        partial = createPartial53(a, b[3]);
        sum = shiftAndAdd53(sum, partial, 3);
        partial = createPartial53(a, b[4]);
        sum = shiftAndAdd53(sum, partial, 4);
        partial = createPartial53(a, b[5]);
        sum = shiftAndAdd53(sum, partial, 5);
        partial = createPartial53(a, b[6]);
        sum = shiftAndAdd53(sum, partial, 6);
        partial = createPartial53(a, b[7]);
        sum = shiftAndAdd53(sum, partial, 7);
        partial = createPartial53(a, b[8]);
        sum = shiftAndAdd53(sum, partial, 8);
        partial = createPartial53(a, b[9]);
        sum = shiftAndAdd53(sum, partial, 9);
        partial = createPartial53(a, b[10]);
        sum = shiftAndAdd53(sum, partial, 10);
        partial = createPartial53(a, b[11]);
        sum = shiftAndAdd53(sum, partial, 11);
        partial = createPartial53(a, b[12]);
        sum = shiftAndAdd53(sum, partial, 12);
        partial = createPartial53(a, b[13]);
        sum = shiftAndAdd53(sum, partial, 13);
        partial = createPartial53(a, b[14]);
        sum = shiftAndAdd53(sum, partial, 14);
        partial = createPartial53(a, b[15]);
        sum = shiftAndAdd53(sum, partial, 15);
        partial = createPartial53(a, b[16]);
        sum = shiftAndAdd53(sum, partial, 16);
        partial = createPartial53(a, b[17]);
        sum = shiftAndAdd53(sum, partial, 17);
        partial = createPartial53(a, b[18]);
        sum = shiftAndAdd53(sum, partial, 18);
        partial = createPartial53(a, b[19]);
        sum = shiftAndAdd53(sum, partial, 19);
        partial = createPartial53(a, b[20]);
        sum = shiftAndAdd53(sum, partial, 20);
        partial = createPartial53(a, b[21]);
        sum = shiftAndAdd53(sum, partial, 21);
        partial = createPartial53(a, b[22]);
        sum = shiftAndAdd53(sum, partial, 22);
        partial = createPartial53(a, b[23]);
        sum = shiftAndAdd53(sum, partial, 23);
        partial = createPartial53(a, b[24]);
        sum = shiftAndAdd53(sum, partial, 24);
        partial = createPartial53(a, b[25]);
        sum = shiftAndAdd53(sum, partial, 25);
        partial = createPartial53(a, b[26]);
        sum = shiftAndAdd53(sum, partial, 26);
        partial = createPartial53(a, b[27]);
        sum = shiftAndAdd53(sum, partial, 27);
        partial = createPartial53(a, b[28]);
        sum = shiftAndAdd53(sum, partial, 28);
        partial = createPartial53(a, b[29]);
        sum = shiftAndAdd53(sum, partial, 29);
        partial = createPartial53(a, b[30]);
        sum = shiftAndAdd53(sum, partial, 30);
        partial = createPartial53(a, b[31]);
        sum = shiftAndAdd53(sum, partial, 31);
        partial = createPartial53(a, b[32]);
        sum = shiftAndAdd53(sum, partial, 32);
        partial = createPartial53(a, b[33]);
        sum = shiftAndAdd53(sum, partial, 33);
        partial = createPartial53(a, b[34]);
        sum = shiftAndAdd53(sum, partial, 34);
        partial = createPartial53(a, b[35]);
        sum = shiftAndAdd53(sum, partial, 35);
        partial = createPartial53(a, b[36]);
        sum = shiftAndAdd53(sum, partial, 36);
        partial = createPartial53(a, b[37]);
        sum = shiftAndAdd53(sum, partial, 37);
        partial = createPartial53(a, b[38]);
        sum = shiftAndAdd53(sum, partial, 38);
        partial = createPartial53(a, b[39]);
        sum = shiftAndAdd53(sum, partial, 39);
        partial = createPartial53(a, b[40]);
        sum = shiftAndAdd53(sum, partial, 40);
        partial = createPartial53(a, b[41]);
        sum = shiftAndAdd53(sum, partial, 41);
        partial = createPartial53(a, b[42]);
        sum = shiftAndAdd53(sum, partial, 42);
        partial = createPartial53(a, b[43]);
        sum = shiftAndAdd53(sum, partial, 43);
        partial = createPartial53(a, b[44]);
        sum = shiftAndAdd53(sum, partial, 44);
        partial = createPartial53(a, b[45]);
        sum = shiftAndAdd53(sum, partial, 45);
        partial = createPartial53(a, b[46]);
        sum = shiftAndAdd53(sum, partial, 46);
        partial = createPartial53(a, b[47]);
        sum = shiftAndAdd53(sum, partial, 47);
        partial = createPartial53(a, b[48]);
        sum = shiftAndAdd53(sum, partial, 48);
        partial = createPartial53(a, b[49]);
        sum = shiftAndAdd53(sum, partial, 49);
        partial = createPartial53(a, b[50]);
        sum = shiftAndAdd53(sum, partial, 50);
        partial = createPartial53(a, b[51]);
        sum = shiftAndAdd53(sum, partial, 51);
        partial = createPartial53(a, b[52]);
        result = shiftAndAdd53(sum, partial, 52);
        return result;
    }
    
    private static boolean[] multiply53High(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[53];
        result[0] = and.gate(a[0], b[0]);
        result[1] = and.gate(a[1], b[0]);
        result[2] = and.gate(a[2], b[0]);
        result[3] = and.gate(a[3], b[0]);
        result[4] = and.gate(a[4], b[0]);
        result[5] = and.gate(a[5], b[0]);
        result[6] = and.gate(a[6], b[0]);
        result[7] = and.gate(a[7], b[0]);
        result[8] = and.gate(a[8], b[0]);
        result[9] = and.gate(a[9], b[0]);
        result[10] = and.gate(a[10], b[0]);
        result[11] = and.gate(a[11], b[0]);
        result[12] = and.gate(a[12], b[0]);
        result[13] = and.gate(a[13], b[0]);
        result[14] = and.gate(a[14], b[0]);
        result[15] = and.gate(a[15], b[0]);
        result[16] = and.gate(a[16], b[0]);
        result[17] = and.gate(a[17], b[0]);
        result[18] = and.gate(a[18], b[0]);
        result[19] = and.gate(a[19], b[0]);
        result[20] = and.gate(a[20], b[0]);
        result[21] = and.gate(a[21], b[0]);
        result[22] = and.gate(a[22], b[0]);
        result[23] = and.gate(a[23], b[0]);
        result[24] = and.gate(a[24], b[0]);
        result[25] = and.gate(a[25], b[0]);
        result[26] = and.gate(a[26], b[0]);
        result[27] = and.gate(a[27], b[0]);
        result[28] = and.gate(a[28], b[0]);
        result[29] = and.gate(a[29], b[0]);
        result[30] = and.gate(a[30], b[0]);
        result[31] = and.gate(a[31], b[0]);
        result[32] = and.gate(a[32], b[0]);
        result[33] = and.gate(a[33], b[0]);
        result[34] = and.gate(a[34], b[0]);
        result[35] = and.gate(a[35], b[0]);
        result[36] = and.gate(a[36], b[0]);
        result[37] = and.gate(a[37], b[0]);
        result[38] = and.gate(a[38], b[0]);
        result[39] = and.gate(a[39], b[0]);
        result[40] = and.gate(a[40], b[0]);
        result[41] = and.gate(a[41], b[0]);
        result[42] = and.gate(a[42], b[0]);
        result[43] = and.gate(a[43], b[0]);
        result[44] = and.gate(a[44], b[0]);
        result[45] = and.gate(a[45], b[0]);
        result[46] = and.gate(a[46], b[0]);
        result[47] = and.gate(a[47], b[0]);
        result[48] = and.gate(a[48], b[0]);
        result[49] = and.gate(a[49], b[0]);
        result[50] = and.gate(a[50], b[0]);
        result[51] = and.gate(a[51], b[0]);
        result[52] = and.gate(a[52], b[0]);
        return result;
    }
    
    private static boolean[] createPartial53(boolean[] a, boolean b) {
        boolean[] result = new boolean[53];
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
        result[24] = and.gate(a[24], b);
        result[25] = and.gate(a[25], b);
        result[26] = and.gate(a[26], b);
        result[27] = and.gate(a[27], b);
        result[28] = and.gate(a[28], b);
        result[29] = and.gate(a[29], b);
        result[30] = and.gate(a[30], b);
        result[31] = and.gate(a[31], b);
        result[32] = and.gate(a[32], b);
        result[33] = and.gate(a[33], b);
        result[34] = and.gate(a[34], b);
        result[35] = and.gate(a[35], b);
        result[36] = and.gate(a[36], b);
        result[37] = and.gate(a[37], b);
        result[38] = and.gate(a[38], b);
        result[39] = and.gate(a[39], b);
        result[40] = and.gate(a[40], b);
        result[41] = and.gate(a[41], b);
        result[42] = and.gate(a[42], b);
        result[43] = and.gate(a[43], b);
        result[44] = and.gate(a[44], b);
        result[45] = and.gate(a[45], b);
        result[46] = and.gate(a[46], b);
        result[47] = and.gate(a[47], b);
        result[48] = and.gate(a[48], b);
        result[49] = and.gate(a[49], b);
        result[50] = and.gate(a[50], b);
        result[51] = and.gate(a[51], b);
        result[52] = and.gate(a[52], b);
        return result;
    }
    
    private static boolean[] shiftAndAdd53(boolean[] a, boolean[] b, int shift) {
        boolean[] shifted = shiftLeft53(b, shift);
        return add53B(a, shifted);
    }
    
    private static boolean[] shiftLeft53(boolean[] a, int shift) {
        boolean[] result = new boolean[53];
        result[0] = false;
        result[1] = a[0]; result[2] = a[1]; result[3] = a[2]; result[4] = a[3];
        result[5] = a[4]; result[6] = a[5]; result[7] = a[6]; result[8] = a[7];
        result[9] = a[8]; result[10] = a[9]; result[11] = a[10]; result[12] = a[11];
        result[13] = a[12]; result[14] = a[13]; result[15] = a[14]; result[16] = a[15];
        result[17] = a[16]; result[18] = a[17]; result[19] = a[18]; result[20] = a[19];
        result[21] = a[20]; result[22] = a[21]; result[23] = a[22]; result[24] = a[23];
        result[25] = a[24]; result[26] = a[25]; result[27] = a[26]; result[28] = a[27];
        result[29] = a[28]; result[30] = a[29]; result[31] = a[30]; result[32] = a[31];
        result[33] = a[32]; result[34] = a[33]; result[35] = a[34]; result[36] = a[35];
        result[37] = a[36]; result[38] = a[37]; result[39] = a[38]; result[40] = a[39];
        result[41] = a[40]; result[42] = a[41]; result[43] = a[42]; result[44] = a[43];
        result[45] = a[44]; result[46] = a[45]; result[47] = a[46]; result[48] = a[47];
        result[49] = a[48]; result[50] = a[49]; result[51] = a[50]; result[52] = a[51];
        return result;
    }
    
    private static boolean[] add53B(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[53];
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
        fa = fullAdder.module(a[47], b[47], c); result[47] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[48], b[48], c); result[48] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[49], b[49], c); result[49] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[50], b[50], c); result[50] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[51], b[51], c); result[51] = fa[0]; c = fa[1];
        fa = fullAdder.module(a[52], b[52], c); result[52] = fa[0];
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
        return add12BWithCarry(a, notB, true);
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
    
    private static boolean[] normalizeMan53(boolean[] product, boolean needNorm) {
        boolean[] result = new boolean[52];
        result[0] = mux2to1.module(product[0], product[1], needNorm);
        result[1] = mux2to1.module(product[1], product[2], needNorm);
        result[2] = mux2to1.module(product[2], product[3], needNorm);
        result[3] = mux2to1.module(product[3], product[4], needNorm);
        result[4] = mux2to1.module(product[4], product[5], needNorm);
        result[5] = mux2to1.module(product[5], product[6], needNorm);
        result[6] = mux2to1.module(product[6], product[7], needNorm);
        result[7] = mux2to1.module(product[7], product[8], needNorm);
        result[8] = mux2to1.module(product[8], product[9], needNorm);
        result[9] = mux2to1.module(product[9], product[10], needNorm);
        result[10] = mux2to1.module(product[10], product[11], needNorm);
        result[11] = mux2to1.module(product[11], product[12], needNorm);
        result[12] = mux2to1.module(product[12], product[13], needNorm);
        result[13] = mux2to1.module(product[13], product[14], needNorm);
        result[14] = mux2to1.module(product[14], product[15], needNorm);
        result[15] = mux2to1.module(product[15], product[16], needNorm);
        result[16] = mux2to1.module(product[16], product[17], needNorm);
        result[17] = mux2to1.module(product[17], product[18], needNorm);
        result[18] = mux2to1.module(product[18], product[19], needNorm);
        result[19] = mux2to1.module(product[19], product[20], needNorm);
        result[20] = mux2to1.module(product[20], product[21], needNorm);
        result[21] = mux2to1.module(product[21], product[22], needNorm);
        result[22] = mux2to1.module(product[22], product[23], needNorm);
        result[23] = mux2to1.module(product[23], product[24], needNorm);
        result[24] = mux2to1.module(product[24], product[25], needNorm);
        result[25] = mux2to1.module(product[25], product[26], needNorm);
        result[26] = mux2to1.module(product[26], product[27], needNorm);
        result[27] = mux2to1.module(product[27], product[28], needNorm);
        result[28] = mux2to1.module(product[28], product[29], needNorm);
        result[29] = mux2to1.module(product[29], product[30], needNorm);
        result[30] = mux2to1.module(product[30], product[31], needNorm);
        result[31] = mux2to1.module(product[31], product[32], needNorm);
        result[32] = mux2to1.module(product[32], product[33], needNorm);
        result[33] = mux2to1.module(product[33], product[34], needNorm);
        result[34] = mux2to1.module(product[34], product[35], needNorm);
        result[35] = mux2to1.module(product[35], product[36], needNorm);
        result[36] = mux2to1.module(product[36], product[37], needNorm);
        result[37] = mux2to1.module(product[37], product[38], needNorm);
        result[38] = mux2to1.module(product[38], product[39], needNorm);
        result[39] = mux2to1.module(product[39], product[40], needNorm);
        result[40] = mux2to1.module(product[40], product[41], needNorm);
        result[41] = mux2to1.module(product[41], product[42], needNorm);
        result[42] = mux2to1.module(product[42], product[43], needNorm);
        result[43] = mux2to1.module(product[43], product[44], needNorm);
        result[44] = mux2to1.module(product[44], product[45], needNorm);
        result[45] = mux2to1.module(product[45], product[46], needNorm);
        result[46] = mux2to1.module(product[46], product[47], needNorm);
        result[47] = mux2to1.module(product[47], product[48], needNorm);
        result[48] = mux2to1.module(product[48], product[49], needNorm);
        result[49] = mux2to1.module(product[49], product[50], needNorm);
        result[50] = mux2to1.module(product[50], product[51], needNorm);
        result[51] = mux2to1.module(product[51], product[52], needNorm);
        return result;
    }
    
    private static boolean[] packFloat64(boolean sign, boolean[] exp, boolean[] man) {
        boolean[] result = new boolean[64];
        result[0] = man[0]; result[1] = man[1]; result[2] = man[2]; result[3] = man[3];
        result[4] = man[4]; result[5] = man[5]; result[6] = man[6]; result[7] = man[7];
        result[8] = man[8]; result[9] = man[9]; result[10] = man[10]; result[11] = man[11];
        result[12] = man[12]; result[13] = man[13]; result[14] = man[14]; result[15] = man[15];
        result[16] = man[16]; result[17] = man[17]; result[18] = man[18]; result[19] = man[19];
        result[20] = man[20]; result[21] = man[21]; result[22] = man[22]; result[23] = man[23];
        result[24] = man[24]; result[25] = man[25]; result[26] = man[26]; result[27] = man[27];
        result[28] = man[28]; result[29] = man[29]; result[30] = man[30]; result[31] = man[31];
        result[32] = man[32]; result[33] = man[33]; result[34] = man[34]; result[35] = man[35];
        result[36] = man[36]; result[37] = man[37]; result[38] = man[38]; result[39] = man[39];
        result[40] = man[40]; result[41] = man[41]; result[42] = man[42]; result[43] = man[43];
        result[44] = man[44]; result[45] = man[45]; result[46] = man[46]; result[47] = man[47];
        result[48] = man[48]; result[49] = man[49]; result[50] = man[50]; result[51] = man[51];
        result[52] = exp[0]; result[53] = exp[1]; result[54] = exp[2]; result[55] = exp[3];
        result[56] = exp[4]; result[57] = exp[5]; result[58] = exp[6]; result[59] = exp[7];
        result[60] = exp[8]; result[61] = exp[9]; result[62] = exp[10];
        result[63] = sign;
        return result;
    }
    
    private static boolean isAllOne11(boolean[] exp) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(exp[0], exp[1]), exp[2]), exp[3]),
            exp[4]), exp[5]), exp[6]), exp[7]), exp[8]), exp[9]), exp[10]);
    }
    
    private static boolean isZeroMan52(boolean[] man) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(
            not.gate(man[0]), not.gate(man[1])), not.gate(man[2])), not.gate(man[3])),
            not.gate(man[4])), not.gate(man[5])), not.gate(man[6])), not.gate(man[7])),
            not.gate(man[8])), not.gate(man[9])), not.gate(man[10])), not.gate(man[11])),
            not.gate(man[12])), not.gate(man[13])), not.gate(man[14])), not.gate(man[15])),
            not.gate(man[16])), not.gate(man[17])), not.gate(man[18])), not.gate(man[19])),
            not.gate(man[20])), not.gate(man[21])), not.gate(man[22])), not.gate(man[23])),
            not.gate(man[24])), not.gate(man[25])), not.gate(man[26])), not.gate(man[27])),
            not.gate(man[28])), not.gate(man[29])), not.gate(man[30])), not.gate(man[31])),
            not.gate(man[32])), not.gate(man[33])), not.gate(man[34])), not.gate(man[35])),
            not.gate(man[36])), not.gate(man[37])), not.gate(man[38])), not.gate(man[39])),
            not.gate(man[40])), not.gate(man[41])), not.gate(man[42])), not.gate(man[43])),
            not.gate(man[44])), not.gate(man[45])), not.gate(man[46])), not.gate(man[47])),
            not.gate(man[48])), not.gate(man[49])), not.gate(man[50])), not.gate(man[51]));
    }
    
    private static boolean[] makeQuietNaN64() {
        boolean[] result = new boolean[64];
        result[51] = true; result[52] = true; result[53] = true; result[54] = true;
        result[55] = true; result[56] = true; result[57] = true; result[58] = true;
        result[59] = true; result[60] = true; result[61] = true; result[62] = true;
        return result;
    }
    
    private static boolean[] makeInf64(boolean sign) {
        boolean[] result = new boolean[64];
        result[52] = true; result[53] = true; result[54] = true; result[55] = true;
        result[56] = true; result[57] = true; result[58] = true; result[59] = true;
        result[60] = true; result[61] = true; result[62] = true;
        result[63] = sign;
        return result;
    }
    
    private static boolean[] makeZero64(boolean sign) {
        boolean[] result = new boolean[64];
        result[63] = sign;
        return result;
    }
    
    private static boolean[] mux64B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[64];
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
        result[24] = mux2to1.module(a[24], b[24], sel); result[25] = mux2to1.module(a[25], b[25], sel);
        result[26] = mux2to1.module(a[26], b[26], sel); result[27] = mux2to1.module(a[27], b[27], sel);
        result[28] = mux2to1.module(a[28], b[28], sel); result[29] = mux2to1.module(a[29], b[29], sel);
        result[30] = mux2to1.module(a[30], b[30], sel); result[31] = mux2to1.module(a[31], b[31], sel);
        result[32] = mux2to1.module(a[32], b[32], sel); result[33] = mux2to1.module(a[33], b[33], sel);
        result[34] = mux2to1.module(a[34], b[34], sel); result[35] = mux2to1.module(a[35], b[35], sel);
        result[36] = mux2to1.module(a[36], b[36], sel); result[37] = mux2to1.module(a[37], b[37], sel);
        result[38] = mux2to1.module(a[38], b[38], sel); result[39] = mux2to1.module(a[39], b[39], sel);
        result[40] = mux2to1.module(a[40], b[40], sel); result[41] = mux2to1.module(a[41], b[41], sel);
        result[42] = mux2to1.module(a[42], b[42], sel); result[43] = mux2to1.module(a[43], b[43], sel);
        result[44] = mux2to1.module(a[44], b[44], sel); result[45] = mux2to1.module(a[45], b[45], sel);
        result[46] = mux2to1.module(a[46], b[46], sel); result[47] = mux2to1.module(a[47], b[47], sel);
        result[48] = mux2to1.module(a[48], b[48], sel); result[49] = mux2to1.module(a[49], b[49], sel);
        result[50] = mux2to1.module(a[50], b[50], sel); result[51] = mux2to1.module(a[51], b[51], sel);
        result[52] = mux2to1.module(a[52], b[52], sel); result[53] = mux2to1.module(a[53], b[53], sel);
        result[54] = mux2to1.module(a[54], b[54], sel); result[55] = mux2to1.module(a[55], b[55], sel);
        result[56] = mux2to1.module(a[56], b[56], sel); result[57] = mux2to1.module(a[57], b[57], sel);
        result[58] = mux2to1.module(a[58], b[58], sel); result[59] = mux2to1.module(a[59], b[59], sel);
        result[60] = mux2to1.module(a[60], b[60], sel); result[61] = mux2to1.module(a[61], b[61], sel);
        result[62] = mux2to1.module(a[62], b[62], sel); result[63] = mux2to1.module(a[63], b[63], sel);
        return result;
    }
}
