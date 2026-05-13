package com.follarce.machine.CPU.ALU.module;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;

public class opcodeDetector {
    public static boolean isRtype(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean not2 = not.gate(opcode[2]);
        boolean not3 = not.gate(opcode[3]);
        boolean is4 = opcode[4];
        boolean is5 = opcode[5];
        boolean not6 = not.gate(opcode[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), not2), not3), is4), is5), not6);
    }

    public static boolean isItype(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean not2 = not.gate(opcode[2]);
        boolean not3 = not.gate(opcode[3]);
        boolean is4 = opcode[4];
        boolean not5 = not.gate(opcode[5]);
        boolean not6 = not.gate(opcode[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), not2), not3), is4), not5), not6);
    }

    public static boolean isRtypeW(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean not2 = not.gate(opcode[2]);
        boolean is3 = opcode[3];
        boolean is4 = opcode[4];
        boolean is5 = opcode[5];
        boolean not6 = not.gate(opcode[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), not2), is3), is4), is5), not6);
    }

    public static boolean isItypeW(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean is2 = opcode[2];
        boolean not3 = not.gate(opcode[3]);
        boolean is4 = opcode[4];
        boolean not5 = not.gate(opcode[5]);
        boolean not6 = not.gate(opcode[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), not3), is4), not5), not6);
    }

    public static boolean isFtype(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean not2 = not.gate(opcode[2]);
        boolean is3 = opcode[3];
        boolean not4 = not.gate(opcode[4]);
        boolean is5 = opcode[5];
        boolean is6 = opcode[6];
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), not2), is3), not4), is5), is6);
    }

    public static boolean isUtype(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean is2 = opcode[2];
        boolean is3 = opcode[3];
        boolean not4 = not.gate(opcode[4]);
        boolean is5 = opcode[5];
        boolean not6 = not.gate(opcode[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), is2), is3), not4), is5), not6);
    }

    public static boolean isLUI(boolean[] opcode) {
        return isUtype(opcode);
    }

    public static boolean isAUIPC(boolean[] opcode) {
        boolean is0 = opcode[0];
        boolean is1 = opcode[1];
        boolean not2 = not.gate(opcode[2]);
        boolean is3 = opcode[3];
        boolean not4 = not.gate(opcode[4]);
        boolean is5 = opcode[5];
        boolean not6 = not.gate(opcode[6]);
        return and.gate(and.gate(and.gate(and.gate(and.gate(and.gate(is0, is1), not2), is3), not4), is5), not6);
    }
}
