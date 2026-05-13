package com.follarce.machine.CPU.ALU.module.float;

import com.follarce.machine.logic.gate.*;

public class fdiv32B {
    public static boolean[] module(boolean[] a, boolean[] b) {
        boolean signA = a[31];
        boolean signB = b[31];
        
        boolean[] expA = extractExp32(a);
        boolean[] expB = extractExp32(b);
        
        boolean[] manA = extractMan32(a);
        boolean[] manB = extractMan32(b);
        
        boolean isZeroA = isZeroExp(expA);
        boolean isZeroB = isZeroExp(expB);
        
        boolean[] manAWithHidden = addHiddenBit24(manA, isZeroA);
        boolean[] manBWithHidden = addHiddenBit24(manB, isZeroB);
        
        boolean[] expAExtended = extendExp8To12(expA);
        boolean[] expBExtended = extendExp8To12(expB);
        
        boolean[] expDiff = sub12B(expAExtended, expBExtended);
        boolean[] bias = createBias32();
        boolean[] expResult = add12B(expDiff, bias);
        
        boolean[] quotient = divide24x24(manAWithHidden, manBWithHidden);
        
        boolean needNorm = not.gate(quotient[23]);
        boolean[] expAdjust = createExpAdjust(needNorm);
        boolean[] finalExp = sub12B(expResult, expAdjust);
        
        boolean[] normalizedMan = normalizeMan23(quotient, needNorm);
        
        boolean resultSign = xor.gate(signA, signB);
        
        return packFloat32(resultSign, finalExp, normalizedMan);
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
    
    private static boolean[] divide24x24(boolean[] dividend, boolean[] divisor) {
        boolean[] quotient = new boolean[24];
        boolean[] remainder = new boolean[24];
        
        remainder[0] = dividend[23];
        boolean[] shifted = shiftLeft24(remainder);
        boolean[] sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        boolean canSubtract = sub[24];
        quotient[23] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[22] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[21] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[20] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[19] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[18] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[17] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[16] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[15] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[14] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[13] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[12] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[11] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[10] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[9] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[8] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[7] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[6] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[5] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[4] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[3] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[2] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[1] = canSubtract;
        remainder = mux24B(shifted, extractLow24From25(sub), canSubtract);
        
        shifted = shiftLeft24(remainder);
        sub = sub25B(extend24To25(shifted), extend24To25(divisor));
        canSubtract = sub[24];
        quotient[0] = canSubtract;
        
        return quotient;
    }
    
    private static boolean[] shiftLeft24(boolean[] a) {
        boolean[] result = new boolean[24];
        result[0] = false;
        result[1] = a[0]; result[2] = a[1]; result[3] = a[2]; result[4] = a[3];
        result[5] = a[4]; result[6] = a[5]; result[7] = a[6]; result[8] = a[7];
        result[9] = a[8]; result[10] = a[9]; result[11] = a[10]; result[12] = a[11];
        result[13] = a[12]; result[14] = a[13]; result[15] = a[14]; result[16] = a[15];
        result[17] = a[16]; result[18] = a[17]; result[19] = a[18]; result[20] = a[19];
        result[21] = a[20]; result[22] = a[21]; result[23] = a[22];
        return result;
    }
    
    private static boolean[] extend24To25(boolean[] a) {
        boolean[] result = new boolean[25];
        result[0] = a[0]; result[1] = a[1]; result[2] = a[2]; result[3] = a[3];
        result[4] = a[4]; result[5] = a[5]; result[6] = a[6]; result[7] = a[7];
        result[8] = a[8]; result[9] = a[9]; result[10] = a[10]; result[11] = a[11];
        result[12] = a[12]; result[13] = a[13]; result[14] = a[14]; result[15] = a[15];
        result[16] = a[16]; result[17] = a[17]; result[18] = a[18]; result[19] = a[19];
        result[20] = a[20]; result[21] = a[21]; result[22] = a[22]; result[23] = a[23];
        result[24] = false;
        return result;
    }
    
    private static boolean[] extractLow24From25(boolean[] a) {
        boolean[] result = new boolean[24];
        result[0] = a[0]; result[1] = a[1]; result[2] = a[2]; result[3] = a[3];
        result[4] = a[4]; result[5] = a[5]; result[6] = a[6]; result[7] = a[7];
        result[8] = a[8]; result[9] = a[9]; result[10] = a[10]; result[11] = a[11];
        result[12] = a[12]; result[13] = a[13]; result[14] = a[14]; result[15] = a[15];
        result[16] = a[16]; result[17] = a[17]; result[18] = a[18]; result[19] = a[19];
        result[20] = a[20]; result[21] = a[21]; result[22] = a[22]; result[23] = a[23];
        return result;
    }
    
    private static boolean[] sub25B(boolean[] a, boolean[] b) {
        boolean[] notB = new boolean[25];
        notB[0] = not.gate(b[0]); notB[1] = not.gate(b[1]); notB[2] = not.gate(b[2]); notB[3] = not.gate(b[3]);
        notB[4] = not.gate(b[4]); notB[5] = not.gate(b[5]); notB[6] = not.gate(b[6]); notB[7] = not.gate(b[7]);
        notB[8] = not.gate(b[8]); notB[9] = not.gate(b[9]); notB[10] = not.gate(b[10]); notB[11] = not.gate(b[11]);
        notB[12] = not.gate(b[12]); notB[13] = not.gate(b[13]); notB[14] = not.gate(b[14]); notB[15] = not.gate(b[15]);
        notB[16] = not.gate(b[16]); notB[17] = not.gate(b[17]); notB[18] = not.gate(b[18]); notB[19] = not.gate(b[19]);
        notB[20] = not.gate(b[20]); notB[21] = not.gate(b[21]); notB[22] = not.gate(b[22]); notB[23] = not.gate(b[23]);
        notB[24] = not.gate(b[24]);
        return add25BWithCarry(a, notB, true);
    }
    
    private static boolean[] add25BWithCarry(boolean[] a, boolean[] b, boolean carryIn) {
        boolean[] result = new boolean[25];
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
        result[24] = c;
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
    
    private static boolean[] normalizeMan23(boolean[] quotient, boolean needNorm) {
        boolean[] result = new boolean[23];
        result[0] = mux2to1.module(quotient[0], quotient[1], needNorm);
        result[1] = mux2to1.module(quotient[1], quotient[2], needNorm);
        result[2] = mux2to1.module(quotient[2], quotient[3], needNorm);
        result[3] = mux2to1.module(quotient[3], quotient[4], needNorm);
        result[4] = mux2to1.module(quotient[4], quotient[5], needNorm);
        result[5] = mux2to1.module(quotient[5], quotient[6], needNorm);
        result[6] = mux2to1.module(quotient[6], quotient[7], needNorm);
        result[7] = mux2to1.module(quotient[7], quotient[8], needNorm);
        result[8] = mux2to1.module(quotient[8], quotient[9], needNorm);
        result[9] = mux2to1.module(quotient[9], quotient[10], needNorm);
        result[10] = mux2to1.module(quotient[10], quotient[11], needNorm);
        result[11] = mux2to1.module(quotient[11], quotient[12], needNorm);
        result[12] = mux2to1.module(quotient[12], quotient[13], needNorm);
        result[13] = mux2to1.module(quotient[13], quotient[14], needNorm);
        result[14] = mux2to1.module(quotient[14], quotient[15], needNorm);
        result[15] = mux2to1.module(quotient[15], quotient[16], needNorm);
        result[16] = mux2to1.module(quotient[16], quotient[17], needNorm);
        result[17] = mux2to1.module(quotient[17], quotient[18], needNorm);
        result[18] = mux2to1.module(quotient[18], quotient[19], needNorm);
        result[19] = mux2to1.module(quotient[19], quotient[20], needNorm);
        result[20] = mux2to1.module(quotient[20], quotient[21], needNorm);
        result[21] = mux2to1.module(quotient[21], quotient[22], needNorm);
        result[22] = mux2to1.module(quotient[22], quotient[23], needNorm);
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
}
