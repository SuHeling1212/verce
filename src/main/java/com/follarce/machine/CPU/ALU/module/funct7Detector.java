package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class funct7Detector {
    public static boolean isADD_SRL(boolean[] funct7) {
        boolean not0 = not.gate(funct7[0]);
        boolean not1 = not.gate(funct7[1]);
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, not1), not2), not3), not4), not5), not6);
    }

    public static boolean isSUB_SRA(boolean[] funct7) {
        boolean not0 = not.gate(funct7[0]);
        boolean not1 = not.gate(funct7[1]);
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean is5 = funct7[5];
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, not1), not2), not3), not4), is5), not6);
    }

    public static boolean isMExtension(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean not1 = not.gate(funct7[1]);
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, not1), not2), not3), not4), not5), not6);
    }
    
    public static boolean isF_S(boolean[] funct7) {
        boolean not0 = not.gate(funct7[0]);
        boolean not1 = not.gate(funct7[1]);
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, not1), not2), not3), not4), not5), not6);
    }
    
    public static boolean isF_D(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean not1 = not.gate(funct7[1]);
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, not1), not2), not3), not4), not5), not6);
    }
    
    public static boolean isFSGNJ(boolean[] funct7) {
        return isF_S(funct7);
    }
    
    public static boolean isFSGNJN(boolean[] funct7) {
        boolean not0 = not.gate(funct7[0]);
        boolean is1 = funct7[1];
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, is1), not2), not3), not4), not5), not6);
    }
    
    public static boolean isFSGNJX(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean is1 = funct7[1];
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), not2), not3), not4), not5), not6);
    }
    
    public static boolean isFMIN(boolean[] funct7) {
        return isF_S(funct7);
    }
    
    public static boolean isFMAX(boolean[] funct7) {
        boolean not0 = not.gate(funct7[0]);
        boolean is1 = funct7[1];
        boolean not2 = not.gate(funct7[2]);
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(not0, is1), not2), not3), not4), not5), not6);
    }
    
    public static boolean isFCVT_S_D(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean not1 = not.gate(funct7[1]);
        boolean is2 = funct7[2];
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, not1), is2), not3), not4), not5), not6);
    }
    
    public static boolean isFCVT_D_S(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean is1 = funct7[1];
        boolean is2 = funct7[2];
        boolean not3 = not.gate(funct7[3]);
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), not3), not4), not5), not6);
    }
    
    public static boolean isFCVT_L_D(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean is1 = funct7[1];
        boolean is2 = funct7[2];
        boolean is3 = funct7[3];
        boolean not4 = not.gate(funct7[4]);
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), is3), not4), not5), not6);
    }
    
    public static boolean isFMV_X_D(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean is1 = funct7[1];
        boolean is2 = funct7[2];
        boolean is3 = funct7[3];
        boolean is4 = funct7[4];
        boolean not5 = not.gate(funct7[5]);
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), is3), is4), not5), not6);
    }
    
    public static boolean isFMV_D_X(boolean[] funct7) {
        boolean is0 = funct7[0];
        boolean is1 = funct7[1];
        boolean is2 = funct7[2];
        boolean is3 = funct7[3];
        boolean is4 = funct7[4];
        boolean is5 = funct7[5];
        boolean not6 = not.gate(funct7[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), is3), is4), is5), not6);
    }
}
