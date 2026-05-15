package com.follarce.machine.CPU.ALU.RV64IMC;

import com.follarce.machine.logic.gate.and;
import com.follarce.machine.logic.gate.not;
import com.follarce.machine.logic.gate.xor;

public class compare32 {
    public static boolean module(boolean[] a, boolean[] b) {
        boolean eq0 = not.gate(xor.gate(a[0], b[0]));
        boolean eq1 = not.gate(xor.gate(a[1], b[1]));
        boolean eq2 = not.gate(xor.gate(a[2], b[2]));
        boolean eq3 = not.gate(xor.gate(a[3], b[3]));
        boolean eq4 = not.gate(xor.gate(a[4], b[4]));
        boolean eq5 = not.gate(xor.gate(a[5], b[5]));
        boolean eq6 = not.gate(xor.gate(a[6], b[6]));
        boolean eq7 = not.gate(xor.gate(a[7], b[7]));
        boolean eq8 = not.gate(xor.gate(a[8], b[8]));
        boolean eq9 = not.gate(xor.gate(a[9], b[9]));
        boolean eq10 = not.gate(xor.gate(a[10], b[10]));
        boolean eq11 = not.gate(xor.gate(a[11], b[11]));
        boolean eq12 = not.gate(xor.gate(a[12], b[12]));
        boolean eq13 = not.gate(xor.gate(a[13], b[13]));
        boolean eq14 = not.gate(xor.gate(a[14], b[14]));
        boolean eq15 = not.gate(xor.gate(a[15], b[15]));
        boolean eq16 = not.gate(xor.gate(a[16], b[16]));
        boolean eq17 = not.gate(xor.gate(a[17], b[17]));
        boolean eq18 = not.gate(xor.gate(a[18], b[18]));
        boolean eq19 = not.gate(xor.gate(a[19], b[19]));
        boolean eq20 = not.gate(xor.gate(a[20], b[20]));
        boolean eq21 = not.gate(xor.gate(a[21], b[21]));
        boolean eq22 = not.gate(xor.gate(a[22], b[22]));
        boolean eq23 = not.gate(xor.gate(a[23], b[23]));
        boolean eq24 = not.gate(xor.gate(a[24], b[24]));
        boolean eq25 = not.gate(xor.gate(a[25], b[25]));
        boolean eq26 = not.gate(xor.gate(a[26], b[26]));
        boolean eq27 = not.gate(xor.gate(a[27], b[27]));
        boolean eq28 = not.gate(xor.gate(a[28], b[28]));
        boolean eq29 = not.gate(xor.gate(a[29], b[29]));
        boolean eq30 = not.gate(xor.gate(a[30], b[30]));
        boolean eq31 = not.gate(xor.gate(a[31], b[31]));
        
        boolean s0 = and.gate(eq0, eq1);
        boolean s1 = and.gate(s0, eq2);
        boolean s2 = and.gate(s1, eq3);
        boolean s3 = and.gate(s2, eq4);
        boolean s4 = and.gate(s3, eq5);
        boolean s5 = and.gate(s4, eq6);
        boolean s6 = and.gate(s5, eq7);
        boolean s7 = and.gate(s6, eq8);
        boolean s8 = and.gate(s7, eq9);
        boolean s9 = and.gate(s8, eq10);
        boolean s10 = and.gate(s9, eq11);
        boolean s11 = and.gate(s10, eq12);
        boolean s12 = and.gate(s11, eq13);
        boolean s13 = and.gate(s12, eq14);
        boolean s14 = and.gate(s13, eq15);
        boolean s15 = and.gate(s14, eq16);
        boolean s16 = and.gate(s15, eq17);
        boolean s17 = and.gate(s16, eq18);
        boolean s18 = and.gate(s17, eq19);
        boolean s19 = and.gate(s18, eq20);
        boolean s20 = and.gate(s19, eq21);
        boolean s21 = and.gate(s20, eq22);
        boolean s22 = and.gate(s21, eq23);
        boolean s23 = and.gate(s22, eq24);
        boolean s24 = and.gate(s23, eq25);
        boolean s25 = and.gate(s24, eq26);
        boolean s26 = and.gate(s25, eq27);
        boolean s27 = and.gate(s26, eq28);
        boolean s28 = and.gate(s27, eq29);
        boolean s29 = and.gate(s28, eq30);
        boolean s30 = and.gate(s29, eq31);
        
        return s30;
    }
}
