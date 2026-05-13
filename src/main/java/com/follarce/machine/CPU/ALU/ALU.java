package com.follarce.machine.CPU.ALU;

import com.follarce.machine.CPU.ALU.module.*;
import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.or;

public class ALU {
    
    public static boolean[] execute(
        boolean[] rs1, boolean[] rs2, 
        boolean[] funct3, boolean[] funct7,
        boolean[] opcode
    ) {
        boolean isRtype = opcodeDetector.isRtype(opcode);
        boolean isRtypeW = opcodeDetector.isRtypeW(opcode);
        
        // 根据类型选择执行路径
        boolean[] rtypeResult = new boolean[64];
        boolean[] rtypeWResult = new boolean[64];
        
        // 条件执行R型指令
        boolean[] tempRtype = executeRtype64(rs1, rs2, funct3, funct7);
        rtypeResult = mux64B(rtypeResult, tempRtype, isRtype);
        
        // 条件执行R型W指令
        boolean[] tempRtypeW = executeRtypeW32(rs1, rs2, funct3, funct7);
        rtypeWResult = mux64B(rtypeWResult, tempRtypeW, isRtypeW);
        
        // 最终结果选择
        boolean[] result0 = mux64B(rtypeResult, rtypeWResult, isRtypeW);
        boolean[] finalResult = mux64B(result0, new boolean[64], not.gate(or.gate(isRtype, isRtypeW)));
        
        return finalResult;
    }
    
    private static boolean[] executeRtype64(boolean[] rs1, boolean[] rs2, boolean[] funct3, boolean[] funct7) {
        // 解码操作类型
        boolean isAdd = decodeAdd(funct3, funct7);
        boolean isSub = decodeSub(funct3, funct7);
        boolean isSll = decodeSll(funct3);
        boolean isSlt = decodeSlt(funct3);
        boolean isSltu = decodeSltu(funct3);
        boolean isXor = decodeXor(funct3);
        boolean isSrl = decodeSrl(funct3, funct7);
        boolean isSra = decodeSra(funct3, funct7);
        boolean isOr = decodeOr(funct3);
        boolean isAnd = decodeAnd(funct3);
        
        // M扩展指令
        boolean isMul = decodeMul(funct3, funct7);
        boolean isMulh = decodeMulh(funct3, funct7);
        boolean isMulhsu = decodeMulhsu(funct3, funct7);
        boolean isMulhu = decodeMulhu(funct3, funct7);
        boolean isDiv = decodeDiv(funct3, funct7);
        boolean isDivu = decodeDivu(funct3, funct7);
        boolean isRem = decodeRem(funct3, funct7);
        boolean isRemu = decodeRemu(funct3, funct7);
        
        // 移位掩码 - 只取低6位
        boolean[] shiftMask = shiftMask6();
        boolean[] rs2Shift = and64B.module(rs2, shiftMask);
        
        // 根据操作类型选择性计算
        boolean[] addResult = conditionalAdd(rs1, rs2, isAdd);
        boolean[] subResult = conditionalSub(rs1, rs2, isSub);
        boolean[] sllResult = conditionalSll(rs1, rs2Shift, isSll);
        boolean[] sltResult = conditionalSlt(rs1, rs2, isSlt);
        boolean[] sltuResult = conditionalSltu(rs1, rs2, isSltu);
        boolean[] xorResult = conditionalXor(rs1, rs2, isXor);
        boolean[] srlResult = conditionalSrl(rs1, rs2Shift, isSrl);
        boolean[] sraResult = conditionalSra(rs1, rs2Shift, isSra);
        boolean[] orResult = conditionalOr(rs1, rs2, isOr);
        boolean[] andResult = conditionalAnd(rs1, rs2, isAnd);
        
        // M扩展条件计算
        boolean[] mulResult = conditionalMul(rs1, rs2, isMul);
        boolean[] mulhResult = conditionalMulh(rs1, rs2, isMulh);
        boolean[] mulhsuResult = conditionalMulhsu(rs1, rs2, isMulhsu);
        boolean[] mulhuResult = conditionalMulhu(rs1, rs2, isMulhu);
        boolean[] divResult = conditionalDiv(rs1, rs2, isDiv);
        boolean[] divuResult = conditionalDivu(rs1, rs2, isDivu);
        boolean[] remResult = conditionalRem(rs1, rs2, isRem);
        boolean[] remuResult = conditionalRemu(rs1, rs2, isRemu);
        
        // 合并结果 - 使用多级MUX选择最终结果
        boolean[] result0 = orResults(addResult, subResult);
        boolean[] result1 = orResults(result0, sllResult);
        boolean[] result2 = orResults(result1, sltResult);
        boolean[] result3 = orResults(result2, sltuResult);
        boolean[] result4 = orResults(result3, xorResult);
        boolean[] result5 = orResults(result4, srlResult);
        boolean[] result6 = orResults(result5, sraResult);
        boolean[] result7 = orResults(result6, orResult);
        boolean[] result8 = orResults(result7, andResult);
        boolean[] result9 = orResults(result8, mulResult);
        boolean[] result10 = orResults(result9, mulhResult);
        boolean[] result11 = orResults(result10, mulhsuResult);
        boolean[] result12 = orResults(result11, mulhuResult);
        boolean[] result13 = orResults(result12, divResult);
        boolean[] result14 = orResults(result13, divuResult);
        boolean[] result15 = orResults(result14, remResult);
        boolean[] finalResult = orResults(result15, remuResult);
        
        return finalResult;
    }
    
    private static boolean[] executeRtypeW32(boolean[] rs1, boolean[] rs2, boolean[] funct3, boolean[] funct7) {
        // 提取低32位
        boolean[] rs1_32 = extractLow32B.module(rs1);
        boolean[] rs2_32 = extractLow32B.module(rs2);
        
        // 移位掩码 - 只取低5位
        boolean[] shiftMask32 = shiftMask5();
        boolean[] rs2Shift32 = and64B.module(rs2_32, shiftMask32);
        
        // 解码操作类型
        boolean isAddw = decodeAdd(funct3, funct7);
        boolean isSubw = decodeSub(funct3, funct7);
        boolean isSllw = decodeSll(funct3);
        boolean isSrlw = decodeSrl(funct3, funct7);
        boolean isSraw = decodeSra(funct3, funct7);
        boolean isMulw = decodeMul(funct3, funct7);
        boolean isDivw = decodeDiv(funct3, funct7);
        boolean isDivuw = decodeDivu(funct3, funct7);
        boolean isRemw = decodeRem(funct3, funct7);
        boolean isRemuw = decodeRemu(funct3, funct7);
        
        // 条件计算32位操作并符号扩展
        boolean[] addwResult = conditionalAdd32(rs1_32, rs2_32, isAddw);
        boolean[] subwResult = conditionalSub32(rs1_32, rs2_32, isSubw);
        boolean[] sllwResult = conditionalSll32(rs1_32, rs2Shift32, isSllw);
        boolean[] srlwResult = conditionalSrl32(rs1_32, rs2Shift32, isSrlw);
        boolean[] srawResult = conditionalSra32(rs1_32, rs2Shift32, isSraw);
        boolean[] mulwResult = conditionalMul32(rs1_32, rs2_32, isMulw);
        boolean[] divwResult = conditionalDiv32(rs1_32, rs2_32, isDivw);
        boolean[] divuwResult = conditionalDivu32(rs1_32, rs2_32, isDivuw);
        boolean[] remwResult = conditionalRem32(rs1_32, rs2_32, isRemw);
        boolean[] remuwResult = conditionalRemu32(rs1_32, rs2_32, isRemuw);
        
        // 合并结果
        boolean[] result0 = orResults(addwResult, subwResult);
        boolean[] result1 = orResults(result0, sllwResult);
        boolean[] result2 = orResults(result1, srlwResult);
        boolean[] result3 = orResults(result2, srawResult);
        boolean[] result4 = orResults(result3, mulwResult);
        boolean[] result5 = orResults(result4, divwResult);
        boolean[] result6 = orResults(result5, divuwResult);
        boolean[] result7 = orResults(result6, remwResult);
        boolean[] finalResult = orResults(result7, remuwResult);
        
        return finalResult;
    }
    
    // ==================== 条件执行函数 ====================
    
    private static boolean[] conditionalAdd(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = adder64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSub(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = subtractor64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSll(boolean[] a, boolean[] shamt, boolean cond) {
        boolean[] result = sll64B.module(a, shamt);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSlt(boolean[] a, boolean[] b, boolean cond) {
        boolean less = signedComparator64B.module(a, b);
        boolean[] result = new boolean[64];
        result[0] = less;
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSltu(boolean[] a, boolean[] b, boolean cond) {
        boolean less = unsignedComparator64B.module(a, b);
        boolean[] result = new boolean[64];
        result[0] = less;
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalXor(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = xor64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSrl(boolean[] a, boolean[] shamt, boolean cond) {
        boolean[] result = srl64B.module(a, shamt);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSra(boolean[] a, boolean[] shamt, boolean cond) {
        boolean[] result = sra64B.module(a, shamt);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalOr(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = or64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalAnd(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = and64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalMul(boolean[] a, boolean[] b, boolean cond) {
        boolean[] full = multiplier64B.module(a, b);
        boolean[] result = extractLow64(full);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalMulh(boolean[] a, boolean[] b, boolean cond) {
        boolean[] full = multiplier64B.module(a, b);
        boolean[] result = extractHigh64(full);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalMulhsu(boolean[] a, boolean[] b, boolean cond) {
        boolean[] aUnsigned = makeUnsignedFixed(a);
        boolean[] full = multiplier64B.module(aUnsigned, b);
        boolean[] result = extractHigh64(full);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalMulhu(boolean[] a, boolean[] b, boolean cond) {
        boolean[] aUnsigned = makeUnsignedFixed(a);
        boolean[] bUnsigned = makeUnsignedFixed(b);
        boolean[] full = multiplier64B.module(aUnsigned, bUnsigned);
        boolean[] result = extractHigh64(full);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalDiv(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = div64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalDivu(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = divu64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalRem(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = rem64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalRemu(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result = remu64B.module(a, b);
        return conditionalZero(result, cond);
    }
    
    // ==================== 32位条件执行函数 ====================
    
    private static boolean[] conditionalAdd32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result32 = adder32B.module(a, b);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSub32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] result32 = subtractor32B.module(a, b);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSll32(boolean[] a, boolean[] shamt, boolean cond) {
        boolean[] result32 = sll32B.module(a, shamt);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSrl32(boolean[] a, boolean[] shamt, boolean cond) {
        boolean[] result32 = srl32B.module(a, shamt);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalSra32(boolean[] a, boolean[] shamt, boolean cond) {
        boolean[] result32 = sra32B.module(a, shamt);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalMul32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] full = multiplier32B.module(a, b);
        boolean[] low32 = extractLow32B.module(full);
        boolean[] result = sext32B.module(low32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalDiv32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] a64 = sext32B.module(a);
        boolean[] b64 = sext32B.module(b);
        boolean[] result64 = div64B.module(a64, b64);
        boolean[] result32 = extractLow32B.module(result64);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalDivu32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] a64 = zext32B.module(a);
        boolean[] b64 = zext32B.module(b);
        boolean[] result64 = divu64B.module(a64, b64);
        boolean[] result32 = extractLow32B.module(result64);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalRem32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] a64 = sext32B.module(a);
        boolean[] b64 = sext32B.module(b);
        boolean[] result64 = rem64B.module(a64, b64);
        boolean[] result32 = extractLow32B.module(result64);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    private static boolean[] conditionalRemu32(boolean[] a, boolean[] b, boolean cond) {
        boolean[] a64 = zext32B.module(a);
        boolean[] b64 = zext32B.module(b);
        boolean[] result64 = remu64B.module(a64, b64);
        boolean[] result32 = extractLow32B.module(result64);
        boolean[] result = sext32B.module(result32);
        return conditionalZero(result, cond);
    }
    
    // ==================== 辅助函数 ====================
    
    private static boolean[] conditionalZero(boolean[] value, boolean cond) {
        boolean[] zero = new boolean[64];
        return mux64B(zero, value, cond);
    }
    
    private static boolean[] orResults(boolean[] existing, boolean[] newResult) {
        boolean[] result = new boolean[64];
        result[0] = or.gate(existing[0], newResult[0]);
        result[1] = or.gate(existing[1], newResult[1]);
        result[2] = or.gate(existing[2], newResult[2]);
        result[3] = or.gate(existing[3], newResult[3]);
        result[4] = or.gate(existing[4], newResult[4]);
        result[5] = or.gate(existing[5], newResult[5]);
        result[6] = or.gate(existing[6], newResult[6]);
        result[7] = or.gate(existing[7], newResult[7]);
        result[8] = or.gate(existing[8], newResult[8]);
        result[9] = or.gate(existing[9], newResult[9]);
        result[10] = or.gate(existing[10], newResult[10]);
        result[11] = or.gate(existing[11], newResult[11]);
        result[12] = or.gate(existing[12], newResult[12]);
        result[13] = or.gate(existing[13], newResult[13]);
        result[14] = or.gate(existing[14], newResult[14]);
        result[15] = or.gate(existing[15], newResult[15]);
        result[16] = or.gate(existing[16], newResult[16]);
        result[17] = or.gate(existing[17], newResult[17]);
        result[18] = or.gate(existing[18], newResult[18]);
        result[19] = or.gate(existing[19], newResult[19]);
        result[20] = or.gate(existing[20], newResult[20]);
        result[21] = or.gate(existing[21], newResult[21]);
        result[22] = or.gate(existing[22], newResult[22]);
        result[23] = or.gate(existing[23], newResult[23]);
        result[24] = or.gate(existing[24], newResult[24]);
        result[25] = or.gate(existing[25], newResult[25]);
        result[26] = or.gate(existing[26], newResult[26]);
        result[27] = or.gate(existing[27], newResult[27]);
        result[28] = or.gate(existing[28], newResult[28]);
        result[29] = or.gate(existing[29], newResult[29]);
        result[30] = or.gate(existing[30], newResult[30]);
        result[31] = or.gate(existing[31], newResult[31]);
        result[32] = or.gate(existing[32], newResult[32]);
        result[33] = or.gate(existing[33], newResult[33]);
        result[34] = or.gate(existing[34], newResult[34]);
        result[35] = or.gate(existing[35], newResult[35]);
        result[36] = or.gate(existing[36], newResult[36]);
        result[37] = or.gate(existing[37], newResult[37]);
        result[38] = or.gate(existing[38], newResult[38]);
        result[39] = or.gate(existing[39], newResult[39]);
        result[40] = or.gate(existing[40], newResult[40]);
        result[41] = or.gate(existing[41], newResult[41]);
        result[42] = or.gate(existing[42], newResult[42]);
        result[43] = or.gate(existing[43], newResult[43]);
        result[44] = or.gate(existing[44], newResult[44]);
        result[45] = or.gate(existing[45], newResult[45]);
        result[46] = or.gate(existing[46], newResult[46]);
        result[47] = or.gate(existing[47], newResult[47]);
        result[48] = or.gate(existing[48], newResult[48]);
        result[49] = or.gate(existing[49], newResult[49]);
        result[50] = or.gate(existing[50], newResult[50]);
        result[51] = or.gate(existing[51], newResult[51]);
        result[52] = or.gate(existing[52], newResult[52]);
        result[53] = or.gate(existing[53], newResult[53]);
        result[54] = or.gate(existing[54], newResult[54]);
        result[55] = or.gate(existing[55], newResult[55]);
        result[56] = or.gate(existing[56], newResult[56]);
        result[57] = or.gate(existing[57], newResult[57]);
        result[58] = or.gate(existing[58], newResult[58]);
        result[59] = or.gate(existing[59], newResult[59]);
        result[60] = or.gate(existing[60], newResult[60]);
        result[61] = or.gate(existing[61], newResult[61]);
        result[62] = or.gate(existing[62], newResult[62]);
        result[63] = or.gate(existing[63], newResult[63]);
        return result;
    }
    
    // ==================== 指令解码函数 ====================
    
    private static boolean decodeAdd(boolean[] funct3, boolean[] funct7) {
        boolean isAddSub = and.gate(and.gate(not.gate(funct3[0]), not.gate(funct3[1])), not.gate(funct3[2]));
        return and.gate(isAddSub, not.gate(funct7[5]));
    }
    
    private static boolean decodeSub(boolean[] funct3, boolean[] funct7) {
        boolean isAddSub = and.gate(and.gate(not.gate(funct3[0]), not.gate(funct3[1])), not.gate(funct3[2]));
        return and.gate(isAddSub, funct7[5]);
    }
    
    private static boolean decodeSll(boolean[] funct3) {
        return and.gate(and.gate(funct3[0], not.gate(funct3[1])), not.gate(funct3[2]));
    }
    
    private static boolean decodeSlt(boolean[] funct3) {
        return and.gate(and.gate(not.gate(funct3[0]), funct3[1]), not.gate(funct3[2]));
    }
    
    private static boolean decodeSltu(boolean[] funct3) {
        return and.gate(and.gate(funct3[0], funct3[1]), not.gate(funct3[2]));
    }
    
    private static boolean decodeXor(boolean[] funct3) {
        return and.gate(and.gate(not.gate(funct3[0]), not.gate(funct3[1])), funct3[2]);
    }
    
    private static boolean decodeSrl(boolean[] funct3, boolean[] funct7) {
        boolean isSrlSra = and.gate(and.gate(funct3[0], not.gate(funct3[1])), funct3[2]);
        return and.gate(isSrlSra, not.gate(funct7[5]));
    }
    
    private static boolean decodeSra(boolean[] funct3, boolean[] funct7) {
        boolean isSrlSra = and.gate(and.gate(funct3[0], not.gate(funct3[1])), funct3[2]);
        return and.gate(isSrlSra, funct7[5]);
    }
    
    private static boolean decodeOr(boolean[] funct3) {
        return and.gate(and.gate(not.gate(funct3[0]), funct3[1]), funct3[2]);
    }
    
    private static boolean decodeAnd(boolean[] funct3) {
        return and.gate(and.gate(funct3[0], funct3[1]), funct3[2]);
    }
    
    private static boolean isMExtension(boolean[] funct7) {
        return and.gate(
            and.gate(funct7[0], not.gate(funct7[1])),
            and.gate(and.gate(not.gate(funct7[2]), not.gate(funct7[3])),
                     and.gate(and.gate(not.gate(funct7[4]), not.gate(funct7[5])),
                              not.gate(funct7[6])))
        );
    }
    
    private static boolean decodeMul(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7), 
               and.gate(and.gate(not.gate(funct3[0]), not.gate(funct3[1])), not.gate(funct3[2])));
    }
    
    private static boolean decodeMulh(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(funct3[0], not.gate(funct3[1])), not.gate(funct3[2])));
    }
    
    private static boolean decodeMulhsu(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(not.gate(funct3[0]), funct3[1]), not.gate(funct3[2])));
    }
    
    private static boolean decodeMulhu(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(funct3[0], funct3[1]), not.gate(funct3[2])));
    }
    
    private static boolean decodeDiv(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(not.gate(funct3[0]), not.gate(funct3[1])), funct3[2]));
    }
    
    private static boolean decodeDivu(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(funct3[0], not.gate(funct3[1])), funct3[2]));
    }
    
    private static boolean decodeRem(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(not.gate(funct3[0]), funct3[1]), funct3[2]));
    }
    
    private static boolean decodeRemu(boolean[] funct3, boolean[] funct7) {
        return and.gate(isMExtension(funct7),
               and.gate(and.gate(funct3[0], funct3[1]), funct3[2]));
    }
    
    // ==================== 辅助函数 ====================
    
    private static boolean[] makeUnsignedFixed(boolean[] in) {
        boolean[] result = new boolean[64];
        result[0] = in[0];
        result[1] = in[1];
        result[2] = in[2];
        result[3] = in[3];
        result[4] = in[4];
        result[5] = in[5];
        result[6] = in[6];
        result[7] = in[7];
        result[8] = in[8];
        result[9] = in[9];
        result[10] = in[10];
        result[11] = in[11];
        result[12] = in[12];
        result[13] = in[13];
        result[14] = in[14];
        result[15] = in[15];
        result[16] = in[16];
        result[17] = in[17];
        result[18] = in[18];
        result[19] = in[19];
        result[20] = in[20];
        result[21] = in[21];
        result[22] = in[22];
        result[23] = in[23];
        result[24] = in[24];
        result[25] = in[25];
        result[26] = in[26];
        result[27] = in[27];
        result[28] = in[28];
        result[29] = in[29];
        result[30] = in[30];
        result[31] = in[31];
        result[32] = in[32];
        result[33] = in[33];
        result[34] = in[34];
        result[35] = in[35];
        result[36] = in[36];
        result[37] = in[37];
        result[38] = in[38];
        result[39] = in[39];
        result[40] = in[40];
        result[41] = in[41];
        result[42] = in[42];
        result[43] = in[43];
        result[44] = in[44];
        result[45] = in[45];
        result[46] = in[46];
        result[47] = in[47];
        result[48] = in[48];
        result[49] = in[49];
        result[50] = in[50];
        result[51] = in[51];
        result[52] = in[52];
        result[53] = in[53];
        result[54] = in[54];
        result[55] = in[55];
        result[56] = in[56];
        result[57] = in[57];
        result[58] = in[58];
        result[59] = in[59];
        result[60] = in[60];
        result[61] = in[61];
        result[62] = in[62];
        result[63] = false;
        return result;
    }
    
    private static boolean[] shiftMask6() {
        boolean[] mask = new boolean[64];
        mask[0] = true;
        mask[1] = true;
        mask[2] = true;
        mask[3] = true;
        mask[4] = true;
        mask[5] = true;
        return mask;
    }
    
    private static boolean[] shiftMask5() {
        boolean[] mask = new boolean[64];
        mask[0] = true;
        mask[1] = true;
        mask[2] = true;
        mask[3] = true;
        mask[4] = true;
        return mask;
    }
    
    private static boolean[] extractLow64(boolean[] in128) {
        boolean[] result = new boolean[64];
        result[0] = in128[0];
        result[1] = in128[1];
        result[2] = in128[2];
        result[3] = in128[3];
        result[4] = in128[4];
        result[5] = in128[5];
        result[6] = in128[6];
        result[7] = in128[7];
        result[8] = in128[8];
        result[9] = in128[9];
        result[10] = in128[10];
        result[11] = in128[11];
        result[12] = in128[12];
        result[13] = in128[13];
        result[14] = in128[14];
        result[15] = in128[15];
        result[16] = in128[16];
        result[17] = in128[17];
        result[18] = in128[18];
        result[19] = in128[19];
        result[20] = in128[20];
        result[21] = in128[21];
        result[22] = in128[22];
        result[23] = in128[23];
        result[24] = in128[24];
        result[25] = in128[25];
        result[26] = in128[26];
        result[27] = in128[27];
        result[28] = in128[28];
        result[29] = in128[29];
        result[30] = in128[30];
        result[31] = in128[31];
        result[32] = in128[32];
        result[33] = in128[33];
        result[34] = in128[34];
        result[35] = in128[35];
        result[36] = in128[36];
        result[37] = in128[37];
        result[38] = in128[38];
        result[39] = in128[39];
        result[40] = in128[40];
        result[41] = in128[41];
        result[42] = in128[42];
        result[43] = in128[43];
        result[44] = in128[44];
        result[45] = in128[45];
        result[46] = in128[46];
        result[47] = in128[47];
        result[48] = in128[48];
        result[49] = in128[49];
        result[50] = in128[50];
        result[51] = in128[51];
        result[52] = in128[52];
        result[53] = in128[53];
        result[54] = in128[54];
        result[55] = in128[55];
        result[56] = in128[56];
        result[57] = in128[57];
        result[58] = in128[58];
        result[59] = in128[59];
        result[60] = in128[60];
        result[61] = in128[61];
        result[62] = in128[62];
        result[63] = in128[63];
        return result;
    }
    
    private static boolean[] extractHigh64(boolean[] in128) {
        boolean[] result = new boolean[64];
        result[0] = in128[64];
        result[1] = in128[65];
        result[2] = in128[66];
        result[3] = in128[67];
        result[4] = in128[68];
        result[5] = in128[69];
        result[6] = in128[70];
        result[7] = in128[71];
        result[8] = in128[72];
        result[9] = in128[73];
        result[10] = in128[74];
        result[11] = in128[75];
        result[12] = in128[76];
        result[13] = in128[77];
        result[14] = in128[78];
        result[15] = in128[79];
        result[16] = in128[80];
        result[17] = in128[81];
        result[18] = in128[82];
        result[19] = in128[83];
        result[20] = in128[84];
        result[21] = in128[85];
        result[22] = in128[86];
        result[23] = in128[87];
        result[24] = in128[88];
        result[25] = in128[89];
        result[26] = in128[90];
        result[27] = in128[91];
        result[28] = in128[92];
        result[29] = in128[93];
        result[30] = in128[94];
        result[31] = in128[95];
        result[32] = in128[96];
        result[33] = in128[97];
        result[34] = in128[98];
        result[35] = in128[99];
        result[36] = in128[100];
        result[37] = in128[101];
        result[38] = in128[102];
        result[39] = in128[103];
        result[40] = in128[104];
        result[41] = in128[105];
        result[42] = in128[106];
        result[43] = in128[107];
        result[44] = in128[108];
        result[45] = in128[109];
        result[46] = in128[110];
        result[47] = in128[111];
        result[48] = in128[112];
        result[49] = in128[113];
        result[50] = in128[114];
        result[51] = in128[115];
        result[52] = in128[116];
        result[53] = in128[117];
        result[54] = in128[118];
        result[55] = in128[119];
        result[56] = in128[120];
        result[57] = in128[121];
        result[58] = in128[122];
        result[59] = in128[123];
        result[60] = in128[124];
        result[61] = in128[125];
        result[62] = in128[126];
        result[63] = in128[127];
        return result;
    }
    
    private static boolean[] mux64B(boolean[] a, boolean[] b, boolean sel) {
        boolean[] result = new boolean[64];
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
        result[53] = mux2to1.module(a[53], b[53], sel);
        result[54] = mux2to1.module(a[54], b[54], sel);
        result[55] = mux2to1.module(a[55], b[55], sel);
        result[56] = mux2to1.module(a[56], b[56], sel);
        result[57] = mux2to1.module(a[57], b[57], sel);
        result[58] = mux2to1.module(a[58], b[58], sel);
        result[59] = mux2to1.module(a[59], b[59], sel);
        result[60] = mux2to1.module(a[60], b[60], sel);
        result[61] = mux2to1.module(a[61], b[61], sel);
        result[62] = mux2to1.module(a[62], b[62], sel);
        result[63] = mux2to1.module(a[63], b[63], sel);
        return result;
    }
}