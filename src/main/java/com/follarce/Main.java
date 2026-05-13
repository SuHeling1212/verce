package com.follarce;

import com.follarce.machine.CPU.ALU.ALU;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RV64 ALU 测试开始 ===\n");
        
        testBasicOperations();
        testMExtension();
        test32BitOperations();
        testEdgeCases();
        
        System.out.println("\n=== 所有测试完成 ===");
    }
    
    private static void testBasicOperations() {
        System.out.println("【测试1：基础64位运算】");
        
        boolean[] a = createNumber(42);
        boolean[] b = createNumber(13);
        
        boolean[] funct3 = new boolean[3];
        boolean[] funct7 = new boolean[7];
        boolean[] opcode = createOpcode(0x33);
        
        boolean[] result;
        
        result = testOperation("ADD", a, b, funct3, funct7, opcode, 0, 0x00);
        printResult("ADD", 42, 13, result);
        
        result = testOperation("SUB", a, b, funct3, funct7, opcode, 0, 0x20);
        printResult("SUB", 42, 13, result);
        
        result = testOperation("AND", a, b, funct3, funct7, opcode, 7, 0x00);
        printResult("AND", 42, 13, result);
        
        result = testOperation("OR", a, b, funct3, funct7, opcode, 6, 0x00);
        printResult("OR", 42, 13, result);
        
        result = testOperation("XOR", a, b, funct3, funct7, opcode, 4, 0x00);
        printResult("XOR", 42, 13, result);
        
        System.out.println();
    }
    
    private static void testMExtension() {
        System.out.println("【测试2：M扩展乘除法】");
        
        boolean[] a = createNumber(123);
        boolean[] b = createNumber(456);
        
        boolean[] funct3 = new boolean[3];
        boolean[] funct7 = new boolean[7];
        boolean[] opcode = createOpcode(0x33);
        
        boolean[] result;
        
        result = testOperation("MUL", a, b, funct3, funct7, opcode, 0, 0x01);
        printResult("MUL", 123, 456, result);
        
        result = testOperation("DIV", a, b, funct3, funct7, opcode, 4, 0x01);
        printResult("DIV", 123, 456, result);
        
        result = testOperation("DIVU", a, b, funct3, funct7, opcode, 5, 0x01);
        printResult("DIVU", 123, 456, result);
        
        result = testOperation("REM", a, b, funct3, funct7, opcode, 6, 0x01);
        printResult("REM", 123, 456, result);
        
        System.out.println();
    }
    
    private static void test32BitOperations() {
        System.out.println("【测试3：32位运算】");
        
        boolean[] a = createNumber(100);
        boolean[] b = createNumber(25);
        
        boolean[] funct3 = new boolean[3];
        boolean[] funct7 = new boolean[7];
        boolean[] opcode = createOpcode(0x3B);
        
        boolean[] result;
        
        System.out.println("  调试: opcode = " + opcodeToString(opcode));
        
        result = testOperation("ADDW", a, b, funct3, funct7, opcode, 0, 0x00);
        printResult("ADDW", 100, 25, result);
        
        result = testOperation("SUBW", a, b, funct3, funct7, opcode, 0, 0x20);
        printResult("SUBW", 100, 25, result);
        
        result = testOperation("SLLW", a, b, funct3, funct7, opcode, 1, 0x00);
        printResult("SLLW", 100, 25, result);
        
        result = testOperation("SRLW", a, b, funct3, funct7, opcode, 5, 0x00);
        printResult("SRLW", 100, 25, result);
        
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("【测试4：边界情况】");
        
        boolean[] zero = createNumber(0);
        boolean[] one = createNumber(1);
        boolean[] max = createMaxNumber();
        
        boolean[] funct3 = new boolean[3];
        boolean[] funct7 = new boolean[7];
        boolean[] opcode = createOpcode(0x33);
        
        boolean[] result;
        
        result = testOperation("ADD (0+1)", zero, one, funct3, funct7, opcode, 0, 0x00);
        printResult("ADD", 0, 1, result);
        
        result = testOperation("AND (max&1)", max, one, funct3, funct7, opcode, 7, 0x00);
        printResult("AND", -1, 1, result);
        
        result = testOperation("OR (0|max)", zero, max, funct3, funct7, opcode, 6, 0x00);
        printResult("OR", 0, -1, result);
        
        System.out.println();
    }
    
    private static boolean[] testOperation(String name, boolean[] a, boolean[] b, 
                                          boolean[] funct3, boolean[] funct7, 
                                          boolean[] opcode, int f3, int f7) {
        setFunct3(funct3, f3);
        setFunct7(funct7, f7);
        System.out.println("  调试: " + name + " funct3=" + f3 + " funct7=" + f7);
        return ALU.execute(a, b, funct3, funct7, opcode);
    }
    
    private static boolean[] createNumber(long value) {
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            result[i] = ((value >> i) & 1) == 1;
        }
        return result;
    }
    
    private static boolean[] createMaxNumber() {
        boolean[] result = new boolean[64];
        for (int i = 0; i < 64; i++) {
            result[i] = true;
        }
        return result;
    }
    
    private static boolean[] createOpcode(int opcode) {
        boolean[] result = new boolean[7];
        for (int i = 0; i < 7; i++) {
            result[i] = ((opcode >> i) & 1) == 1;
        }
        return result;
    }
    
    private static void setFunct3(boolean[] funct3, int value) {
        funct3[0] = ((value >> 0) & 1) == 1;
        funct3[1] = ((value >> 1) & 1) == 1;
        funct3[2] = ((value >> 2) & 1) == 1;
    }
    
    private static void setFunct7(boolean[] funct7, int value) {
        for (int i = 0; i < 7; i++) {
            funct7[i] = ((value >> i) & 1) == 1;
        }
    }
    
    private static void printResult(String op, long a, long b, boolean[] result) {
        long value = booleanArrayToLong(result);
        System.out.println(String.format("  %s: %d %s %d = %d (0x%X)", 
            op, a, getOperatorSymbol(op), b, value, value));
    }
    
    private static long booleanArrayToLong(boolean[] arr) {
        long result = 0;
        for (int i = 0; i < 64; i++) {
            if (arr[i]) {
                result |= (1L << i);
            }
        }
        return result;
    }
    
    private static String opcodeToString(boolean[] opcode) {
        StringBuilder sb = new StringBuilder();
        for (int i = 6; i >= 0; i--) {
            sb.append(opcode[i] ? '1' : '0');
        }
        return sb.toString();
    }
    
    private static String getOperatorSymbol(String op) {
        switch (op) {
            case "ADD":
            case "ADDW":
                return "+";
            case "SUB":
            case "SUBW":
                return "-";
            case "AND":
                return "&";
            case "OR":
                return "|";
            case "XOR":
                return "^";
            case "MUL":
            case "MULW":
                return "*";
            case "DIV":
            case "DIVU":
            case "DIVW":
            case "DIVUW":
                return "/";
            case "REM":
            case "REMU":
            case "REMW":
            case "REMUW":
                return "%";
            case "SLL":
            case "SLLW":
                return "<<";
            case "SRL":
            case "SRLW":
                return ">>>";
            case "SRA":
            case "SRAW":
                return ">>";
            default:
                return "?";
        }
    }
}
