package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import processor.Processor;

import java.util.Arrays;

public class Execute{
    Processor containingProcessor;
    OF_EX_LatchType OF_EX_Latch;
    EX_MA_LatchType EX_MA_Latch;
    EX_IF_LatchType EX_IF_Latch;
    IF_OF_LatchType IF_OF_Latch;
    MA_RW_LatchType MA_RW_Latch;
    IF_EnableLatchType IF_EnableLatch;

    public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType iF_OF_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
        this.containingProcessor = containingProcessor;
        this.OF_EX_Latch = oF_EX_Latch;
        this.EX_MA_Latch = eX_MA_Latch;
        this.EX_IF_Latch = eX_IF_Latch;
        this.IF_OF_Latch = iF_OF_Latch;
        this.MA_RW_Latch = mA_RW_Latch;
        this.IF_EnableLatch = iF_EnableLatch;
    }

    public void performEX() {
        //System.out.println("YessE");
        //System.out.println(EX_MA_Latch.isMA_busy());
        if(EX_MA_Latch.isMA_busy()) OF_EX_Latch.setEX_busy(true);
        else OF_EX_Latch.setEX_busy(false);
        // if(OF_EX_Latch.EX_enable == false)  EX_MA_Latch.MA_enable = false;
        if(OF_EX_Latch.isEX_enable() && !EX_MA_Latch.isMA_busy()) {
            int offset = 70000;
            if(OF_EX_Latch.isNop) {
                EX_MA_Latch.isNop = true;
            }
            else {
                EX_MA_Latch.isNop = false;
                Instruction cmd = OF_EX_Latch.getInstruction();
                EX_MA_Latch.setInstruction(cmd);
                OperationType cmd_op = cmd.getOperationType();
                int cmd_op_opcode = Arrays.asList(OperationType.values()).indexOf(cmd_op);
                int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

                int ALU_output = 0;

                if (cmd_op_opcode < 21 && cmd_op_opcode % 2 == 0) {
                    int SOP1 = containingProcessor.getRegisterFile().getValue(
                            cmd.getSourceOperand1().getValue());
                    int SOP2 = containingProcessor.getRegisterFile().getValue(
                            cmd.getSourceOperand2().getValue());
                    if (cmd_op.toString().equals("add")) {
                        ALU_output = SOP1 + SOP2;
                    } else if (cmd_op.toString().equals("sub")) {
                        //System.out.println(EX_MA_Latch.getInstruction());
                        ALU_output = SOP1 - SOP2;
                        //System.out.println(ALU_output);
                    } else if (cmd_op.toString().equals("mul")) {
                        ALU_output = SOP1 * SOP2;
                    } else if (cmd_op.toString().equals("div")) {
                        ALU_output = SOP1 / SOP2;
                        int r = SOP1 % SOP2;
                        containingProcessor.getRegisterFile().setValue(31, r);
                    } else if (cmd_op.toString().equals("and")) {
                        ALU_output = SOP1 & SOP2;
                    } else if (cmd_op.toString().equals("or")) {
                        ALU_output = SOP1 | SOP2;
                    } else if (cmd_op.toString().equals("xor")) {
                        ALU_output = SOP1 ^ SOP2;
                    } else if (cmd_op.toString().equals("slt")) {
                        if (SOP1 < SOP2)
                            ALU_output = 1;
                        else
                            ALU_output = 0;
                    } else if (cmd_op.toString().equals("sll")) {
                        ALU_output = SOP1 << SOP2;
                    } else if (cmd_op.toString().equals("srl")) {
                        ALU_output = SOP1 >>> SOP2;
                    } else if (cmd_op.toString().equals("sra")) {
                        ALU_output = SOP1 >> SOP2;
                    }
                } else if (cmd_op_opcode < 23) {
                    int con = cmd.getSourceOperand1().getValue();
                    int SOP1 = containingProcessor.getRegisterFile().getValue(con);
                    int SOP2 = cmd.getSourceOperand2().getValue();

                    if (cmd_op.toString().equals("addi")) {
                        ALU_output = SOP1 + SOP2;
                    } else if (cmd_op.toString().equals("subi")) {
                        ALU_output = SOP1 - SOP2;
                    } else if (cmd_op.toString().equals("muli")) {
                        ALU_output = SOP1 * SOP2;
                    } else if (cmd_op.toString().equals("divi")) {
                        //System.out.println(EX_MA_Latch.getInstruction());
                        //System.out.println(cmd);
                        //System.out.println(SOP1+" "+SOP2);
                        ALU_output = SOP1 / SOP2;
                        int r = SOP1 % SOP2;
                        //System.out.println("Value of remainder "+r);
                        containingProcessor.getRegisterFile().setValue(31, r);
                    } else if (cmd_op.toString().equals("andi")) {
                        ALU_output = SOP1 & SOP2;
                    } else if (cmd_op.toString().equals("ori")) {
                        ALU_output = SOP1 | SOP2;
                    } else if (cmd_op.toString().equals("xori")) {
                        ALU_output = SOP1 ^ SOP2;
                    } else if (cmd_op.toString().equals("slti")) {
                        if (SOP1 < SOP2)
                            ALU_output = 1;
                        else
                            ALU_output = 0;
                    } else if (cmd_op.toString().equals("slli")) {
                        ALU_output = SOP1 << SOP2;
                    } else if (cmd_op.toString().equals("srli")) {
                        ALU_output = SOP1 >>> SOP2;
                    } else if (cmd_op.toString().equals("srai")) {
                        ALU_output = SOP1 >> SOP2;
                    } else if (cmd_op.toString().equals("load")) {
                        //System.out.println(EX_MA_Latch.getInstruction());
                        ALU_output = SOP1 + SOP2;
                    }
                } else if (cmd_op_opcode == 23) {
                    //store
                    //System.out.println(cmd);
                    int SOP1 = cmd.getDestinationOperand().getValue();
                    int SOP2 = containingProcessor.getRegisterFile().getValue(cmd.getSourceOperand2().getValue());
                    //int SOP = containingProcessor.getRegisterFile().getValue(cmd.getSourceOperand1().getValue());
                    ALU_output = SOP1+SOP2;
                    //System.out.println(ALU_output);
                } else if (cmd_op_opcode == 24) {
                    //jmp
                    //System.out.println(cmd);
                    Operand.OperandType OPERNDTYPE = cmd.getDestinationOperand().getOperandType();
                    int immediate = 0;
                    if (OPERNDTYPE == Operand.OperandType.Register) {
                        immediate = containingProcessor.getRegisterFile().getValue(
                                cmd.getDestinationOperand().getValue());
                    } else {
                        immediate = cmd.getDestinationOperand().getValue();
                    }
                    //int SOP1 = containingProcessor.getRegisterFile().getValue(cmd.getDestinationOperand().getValue()+currentPC);
                    //int SOP2 = cmd.getSourceOperand2().getValue();
                    ALU_output = immediate;
                    //ALU_output = SOP1+SOP2;
                    offset = ALU_output;
                } else if (cmd_op_opcode < 29) {
                    //System.out.println(cmd_op);
                    int SOP1 = containingProcessor.getRegisterFile().getValue(cmd.getSourceOperand1().getValue());
                    int SOP2 = containingProcessor.getRegisterFile().getValue(cmd.getSourceOperand2().getValue());
                    int immediate = cmd.getDestinationOperand().getValue();

                    if (cmd_op.toString().equals("beq")) {
                        //System.out.println(EX_MA_Latch.getInstruction());
                        //System.out.println("INSTRUCTION IN EXECUTE: "+cmd);
                        if (SOP1 == SOP2) {
                            ALU_output = immediate;
                            offset = ALU_output;
                            //System.out.println("ENTERING INTO BEQ");
                            //containingProcessor.getRegisterFile().setProgramCounter(ALU_output);
                            //EX_MA_Latch.setFlag(1);
                        }
                    } else if (cmd_op.toString().equals("bne")) {
                        if (SOP1 != SOP2) {
                            ALU_output = immediate;
                            offset = ALU_output;
                            //containingProcessor.getRegisterFile().setProgramCounter(ALU_output);
                            //EX_MA_Latch.setFlag(1);
                        }
                    } else if (cmd_op.toString().equals("blt")) {
                        if (SOP1 < SOP2) {
                            ALU_output = immediate;
                            offset = ALU_output;
                            //containingProcessor.getRegisterFile().setProgramCounter(ALU_output);
                            //EX_MA_Latch.setFlag(1);
                        }
                    } else if (cmd_op.toString().equals("bgt")) {
                        if (SOP1 > SOP2) {
                            ALU_output = immediate;
                            offset = ALU_output;
                            //containingProcessor.getRegisterFile().setProgramCounter(ALU_output);
                            //EX_MA_Latch.setFlag(1);
                        }
                    }
                }
                if(offset != 70000) {
                    EX_IF_Latch.isBranchTaken = true;
                    EX_IF_Latch.offset = offset - 1;
                    IF_EnableLatch.setIF_enable(true);
                    OF_EX_Latch.setEX_enable(false);
                    IF_OF_Latch.setOF_enable(false);
                    // IF_OF_Latch.instruction = 0;
                    Instruction nop = new Instruction();
                    nop.setOperationType(OperationType.nop);
                    OF_EX_Latch.setInstruction(nop);

                }
                EX_MA_Latch.setALU_result(ALU_output);
                EX_MA_Latch.insPC = OF_EX_Latch.insPC;
                if(OF_EX_Latch.getInstruction().getOperationType().toString().equals("end")) {
                    OF_EX_Latch.setEX_enable(false);
                }
            }
            OF_EX_Latch.setEX_enable(false);
            EX_MA_Latch.setMA_enable(true);
        }
    }
}
