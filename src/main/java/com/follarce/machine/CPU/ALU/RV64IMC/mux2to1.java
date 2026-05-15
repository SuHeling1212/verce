package com.follarce.machine.CPU.ALU.RV64IMC;

import com.follarce.machine.logic.gate.*;

public class mux2to1 {
    public static boolean module(boolean a, boolean b, boolean sel) {

        return or.gate(
                and.gate(a, not.gate(sel)),
                and.gate(b, sel));
    }
}