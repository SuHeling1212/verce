package com.follarce.machine.logic.gate;

public class not {
    public static boolean gate(boolean a) {
        return nand.gate(a, a);
    }
}