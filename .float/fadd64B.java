package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.*;

public class fadd64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean signA = a[63];
        boolean signB = b[63];
        
        boolean[] expA = extractExp64(a);
        boolean[] expB = extractExp64(b);
        
        boolean[] manA = extractMan64(a);
        boolean[] manB = extractMan64(b);
        
        boolean isZeroExpA = isAllZero11(expA);
        boolean isZeroExpB = isAllZero11(expB);
        boolean isAllOneExpA = isAllOne11(expA);
        boolean isAllOneExpB = isAllOne11(expB);
        boolean isZeroManA = isAllZero52(manA);
        boolean isZeroManB = isAllZero52(manB);
        
        boolean isZeroA = and.gate(isZeroExpA, isZeroManA);
        boolean isZeroB = and.gate(isZeroExpB, isZeroManB);
        boolean isInfA = and.gate(isAllOneExpA, isZeroManA);
        boolean isInfB = and.gate(isAllOneExpB, isZeroManB);
        boolean isNaNA = and.gate(isAllOneExpA, not.gate(isZeroManA));
        boolean isNaNB = and.gate(isAllOneExpB, not.gate(isZeroManB));
        
        boolean bothZero = and.gate(isZeroA, isZeroB);
        boolean eitherNaN = or.gate(isNaNA, isNaNB);
        boolean bothInf = and.gate(isInfA, isInfB);
        boolean infSignsDiffer = and.gate(bothInf, xor.gate(signA, signB));
        
        boolean[] nanResult = makeQuietNaN();
        boolean[] infResult = makeInf(mux2to1.module(signA, signB, isInfB));
        boolean zeroResultSign = and.gate(signA, signB);
        boolean[] zeroResult = makeZero(zeroResultSign);
        
        boolean hiddenA = not.gate(isZeroExpA);
        boolean hiddenB = not.gate(isZeroExpB);
        
        boolean[] manAExt = extendMan54(manA, hiddenA);
        boolean[] manBExt = extendMan54(manB, hiddenB);
        
        boolean[] expDiffA = sub11(expA, expB);
        boolean[] expDiffB = sub11(expB, expA);
        boolean expAGreater = not.gate(expDiffA[10]);
        
        boolean[] expBig = mux11(expB, expA, expAGreater);
        boolean[] expDiff = mux11(expDiffB, expDiffA, expAGreater);
        
        boolean[] manBig = mux54(manBExt, manAExt, expAGreater);
        boolean[] manSmall = mux54(manAExt, manBExt, expAGreater);
        boolean signBig = mux2to1.module(signB, signA, expAGreater);
        boolean signSmall = mux2to1.module(signA, signB, expAGreater);
        
        boolean[] shiftResult = shiftRight54ByAmountWithGRS(manSmall, expDiff);
        boolean[] manSmallShifted = new boolean[54];
        manSmallShifted[0] = shiftResult[0]; manSmallShifted[1] = shiftResult[1]; manSmallShifted[2] = shiftResult[2]; manSmallShifted[3] = shiftResult[3];
        manSmallShifted[4] = shiftResult[4]; manSmallShifted[5] = shiftResult[5]; manSmallShifted[6] = shiftResult[6]; manSmallShifted[7] = shiftResult[7];
        manSmallShifted[8] = shiftResult[8]; manSmallShifted[9] = shiftResult[9]; manSmallShifted[10] = shiftResult[10]; manSmallShifted[11] = shiftResult[11];
        manSmallShifted[12] = shiftResult[12]; manSmallShifted[13] = shiftResult[13]; manSmallShifted[14] = shiftResult[14]; manSmallShifted[15] = shiftResult[15];
        manSmallShifted[16] = shiftResult[16]; manSmallShifted[17] = shiftResult[17]; manSmallShifted[18] = shiftResult[18]; manSmallShifted[19] = shiftResult[19];
        manSmallShifted[20] = shiftResult[20]; manSmallShifted[21] = shiftResult[21]; manSmallShifted[22] = shiftResult[22]; manSmallShifted[23] = shiftResult[23];
        manSmallShifted[24] = shiftResult[24]; manSmallShifted[25] = shiftResult[25]; manSmallShifted[26] = shiftResult[26]; manSmallShifted[27] = shiftResult[27];
        manSmallShifted[28] = shiftResult[28]; manSmallShifted[29] = shiftResult[29]; manSmallShifted[30] = shiftResult[30]; manSmallShifted[31] = shiftResult[31];
        manSmallShifted[32] = shiftResult[32]; manSmallShifted[33] = shiftResult[33]; manSmallShifted[34] = shiftResult[34]; manSmallShifted[35] = shiftResult[35];
        manSmallShifted[36] = shiftResult[36]; manSmallShifted[37] = shiftResult[37]; manSmallShifted[38] = shiftResult[38]; manSmallShifted[39] = shiftResult[39];
        manSmallShifted[40] = shiftResult[40]; manSmallShifted[41] = shiftResult[41]; manSmallShifted[42] = shiftResult[42]; manSmallShifted[43] = shiftResult[43];
        manSmallShifted[44] = shiftResult[44]; manSmallShifted[45] = shiftResult[45]; manSmallShifted[46] = shiftResult[46]; manSmallShifted[47] = shiftResult[47];
        manSmallShifted[48] = shiftResult[48]; manSmallShifted[49] = shiftResult[49]; manSmallShifted[50] = shiftResult[50]; manSmallShifted[51] = shiftResult[51];
        manSmallShifted[52] = shiftResult[52]; manSmallShifted[53] = shiftResult[53];
        boolean guard = shiftResult[54];
        boolean round = shiftResult[55];
        boolean sticky = shiftResult[56];
        
        boolean signsDiffer = xor.gate(signBig, signSmall);
        
        boolean isTrueSubtraction = signsDiffer;
        
        boolean[] manAddResult = add54(manBig, manSmallShifted);
        boolean addCarry = manAddResult[53];
        
        boolean[] manSubAB = sub54(manBig, manSmallShifted);
        boolean[] manSubBA = sub54(manSmallShifted, manBig);
        boolean subBorrow = manSubAB[53];
        
        boolean[] manSubResult = mux54(manSubAB, manSubBA, subBorrow);
        boolean subResultZero = isAllZero54(manSubResult);
        
        boolean[] manResult = mux54(manAddResult, manSubResult, signsDiffer);
        boolean carryOut = mux2to1.module(addCarry, false, signsDiffer);
        
        boolean resultSign = xor.gate(signBig, and.gate(signsDiffer, subBorrow));
        boolean resultZero = and.gate(signsDiffer, subResultZero);
        
        boolean zeroSignSame = and.gate(signA, signB);
        boolean zeroSign = mux2to1.module(false, zeroSignSame, not.gate(signsDiffer));
        resultSign = mux2to1.module(resultSign, zeroSign, resultZero);
        
        boolean guardAfterCarry = mux2to1.module(guard, manResult[0], carryOut);
        boolean roundAfterCarry = mux2to1.module(round, guard, carryOut);
        boolean stickyAfterCarry = or.gate(sticky, and.gate(carryOut, or.gate(guard, round)));
        
        boolean[] manAfterCarry = mux54(manResult, shiftRight54By1(manResult), carryOut);
        boolean[] expAfterCarry = mux11(expBig, add11(expBig, makeOne11()), carryOut);
        
        boolean needNorm = and.gate(signsDiffer, not.gate(manAfterCarry[52]));
        boolean[] manNorm1 = mux54(manAfterCarry, shiftLeft54By1(manAfterCarry), needNorm);
        boolean[] expNorm1 = mux11(expAfterCarry, sub11(expAfterCarry, makeOne11()), needNorm);
        boolean guardNorm1 = mux2to1.module(guardAfterCarry, manAfterCarry[0], needNorm);
        boolean roundNorm1 = mux2to1.module(roundAfterCarry, guardAfterCarry, needNorm);
        boolean stickyNorm1 = or.gate(stickyAfterCarry, and.gate(needNorm, roundAfterCarry));
        
        boolean needNorm2 = and.gate(needNorm, not.gate(manNorm1[52]));
        boolean[] manNorm2 = mux54(manNorm1, shiftLeft54By1(manNorm1), needNorm2);
        boolean[] expNorm2 = mux11(expNorm1, sub11(expNorm1, makeOne11()), needNorm2);
        boolean guardNorm2 = mux2to1.module(guardNorm1, manNorm1[0], needNorm2);
        boolean roundNorm2 = mux2to1.module(roundNorm1, guardNorm1, needNorm2);
        boolean stickyNorm2 = or.gate(stickyNorm1, and.gate(needNorm2, roundNorm1));
        
        boolean needNorm3 = and.gate(needNorm2, not.gate(manNorm2[52]));
        boolean[] manNorm3 = mux54(manNorm2, shiftLeft54By1(manNorm2), needNorm3);
        boolean[] expNorm3 = mux11(expNorm2, sub11(expNorm2, makeOne11()), needNorm3);
        boolean guardNorm3 = mux2to1.module(guardNorm2, manNorm2[0], needNorm3);
        boolean roundNorm3 = mux2to1.module(roundNorm2, guardNorm2, needNorm3);
        boolean stickyNorm3 = or.gate(stickyNorm2, and.gate(needNorm3, roundNorm2));
        
        boolean needNorm4 = and.gate(needNorm3, not.gate(manNorm3[52]));
        boolean[] manNorm4 = mux54(manNorm3, shiftLeft54By1(manNorm3), needNorm4);
        boolean[] expNorm4 = mux11(expNorm3, sub11(expNorm3, makeOne11()), needNorm4);
        boolean guardNorm4 = mux2to1.module(guardNorm3, manNorm3[0], needNorm4);
        boolean roundNorm4 = mux2to1.module(roundNorm3, guardNorm3, needNorm4);
        boolean stickyNorm4 = or.gate(stickyNorm3, and.gate(needNorm4, roundNorm3));
        
        boolean needNorm5 = and.gate(needNorm4, not.gate(manNorm4[52]));
        boolean[] manFinal = mux54(manNorm4, shiftLeft54By1(manNorm4), needNorm5);
        boolean[] expFinal = mux11(expNorm4, sub11(expNorm4, makeOne11()), needNorm5);
        boolean guardFinal = mux2to1.module(guardNorm4, manNorm4[0], needNorm5);
        boolean roundFinal = mux2to1.module(roundNorm4, guardNorm4, needNorm5);
        boolean stickyFinal = or.gate(stickyNorm4, and.gate(needNorm5, roundNorm4));
        
        boolean[] manRounded = roundMan54(manFinal, guardFinal, roundFinal, stickyFinal, isTrueSubtraction);
        boolean roundCarry = manRounded[53];
        
        boolean[] manAfterRound = mux54(manRounded, shiftRight54By1(manRounded), roundCarry);
        boolean[] expAfterRound = mux11(expFinal, add11(expFinal, makeOne11()), roundCarry);
        
        boolean[] finalMan = extractMan52From54(manAfterRound);
        
        boolean[] normalResult = assembleFloat(resultSign, expAfterRound, finalMan);
        boolean[] signedZeroResult = makeZero(resultSign);
        
        boolean[] result4 = mux64(normalResult, signedZeroResult, resultZero);
        
        boolean returnNaN = or.gate(eitherNaN, infSignsDiffer);
        boolean returnInf = and.gate(not.gate(returnNaN), or.gate(isInfA, isInfB));
        boolean returnZero = and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), bothZero);
        boolean returnA = and.gate(and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), isZeroB), not.gate(isZeroA));
        boolean returnB = and.gate(and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), isZeroA), not.gate(isZeroB));
        
        boolean[] result1 = mux64(result4, nanResult, returnNaN);
        boolean[] result2 = mux64(result1, infResult, returnInf);
        boolean[] result3 = mux64(result2, zeroResult, returnZero);
        boolean[] result5 = mux64(result3, a, returnA);
        boolean[] result6 = mux64(result5, b, returnB);
        
        return result6;
    }
    
    private static boolean[] roundMan54(boolean[] man, boolean g, boolean r, boolean s, boolean isSubtraction) {
        boolean tie = and.gate(g, and.gate(not.gate(r), not.gate(s)));
        boolean lsb = man[0];
        
        boolean roundUpAdd = or.gate(and.gate(g, r), and.gate(g, s));
        boolean roundUpTieAdd = and.gate(tie, lsb);
        boolean doRoundAdd = or.gate(roundUpAdd, roundUpTieAdd);
        
        boolean hasExtra = or.gate(or.gate(g, r), s);
        boolean greaterThanHalf = or.gate(r, s);
        
        boolean roundDownSub = and.gate(isSubtraction, and.gate(hasExtra, greaterThanHalf));
        
        boolean[] one = new boolean[54];
        one[0] = true;
        boolean[] manPlusOne = add54(man, one);
        boolean[] manMinusOne = sub54(man, one);
        
        boolean[] resultAdd = mux54(man, manPlusOne, doRoundAdd);
        boolean[] resultSub = mux54(man, manMinusOne, roundDownSub);
        
        return mux54(resultAdd, resultSub, isSubtraction);
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
    
    private static boolean isAllZero11(boolean[] arr) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(not.gate(arr[0]), not.gate(arr[1])), not.gate(arr[2])), not.gate(arr[3])),
            not.gate(arr[4])), not.gate(arr[5])), not.gate(arr[6])), not.gate(arr[7])),
            not.gate(arr[8])), not.gate(arr[9])), not.gate(arr[10]));
    }
    
    private static boolean isAllZero52(boolean[] arr) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(
            not.gate(arr[0]), not.gate(arr[1])), not.gate(arr[2])), not.gate(arr[3])),
            not.gate(arr[4])), not.gate(arr[5])), not.gate(arr[6])), not.gate(arr[7])),
            not.gate(arr[8])), not.gate(arr[9])), not.gate(arr[10])), not.gate(arr[11])),
            not.gate(arr[12])), not.gate(arr[13])), not.gate(arr[14])), not.gate(arr[15])),
            not.gate(arr[16])), not.gate(arr[17])), not.gate(arr[18])), not.gate(arr[19])),
            not.gate(arr[20])), not.gate(arr[21])), not.gate(arr[22])), not.gate(arr[23])),
            not.gate(arr[24])), not.gate(arr[25])), not.gate(arr[26])), not.gate(arr[27])),
            not.gate(arr[28])), not.gate(arr[29])), not.gate(arr[30])), not.gate(arr[31])),
            not.gate(arr[32])), not.gate(arr[33])), not.gate(arr[34])), not.gate(arr[35])),
            not.gate(arr[36])), not.gate(arr[37])), not.gate(arr[38])), not.gate(arr[39])),
            not.gate(arr[40])), not.gate(arr[41])), not.gate(arr[42])), not.gate(arr[43])),
            not.gate(arr[44])), not.gate(arr[45])), not.gate(arr[46])), not.gate(arr[47])),
            not.gate(arr[48])), not.gate(arr[49])), not.gate(arr[50])), not.gate(arr[51]));
    }
    
    private static boolean isAllZero54(boolean[] arr) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(and.gate(and.gate(and.gate(
            not.gate(arr[0]), not.gate(arr[1])), not.gate(arr[2])), not.gate(arr[3])),
            not.gate(arr[4])), not.gate(arr[5])), not.gate(arr[6])), not.gate(arr[7])),
            not.gate(arr[8])), not.gate(arr[9])), not.gate(arr[10])), not.gate(arr[11])),
            not.gate(arr[12])), not.gate(arr[13])), not.gate(arr[14])), not.gate(arr[15])),
            not.gate(arr[16])), not.gate(arr[17])), not.gate(arr[18])), not.gate(arr[19])),
            not.gate(arr[20])), not.gate(arr[21])), not.gate(arr[22])), not.gate(arr[23])),
            not.gate(arr[24])), not.gate(arr[25])), not.gate(arr[26])), not.gate(arr[27])),
            not.gate(arr[28])), not.gate(arr[29])), not.gate(arr[30])), not.gate(arr[31])),
            not.gate(arr[32])), not.gate(arr[33])), not.gate(arr[34])), not.gate(arr[35])),
            not.gate(arr[36])), not.gate(arr[37])), not.gate(arr[38])), not.gate(arr[39])),
            not.gate(arr[40])), not.gate(arr[41])), not.gate(arr[42])), not.gate(arr[43])),
            not.gate(arr[44])), not.gate(arr[45])), not.gate(arr[46])), not.gate(arr[47])),
            not.gate(arr[48])), not.gate(arr[49])), not.gate(arr[50])), not.gate(arr[51])),
            not.gate(arr[52])), not.gate(arr[53]));
    }
    
    private static boolean isAllZero53(boolean[] arr) {
        boolean z0 = not.gate(arr[0]); boolean z1 = not.gate(arr[1]); boolean z2 = not.gate(arr[2]);
        boolean z3 = not.gate(arr[3]); boolean z4 = not.gate(arr[4]); boolean z5 = not.gate(arr[5]);
        boolean z6 = not.gate(arr[6]); boolean z7 = not.gate(arr[7]); boolean z8 = not.gate(arr[8]);
        boolean z9 = not.gate(arr[9]); boolean z10 = not.gate(arr[10]); boolean z11 = not.gate(arr[11]);
        boolean z12 = not.gate(arr[12]); boolean z13 = not.gate(arr[13]); boolean z14 = not.gate(arr[14]);
        boolean z15 = not.gate(arr[15]); boolean z16 = not.gate(arr[16]); boolean z17 = not.gate(arr[17]);
        boolean z18 = not.gate(arr[18]); boolean z19 = not.gate(arr[19]); boolean z20 = not.gate(arr[20]);
        boolean z21 = not.gate(arr[21]); boolean z22 = not.gate(arr[22]); boolean z23 = not.gate(arr[23]);
        boolean z24 = not.gate(arr[24]); boolean z25 = not.gate(arr[25]); boolean z26 = not.gate(arr[26]);
        boolean z27 = not.gate(arr[27]); boolean z28 = not.gate(arr[28]); boolean z29 = not.gate(arr[29]);
        boolean z30 = not.gate(arr[30]); boolean z31 = not.gate(arr[31]); boolean z32 = not.gate(arr[32]);
        boolean z33 = not.gate(arr[33]); boolean z34 = not.gate(arr[34]); boolean z35 = not.gate(arr[35]);
        boolean z36 = not.gate(arr[36]); boolean z37 = not.gate(arr[37]); boolean z38 = not.gate(arr[38]);
        boolean z39 = not.gate(arr[39]); boolean z40 = not.gate(arr[40]); boolean z41 = not.gate(arr[41]);
        boolean z42 = not.gate(arr[42]); boolean z43 = not.gate(arr[43]); boolean z44 = not.gate(arr[44]);
        boolean z45 = not.gate(arr[45]); boolean z46 = not.gate(arr[46]); boolean z47 = not.gate(arr[47]);
        boolean z48 = not.gate(arr[48]); boolean z49 = not.gate(arr[49]); boolean z50 = not.gate(arr[50]);
        boolean z51 = not.gate(arr[51]); boolean z52 = not.gate(arr[52]);
        boolean s0 = and.gate(z0, z1); boolean s1 = and.gate(s0, z2); boolean s2 = and.gate(s1, z3);
        boolean s3 = and.gate(s2, z4); boolean s4 = and.gate(s3, z5); boolean s5 = and.gate(s4, z6);
        boolean s6 = and.gate(s5, z7); boolean s7 = and.gate(s6, z8); boolean s8 = and.gate(s7, z9);
        boolean s9 = and.gate(s8, z10); boolean s10 = and.gate(s9, z11); boolean s11 = and.gate(s10, z12);
        boolean s12 = and.gate(s11, z13); boolean s13 = and.gate(s12, z14); boolean s14 = and.gate(s13, z15);
        boolean s15 = and.gate(s14, z16); boolean s16 = and.gate(s15, z17); boolean s17 = and.gate(s16, z18);
        boolean s18 = and.gate(s17, z19); boolean s19 = and.gate(s18, z20); boolean s20 = and.gate(s19, z21);
        boolean s21 = and.gate(s20, z22); boolean s22 = and.gate(s21, z23); boolean s23 = and.gate(s22, z24);
        boolean s24 = and.gate(s23, z25); boolean s25 = and.gate(s24, z26); boolean s26 = and.gate(s25, z27);
        boolean s27 = and.gate(s26, z28); boolean s28 = and.gate(s27, z29); boolean s29 = and.gate(s28, z30);
        boolean s30 = and.gate(s29, z31); boolean s31 = and.gate(s30, z32); boolean s32 = and.gate(s31, z33);
        boolean s33 = and.gate(s32, z34); boolean s34 = and.gate(s33, z35); boolean s35 = and.gate(s34, z36);
        boolean s36 = and.gate(s35, z37); boolean s37 = and.gate(s36, z38); boolean s38 = and.gate(s37, z39);
        boolean s39 = and.gate(s38, z40); boolean s40 = and.gate(s39, z41); boolean s41 = and.gate(s40, z42);
        boolean s42 = and.gate(s41, z43); boolean s43 = and.gate(s42, z44); boolean s44 = and.gate(s43, z45);
        boolean s45 = and.gate(s44, z46); boolean s46 = and.gate(s45, z47); boolean s47 = and.gate(s46, z48);
        boolean s48 = and.gate(s47, z49); boolean s49 = and.gate(s48, z50); boolean s50 = and.gate(s49, z51);
        boolean s51 = and.gate(s50, z52);
        return s51;
    }
    
    private static boolean isAllOne11(boolean[] arr) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(arr[0], arr[1]), arr[2]), arr[3]),
            arr[4]), arr[5]), arr[6]), arr[7]), arr[8]), arr[9]), arr[10]);
    }
    
    private static boolean[] makeQuietNaN() {
        boolean[] result = new boolean[64];
        result[51] = true; result[52] = true; result[53] = true; result[54] = true;
        result[55] = true; result[56] = true; result[57] = true; result[58] = true;
        result[59] = true; result[60] = true; result[61] = true; result[62] = true;
        return result;
    }
    
    private static boolean[] makeInf(boolean sign) {
        boolean[] result = new boolean[64];
        result[52] = true; result[53] = true; result[54] = true; result[55] = true;
        result[56] = true; result[57] = true; result[58] = true; result[59] = true;
        result[60] = true; result[61] = true; result[62] = true;
        result[63] = sign;
        return result;
    }
    
    private static boolean[] makeZero(boolean sign) {
        boolean[] result = new boolean[64];
        result[63] = sign;
        return result;
    }
    
    private static boolean[] makeOne11() {
        boolean[] result = new boolean[11];
        result[0] = true;
        return result;
    }
    
    private static boolean[] extendMan54(boolean[] man, boolean hiddenBit) {
        boolean[] result = new boolean[54];
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
        result[52] = hiddenBit;
        result[53] = false;
        return result;
    }
    
    private static boolean[] extractMan52From54(boolean[] man54) {
        boolean[] result = new boolean[52];
        result[0] = man54[0]; result[1] = man54[1]; result[2] = man54[2]; result[3] = man54[3];
        result[4] = man54[4]; result[5] = man54[5]; result[6] = man54[6]; result[7] = man54[7];
        result[8] = man54[8]; result[9] = man54[9]; result[10] = man54[10]; result[11] = man54[11];
        result[12] = man54[12]; result[13] = man54[13]; result[14] = man54[14]; result[15] = man54[15];
        result[16] = man54[16]; result[17] = man54[17]; result[18] = man54[18]; result[19] = man54[19];
        result[20] = man54[20]; result[21] = man54[21]; result[22] = man54[22]; result[23] = man54[23];
        result[24] = man54[24]; result[25] = man54[25]; result[26] = man54[26]; result[27] = man54[27];
        result[28] = man54[28]; result[29] = man54[29]; result[30] = man54[30]; result[31] = man54[31];
        result[32] = man54[32]; result[33] = man54[33]; result[34] = man54[34]; result[35] = man54[35];
        result[36] = man54[36]; result[37] = man54[37]; result[38] = man54[38]; result[39] = man54[39];
        result[40] = man54[40]; result[41] = man54[41]; result[42] = man54[42]; result[43] = man54[43];
        result[44] = man54[44]; result[45] = man54[45]; result[46] = man54[46]; result[47] = man54[47];
        result[48] = man54[48]; result[49] = man54[49]; result[50] = man54[50]; result[51] = man54[51];
        return result;
    }
    
    private static boolean[] add54(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[54];
        boolean carry = false;
        boolean[] s;
        s = fullAdder(a[0], b[0], carry); result[0] = s[0]; carry = s[1];
        s = fullAdder(a[1], b[1], carry); result[1] = s[0]; carry = s[1];
        s = fullAdder(a[2], b[2], carry); result[2] = s[0]; carry = s[1];
        s = fullAdder(a[3], b[3], carry); result[3] = s[0]; carry = s[1];
        s = fullAdder(a[4], b[4], carry); result[4] = s[0]; carry = s[1];
        s = fullAdder(a[5], b[5], carry); result[5] = s[0]; carry = s[1];
        s = fullAdder(a[6], b[6], carry); result[6] = s[0]; carry = s[1];
        s = fullAdder(a[7], b[7], carry); result[7] = s[0]; carry = s[1];
        s = fullAdder(a[8], b[8], carry); result[8] = s[0]; carry = s[1];
        s = fullAdder(a[9], b[9], carry); result[9] = s[0]; carry = s[1];
        s = fullAdder(a[10], b[10], carry); result[10] = s[0]; carry = s[1];
        s = fullAdder(a[11], b[11], carry); result[11] = s[0]; carry = s[1];
        s = fullAdder(a[12], b[12], carry); result[12] = s[0]; carry = s[1];
        s = fullAdder(a[13], b[13], carry); result[13] = s[0]; carry = s[1];
        s = fullAdder(a[14], b[14], carry); result[14] = s[0]; carry = s[1];
        s = fullAdder(a[15], b[15], carry); result[15] = s[0]; carry = s[1];
        s = fullAdder(a[16], b[16], carry); result[16] = s[0]; carry = s[1];
        s = fullAdder(a[17], b[17], carry); result[17] = s[0]; carry = s[1];
        s = fullAdder(a[18], b[18], carry); result[18] = s[0]; carry = s[1];
        s = fullAdder(a[19], b[19], carry); result[19] = s[0]; carry = s[1];
        s = fullAdder(a[20], b[20], carry); result[20] = s[0]; carry = s[1];
        s = fullAdder(a[21], b[21], carry); result[21] = s[0]; carry = s[1];
        s = fullAdder(a[22], b[22], carry); result[22] = s[0]; carry = s[1];
        s = fullAdder(a[23], b[23], carry); result[23] = s[0]; carry = s[1];
        s = fullAdder(a[24], b[24], carry); result[24] = s[0]; carry = s[1];
        s = fullAdder(a[25], b[25], carry); result[25] = s[0]; carry = s[1];
        s = fullAdder(a[26], b[26], carry); result[26] = s[0]; carry = s[1];
        s = fullAdder(a[27], b[27], carry); result[27] = s[0]; carry = s[1];
        s = fullAdder(a[28], b[28], carry); result[28] = s[0]; carry = s[1];
        s = fullAdder(a[29], b[29], carry); result[29] = s[0]; carry = s[1];
        s = fullAdder(a[30], b[30], carry); result[30] = s[0]; carry = s[1];
        s = fullAdder(a[31], b[31], carry); result[31] = s[0]; carry = s[1];
        s = fullAdder(a[32], b[32], carry); result[32] = s[0]; carry = s[1];
        s = fullAdder(a[33], b[33], carry); result[33] = s[0]; carry = s[1];
        s = fullAdder(a[34], b[34], carry); result[34] = s[0]; carry = s[1];
        s = fullAdder(a[35], b[35], carry); result[35] = s[0]; carry = s[1];
        s = fullAdder(a[36], b[36], carry); result[36] = s[0]; carry = s[1];
        s = fullAdder(a[37], b[37], carry); result[37] = s[0]; carry = s[1];
        s = fullAdder(a[38], b[38], carry); result[38] = s[0]; carry = s[1];
        s = fullAdder(a[39], b[39], carry); result[39] = s[0]; carry = s[1];
        s = fullAdder(a[40], b[40], carry); result[40] = s[0]; carry = s[1];
        s = fullAdder(a[41], b[41], carry); result[41] = s[0]; carry = s[1];
        s = fullAdder(a[42], b[42], carry); result[42] = s[0]; carry = s[1];
        s = fullAdder(a[43], b[43], carry); result[43] = s[0]; carry = s[1];
        s = fullAdder(a[44], b[44], carry); result[44] = s[0]; carry = s[1];
        s = fullAdder(a[45], b[45], carry); result[45] = s[0]; carry = s[1];
        s = fullAdder(a[46], b[46], carry); result[46] = s[0]; carry = s[1];
        s = fullAdder(a[47], b[47], carry); result[47] = s[0]; carry = s[1];
        s = fullAdder(a[48], b[48], carry); result[48] = s[0]; carry = s[1];
        s = fullAdder(a[49], b[49], carry); result[49] = s[0]; carry = s[1];
        s = fullAdder(a[50], b[50], carry); result[50] = s[0]; carry = s[1];
        s = fullAdder(a[51], b[51], carry); result[51] = s[0]; carry = s[1];
        s = fullAdder(a[52], b[52], carry); result[52] = s[0]; carry = s[1];
        s = fullAdder(a[53], b[53], carry); result[53] = s[0]; carry = s[1];
        return result;
    }
    
    private static boolean[] sub54(boolean[] a, boolean[] b) {
        return add54(a, negate54(b));
    }
    
    private static boolean[] negate54(boolean[] a) {
        boolean[] one = new boolean[54];
        one[0] = true;
        boolean[] notA = new boolean[54];
        notA[0] = not.gate(a[0]); notA[1] = not.gate(a[1]); notA[2] = not.gate(a[2]); notA[3] = not.gate(a[3]);
        notA[4] = not.gate(a[4]); notA[5] = not.gate(a[5]); notA[6] = not.gate(a[6]); notA[7] = not.gate(a[7]);
        notA[8] = not.gate(a[8]); notA[9] = not.gate(a[9]); notA[10] = not.gate(a[10]); notA[11] = not.gate(a[11]);
        notA[12] = not.gate(a[12]); notA[13] = not.gate(a[13]); notA[14] = not.gate(a[14]); notA[15] = not.gate(a[15]);
        notA[16] = not.gate(a[16]); notA[17] = not.gate(a[17]); notA[18] = not.gate(a[18]); notA[19] = not.gate(a[19]);
        notA[20] = not.gate(a[20]); notA[21] = not.gate(a[21]); notA[22] = not.gate(a[22]); notA[23] = not.gate(a[23]);
        notA[24] = not.gate(a[24]); notA[25] = not.gate(a[25]); notA[26] = not.gate(a[26]); notA[27] = not.gate(a[27]);
        notA[28] = not.gate(a[28]); notA[29] = not.gate(a[29]); notA[30] = not.gate(a[30]); notA[31] = not.gate(a[31]);
        notA[32] = not.gate(a[32]); notA[33] = not.gate(a[33]); notA[34] = not.gate(a[34]); notA[35] = not.gate(a[35]);
        notA[36] = not.gate(a[36]); notA[37] = not.gate(a[37]); notA[38] = not.gate(a[38]); notA[39] = not.gate(a[39]);
        notA[40] = not.gate(a[40]); notA[41] = not.gate(a[41]); notA[42] = not.gate(a[42]); notA[43] = not.gate(a[43]);
        notA[44] = not.gate(a[44]); notA[45] = not.gate(a[45]); notA[46] = not.gate(a[46]); notA[47] = not.gate(a[47]);
        notA[48] = not.gate(a[48]); notA[49] = not.gate(a[49]); notA[50] = not.gate(a[50]); notA[51] = not.gate(a[51]);
        notA[52] = not.gate(a[52]); notA[53] = not.gate(a[53]);
        return add54(notA, one);
    }
    
    private static boolean[] fullAdder(boolean a, boolean b, boolean cin) {
        boolean[] result = new boolean[2];
        boolean axorb = xor.gate(a, b);
        result[0] = xor.gate(axorb, cin);
        result[1] = or.gate(and.gate(a, b), and.gate(axorb, cin));
        return result;
    }
    
    private static boolean[] shiftRight54By1(boolean[] a) {
        boolean[] result = new boolean[54];
        result[0] = a[1]; result[1] = a[2]; result[2] = a[3]; result[3] = a[4];
        result[4] = a[5]; result[5] = a[6]; result[6] = a[7]; result[7] = a[8];
        result[8] = a[9]; result[9] = a[10]; result[10] = a[11]; result[11] = a[12];
        result[12] = a[13]; result[13] = a[14]; result[14] = a[15]; result[15] = a[16];
        result[16] = a[17]; result[17] = a[18]; result[18] = a[19]; result[19] = a[20];
        result[20] = a[21]; result[21] = a[22]; result[22] = a[23]; result[23] = a[24];
        result[24] = a[25]; result[25] = a[26]; result[26] = a[27]; result[27] = a[28];
        result[28] = a[29]; result[29] = a[30]; result[30] = a[31]; result[31] = a[32];
        result[32] = a[33]; result[33] = a[34]; result[34] = a[35]; result[35] = a[36];
        result[36] = a[37]; result[37] = a[38]; result[38] = a[39]; result[39] = a[40];
        result[40] = a[41]; result[41] = a[42]; result[42] = a[43]; result[43] = a[44];
        result[44] = a[45]; result[45] = a[46]; result[46] = a[47]; result[47] = a[48];
        result[48] = a[49]; result[49] = a[50]; result[50] = a[51]; result[51] = a[52];
        result[52] = a[53];
        result[53] = false;
        return result;
    }
    
    private static boolean[] shiftLeft54By1(boolean[] a) {
        boolean[] result = new boolean[54];
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
        result[53] = a[52];
        return result;
    }
    
    private static boolean[] shiftRight54By16(boolean[] a) {
        boolean[] result = new boolean[54];
        result[0] = a[16]; result[1] = a[17]; result[2] = a[18]; result[3] = a[19];
        result[4] = a[20]; result[5] = a[21]; result[6] = a[22]; result[7] = a[23];
        result[8] = a[24]; result[9] = a[25]; result[10] = a[26]; result[11] = a[27];
        result[12] = a[28]; result[13] = a[29]; result[14] = a[30]; result[15] = a[31];
        result[16] = a[32]; result[17] = a[33]; result[18] = a[34]; result[19] = a[35];
        result[20] = a[36]; result[21] = a[37]; result[22] = a[38]; result[23] = a[39];
        result[24] = a[40]; result[25] = a[41]; result[26] = a[42]; result[27] = a[43];
        result[28] = a[44]; result[29] = a[45]; result[30] = a[46]; result[31] = a[47];
        result[32] = a[48]; result[33] = a[49]; result[34] = a[50]; result[35] = a[51];
        result[36] = a[52]; result[37] = a[53];
        result[38] = false; result[39] = false; result[40] = false; result[41] = false;
        result[42] = false; result[43] = false; result[44] = false; result[45] = false;
        result[46] = false; result[47] = false; result[48] = false; result[49] = false;
        result[50] = false; result[51] = false; result[52] = false; result[53] = false;
        return result;
    }
    
    private static boolean[] shiftRight54ByAmountWithGRS(boolean[] a, boolean[] amount) {
        boolean[] result = new boolean[57];
        result[0] = a[0]; result[1] = a[1]; result[2] = a[2]; result[3] = a[3];
        result[4] = a[4]; result[5] = a[5]; result[6] = a[6]; result[7] = a[7];
        result[8] = a[8]; result[9] = a[9]; result[10] = a[10]; result[11] = a[11];
        result[12] = a[12]; result[13] = a[13]; result[14] = a[14]; result[15] = a[15];
        result[16] = a[16]; result[17] = a[17]; result[18] = a[18]; result[19] = a[19];
        result[20] = a[20]; result[21] = a[21]; result[22] = a[22]; result[23] = a[23];
        result[24] = a[24]; result[25] = a[25]; result[26] = a[26]; result[27] = a[27];
        result[28] = a[28]; result[29] = a[29]; result[30] = a[30]; result[31] = a[31];
        result[32] = a[32]; result[33] = a[33]; result[34] = a[34]; result[35] = a[35];
        result[36] = a[36]; result[37] = a[37]; result[38] = a[38]; result[39] = a[39];
        result[40] = a[40]; result[41] = a[41]; result[42] = a[42]; result[43] = a[43];
        result[44] = a[44]; result[45] = a[45]; result[46] = a[46]; result[47] = a[47];
        result[48] = a[48]; result[49] = a[49]; result[50] = a[50]; result[51] = a[51];
        result[52] = a[52]; result[53] = a[53];
        result[54] = false;
        result[55] = false;
        result[56] = false;
        
        result = mux57(result, shiftRight57By1WithGRS(result), amount[0]);
        result = mux57(result, shiftRight57By2WithGRS(result), amount[1]);
        result = mux57(result, shiftRight57By4WithGRS(result), amount[2]);
        result = mux57(result, shiftRight57By8WithGRS(result), amount[3]);
        result = mux57(result, shiftRight57By16WithGRS(result), amount[4]);
        result = mux57(result, shiftRight57By32WithGRS(result), amount[5]);
        result = mux57(result, shiftRight57By64WithGRS(result), amount[6]);
        result = mux57(result, shiftRight57By128WithGRS(result), amount[7]);
        result = mux57(result, shiftRight57By256WithGRS(result), amount[8]);
        result = mux57(result, shiftRight57By512WithGRS(result), amount[9]);
        result = mux57(result, shiftRight57By1024WithGRS(result), amount[10]);
        
        return result;
    }
    
    private static boolean[] shiftRight57By128WithGRS(boolean[] a) {
        boolean[] r = shiftRight57By64WithGRS(a);
        return shiftRight57By64WithGRS(r);
    }
    
    private static boolean[] shiftRight57By256WithGRS(boolean[] a) {
        boolean[] r = shiftRight57By128WithGRS(a);
        return shiftRight57By128WithGRS(r);
    }
    
    private static boolean[] shiftRight57By512WithGRS(boolean[] a) {
        boolean[] r = shiftRight57By256WithGRS(a);
        return shiftRight57By256WithGRS(r);
    }
    
    private static boolean[] shiftRight57By1024WithGRS(boolean[] a) {
        boolean[] r = shiftRight57By512WithGRS(a);
        return shiftRight57By512WithGRS(r);
    }
    
    private static boolean[] shiftRight57By1WithGRS(boolean[] a) {
        boolean[] result = new boolean[57];
        result[0] = a[1]; result[1] = a[2]; result[2] = a[3]; result[3] = a[4];
        result[4] = a[5]; result[5] = a[6]; result[6] = a[7]; result[7] = a[8];
        result[8] = a[9]; result[9] = a[10]; result[10] = a[11]; result[11] = a[12];
        result[12] = a[13]; result[13] = a[14]; result[14] = a[15]; result[15] = a[16];
        result[16] = a[17]; result[17] = a[18]; result[18] = a[19]; result[19] = a[20];
        result[20] = a[21]; result[21] = a[22]; result[22] = a[23]; result[23] = a[24];
        result[24] = a[25]; result[25] = a[26]; result[26] = a[27]; result[27] = a[28];
        result[28] = a[29]; result[29] = a[30]; result[30] = a[31]; result[31] = a[32];
        result[32] = a[33]; result[33] = a[34]; result[34] = a[35]; result[35] = a[36];
        result[36] = a[37]; result[37] = a[38]; result[38] = a[39]; result[39] = a[40];
        result[40] = a[41]; result[41] = a[42]; result[42] = a[43]; result[43] = a[44];
        result[44] = a[45]; result[45] = a[46]; result[46] = a[47]; result[47] = a[48];
        result[48] = a[49]; result[49] = a[50]; result[50] = a[51]; result[51] = a[52];
        result[52] = a[53];
        result[53] = false;
        result[54] = a[0];
        result[55] = a[54];
        result[56] = or.gate(or.gate(a[55], a[56]), a[0]);
        return result;
    }
    
    private static boolean[] shiftRight57By2WithGRS(boolean[] a) {
        boolean[] result = new boolean[57];
        result[0] = a[2]; result[1] = a[3]; result[2] = a[4]; result[3] = a[5];
        result[4] = a[6]; result[5] = a[7]; result[6] = a[8]; result[7] = a[9];
        result[8] = a[10]; result[9] = a[11]; result[10] = a[12]; result[11] = a[13];
        result[12] = a[14]; result[13] = a[15]; result[14] = a[16]; result[15] = a[17];
        result[16] = a[18]; result[17] = a[19]; result[18] = a[20]; result[19] = a[21];
        result[20] = a[22]; result[21] = a[23]; result[22] = a[24]; result[23] = a[25];
        result[24] = a[26]; result[25] = a[27]; result[26] = a[28]; result[27] = a[29];
        result[28] = a[30]; result[29] = a[31]; result[30] = a[32]; result[31] = a[33];
        result[32] = a[34]; result[33] = a[35]; result[34] = a[36]; result[35] = a[37];
        result[36] = a[38]; result[37] = a[39]; result[38] = a[40]; result[39] = a[41];
        result[40] = a[42]; result[41] = a[43]; result[42] = a[44]; result[43] = a[45];
        result[44] = a[46]; result[45] = a[47]; result[46] = a[48]; result[47] = a[49];
        result[48] = a[50]; result[49] = a[51]; result[50] = a[52]; result[51] = a[53];
        result[52] = false;
        result[53] = false;
        result[54] = a[1];
        result[55] = a[0];
        result[56] = or.gate(a[54], or.gate(a[55], a[56]));
        return result;
    }
    
    private static boolean[] shiftRight57By4WithGRS(boolean[] a) {
        boolean[] r = a;
        r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r);
        return shiftRight57By1WithGRS(r);
    }
    
    private static boolean[] shiftRight57By8WithGRS(boolean[] a) {
        boolean[] r = a;
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        return r;
    }
    
    private static boolean[] shiftRight57By16WithGRS(boolean[] a) {
        boolean[] r = a;
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        return r;
    }
    
    private static boolean[] shiftRight57By32WithGRS(boolean[] a) {
        boolean[] r = a;
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        return r;
    }
    
    private static boolean[] shiftRight57By64WithGRS(boolean[] a) {
        boolean[] r = a;
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r); r = shiftRight57By1WithGRS(r);
        return r;
    }
    
    private static boolean[] mux57(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[57];
        result[0] = mux2to1.module(a[0], b[0], sel); result[1] = mux2to1.module(a[1], b[1], sel); result[2] = mux2to1.module(a[2], b[2], sel); result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel); result[5] = mux2to1.module(a[5], b[5], sel); result[6] = mux2to1.module(a[6], b[6], sel); result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel); result[9] = mux2to1.module(a[9], b[9], sel); result[10] = mux2to1.module(a[10], b[10], sel); result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel); result[13] = mux2to1.module(a[13], b[13], sel); result[14] = mux2to1.module(a[14], b[14], sel); result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel); result[17] = mux2to1.module(a[17], b[17], sel); result[18] = mux2to1.module(a[18], b[18], sel); result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel); result[21] = mux2to1.module(a[21], b[21], sel); result[22] = mux2to1.module(a[22], b[22], sel); result[23] = mux2to1.module(a[23], b[23], sel);
        result[24] = mux2to1.module(a[24], b[24], sel); result[25] = mux2to1.module(a[25], b[25], sel); result[26] = mux2to1.module(a[26], b[26], sel); result[27] = mux2to1.module(a[27], b[27], sel);
        result[28] = mux2to1.module(a[28], b[28], sel); result[29] = mux2to1.module(a[29], b[29], sel); result[30] = mux2to1.module(a[30], b[30], sel); result[31] = mux2to1.module(a[31], b[31], sel);
        result[32] = mux2to1.module(a[32], b[32], sel); result[33] = mux2to1.module(a[33], b[33], sel); result[34] = mux2to1.module(a[34], b[34], sel); result[35] = mux2to1.module(a[35], b[35], sel);
        result[36] = mux2to1.module(a[36], b[36], sel); result[37] = mux2to1.module(a[37], b[37], sel); result[38] = mux2to1.module(a[38], b[38], sel); result[39] = mux2to1.module(a[39], b[39], sel);
        result[40] = mux2to1.module(a[40], b[40], sel); result[41] = mux2to1.module(a[41], b[41], sel); result[42] = mux2to1.module(a[42], b[42], sel); result[43] = mux2to1.module(a[43], b[43], sel);
        result[44] = mux2to1.module(a[44], b[44], sel); result[45] = mux2to1.module(a[45], b[45], sel); result[46] = mux2to1.module(a[46], b[46], sel); result[47] = mux2to1.module(a[47], b[47], sel);
        result[48] = mux2to1.module(a[48], b[48], sel); result[49] = mux2to1.module(a[49], b[49], sel); result[50] = mux2to1.module(a[50], b[50], sel); result[51] = mux2to1.module(a[51], b[51], sel);
        result[52] = mux2to1.module(a[52], b[52], sel); result[53] = mux2to1.module(a[53], b[53], sel); result[54] = mux2to1.module(a[54], b[54], sel); result[55] = mux2to1.module(a[55], b[55], sel);
        result[56] = mux2to1.module(a[56], b[56], sel);
        return result;
    }
    
    private static boolean[] add11(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[11];
        boolean carry = false;
        boolean[] s;
        s = fullAdder(a[0], b[0], carry); result[0] = s[0]; carry = s[1];
        s = fullAdder(a[1], b[1], carry); result[1] = s[0]; carry = s[1];
        s = fullAdder(a[2], b[2], carry); result[2] = s[0]; carry = s[1];
        s = fullAdder(a[3], b[3], carry); result[3] = s[0]; carry = s[1];
        s = fullAdder(a[4], b[4], carry); result[4] = s[0]; carry = s[1];
        s = fullAdder(a[5], b[5], carry); result[5] = s[0]; carry = s[1];
        s = fullAdder(a[6], b[6], carry); result[6] = s[0]; carry = s[1];
        s = fullAdder(a[7], b[7], carry); result[7] = s[0]; carry = s[1];
        s = fullAdder(a[8], b[8], carry); result[8] = s[0]; carry = s[1];
        s = fullAdder(a[9], b[9], carry); result[9] = s[0]; carry = s[1];
        s = fullAdder(a[10], b[10], carry); result[10] = s[0]; carry = s[1];
        return result;
    }
    
    private static boolean[] sub11(boolean[] a, boolean[] b) {
        boolean[] notB = new boolean[11];
        notB[0] = not.gate(b[0]); notB[1] = not.gate(b[1]); notB[2] = not.gate(b[2]); notB[3] = not.gate(b[3]);
        notB[4] = not.gate(b[4]); notB[5] = not.gate(b[5]); notB[6] = not.gate(b[6]); notB[7] = not.gate(b[7]);
        notB[8] = not.gate(b[8]); notB[9] = not.gate(b[9]); notB[10] = not.gate(b[10]);
        boolean[] one = new boolean[11];
        one[0] = true;
        return add11(a, add11(notB, one));
    }
    
    private static boolean[] mux11(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[11];
        result[0] = mux2to1.module(a[0], b[0], sel); result[1] = mux2to1.module(a[1], b[1], sel);
        result[2] = mux2to1.module(a[2], b[2], sel); result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel); result[5] = mux2to1.module(a[5], b[5], sel);
        result[6] = mux2to1.module(a[6], b[6], sel); result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel); result[9] = mux2to1.module(a[9], b[9], sel);
        result[10] = mux2to1.module(a[10], b[10], sel);
        return result;
    }
    
    private static boolean[] mux54(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[54];
        result[0] = mux2to1.module(a[0], b[0], sel); result[1] = mux2to1.module(a[1], b[1], sel); result[2] = mux2to1.module(a[2], b[2], sel); result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel); result[5] = mux2to1.module(a[5], b[5], sel); result[6] = mux2to1.module(a[6], b[6], sel); result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel); result[9] = mux2to1.module(a[9], b[9], sel); result[10] = mux2to1.module(a[10], b[10], sel); result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel); result[13] = mux2to1.module(a[13], b[13], sel); result[14] = mux2to1.module(a[14], b[14], sel); result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel); result[17] = mux2to1.module(a[17], b[17], sel); result[18] = mux2to1.module(a[18], b[18], sel); result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel); result[21] = mux2to1.module(a[21], b[21], sel); result[22] = mux2to1.module(a[22], b[22], sel); result[23] = mux2to1.module(a[23], b[23], sel);
        result[24] = mux2to1.module(a[24], b[24], sel); result[25] = mux2to1.module(a[25], b[25], sel); result[26] = mux2to1.module(a[26], b[26], sel); result[27] = mux2to1.module(a[27], b[27], sel);
        result[28] = mux2to1.module(a[28], b[28], sel); result[29] = mux2to1.module(a[29], b[29], sel); result[30] = mux2to1.module(a[30], b[30], sel); result[31] = mux2to1.module(a[31], b[31], sel);
        result[32] = mux2to1.module(a[32], b[32], sel); result[33] = mux2to1.module(a[33], b[33], sel); result[34] = mux2to1.module(a[34], b[34], sel); result[35] = mux2to1.module(a[35], b[35], sel);
        result[36] = mux2to1.module(a[36], b[36], sel); result[37] = mux2to1.module(a[37], b[37], sel); result[38] = mux2to1.module(a[38], b[38], sel); result[39] = mux2to1.module(a[39], b[39], sel);
        result[40] = mux2to1.module(a[40], b[40], sel); result[41] = mux2to1.module(a[41], b[41], sel); result[42] = mux2to1.module(a[42], b[42], sel); result[43] = mux2to1.module(a[43], b[43], sel);
        result[44] = mux2to1.module(a[44], b[44], sel); result[45] = mux2to1.module(a[45], b[45], sel); result[46] = mux2to1.module(a[46], b[46], sel); result[47] = mux2to1.module(a[47], b[47], sel);
        result[48] = mux2to1.module(a[48], b[48], sel); result[49] = mux2to1.module(a[49], b[49], sel); result[50] = mux2to1.module(a[50], b[50], sel); result[51] = mux2to1.module(a[51], b[51], sel);
        result[52] = mux2to1.module(a[52], b[52], sel); result[53] = mux2to1.module(a[53], b[53], sel);
        return result;
    }
    
    private static boolean[] mux64(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[64];
        result[0] = mux2to1.module(a[0], b[0], sel); result[1] = mux2to1.module(a[1], b[1], sel); result[2] = mux2to1.module(a[2], b[2], sel); result[3] = mux2to1.module(a[3], b[3], sel);
        result[4] = mux2to1.module(a[4], b[4], sel); result[5] = mux2to1.module(a[5], b[5], sel); result[6] = mux2to1.module(a[6], b[6], sel); result[7] = mux2to1.module(a[7], b[7], sel);
        result[8] = mux2to1.module(a[8], b[8], sel); result[9] = mux2to1.module(a[9], b[9], sel); result[10] = mux2to1.module(a[10], b[10], sel); result[11] = mux2to1.module(a[11], b[11], sel);
        result[12] = mux2to1.module(a[12], b[12], sel); result[13] = mux2to1.module(a[13], b[13], sel); result[14] = mux2to1.module(a[14], b[14], sel); result[15] = mux2to1.module(a[15], b[15], sel);
        result[16] = mux2to1.module(a[16], b[16], sel); result[17] = mux2to1.module(a[17], b[17], sel); result[18] = mux2to1.module(a[18], b[18], sel); result[19] = mux2to1.module(a[19], b[19], sel);
        result[20] = mux2to1.module(a[20], b[20], sel); result[21] = mux2to1.module(a[21], b[21], sel); result[22] = mux2to1.module(a[22], b[22], sel); result[23] = mux2to1.module(a[23], b[23], sel);
        result[24] = mux2to1.module(a[24], b[24], sel); result[25] = mux2to1.module(a[25], b[25], sel); result[26] = mux2to1.module(a[26], b[26], sel); result[27] = mux2to1.module(a[27], b[27], sel);
        result[28] = mux2to1.module(a[28], b[28], sel); result[29] = mux2to1.module(a[29], b[29], sel); result[30] = mux2to1.module(a[30], b[30], sel); result[31] = mux2to1.module(a[31], b[31], sel);
        result[32] = mux2to1.module(a[32], b[32], sel); result[33] = mux2to1.module(a[33], b[33], sel); result[34] = mux2to1.module(a[34], b[34], sel); result[35] = mux2to1.module(a[35], b[35], sel);
        result[36] = mux2to1.module(a[36], b[36], sel); result[37] = mux2to1.module(a[37], b[37], sel); result[38] = mux2to1.module(a[38], b[38], sel); result[39] = mux2to1.module(a[39], b[39], sel);
        result[40] = mux2to1.module(a[40], b[40], sel); result[41] = mux2to1.module(a[41], b[41], sel); result[42] = mux2to1.module(a[42], b[42], sel); result[43] = mux2to1.module(a[43], b[43], sel);
        result[44] = mux2to1.module(a[44], b[44], sel); result[45] = mux2to1.module(a[45], b[45], sel); result[46] = mux2to1.module(a[46], b[46], sel); result[47] = mux2to1.module(a[47], b[47], sel);
        result[48] = mux2to1.module(a[48], b[48], sel); result[49] = mux2to1.module(a[49], b[49], sel); result[50] = mux2to1.module(a[50], b[50], sel); result[51] = mux2to1.module(a[51], b[51], sel);
        result[52] = mux2to1.module(a[52], b[52], sel); result[53] = mux2to1.module(a[53], b[53], sel); result[54] = mux2to1.module(a[54], b[54], sel); result[55] = mux2to1.module(a[55], b[55], sel);
        result[56] = mux2to1.module(a[56], b[56], sel); result[57] = mux2to1.module(a[57], b[57], sel); result[58] = mux2to1.module(a[58], b[58], sel); result[59] = mux2to1.module(a[59], b[59], sel);
        result[60] = mux2to1.module(a[60], b[60], sel); result[61] = mux2to1.module(a[61], b[61], sel); result[62] = mux2to1.module(a[62], b[62], sel); result[63] = mux2to1.module(a[63], b[63], sel);
        return result;
    }
    
    private static boolean[] assembleFloat(boolean sign, boolean[] exp, boolean[] man) {
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
}
