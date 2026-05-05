package com.follarce.machine.logic.gate;

public class xor {
    public static boolean gate(boolean a, boolean b) {
        return or.gate(and.gate(a, not.gate(b)), and.gate(not.gate(a), b));
    }
}