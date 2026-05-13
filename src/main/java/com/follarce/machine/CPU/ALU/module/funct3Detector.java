package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class funct3Detector {
    public static boolean isADD(boolean[] funct3) {
        boolean not0 = not.gate(funct3[0]);
        boolean not1 = not.gate(funct3[1]);
        boolean not2 = not.gate(funct3[2]);
        return and.gate(and.gate(not0, not1), not2);
    }

    public static boolean isSLL(boolean[] funct3) {
        boolean is0 = funct3[0];
        boolean not1 = not.gate(funct3[1]);
        boolean not2 = not.gate(funct3[2]);
        return and.gate(and.gate(is0, not1), not2);
    }

    public static boolean isSLT(boolean[] funct3) {
        boolean not0 = not.gate(funct3[0]);
        boolean is1 = funct3[1];
        boolean not2 = not.gate(funct3[2]);
        return and.gate(and.gate(not0, is1), not2);
    }

    public static boolean isSLTU(boolean[] funct3) {
        boolean is0 = funct3[0];
        boolean is1 = funct3[1];
        boolean not2 = not.gate(funct3[2]);
        return and.gate(and.gate(is0, is1), not2);
    }

    public static boolean isXOR(boolean[] funct3) {
        boolean not0 = not.gate(funct3[0]);
        boolean not1 = not.gate(funct3[1]);
        boolean is2 = funct3[2];
        return and.gate(and.gate(not0, not1), is2);
    }

    public static boolean isSRL_SRA(boolean[] funct3) {
        boolean is0 = funct3[0];
        boolean not1 = not.gate(funct3[1]);
        boolean is2 = funct3[2];
        return and.gate(and.gate(is0, not1), is2);
    }

    public static boolean isOR(boolean[] funct3) {
        boolean not0 = not.gate(funct3[0]);
        boolean is1 = funct3[1];
        boolean is2 = funct3[2];
        return and.gate(and.gate(not0, is1), is2);
    }

    public static boolean isAND(boolean[] funct3) {
        boolean is0 = funct3[0];
        boolean is1 = funct3[1];
        boolean is2 = funct3[2];
        return and.gate(and.gate(is0, is1), is2);
    }

    public static boolean isMUL(boolean[] funct3) {
        return isADD(funct3);
    }

    public static boolean isMULH(boolean[] funct3) {
        return isSLL(funct3);
    }

    public static boolean isMULHSU(boolean[] funct3) {
        return isSLT(funct3);
    }

    public static boolean isMULHU(boolean[] funct3) {
        return isSLTU(funct3);
    }

    public static boolean isDIV(boolean[] funct3) {
        return isXOR(funct3);
    }

    public static boolean isDIVU(boolean[] funct3) {
        return isSRL_SRA(funct3);
    }

    public static boolean isREM(boolean[] funct3) {
        return isOR(funct3);
    }

    public static boolean isREMU(boolean[] funct3) {
        return isAND(funct3);
    }
    
    public static boolean isFADD(boolean[] funct3) {
        return isADD(funct3);
    }
    
    public static boolean isFSUB(boolean[] funct3) {
        return isSLL(funct3);
    }
    
    public static boolean isFMUL(boolean[] funct3) {
        return isSLT(funct3);
    }
    
    public static boolean isFDIV(boolean[] funct3) {
        return isSLTU(funct3);
    }
    
    public static boolean isFSGNJ(boolean[] funct3) {
        return isXOR(funct3);
    }
    
    public static boolean isFMINMAX(boolean[] funct3) {
        return isSRL_SRA(funct3);
    }
    
    public static boolean isFCVT(boolean[] funct3) {
        return isOR(funct3);
    }
    
    public static boolean isFMV_FCLASS(boolean[] funct3) {
        return isAND(funct3);
    }
}
