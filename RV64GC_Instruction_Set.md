# RV64GC 指令集规范

## 概述

RV64GC 是 RISC-V 64位通用指令集，由以下扩展组成：
- **I**: 基础整数指令集 (RV64I)
- **M**: 整数乘法和除法扩展
- **A**: 原子操作扩展
- **F**: 单精度浮点扩展
- **D**: 双精度浮点扩展
- **Zicsr**: 控制和状态寄存器指令扩展
- **Zifencei**: 指令缓存同步扩展

---

## 1. RV64I 基础整数指令集

### 1.1 整数计算指令 (R-type)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| ADD | add rd, rs1, rs2 | 加法 | x[rd] = x[rs1] + x[rs2] |
| SUB | sub rd, rs1, rs2 | 减法 | x[rd] = x[rs1] - x[rs2] |
| SLL | sll rd, rs1, rs2 | 逻辑左移 | x[rd] = x[rs1] << x[rs2] |
| SLT | slt rd, rs1, rs2 | 有符号小于比较 | x[rd] = (x[rs1] <s x[rs2]) ? 1 : 0 |
| SLTU | sltu rd, rs1, rs2 | 无符号小于比较 | x[rd] = (x[rs1] <u x[rs2]) ? 1 : 0 |
| XOR | xor rd, rs1, rs2 | 异或 | x[rd] = x[rs1] ^ x[rs2] |
| SRL | srl rd, rs1, rs2 | 逻辑右移 | x[rd] = x[rs1] >>u x[rs2] |
| SRA | sra rd, rs1, rs2 | 算术右移 | x[rd] = x[rs1] >>s x[rs2] |
| OR | or rd, rs1, rs2 | 或 | x[rd] = x[rs1] \| x[rs2] |
| AND | and rd, rs1, rs2 | 与 | x[rd] = x[rs1] & x[rs2] |

### 1.2 立即数计算指令 (I-type)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| ADDI | addi rd, rs1, imm | 加立即数 | x[rd] = x[rs1] + sext(imm) |
| SLTI | slti rd, rs1, imm | 有符号小于立即数 | x[rd] = (x[rs1] <s sext(imm)) ? 1 : 0 |
| SLTIU | sltiu rd, rs1, imm | 无符号小于立即数 | x[rd] = (x[rs1] <u sext(imm)) ? 1 : 0 |
| XORI | xori rd, rs1, imm | 异或立即数 | x[rd] = x[rs1] ^ sext(imm) |
| ORI | ori rd, rs1, imm | 或立即数 | x[rd] = x[rs1] \| sext(imm) |
| ANDI | andi rd, rs1, imm | 与立即数 | x[rd] = x[rs1] & sext(imm) |
| SLLI | slli rd, rs1, shamt | 逻辑左移立即数 | x[rd] = x[rs1] << shamt |
| SRLI | srli rd, rs1, shamt | 逻辑右移立即数 | x[rd] = x[rs1] >>u shamt |
| SRAI | srai rd, rs1, shamt | 算术右移立即数 | x[rd] = x[rs1] >>s shamt |

### 1.3 加载指令 (I-type)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| LB | lb rd, offset(rs1) | 加载字节(有符号扩展) | x[rd] = sext(M[x[rs1] + offset][7:0]) |
| LH | lh rd, offset(rs1) | 加载半字(有符号扩展) | x[rd] = sext(M[x[rs1] + offset][15:0]) |
| LW | lw rd, offset(rs1) | 加载字(有符号扩展) | x[rd] = sext(M[x[rs1] + offset][31:0]) |
| LBU | lbu rd, offset(rs1) | 加载字节(无符号扩展) | x[rd] = zext(M[x[rs1] + offset][7:0]) |
| LHU | lhu rd, offset(rs1) | 加载半字(无符号扩展) | x[rd] = zext(M[x[rs1] + offset][15:0]) |
| LWU | lwu rd, offset(rs1) | 加载字(无符号扩展) | x[rd] = zext(M[x[rs1] + offset][31:0]) |
| LD | ld rd, offset(rs1) | 加载双字 | x[rd] = M[x[rs1] + offset][63:0] |

### 1.4 存储指令 (S-type)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| SB | sb rs2, offset(rs1) | 存储字节 | M[x[rs1] + offset][7:0] = x[rs2][7:0] |
| SH | sh rs2, offset(rs1) | 存储半字 | M[x[rs1] + offset][15:0] = x[rs2][15:0] |
| SW | sw rs2, offset(rs1) | 存储字 | M[x[rs1] + offset][31:0] = x[rs2][31:0] |
| SD | sd rs2, offset(rs1) | 存储双字 | M[x[rs1] + offset][63:0] = x[rs2][63:0] |

### 1.5 分支指令 (B-type)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| BEQ | beq rs1, rs2, offset | 相等分支 | if (x[rs1] == x[rs2]) PC += sext(offset) |
| BNE | bne rs1, rs2, offset | 不等分支 | if (x[rs1] != x[rs2]) PC += sext(offset) |
| BLT | blt rs1, rs2, offset | 有符号小于分支 | if (x[rs1] <s x[rs2]) PC += sext(offset) |
| BGE | bge rs1, rs2, offset | 有符号大于等于分支 | if (x[rs1] >=s x[rs2]) PC += sext(offset) |
| BLTU | bltu rs1, rs2, offset | 无符号小于分支 | if (x[rs1] <u x[rs2]) PC += sext(offset) |
| BGEU | bgeu rs1, rs2, offset | 无符号大于等于分支 | if (x[rs1] >=u x[rs2]) PC += sext(offset) |

### 1.6 跳转指令

| 指令 | 格式 | 类型 | 描述 | 操作 |
|------|------|------|------|------|
| JAL | jal rd, offset | UJ | 跳转并链接 | x[rd] = PC+4; PC += sext(offset) |
| JALR | jalr rd, rs1, offset | I | 间接跳转并链接 | x[rd] = PC+4; PC = (x[rs1] + sext(offset)) & ~1 |

### 1.7 上位立即数指令 (U-type)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| LUI | lui rd, imm | 加载上位立即数 | x[rd] = imm << 12 |
| AUIPC | auipc rd, imm | 加上位立即数到PC | x[rd] = PC + (imm << 12) |

### 1.8 RV64I 特有的32位操作指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| ADDIW | addiw rd, rs1, imm | 32位加立即数 | x[rd] = sext((x[rs1] + sext(imm))[31:0]) |
| SLLIW | slliw rd, rs1, shamt | 32位逻辑左移立即数 | x[rd] = sext((x[rs1] << shamt)[31:0]) |
| SRLIW | srliw rd, rs1, shamt | 32位逻辑右移立即数 | x[rd] = sext(x[rs1][31:0] >>u shamt) |
| SRAIW | sraiw rd, rs1, shamt | 32位算术右移立即数 | x[rd] = sext(x[rs1][31:0] >>s shamt) |
| ADDW | addw rd, rs1, rs2 | 32位加法 | x[rd] = sext((x[rs1] + x[rs2])[31:0]) |
| SUBW | subw rd, rs1, rs2 | 32位减法 | x[rd] = sext((x[rs1] - x[rs2])[31:0]) |
| SLLW | sllw rd, rs1, rs2 | 32位逻辑左移 | x[rd] = sext((x[rs1] << x[rs2][4:0])[31:0]) |
| SRLW | srlw rd, rs1, rs2 | 32位逻辑右移 | x[rd] = sext(x[rs1][31:0] >>u x[rs2][4:0]) |
| SRAW | sraw rd, rs1, rs2 | 32位算术右移 | x[rd] = sext(x[rs1][31:0] >>s x[rs2][4:0]) |

### 1.9 系统指令

| 指令 | 格式 | 描述 |
|------|------|------|
| ECALL | ecall | 环境调用(系统调用) |
| EBREAK | ebreak | 环境断点 |

---

## 2. M 扩展 - 整数乘法和除法

### 2.1 乘法指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| MUL | mul rd, rs1, rs2 | 乘法(低位) | x[rd] = (x[rs1] * x[rs2])[XLEN-1:0] |
| MULH | mulh rd, rs1, rs2 | 有符号乘法(高位) | x[rd] = (x[rs1] *s x[rs2])[2*XLEN-1:XLEN] |
| MULHU | mulhu rd, rs1, rs2 | 无符号乘法(高位) | x[rd] = (x[rs1] *u x[rs2])[2*XLEN-1:XLEN] |
| MULHSU | mulhsu rd, rs1, rs2 | 有符号*无符号乘法(高位) | x[rd] = (x[rs1] *s x[rs2]u)[2*XLEN-1:XLEN] |

### 2.2 除法指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| DIV | div rd, rs1, rs2 | 有符号除法 | x[rd] = x[rs1] /s x[rs2] |
| DIVU | divu rd, rs1, rs2 | 无符号除法 | x[rd] = x[rs1] /u x[rs2] |
| REM | rem rd, rs1, rs2 | 有符号取余 | x[rd] = x[rs1] %s x[rs2] |
| REMU | remu rd, rs1, rs2 | 无符号取余 | x[rd] = x[rs1] %u x[rs2] |

### 2.3 RV64M 特有的32位乘除指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| MULW | mulw rd, rs1, rs2 | 32位乘法 | x[rd] = sext((x[rs1] * x[rs2])[31:0]) |
| DIVW | divw rd, rs1, rs2 | 32位有符号除法 | x[rd] = sext(x[rs1][31:0] /s x[rs2][31:0]) |
| DIVUW | divuw rd, rs1, rs2 | 32位无符号除法 | x[rd] = sext(x[rs1][31:0] /u x[rs2][31:0]) |
| REMW | remw rd, rs1, rs2 | 32位有符号取余 | x[rd] = sext(x[rs1][31:0] %s x[rs2][31:0]) |
| REMUW | remuw rd, rs1, rs2 | 32位无符号取余 | x[rd] = sext(x[rs1][31:0] %u x[rs2][31:0]) |

### 2.4 除法特殊情况处理

| 情况 | DIV/DIVU 结果 | REM/REMU 结果 |
|------|---------------|---------------|
| 除数为0 | -1 (全1) | 被除数 |
| 有符号除法: -2^63 / -1 | -2^63 (溢出) | 0 |

---

## 3. A 扩展 - 原子操作

### 3.1 加载保留/存储条件指令

| 指令 | 格式 | 描述 |
|------|------|------|
| LR.W | lr.w rd, (rs1) | 加载保留(字) |
| LR.D | lr.d rd, (rs1) | 加载保留(双字) |
| SC.W | sc.w rd, rs2, (rs1) | 存储条件(字) |
| SC.D | sc.d rd, rs2, (rs1) | 存储条件(双字) |

### 3.2 原子内存操作指令 (AMO)

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| AMOSWAP.W | amoswap.w rd, rs2, (rs1) | 原子交换(字) | temp = M[rs1]; M[rs1] = rs2; rd = temp |
| AMOSWAP.D | amoswap.d rd, rs2, (rs1) | 原子交换(双字) | temp = M[rs1]; M[rs1] = rs2; rd = temp |
| AMOADD.W | amoadd.w rd, rs2, (rs1) | 原子加(字) | temp = M[rs1]; M[rs1] += rs2; rd = temp |
| AMOADD.D | amoadd.d rd, rs2, (rs1) | 原子加(双字) | temp = M[rs1]; M[rs1] += rs2; rd = temp |
| AMOXOR.W | amoxor.w rd, rs2, (rs1) | 原子异或(字) | temp = M[rs1]; M[rs1] ^= rs2; rd = temp |
| AMOXOR.D | amoxor.d rd, rs2, (rs1) | 原子异或(双字) | temp = M[rs1]; M[rs1] ^= rs2; rd = temp |
| AMOAND.W | amoand.w rd, rs2, (rs1) | 原子与(字) | temp = M[rs1]; M[rs1] &= rs2; rd = temp |
| AMOAND.D | amoand.d rd, rs2, (rs1) | 原子与(双字) | temp = M[rs1]; M[rs1] &= rs2; rd = temp |
| AMOOR.W | amoor.w rd, rs2, (rs1) | 原子或(字) | temp = M[rs1]; M[rs1] \|= rs2; rd = temp |
| AMOOR.D | amoor.d rd, rs2, (rs1) | 原子或(双字) | temp = M[rs1]; M[rs1] \|= rs2; rd = temp |
| AMOMIN.W | amomin.w rd, rs2, (rs1) | 原子有符号最小值(字) | temp = M[rs1]; M[rs1] = min(M[rs1], rs2); rd = temp |
| AMOMIN.D | amomin.d rd, rs2, (rs1) | 原子有符号最小值(双字) | temp = M[rs1]; M[rs1] = min(M[rs1], rs2); rd = temp |
| AMOMAX.W | amomax.w rd, rs2, (rs1) | 原子有符号最大值(字) | temp = M[rs1]; M[rs1] = max(M[rs1], rs2); rd = temp |
| AMOMAX.D | amomax.d rd, rs2, (rs1) | 原子有符号最大值(双字) | temp = M[rs1]; M[rs1] = max(M[rs1], rs2); rd = temp |
| AMOMINU.W | amominu.w rd, rs2, (rs1) | 原子无符号最小值(字) | temp = M[rs1]; M[rs1] = minu(M[rs1], rs2); rd = temp |
| AMOMINU.D | amominu.d rd, rs2, (rs1) | 原子无符号最小值(双字) | temp = M[rs1]; M[rs1] = minu(M[rs1], rs2); rd = temp |
| AMOMAXU.W | amomaxu.w rd, rs2, (rs1) | 原子无符号最大值(字) | temp = M[rs1]; M[rs1] = maxu(M[rs1], rs2); rd = temp |
| AMOMAXU.D | amomaxu.d rd, rs2, (rs1) | 原子无符号最大值(双字) | temp = M[rs1]; M[rs1] = maxu(M[rs1], rs2); rd = temp |

---

## 4. F 扩展 - 单精度浮点

### 4.1 浮点加载/存储指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FLW | flw fd, offset(rs1) | 加载单精度浮点数 | f[fd] = M[x[rs1] + offset][31:0] |
| FSW | fsw fs2, offset(rs1) | 存储单精度浮点数 | M[x[rs1] + offset][31:0] = f[fs2] |

### 4.2 浮点计算指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FADD.S | fadd.s fd, fs1, fs2 | 单精度浮点加法 | f[fd] = f[fs1] + f[fs2] |
| FSUB.S | fsub.s fd, fs1, fs2 | 单精度浮点减法 | f[fd] = f[fs1] - f[fs2] |
| FMUL.S | fmul.s fd, fs1, fs2 | 单精度浮点乘法 | f[fd] = f[fs1] * f[fs2] |
| FDIV.S | fdiv.s fd, fs1, fs2 | 单精度浮点除法 | f[fd] = f[fs1] / f[fs2] |
| FSQRT.S | fsqrt.s fd, fs1 | 单精度浮点平方根 | f[fd] = sqrt(f[fs1]) |
| FMIN.S | fmin.s fd, fs1, fs2 | 单精度浮点最小值 | f[fd] = min(f[fs1], f[fs2]) |
| FMAX.S | fmax.s fd, fs1, fs2 | 单精度浮点最大值 | f[fd] = max(f[fs1], f[fs2]) |

### 4.3 浮点融合乘加指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FMADD.S | fmadd.s fd, fs1, fs2, fs3 | 融合乘加 | f[fd] = f[fs1] * f[fs2] + f[fs3] |
| FMSUB.S | fmsub.s fd, fs1, fs2, fs3 | 融合乘减 | f[fd] = f[fs1] * f[fs2] - f[fs3] |
| FNMSUB.S | fnmsub.s fd, fs1, fs2, fs3 | 负融合乘减 | f[fd] = -f[fs1] * f[fs2] + f[fs3] |
| FNMADD.S | fnmadd.s fd, fs1, fs2, fs3 | 负融合乘加 | f[fd] = -f[fs1] * f[fs2] - f[fs3] |

### 4.4 浮点比较指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FEQ.S | feq.s rd, fs1, fs2 | 浮点相等 | x[rd] = (f[fs1] == f[fs2]) ? 1 : 0 |
| FLT.S | flt.s rd, fs1, fs2 | 浮点小于 | x[rd] = (f[fs1] < f[fs2]) ? 1 : 0 |
| FLE.S | fle.s rd, fs1, fs2 | 浮点小于等于 | x[rd] = (f[fs1] <= f[fs2]) ? 1 : 0 |

### 4.5 浮点转换指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FCVT.W.S | fcvt.w.s rd, fs1 | 单精度浮点转32位有符号整数 | x[rd] = sext((int32_t)f[fs1]) |
| FCVT.WU.S | fcvt.wu.s rd, fs1 | 单精度浮点转32位无符号整数 | x[rd] = sext((uint32_t)f[fs1]) |
| FCVT.L.S | fcvt.l.s rd, fs1 | 单精度浮点转64位有符号整数 | x[rd] = (int64_t)f[fs1] |
| FCVT.LU.S | fcvt.lu.s rd, fs1 | 单精度浮点转64位无符号整数 | x[rd] = (uint64_t)f[fs1] |
| FCVT.S.W | fcvt.s.w fd, rs1 | 32位有符号整数转单精度浮点 | f[fd] = (float)(int32_t)x[rs1] |
| FCVT.S.WU | fcvt.s.wu fd, rs1 | 32位无符号整数转单精度浮点 | f[fd] = (float)(uint32_t)x[rs1] |
| FCVT.S.L | fcvt.s.l fd, rs1 | 64位有符号整数转单精度浮点 | f[fd] = (float)(int64_t)x[rs1] |
| FCVT.S.LU | fcvt.s.lu fd, rs1 | 64位无符号整数转单精度浮点 | f[fd] = (float)(uint64_t)x[rs1] |

### 4.6 浮点移动指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FMV.X.W | fmv.x.w rd, fs1 | 浮点寄存器移到整数寄存器 | x[rd] = sext(f[fs1][31:0]) |
| FMV.W.X | fmv.w.x fd, rs1 | 整数寄存器移到浮点寄存器 | f[fd] = x[rs1][31:0] |
| FSGNJ.S | fsgnj.s fd, fs1, fs2 | 浮点符号注入 | f[fd] = {f[fs2][31], f[fs1][30:0]} |
| FSGNJN.S | fsgnjn.s fd, fs1, fs2 | 浮点符号注入(取反) | f[fd] = {~f[fs2][31], f[fs1][30:0]} |
| FSGNJX.S | fsgnjx.s fd, fs1, fs2 | 浮点符号注入(异或) | f[fd] = {f[fs2][31] ^ f[fs1][31], f[fs1][30:0]} |

### 4.7 浮点分类指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FCLASS.S | fclass.s rd, fs1 | 浮点分类 | x[rd] = classify(f[fs1]) |

---

## 5. D 扩展 - 双精度浮点

### 5.1 浮点加载/存储指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FLD | fld fd, offset(rs1) | 加载双精度浮点数 | f[fd] = M[x[rs1] + offset][63:0] |
| FSD | fsd fs2, offset(rs1) | 存储双精度浮点数 | M[x[rs1] + offset][63:0] = f[fs2] |

### 5.2 浮点计算指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FADD.D | fadd.d fd, fs1, fs2 | 双精度浮点加法 | f[fd] = f[fs1] + f[fs2] |
| FSUB.D | fsub.d fd, fs1, fs2 | 双精度浮点减法 | f[fd] = f[fs1] - f[fs2] |
| FMUL.D | fmul.d fd, fs1, fs2 | 双精度浮点乘法 | f[fd] = f[fs1] * f[fs2] |
| FDIV.D | fdiv.d fd, fs1, fs2 | 双精度浮点除法 | f[fd] = f[fs1] / f[fs2] |
| FSQRT.D | fsqrt.d fd, fs1 | 双精度浮点平方根 | f[fd] = sqrt(f[fs1]) |
| FMIN.D | fmin.d fd, fs1, fs2 | 双精度浮点最小值 | f[fd] = min(f[fs1], f[fs2]) |
| FMAX.D | fmax.d fd, fs1, fs2 | 双精度浮点最大值 | f[fd] = max(f[fs1], f[fs2]) |

### 5.3 浮点融合乘加指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FMADD.D | fmadd.d fd, fs1, fs2, fs3 | 融合乘加 | f[fd] = f[fs1] * f[fs2] + f[fs3] |
| FMSUB.D | fmsub.d fd, fs1, fs2, fs3 | 融合乘减 | f[fd] = f[fs1] * f[fs2] - f[fs3] |
| FNMSUB.D | fnmsub.d fd, fs1, fs2, fs3 | 负融合乘减 | f[fd] = -f[fs1] * f[fs2] + f[fs3] |
| FNMADD.D | fnmadd.d fd, fs1, fs2, fs3 | 负融合乘加 | f[fd] = -f[fs1] * f[fs2] - f[fs3] |

### 5.4 浮点比较指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FEQ.D | feq.d rd, fs1, fs2 | 浮点相等 | x[rd] = (f[fs1] == f[fs2]) ? 1 : 0 |
| FLT.D | flt.d rd, fs1, fs2 | 浮点小于 | x[rd] = (f[fs1] < f[fs2]) ? 1 : 0 |
| FLE.D | fle.d rd, fs1, fs2 | 浮点小于等于 | x[rd] = (f[fs1] <= f[fs2]) ? 1 : 0 |

### 5.5 浮点转换指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FCVT.W.D | fcvt.w.d rd, fs1 | 双精度浮点转32位有符号整数 | x[rd] = sext((int32_t)f[fs1]) |
| FCVT.WU.D | fcvt.wu.d rd, fs1 | 双精度浮点转32位无符号整数 | x[rd] = sext((uint32_t)f[fs1]) |
| FCVT.L.D | fcvt.l.d rd, fs1 | 双精度浮点转64位有符号整数 | x[rd] = (int64_t)f[fs1] |
| FCVT.LU.D | fcvt.lu.d rd, fs1 | 双精度浮点转64位无符号整数 | x[rd] = (uint64_t)f[fs1] |
| FCVT.D.W | fcvt.d.w fd, rs1 | 32位有符号整数转双精度浮点 | f[fd] = (double)(int32_t)x[rs1] |
| FCVT.D.WU | fcvt.d.wu fd, rs1 | 32位无符号整数转双精度浮点 | f[fd] = (double)(uint32_t)x[rs1] |
| FCVT.D.L | fcvt.d.l fd, rs1 | 64位有符号整数转双精度浮点 | f[fd] = (double)(int64_t)x[rs1] |
| FCVT.D.LU | fcvt.d.lu fd, rs1 | 64位无符号整数转双精度浮点 | f[fd] = (double)(uint64_t)x[rs1] |
| FCVT.S.D | fcvt.s.d fd, fs1 | 双精度转单精度浮点 | f[fd] = (float)f[fs1] |
| FCVT.D.S | fcvt.d.s fd, fs1 | 单精度转双精度浮点 | f[fd] = (double)f[fs1] |

### 5.6 浮点移动指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FMV.X.D | fmv.x.d rd, fs1 | 浮点寄存器移到整数寄存器 | x[rd] = f[fs1][63:0] |
| FMV.D.X | fmv.d.x fd, rs1 | 整数寄存器移到浮点寄存器 | f[fd] = x[rs1][63:0] |
| FSGNJ.D | fsgnj.d fd, fs1, fs2 | 浮点符号注入 | f[fd] = {f[fs2][63], f[fs1][62:0]} |
| FSGNJN.D | fsgnjn.d fd, fs1, fs2 | 浮点符号注入(取反) | f[fd] = {~f[fs2][63], f[fs1][62:0]} |
| FSGNJX.D | fsgnjx.d fd, fs1, fs2 | 浮点符号注入(异或) | f[fd] = {f[fs2][63] ^ f[fs1][63], f[fs1][62:0]} |

### 5.7 浮点分类指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| FCLASS.D | fclass.d rd, fs1 | 浮点分类 | x[rd] = classify(f[fs1]) |

---

## 6. Zicsr 扩展 - 控制和状态寄存器指令

| 指令 | 格式 | 描述 | 操作 |
|------|------|------|------|
| CSRRW | csrrw rd, csr, rs1 | 原子读写CSR | t = CSR[csr]; CSR[csr] = x[rs1]; x[rd] = t |
| CSRRS | csrrs rd, csr, rs1 | 原子读并置位CSR | t = CSR[csr]; CSR[csr] \|= x[rs1]; x[rd] = t |
| CSRRC | csrrc rd, csr, rs1 | 原子读并清零CSR | t = CSR[csr]; CSR[csr] &= ~x[rs1]; x[rd] = t |
| CSRRWI | csrrwi rd, csr, imm | 立即数原子读写CSR | t = CSR[csr]; CSR[csr] = imm; x[rd] = t |
| CSRRSI | csrrsi rd, csr, imm | 立即数原子读并置位CSR | t = CSR[csr]; CSR[csr] \|= imm; x[rd] = t |
| CSRRCI | csrrci rd, csr, imm | 立即数原子读并清零CSR | t = CSR[csr]; CSR[csr] &= ~imm; x[rd] = t |

---

## 7. Zifencei 扩展 - 指令缓存同步

| 指令 | 格式 | 描述 |
|------|------|------|
| FENCE.I | fence.i | 指令存储屏障，确保指令缓存与数据缓存一致性 |

---

## 8. 内存屏障指令

| 指令 | 格式 | 描述 |
|------|------|------|
| FENCE | fence | 内存屏障，确保内存操作顺序 |
| FENCE.TSO | fence.tso | 完全内存屏障 |

---

## 9. 指令统计

| 扩展 | 指令数量 | 描述 |
|------|---------|------|
| RV64I | 40+ | 基础整数指令 |
| M | 20 | 乘除法指令 |
| A | 22 | 原子操作指令 |
| F | 30+ | 单精度浮点指令 |
| D | 30+ | 双精度浮点指令 |
| Zicsr | 6 | CSR指令 |
| Zifencei | 1 | 指令屏障 |
| **总计** | **150+** | RV64GC完整指令集 |

---

## 10. 参考资料

- RISC-V ISA Specification: https://riscv.org/technical/specifications/
- RISC-V Reader: https://riscvbook.com/
- RISC-V Green Card: https://www.cl.cam.ac.uk/teaching/1617/ECAD+Arch/files/docs/RISCVGreenCardv8-20171022.pdf

---

*文档生成日期: 2026-05-06*
*基于 RISC-V ISA Specification Version 2024.04*
