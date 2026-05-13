package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.*;

public class fdiv64B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean signA = a[63];
        boolean signB = b[63];
        
        boolean[] expA = extractExp64(a);
        boolean[] expB = extractExp64(b);
        
        boolean[] manA = extractMan64(a);
        boolean[] manB = extractMan64(b);
        
        boolean isZeroExpA = isZeroExp11(expA);
        boolean isZeroExpB = isZeroExp11(expB);
        boolean isZeroManA = isZeroMan52(manA);
        boolean isZeroManB = isZeroMan52(manB);
        
        boolean isZeroA = and.gate(isZeroExpA, isZeroManA);
        boolean isZeroB = and.gate(isZeroExpB, isZeroManB);
        
        boolean isInfExpA = isAllOnes11(expA);
        boolean isInfExpB = isAllOnes11(expB);
        
        boolean isInfA = and.gate(isInfExpA, isZeroManA);
        boolean isInfB = and.gate(isInfExpB, isZeroManB);
        
        boolean isNaNA = and.gate(isInfExpA, not.gate(isZeroManA));
        boolean isNaNB = and.gate(isInfExpB, not.gate(isZeroManB));
        
        boolean resultSign = xor.gate(signA, signB);
        
        boolean[] nanResult = makeQuietNaN64();
        boolean[] infResult = makeInf64(resultSign);
        boolean[] zeroResult = makeZero64(resultSign);
        
        boolean eitherNaN = or.gate(isNaNA, isNaNB);
        boolean bothInf = and.gate(isInfA, isInfB);
        boolean bothZero = and.gate(isZeroA, isZeroB);
        boolean divByZero = and.gate(isZeroB, not.gate(isZeroA));
        boolean zeroDivNonZero = and.gate(isZeroA, not.gate(isZeroB));
        boolean infDivInf = bothInf;
        
        boolean[] normalResult = divideNormal(signA, signB, expA, expB, manA, manB);
        
        boolean returnNaN = or.gate(or.gate(eitherNaN, bothZero), infDivInf);
        boolean returnInf = and.gate(not.gate(returnNaN), divByZero);
        boolean returnZero = and.gate(and.gate(not.gate(returnNaN), not.gate(returnInf)), zeroDivNonZero);
        
        boolean[] result1 = mux64B(normalResult, nanResult, returnNaN);
        boolean[] result2 = mux64B(result1, infResult, returnInf);
        boolean[] result3 = mux64B(result2, zeroResult, returnZero);
        
        return result3;
    }
    
    private static boolean[] divideNormal(boolean signA, boolean signB, boolean[] expA, boolean[] expB, boolean[] manA, boolean[] manB) {
        boolean[] result = new boolean[64];
        
        boolean[] expAVal = add11(expA, new boolean[]{false, false, false, false, false, false, false, false, false, false, false});
        boolean[] expBVal = add11(expB, new boolean[]{false, false, false, false, false, false, false, false, false, false, false});
        
        boolean[] expDiff = sub11(expAVal, expBVal);
        
        boolean[] bias = new boolean[]{true, true, true, true, true, true, true, true, false, false, false};
        boolean[] expResult = sub11(expDiff, bias);
        
        boolean[] manAExt = new boolean[53];
        manAExt[0] = true;
        manAExt[1] = manA[0]; manAExt[2] = manA[1]; manAExt[3] = manA[2]; manAExt[4] = manA[3];
        manAExt[5] = manA[4]; manAExt[6] = manA[5]; manAExt[7] = manA[6]; manAExt[8] = manA[7];
        manAExt[9] = manA[8]; manAExt[10] = manA[9]; manAExt[11] = manA[10]; manAExt[12] = manA[11];
        manAExt[13] = manA[12]; manAExt[14] = manA[13]; manAExt[15] = manA[14]; manAExt[16] = manA[15];
        manAExt[17] = manA[16]; manAExt[18] = manA[17]; manAExt[19] = manA[18]; manAExt[20] = manA[19];
        manAExt[21] = manA[20]; manAExt[22] = manA[21]; manAExt[23] = manA[22]; manAExt[24] = manA[23];
        manAExt[25] = manA[24]; manAExt[26] = manA[25]; manAExt[27] = manA[26]; manAExt[28] = manA[27];
        manAExt[29] = manA[28]; manAExt[30] = manA[29]; manAExt[31] = manA[30]; manAExt[32] = manA[31];
        manAExt[33] = manA[32]; manAExt[34] = manA[33]; manAExt[35] = manA[34]; manAExt[36] = manA[35];
        manAExt[37] = manA[36]; manAExt[38] = manA[37]; manAExt[39] = manA[38]; manAExt[40] = manA[39];
        manAExt[41] = manA[40]; manAExt[42] = manA[41]; manAExt[43] = manA[42]; manAExt[44] = manA[43];
        manAExt[45] = manA[44]; manAExt[46] = manA[45]; manAExt[47] = manA[46]; manAExt[48] = manA[47];
        manAExt[49] = manA[48]; manAExt[50] = manA[49]; manAExt[51] = manA[50]; manAExt[52] = manA[51];
        
        boolean[] manBExt = new boolean[53];
        manBExt[0] = true;
        manBExt[1] = manB[0]; manBExt[2] = manB[1]; manBExt[3] = manB[2]; manBExt[4] = manB[3];
        manBExt[5] = manB[4]; manBExt[6] = manB[5]; manBExt[7] = manB[6]; manBExt[8] = manB[7];
        manBExt[9] = manB[8]; manBExt[10] = manB[9]; manBExt[11] = manB[10]; manBExt[12] = manB[11];
        manBExt[13] = manB[12]; manBExt[14] = manB[13]; manBExt[15] = manB[14]; manBExt[16] = manB[15];
        manBExt[17] = manB[16]; manBExt[18] = manB[17]; manBExt[19] = manB[18]; manBExt[20] = manB[19];
        manBExt[21] = manB[20]; manBExt[22] = manB[21]; manBExt[23] = manB[22]; manBExt[24] = manB[23];
        manBExt[25] = manB[24]; manBExt[26] = manB[25]; manBExt[27] = manB[26]; manBExt[28] = manB[27];
        manBExt[29] = manB[28]; manBExt[30] = manB[29]; manBExt[31] = manB[30]; manBExt[32] = manB[31];
        manBExt[33] = manB[32]; manBExt[34] = manB[33]; manBExt[35] = manB[34]; manBExt[36] = manB[35];
        manBExt[37] = manB[36]; manBExt[38] = manB[37]; manBExt[39] = manB[38]; manBExt[40] = manB[39];
        manBExt[41] = manB[40]; manBExt[42] = manB[41]; manBExt[43] = manB[42]; manBExt[44] = manB[43];
        manBExt[45] = manB[44]; manBExt[46] = manB[45]; manBExt[47] = manB[46]; manBExt[48] = manB[47];
        manBExt[49] = manB[48]; manBExt[50] = manB[49]; manBExt[51] = manB[50]; manBExt[52] = manB[51];
        
        boolean[] manResult = divideMantissa(manAExt, manBExt);
        
        result[0] = manResult[1]; result[1] = manResult[2]; result[2] = manResult[3]; result[3] = manResult[4];
        result[4] = manResult[5]; result[5] = manResult[6]; result[6] = manResult[7]; result[7] = manResult[8];
        result[8] = manResult[9]; result[9] = manResult[10]; result[10] = manResult[11]; result[11] = manResult[12];
        result[12] = manResult[13]; result[13] = manResult[14]; result[14] = manResult[15]; result[15] = manResult[16];
        result[16] = manResult[17]; result[17] = manResult[18]; result[18] = manResult[19]; result[19] = manResult[20];
        result[20] = manResult[21]; result[21] = manResult[22]; result[22] = manResult[23]; result[23] = manResult[24];
        result[24] = manResult[25]; result[25] = manResult[26]; result[26] = manResult[27]; result[27] = manResult[28];
        result[28] = manResult[29]; result[29] = manResult[30]; result[30] = manResult[31]; result[31] = manResult[32];
        result[32] = manResult[33]; result[33] = manResult[34]; result[34] = manResult[35]; result[35] = manResult[36];
        result[36] = manResult[37]; result[37] = manResult[38]; result[38] = manResult[39]; result[39] = manResult[40];
        result[40] = manResult[41]; result[41] = manResult[42]; result[42] = manResult[43]; result[43] = manResult[44];
        result[44] = manResult[45]; result[45] = manResult[46]; result[46] = manResult[47]; result[47] = manResult[48];
        result[48] = manResult[49]; result[49] = manResult[50]; result[50] = manResult[51]; result[51] = manResult[52];
        
        result[52] = expResult[0]; result[53] = expResult[1]; result[54] = expResult[2]; result[55] = expResult[3];
        result[56] = expResult[4]; result[57] = expResult[5]; result[58] = expResult[6]; result[59] = expResult[7];
        result[60] = expResult[8]; result[61] = expResult[9]; result[62] = expResult[10];
        
        result[63] = xor.gate(signA, signB);
        
        return result;
    }
    
    private static boolean[] divideMantissa(boolean[] a, boolean[] b) {
        boolean[] quotient = new boolean[53];
        boolean[] remainder = new boolean[53];
        
        remainder[0] = a[0]; remainder[1] = a[1]; remainder[2] = a[2]; remainder[3] = a[3];
        remainder[4] = a[4]; remainder[5] = a[5]; remainder[6] = a[6]; remainder[7] = a[7];
        remainder[8] = a[8]; remainder[9] = a[9]; remainder[10] = a[10]; remainder[11] = a[11];
        remainder[12] = a[12]; remainder[13] = a[13]; remainder[14] = a[14]; remainder[15] = a[15];
        remainder[16] = a[16]; remainder[17] = a[17]; remainder[18] = a[18]; remainder[19] = a[19];
        remainder[20] = a[20]; remainder[21] = a[21]; remainder[22] = a[22]; remainder[23] = a[23];
        remainder[24] = a[24]; remainder[25] = a[25]; remainder[26] = a[26]; remainder[27] = a[27];
        remainder[28] = a[28]; remainder[29] = a[29]; remainder[30] = a[30]; remainder[31] = a[31];
        remainder[32] = a[32]; remainder[33] = a[33]; remainder[34] = a[34]; remainder[35] = a[35];
        remainder[36] = a[36]; remainder[37] = a[37]; remainder[38] = a[38]; remainder[39] = a[39];
        remainder[40] = a[40]; remainder[41] = a[41]; remainder[42] = a[42]; remainder[43] = a[43];
        remainder[44] = a[44]; remainder[45] = a[45]; remainder[46] = a[46]; remainder[47] = a[47];
        remainder[48] = a[48]; remainder[49] = a[49]; remainder[50] = a[50]; remainder[51] = a[51];
        remainder[52] = a[52];
        
        boolean canSubtract = canSubtract53(remainder, b);
        boolean[] subResult = sub53(remainder, b);
        
        quotient[0] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[1] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[2] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[3] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[4] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[5] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[6] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[7] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[8] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[9] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[10] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[11] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[12] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[13] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[14] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[15] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[16] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[17] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[18] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[19] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[20] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[21] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[22] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[23] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[24] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[25] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[26] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[27] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[28] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[29] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[30] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[31] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[32] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[33] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[34] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[35] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[36] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[37] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[38] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[39] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[40] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[41] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[42] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[43] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[44] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[45] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[46] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[47] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[48] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[49] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[50] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[51] = canSubtract;
        remainder = mux53(subResult, remainder, not.gate(canSubtract));
        remainder = shiftLeft53(remainder);
        
        canSubtract = canSubtract53(remainder, b);
        subResult = sub53(remainder, b);
        quotient[52] = canSubtract;
        
        return quotient;
    }
    
    private static boolean[] shiftLeft53(boolean[] a) {
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
    
    private static boolean[] mux53(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[53];
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
        result[32] = mux2to1.module(a[32], b[32], sel);
        result[33] = mux2to1.module(a[33], b[33], sel);
        result[34] = mux2to1.module(a[34], b[34], sel);
        result[35] = mux2to1.module(a[35], b[35], sel);
        result[36] = mux2to1.module(a[36], b[36], sel);
        result[37] = mux2to1.module(a[37], b[37], sel);
        result[38] = mux2to1.module(a[38], b[38], sel);
        result[39] = mux2to1.module(a[39], b[39], sel);
        result[40] = mux2to1.module(a[40], b[40], sel);
        result[41] = mux2to1.module(a[41], b[41], sel);
        result[42] = mux2to1.module(a[42], b[42], sel);
        result[43] = mux2to1.module(a[43], b[43], sel);
        result[44] = mux2to1.module(a[44], b[44], sel);
        result[45] = mux2to1.module(a[45], b[45], sel);
        result[46] = mux2to1.module(a[46], b[46], sel);
        result[47] = mux2to1.module(a[47], b[47], sel);
        result[48] = mux2to1.module(a[48], b[48], sel);
        result[49] = mux2to1.module(a[49], b[49], sel);
        result[50] = mux2to1.module(a[50], b[50], sel);
        result[51] = mux2to1.module(a[51], b[51], sel);
        result[52] = mux2to1.module(a[52], b[52], sel);
        return result;
    }
    
    private static boolean canSubtract53(boolean[] a, boolean[] b) {
        boolean aLarger = false;
        boolean equal = true;
        
        aLarger = or.gate(and.gate(a[52], not.gate(b[52])), and.gate(equal, xor.gate(a[52], b[52])));
        equal = and.gate(equal, not.gate(xor.gate(a[52], b[52])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[51]), not.gate(b[51])));
        equal = and.gate(equal, not.gate(xor.gate(a[51], b[51])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[50]), not.gate(b[50])));
        equal = and.gate(equal, not.gate(xor.gate(a[50], b[50])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[49]), not.gate(b[49])));
        equal = and.gate(equal, not.gate(xor.gate(a[49], b[49])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[48]), not.gate(b[48])));
        equal = and.gate(equal, not.gate(xor.gate(a[48], b[48])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[47]), not.gate(b[47])));
        equal = and.gate(equal, not.gate(xor.gate(a[47], b[47])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[46]), not.gate(b[46])));
        equal = and.gate(equal, not.gate(xor.gate(a[46], b[46])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[45]), not.gate(b[45])));
        equal = and.gate(equal, not.gate(xor.gate(a[45], b[45])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[44]), not.gate(b[44])));
        equal = and.gate(equal, not.gate(xor.gate(a[44], b[44])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[43]), not.gate(b[43])));
        equal = and.gate(equal, not.gate(xor.gate(a[43], b[43])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[42]), not.gate(b[42])));
        equal = and.gate(equal, not.gate(xor.gate(a[42], b[42])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[41]), not.gate(b[41])));
        equal = and.gate(equal, not.gate(xor.gate(a[41], b[41])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[40]), not.gate(b[40])));
        equal = and.gate(equal, not.gate(xor.gate(a[40], b[40])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[39]), not.gate(b[39])));
        equal = and.gate(equal, not.gate(xor.gate(a[39], b[39])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[38]), not.gate(b[38])));
        equal = and.gate(equal, not.gate(xor.gate(a[38], b[38])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[37]), not.gate(b[37])));
        equal = and.gate(equal, not.gate(xor.gate(a[37], b[37])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[36]), not.gate(b[36])));
        equal = and.gate(equal, not.gate(xor.gate(a[36], b[36])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[35]), not.gate(b[35])));
        equal = and.gate(equal, not.gate(xor.gate(a[35], b[35])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[34]), not.gate(b[34])));
        equal = and.gate(equal, not.gate(xor.gate(a[34], b[34])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[33]), not.gate(b[33])));
        equal = and.gate(equal, not.gate(xor.gate(a[33], b[33])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[32]), not.gate(b[32])));
        equal = and.gate(equal, not.gate(xor.gate(a[32], b[32])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[31]), not.gate(b[31])));
        equal = and.gate(equal, not.gate(xor.gate(a[31], b[31])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[30]), not.gate(b[30])));
        equal = and.gate(equal, not.gate(xor.gate(a[30], b[30])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[29]), not.gate(b[29])));
        equal = and.gate(equal, not.gate(xor.gate(a[29], b[29])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[28]), not.gate(b[28])));
        equal = and.gate(equal, not.gate(xor.gate(a[28], b[28])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[27]), not.gate(b[27])));
        equal = and.gate(equal, not.gate(xor.gate(a[27], b[27])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[26]), not.gate(b[26])));
        equal = and.gate(equal, not.gate(xor.gate(a[26], b[26])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[25]), not.gate(b[25])));
        equal = and.gate(equal, not.gate(xor.gate(a[25], b[25])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[24]), not.gate(b[24])));
        equal = and.gate(equal, not.gate(xor.gate(a[24], b[24])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[23]), not.gate(b[23])));
        equal = and.gate(equal, not.gate(xor.gate(a[23], b[23])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[22]), not.gate(b[22])));
        equal = and.gate(equal, not.gate(xor.gate(a[22], b[22])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[21]), not.gate(b[21])));
        equal = and.gate(equal, not.gate(xor.gate(a[21], b[21])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[20]), not.gate(b[20])));
        equal = and.gate(equal, not.gate(xor.gate(a[20], b[20])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[19]), not.gate(b[19])));
        equal = and.gate(equal, not.gate(xor.gate(a[19], b[19])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[18]), not.gate(b[18])));
        equal = and.gate(equal, not.gate(xor.gate(a[18], b[18])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[17]), not.gate(b[17])));
        equal = and.gate(equal, not.gate(xor.gate(a[17], b[17])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[16]), not.gate(b[16])));
        equal = and.gate(equal, not.gate(xor.gate(a[16], b[16])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[15]), not.gate(b[15])));
        equal = and.gate(equal, not.gate(xor.gate(a[15], b[15])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[14]), not.gate(b[14])));
        equal = and.gate(equal, not.gate(xor.gate(a[14], b[14])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[13]), not.gate(b[13])));
        equal = and.gate(equal, not.gate(xor.gate(a[13], b[13])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[12]), not.gate(b[12])));
        equal = and.gate(equal, not.gate(xor.gate(a[12], b[12])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[11]), not.gate(b[11])));
        equal = and.gate(equal, not.gate(xor.gate(a[11], b[11])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[10]), not.gate(b[10])));
        equal = and.gate(equal, not.gate(xor.gate(a[10], b[10])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[9]), not.gate(b[9])));
        equal = and.gate(equal, not.gate(xor.gate(a[9], b[9])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[8]), not.gate(b[8])));
        equal = and.gate(equal, not.gate(xor.gate(a[8], b[8])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[7]), not.gate(b[7])));
        equal = and.gate(equal, not.gate(xor.gate(a[7], b[7])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[6]), not.gate(b[6])));
        equal = and.gate(equal, not.gate(xor.gate(a[6], b[6])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[5]), not.gate(b[5])));
        equal = and.gate(equal, not.gate(xor.gate(a[5], b[5])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[4]), not.gate(b[4])));
        equal = and.gate(equal, not.gate(xor.gate(a[4], b[4])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[3]), not.gate(b[3])));
        equal = and.gate(equal, not.gate(xor.gate(a[3], b[3])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[2]), not.gate(b[2])));
        equal = and.gate(equal, not.gate(xor.gate(a[2], b[2])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[1]), not.gate(b[1])));
        equal = and.gate(equal, not.gate(xor.gate(a[1], b[1])));
        
        aLarger = or.gate(aLarger, and.gate(and.gate(equal, a[0]), not.gate(b[0])));
        
        return or.gate(aLarger, equal);
    }
    
    private static boolean[] sub53(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[53];
        boolean borrow = false;
        
        boolean diff0 = xor.gate(xor.gate(a[0], b[0]), borrow);
        boolean borrow0 = or.gate(or.gate(and.gate(not.gate(a[0]), b[0]), and.gate(not.gate(a[0]), borrow)), and.gate(b[0], borrow));
        result[0] = diff0;
        
        boolean diff1 = xor.gate(xor.gate(a[1], b[1]), borrow0);
        boolean borrow1 = or.gate(or.gate(and.gate(not.gate(a[1]), b[1]), and.gate(not.gate(a[1]), borrow0)), and.gate(b[1], borrow0));
        result[1] = diff1;
        
        boolean diff2 = xor.gate(xor.gate(a[2], b[2]), borrow1);
        boolean borrow2 = or.gate(or.gate(and.gate(not.gate(a[2]), b[2]), and.gate(not.gate(a[2]), borrow1)), and.gate(b[2], borrow1));
        result[2] = diff2;
        
        boolean diff3 = xor.gate(xor.gate(a[3], b[3]), borrow2);
        boolean borrow3 = or.gate(or.gate(and.gate(not.gate(a[3]), b[3]), and.gate(not.gate(a[3]), borrow2)), and.gate(b[3], borrow2));
        result[3] = diff3;
        
        boolean diff4 = xor.gate(xor.gate(a[4], b[4]), borrow3);
        boolean borrow4 = or.gate(or.gate(and.gate(not.gate(a[4]), b[4]), and.gate(not.gate(a[4]), borrow3)), and.gate(b[4], borrow3));
        result[4] = diff4;
        
        boolean diff5 = xor.gate(xor.gate(a[5], b[5]), borrow4);
        boolean borrow5 = or.gate(or.gate(and.gate(not.gate(a[5]), b[5]), and.gate(not.gate(a[5]), borrow4)), and.gate(b[5], borrow4));
        result[5] = diff5;
        
        boolean diff6 = xor.gate(xor.gate(a[6], b[6]), borrow5);
        boolean borrow6 = or.gate(or.gate(and.gate(not.gate(a[6]), b[6]), and.gate(not.gate(a[6]), borrow5)), and.gate(b[6], borrow5));
        result[6] = diff6;
        
        boolean diff7 = xor.gate(xor.gate(a[7], b[7]), borrow6);
        boolean borrow7 = or.gate(or.gate(and.gate(not.gate(a[7]), b[7]), and.gate(not.gate(a[7]), borrow6)), and.gate(b[7], borrow6));
        result[7] = diff7;
        
        boolean diff8 = xor.gate(xor.gate(a[8], b[8]), borrow7);
        boolean borrow8 = or.gate(or.gate(and.gate(not.gate(a[8]), b[8]), and.gate(not.gate(a[8]), borrow7)), and.gate(b[8], borrow7));
        result[8] = diff8;
        
        boolean diff9 = xor.gate(xor.gate(a[9], b[9]), borrow8);
        boolean borrow9 = or.gate(or.gate(and.gate(not.gate(a[9]), b[9]), and.gate(not.gate(a[9]), borrow8)), and.gate(b[9], borrow8));
        result[9] = diff9;
        
        boolean diff10 = xor.gate(xor.gate(a[10], b[10]), borrow9);
        boolean borrow10 = or.gate(or.gate(and.gate(not.gate(a[10]), b[10]), and.gate(not.gate(a[10]), borrow9)), and.gate(b[10], borrow9));
        result[10] = diff10;
        
        boolean diff11 = xor.gate(xor.gate(a[11], b[11]), borrow10);
        boolean borrow11 = or.gate(or.gate(and.gate(not.gate(a[11]), b[11]), and.gate(not.gate(a[11]), borrow10)), and.gate(b[11], borrow10));
        result[11] = diff11;
        
        boolean diff12 = xor.gate(xor.gate(a[12], b[12]), borrow11);
        boolean borrow12 = or.gate(or.gate(and.gate(not.gate(a[12]), b[12]), and.gate(not.gate(a[12]), borrow11)), and.gate(b[12], borrow11));
        result[12] = diff12;
        
        boolean diff13 = xor.gate(xor.gate(a[13], b[13]), borrow12);
        boolean borrow13 = or.gate(or.gate(and.gate(not.gate(a[13]), b[13]), and.gate(not.gate(a[13]), borrow12)), and.gate(b[13], borrow12));
        result[13] = diff13;
        
        boolean diff14 = xor.gate(xor.gate(a[14], b[14]), borrow13);
        boolean borrow14 = or.gate(or.gate(and.gate(not.gate(a[14]), b[14]), and.gate(not.gate(a[14]), borrow13)), and.gate(b[14], borrow13));
        result[14] = diff14;
        
        boolean diff15 = xor.gate(xor.gate(a[15], b[15]), borrow14);
        boolean borrow15 = or.gate(or.gate(and.gate(not.gate(a[15]), b[15]), and.gate(not.gate(a[15]), borrow14)), and.gate(b[15], borrow14));
        result[15] = diff15;
        
        boolean diff16 = xor.gate(xor.gate(a[16], b[16]), borrow15);
        boolean borrow16 = or.gate(or.gate(and.gate(not.gate(a[16]), b[16]), and.gate(not.gate(a[16]), borrow15)), and.gate(b[16], borrow15));
        result[16] = diff16;
        
        boolean diff17 = xor.gate(xor.gate(a[17], b[17]), borrow16);
        boolean borrow17 = or.gate(or.gate(and.gate(not.gate(a[17]), b[17]), and.gate(not.gate(a[17]), borrow16)), and.gate(b[17], borrow16));
        result[17] = diff17;
        
        boolean diff18 = xor.gate(xor.gate(a[18], b[18]), borrow17);
        boolean borrow18 = or.gate(or.gate(and.gate(not.gate(a[18]), b[18]), and.gate(not.gate(a[18]), borrow17)), and.gate(b[18], borrow17));
        result[18] = diff18;
        
        boolean diff19 = xor.gate(xor.gate(a[19], b[19]), borrow18);
        boolean borrow19 = or.gate(or.gate(and.gate(not.gate(a[19]), b[19]), and.gate(not.gate(a[19]), borrow18)), and.gate(b[19], borrow18));
        result[19] = diff19;
        
        boolean diff20 = xor.gate(xor.gate(a[20], b[20]), borrow19);
        boolean borrow20 = or.gate(or.gate(and.gate(not.gate(a[20]), b[20]), and.gate(not.gate(a[20]), borrow19)), and.gate(b[20], borrow19));
        result[20] = diff20;
        
        boolean diff21 = xor.gate(xor.gate(a[21], b[21]), borrow20);
        boolean borrow21 = or.gate(or.gate(and.gate(not.gate(a[21]), b[21]), and.gate(not.gate(a[21]), borrow20)), and.gate(b[21], borrow20));
        result[21] = diff21;
        
        boolean diff22 = xor.gate(xor.gate(a[22], b[22]), borrow21);
        boolean borrow22 = or.gate(or.gate(and.gate(not.gate(a[22]), b[22]), and.gate(not.gate(a[22]), borrow21)), and.gate(b[22], borrow21));
        result[22] = diff22;
        
        boolean diff23 = xor.gate(xor.gate(a[23], b[23]), borrow22);
        boolean borrow23 = or.gate(or.gate(and.gate(not.gate(a[23]), b[23]), and.gate(not.gate(a[23]), borrow22)), and.gate(b[23], borrow22));
        result[23] = diff23;
        
        boolean diff24 = xor.gate(xor.gate(a[24], b[24]), borrow23);
        boolean borrow24 = or.gate(or.gate(and.gate(not.gate(a[24]), b[24]), and.gate(not.gate(a[24]), borrow23)), and.gate(b[24], borrow23));
        result[24] = diff24;
        
        boolean diff25 = xor.gate(xor.gate(a[25], b[25]), borrow24);
        boolean borrow25 = or.gate(or.gate(and.gate(not.gate(a[25]), b[25]), and.gate(not.gate(a[25]), borrow24)), and.gate(b[25], borrow24));
        result[25] = diff25;
        
        boolean diff26 = xor.gate(xor.gate(a[26], b[26]), borrow25);
        boolean borrow26 = or.gate(or.gate(and.gate(not.gate(a[26]), b[26]), and.gate(not.gate(a[26]), borrow25)), and.gate(b[26], borrow25));
        result[26] = diff26;
        
        boolean diff27 = xor.gate(xor.gate(a[27], b[27]), borrow26);
        boolean borrow27 = or.gate(or.gate(and.gate(not.gate(a[27]), b[27]), and.gate(not.gate(a[27]), borrow26)), and.gate(b[27], borrow26));
        result[27] = diff27;
        
        boolean diff28 = xor.gate(xor.gate(a[28], b[28]), borrow27);
        boolean borrow28 = or.gate(or.gate(and.gate(not.gate(a[28]), b[28]), and.gate(not.gate(a[28]), borrow27)), and.gate(b[28], borrow27));
        result[28] = diff28;
        
        boolean diff29 = xor.gate(xor.gate(a[29], b[29]), borrow28);
        boolean borrow29 = or.gate(or.gate(and.gate(not.gate(a[29]), b[29]), and.gate(not.gate(a[29]), borrow28)), and.gate(b[29], borrow28));
        result[29] = diff29;
        
        boolean diff30 = xor.gate(xor.gate(a[30], b[30]), borrow29);
        boolean borrow30 = or.gate(or.gate(and.gate(not.gate(a[30]), b[30]), and.gate(not.gate(a[30]), borrow29)), and.gate(b[30], borrow29));
        result[30] = diff30;
        
        boolean diff31 = xor.gate(xor.gate(a[31], b[31]), borrow30);
        boolean borrow31 = or.gate(or.gate(and.gate(not.gate(a[31]), b[31]), and.gate(not.gate(a[31]), borrow30)), and.gate(b[31], borrow30));
        result[31] = diff31;
        
        boolean diff32 = xor.gate(xor.gate(a[32], b[32]), borrow31);
        boolean borrow32 = or.gate(or.gate(and.gate(not.gate(a[32]), b[32]), and.gate(not.gate(a[32]), borrow31)), and.gate(b[32], borrow31));
        result[32] = diff32;
        
        boolean diff33 = xor.gate(xor.gate(a[33], b[33]), borrow32);
        boolean borrow33 = or.gate(or.gate(and.gate(not.gate(a[33]), b[33]), and.gate(not.gate(a[33]), borrow32)), and.gate(b[33], borrow32));
        result[33] = diff33;
        
        boolean diff34 = xor.gate(xor.gate(a[34], b[34]), borrow33);
        boolean borrow34 = or.gate(or.gate(and.gate(not.gate(a[34]), b[34]), and.gate(not.gate(a[34]), borrow33)), and.gate(b[34], borrow33));
        result[34] = diff34;
        
        boolean diff35 = xor.gate(xor.gate(a[35], b[35]), borrow34);
        boolean borrow35 = or.gate(or.gate(and.gate(not.gate(a[35]), b[35]), and.gate(not.gate(a[35]), borrow34)), and.gate(b[35], borrow34));
        result[35] = diff35;
        
        boolean diff36 = xor.gate(xor.gate(a[36], b[36]), borrow35);
        boolean borrow36 = or.gate(or.gate(and.gate(not.gate(a[36]), b[36]), and.gate(not.gate(a[36]), borrow35)), and.gate(b[36], borrow35));
        result[36] = diff36;
        
        boolean diff37 = xor.gate(xor.gate(a[37], b[37]), borrow36);
        boolean borrow37 = or.gate(or.gate(and.gate(not.gate(a[37]), b[37]), and.gate(not.gate(a[37]), borrow36)), and.gate(b[37], borrow36));
        result[37] = diff37;
        
        boolean diff38 = xor.gate(xor.gate(a[38], b[38]), borrow37);
        boolean borrow38 = or.gate(or.gate(and.gate(not.gate(a[38]), b[38]), and.gate(not.gate(a[38]), borrow37)), and.gate(b[38], borrow37));
        result[38] = diff38;
        
        boolean diff39 = xor.gate(xor.gate(a[39], b[39]), borrow38);
        boolean borrow39 = or.gate(or.gate(and.gate(not.gate(a[39]), b[39]), and.gate(not.gate(a[39]), borrow38)), and.gate(b[39], borrow38));
        result[39] = diff39;
        
        boolean diff40 = xor.gate(xor.gate(a[40], b[40]), borrow39);
        boolean borrow40 = or.gate(or.gate(and.gate(not.gate(a[40]), b[40]), and.gate(not.gate(a[40]), borrow39)), and.gate(b[40], borrow39));
        result[40] = diff40;
        
        boolean diff41 = xor.gate(xor.gate(a[41], b[41]), borrow40);
        boolean borrow41 = or.gate(or.gate(and.gate(not.gate(a[41]), b[41]), and.gate(not.gate(a[41]), borrow40)), and.gate(b[41], borrow40));
        result[41] = diff41;
        
        boolean diff42 = xor.gate(xor.gate(a[42], b[42]), borrow41);
        boolean borrow42 = or.gate(or.gate(and.gate(not.gate(a[42]), b[42]), and.gate(not.gate(a[42]), borrow41)), and.gate(b[42], borrow41));
        result[42] = diff42;
        
        boolean diff43 = xor.gate(xor.gate(a[43], b[43]), borrow42);
        boolean borrow43 = or.gate(or.gate(and.gate(not.gate(a[43]), b[43]), and.gate(not.gate(a[43]), borrow42)), and.gate(b[43], borrow42));
        result[43] = diff43;
        
        boolean diff44 = xor.gate(xor.gate(a[44], b[44]), borrow43);
        boolean borrow44 = or.gate(or.gate(and.gate(not.gate(a[44]), b[44]), and.gate(not.gate(a[44]), borrow43)), and.gate(b[44], borrow43));
        result[44] = diff44;
        
        boolean diff45 = xor.gate(xor.gate(a[45], b[45]), borrow44);
        boolean borrow45 = or.gate(or.gate(and.gate(not.gate(a[45]), b[45]), and.gate(not.gate(a[45]), borrow44)), and.gate(b[45], borrow44));
        result[45] = diff45;
        
        boolean diff46 = xor.gate(xor.gate(a[46], b[46]), borrow45);
        boolean borrow46 = or.gate(or.gate(and.gate(not.gate(a[46]), b[46]), and.gate(not.gate(a[46]), borrow45)), and.gate(b[46], borrow45));
        result[46] = diff46;
        
        boolean diff47 = xor.gate(xor.gate(a[47], b[47]), borrow46);
        boolean borrow47 = or.gate(or.gate(and.gate(not.gate(a[47]), b[47]), and.gate(not.gate(a[47]), borrow46)), and.gate(b[47], borrow46));
        result[47] = diff47;
        
        boolean diff48 = xor.gate(xor.gate(a[48], b[48]), borrow47);
        boolean borrow48 = or.gate(or.gate(and.gate(not.gate(a[48]), b[48]), and.gate(not.gate(a[48]), borrow47)), and.gate(b[48], borrow47));
        result[48] = diff48;
        
        boolean diff49 = xor.gate(xor.gate(a[49], b[49]), borrow48);
        boolean borrow49 = or.gate(or.gate(and.gate(not.gate(a[49]), b[49]), and.gate(not.gate(a[49]), borrow48)), and.gate(b[49], borrow48));
        result[49] = diff49;
        
        boolean diff50 = xor.gate(xor.gate(a[50], b[50]), borrow49);
        boolean borrow50 = or.gate(or.gate(and.gate(not.gate(a[50]), b[50]), and.gate(not.gate(a[50]), borrow49)), and.gate(b[50], borrow49));
        result[50] = diff50;
        
        boolean diff51 = xor.gate(xor.gate(a[51], b[51]), borrow50);
        boolean borrow51 = or.gate(or.gate(and.gate(not.gate(a[51]), b[51]), and.gate(not.gate(a[51]), borrow50)), and.gate(b[51], borrow50));
        result[51] = diff51;
        
        boolean diff52 = xor.gate(xor.gate(a[52], b[52]), borrow51);
        result[52] = diff52;
        
        return result;
    }
    
    private static boolean[] add11(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[11];
        boolean carry = false;
        
        boolean sum0 = xor.gate(xor.gate(a[0], b[0]), carry);
        boolean carry0 = or.gate(and.gate(a[0], b[0]), and.gate(xor.gate(a[0], b[0]), carry));
        result[0] = sum0;
        
        boolean sum1 = xor.gate(xor.gate(a[1], b[1]), carry0);
        boolean carry1 = or.gate(and.gate(a[1], b[1]), and.gate(xor.gate(a[1], b[1]), carry0));
        result[1] = sum1;
        
        boolean sum2 = xor.gate(xor.gate(a[2], b[2]), carry1);
        boolean carry2 = or.gate(and.gate(a[2], b[2]), and.gate(xor.gate(a[2], b[2]), carry1));
        result[2] = sum2;
        
        boolean sum3 = xor.gate(xor.gate(a[3], b[3]), carry2);
        boolean carry3 = or.gate(and.gate(a[3], b[3]), and.gate(xor.gate(a[3], b[3]), carry2));
        result[3] = sum3;
        
        boolean sum4 = xor.gate(xor.gate(a[4], b[4]), carry3);
        boolean carry4 = or.gate(and.gate(a[4], b[4]), and.gate(xor.gate(a[4], b[4]), carry3));
        result[4] = sum4;
        
        boolean sum5 = xor.gate(xor.gate(a[5], b[5]), carry4);
        boolean carry5 = or.gate(and.gate(a[5], b[5]), and.gate(xor.gate(a[5], b[5]), carry4));
        result[5] = sum5;
        
        boolean sum6 = xor.gate(xor.gate(a[6], b[6]), carry5);
        boolean carry6 = or.gate(and.gate(a[6], b[6]), and.gate(xor.gate(a[6], b[6]), carry5));
        result[6] = sum6;
        
        boolean sum7 = xor.gate(xor.gate(a[7], b[7]), carry6);
        boolean carry7 = or.gate(and.gate(a[7], b[7]), and.gate(xor.gate(a[7], b[7]), carry6));
        result[7] = sum7;
        
        boolean sum8 = xor.gate(xor.gate(a[8], b[8]), carry7);
        boolean carry8 = or.gate(and.gate(a[8], b[8]), and.gate(xor.gate(a[8], b[8]), carry7));
        result[8] = sum8;
        
        boolean sum9 = xor.gate(xor.gate(a[9], b[9]), carry8);
        boolean carry9 = or.gate(and.gate(a[9], b[9]), and.gate(xor.gate(a[9], b[9]), carry8));
        result[9] = sum9;
        
        boolean sum10 = xor.gate(xor.gate(a[10], b[10]), carry9);
        result[10] = sum10;
        
        return result;
    }
    
    private static boolean[] sub11(boolean[] a, boolean[] b) {
        boolean[] result = new boolean[11];
        boolean borrow = false;
        
        boolean diff0 = xor.gate(xor.gate(a[0], b[0]), borrow);
        boolean borrow0 = or.gate(or.gate(and.gate(not.gate(a[0]), b[0]), and.gate(not.gate(a[0]), borrow)), and.gate(b[0], borrow));
        result[0] = diff0;
        
        boolean diff1 = xor.gate(xor.gate(a[1], b[1]), borrow0);
        boolean borrow1 = or.gate(or.gate(and.gate(not.gate(a[1]), b[1]), and.gate(not.gate(a[1]), borrow0)), and.gate(b[1], borrow0));
        result[1] = diff1;
        
        boolean diff2 = xor.gate(xor.gate(a[2], b[2]), borrow1);
        boolean borrow2 = or.gate(or.gate(and.gate(not.gate(a[2]), b[2]), and.gate(not.gate(a[2]), borrow1)), and.gate(b[2], borrow1));
        result[2] = diff2;
        
        boolean diff3 = xor.gate(xor.gate(a[3], b[3]), borrow2);
        boolean borrow3 = or.gate(or.gate(and.gate(not.gate(a[3]), b[3]), and.gate(not.gate(a[3]), borrow2)), and.gate(b[3], borrow2));
        result[3] = diff3;
        
        boolean diff4 = xor.gate(xor.gate(a[4], b[4]), borrow3);
        boolean borrow4 = or.gate(or.gate(and.gate(not.gate(a[4]), b[4]), and.gate(not.gate(a[4]), borrow3)), and.gate(b[4], borrow3));
        result[4] = diff4;
        
        boolean diff5 = xor.gate(xor.gate(a[5], b[5]), borrow4);
        boolean borrow5 = or.gate(or.gate(and.gate(not.gate(a[5]), b[5]), and.gate(not.gate(a[5]), borrow4)), and.gate(b[5], borrow4));
        result[5] = diff5;
        
        boolean diff6 = xor.gate(xor.gate(a[6], b[6]), borrow5);
        boolean borrow6 = or.gate(or.gate(and.gate(not.gate(a[6]), b[6]), and.gate(not.gate(a[6]), borrow5)), and.gate(b[6], borrow5));
        result[6] = diff6;
        
        boolean diff7 = xor.gate(xor.gate(a[7], b[7]), borrow6);
        boolean borrow7 = or.gate(or.gate(and.gate(not.gate(a[7]), b[7]), and.gate(not.gate(a[7]), borrow6)), and.gate(b[7], borrow6));
        result[7] = diff7;
        
        boolean diff8 = xor.gate(xor.gate(a[8], b[8]), borrow7);
        boolean borrow8 = or.gate(or.gate(and.gate(not.gate(a[8]), b[8]), and.gate(not.gate(a[8]), borrow7)), and.gate(b[8], borrow7));
        result[8] = diff8;
        
        boolean diff9 = xor.gate(xor.gate(a[9], b[9]), borrow8);
        boolean borrow9 = or.gate(or.gate(and.gate(not.gate(a[9]), b[9]), and.gate(not.gate(a[9]), borrow8)), and.gate(b[9], borrow8));
        result[9] = diff9;
        
        boolean diff10 = xor.gate(xor.gate(a[10], b[10]), borrow9);
        result[10] = diff10;
        
        return result;
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
    
    private static boolean isZeroExp11(boolean[] exp) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(not.gate(exp[0]), not.gate(exp[1])), not.gate(exp[2])), not.gate(exp[3])),
            not.gate(exp[4])), not.gate(exp[5])), not.gate(exp[6])), not.gate(exp[7])),
            not.gate(exp[8])), not.gate(exp[9])), not.gate(exp[10]));
    }
    
    private static boolean isAllOnes11(boolean[] exp) {
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(
            and.gate(and.gate(exp[0], exp[1]), exp[2]), exp[3]),
            exp[4]), exp[5]), exp[6]), exp[7]), exp[8]), exp[9]), exp[10]);
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
