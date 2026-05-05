package com.follarce.machine.logic.module;

import com.follarce.machine.logic.gate.*;

public class fullAdder {
    public static boolean[] module(boolean a, boolean b, boolean cin) {
        boolean[] result = new boolean[2];

        boolean[] ha1 = halfAdder.module(a, b);
        boolean[] ha2 = halfAdder.module(ha1[0], cin);

        result[0] = ha2[0];
        result[1] = or.gate(ha1[1], ha2[1]);

        return result;
    }
}