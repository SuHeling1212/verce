package com.follarce.machine.logic.gate;

public class or {
    public static boolean gate(boolean a, boolean b) {
        return nand.gate(not.gate(a), not.gate(b));
    }
}