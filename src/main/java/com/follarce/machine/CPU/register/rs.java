package com.follarce.machine.CPU.register;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class rs {

    private static final String FILE_PATH = "regfile.bin";
    private static final int REG_COUNT = 32;
    private static final int REG_BYTES = 8;
    private static final int FILE_SIZE = REG_COUNT * REG_BYTES;

    private static RandomAccessFile regFile;

    static {
        try {
            File f = new File(FILE_PATH);
            regFile = new RandomAccessFile(FILE_PATH, "rw");
            if (!f.exists()) {
                regFile.setLength(FILE_SIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot open register file", e);
        }
    }

    public static boolean[] read(boolean[] addr5) {
        boolean isX0 = true;
        for (int i = 0; i < 5; i++) {
            if (addr5[i]) {
                isX0 = false;
                break;
            }
        }
        if (isX0) {
            return new boolean[64];
        }
        return readIdx(addr5toInt(addr5));
    }
    public static void write(boolean[] addr5, boolean[] val) {
        boolean isX0 = true;
        for (int i = 0; i < 5; i++) {
            if (addr5[i]) {
                isX0 = false;
                break;
            }
        }
        if (isX0) {
            return;
        }
        writeIdx(addr5toInt(addr5), val);
    }
    public static boolean[] readIdx(int regIdx) {
        boolean[] val = new boolean[64];
        try {
            long offset = (long) regIdx * REG_BYTES;
            regFile.seek(offset);
            for (int i = 0; i < REG_BYTES; i++) {
                int b = regFile.read();
                if (b < 0) b = 0;
                for (int j = 0; j < 8; j++) {
                    val[i * 8 + j] = ((b >> j) & 1) == 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return val;
    }
    public static void writeIdx(int regIdx, boolean[] val) {
        try {
            long offset = (long) regIdx * REG_BYTES;
            regFile.seek(offset);
            for (int i = 0; i < REG_BYTES; i++) {
                int b = 0;
                for (int j = 0; j < 8; j++) {
                    if (val[i * 8 + j]) {
                        b |= (1 << j);
                    }
                }
                regFile.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int addr5toInt(boolean[] addr5) {
        int idx = 0;
        if (addr5[0]) idx |= 1;
        if (addr5[1]) idx |= 2;
        if (addr5[2]) idx |= 4;
        if (addr5[3]) idx |= 8;
        if (addr5[4]) idx |= 16;
        return idx;
    }
    public static void close() {
        try {
            if (regFile != null) {
                regFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}