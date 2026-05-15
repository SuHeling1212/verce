package com.follarce.machine.CPU.ALU.RV64IMC;

public class mux32to1_64 {
    public static boolean[] module(
        boolean[] in0, boolean[] in1, boolean[] in2, boolean[] in3,
        boolean[] in4, boolean[] in5, boolean[] in6, boolean[] in7,
        boolean[] in8, boolean[] in9, boolean[] in10, boolean[] in11,
        boolean[] in12, boolean[] in13, boolean[] in14, boolean[] in15,
        boolean[] in16, boolean[] in17, boolean[] in18, boolean[] in19,
        boolean[] in20, boolean[] in21, boolean[] in22, boolean[] in23,
        boolean[] in24, boolean[] in25, boolean[] in26, boolean[] in27,
        boolean[] sel
    ) {
        boolean[] s0 = mux64B.module(in0, in1, sel[0]);
        boolean[] s1 = mux64B.module(in2, in3, sel[0]);
        boolean[] s2 = mux64B.module(in4, in5, sel[0]);
        boolean[] s3 = mux64B.module(in6, in7, sel[0]);
        boolean[] s4 = mux64B.module(in8, in9, sel[0]);
        boolean[] s5 = mux64B.module(in10, in11, sel[0]);
        boolean[] s6 = mux64B.module(in12, in13, sel[0]);
        boolean[] s7 = mux64B.module(in14, in15, sel[0]);
        boolean[] s8 = mux64B.module(in16, in17, sel[0]);
        boolean[] s9 = mux64B.module(in18, in19, sel[0]);
        boolean[] s10 = mux64B.module(in20, in21, sel[0]);
        boolean[] s11 = mux64B.module(in22, in23, sel[0]);
        boolean[] s12 = mux64B.module(in24, in25, sel[0]);
        boolean[] s13 = mux64B.module(in26, in27, sel[0]);
        
        boolean[] t0 = mux64B.module(s0, s1, sel[1]);
        boolean[] t1 = mux64B.module(s2, s3, sel[1]);
        boolean[] t2 = mux64B.module(s4, s5, sel[1]);
        boolean[] t3 = mux64B.module(s6, s7, sel[1]);
        boolean[] t4 = mux64B.module(s8, s9, sel[1]);
        boolean[] t5 = mux64B.module(s10, s11, sel[1]);
        boolean[] t6 = mux64B.module(s12, s13, sel[1]);
        
        boolean[] u0 = mux64B.module(t0, t1, sel[2]);
        boolean[] u1 = mux64B.module(t2, t3, sel[2]);
        boolean[] u2 = mux64B.module(t4, t5, sel[2]);
        boolean[] u3 = mux64B.module(t6, t6, sel[2]);
        
        boolean[] v0 = mux64B.module(u0, u1, sel[3]);
        boolean[] v1 = mux64B.module(u2, u3, sel[3]);
        
        return mux64B.module(v0, v1, sel[4]);
    }
}
