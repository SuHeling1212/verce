package com.follarce.machine.logic.gate;

public class nor {
    public static boolean gate(boolean a, boolean b) {
        return not.gate(or.gate(a, b));
    }
}