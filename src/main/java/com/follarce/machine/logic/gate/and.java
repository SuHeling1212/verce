package com.follarce.machine.logic.gate;

public class and {
    public static boolean gate(boolean a, boolean b) {
        return not.gate(nand.gate(a, b));
    }
}