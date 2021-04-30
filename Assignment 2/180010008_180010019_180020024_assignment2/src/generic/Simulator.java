package generic;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulator {

	static FileInputStream inputcodeStream = null;

	public static void setupSimulation(String assemblyProgramFile) {
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	public static void assemble(String objectProgramFile, String assemblyProgramFile) {
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(objectProgramFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			//2. write the firstCodeAddress to the file
			//3. write the data to the file
			//4. assemble one instruction at a time, and write to the file
			//5. close the file

			Hashtable<String, String> r = new Hashtable<String, String>();
			Hashtable<Integer, String> reg = new Hashtable<Integer, String>();
			//int[] a = new int[ParsedProgram.code.size()+ParsedProgram.parseDataSection(assemblyProgramFile)+1];
			//Hashtable<Integer, String> a = new Hashtable<Integer, String>();
			//int acount = 0;
			//System.out.println((ParsedProgram.code.size()+ParsedProgram.parseDataSection(assemblyProgramFile))+" Here issize");
			r.put("add", "00000");
			r.put("sub", "00010");
			r.put("mul", "00100");
			r.put("div", "00110");
			r.put("and", "01000");
			r.put("or", "01010");
			r.put("xor", "01100");
			r.put("slt", "01110");
			r.put("sll", "10000");
			r.put("srl", "10010");
			r.put("sra", "10100");

			r.put("addi", "00001");
			r.put("subi", "00011");
			r.put("muli", "00101");
			r.put("divi", "00111");
			r.put("andi", "01001");
			r.put("ori", "01011");
			r.put("xori", "01101");
			r.put("slti", "01111");
			r.put("slli", "10001");
			r.put("srli", "10011");
			r.put("srai", "10101");
			r.put("load", "10110");
			r.put("store", "10111");
			r.put("beq", "11001");
			r.put("bne", "11010");
			r.put("blt", "11011");
			r.put("bgt", "11100");

			r.put("jmp", "11000");

			reg.put(0, "00000");
			reg.put(1, "00001");
			reg.put(2, "00010");
			reg.put(3, "00011");
			reg.put(4, "00100");
			reg.put(5, "00101");
			reg.put(6, "00110");
			reg.put(7, "00111");
			reg.put(8, "01000");
			reg.put(9, "01001");
			reg.put(10, "01010");
			reg.put(11, "01011");
			reg.put(12, "01100");
			reg.put(13, "01101");
			reg.put(14, "01110");
			reg.put(15, "01111");
			reg.put(16, "10000");
			reg.put(17, "10001");
			reg.put(18, "10010");
			reg.put(19, "10011");
			reg.put(20, "10100");
			reg.put(21, "10101");
			reg.put(22, "10110");
			reg.put(23, "10111");
			reg.put(24, "11000");
			reg.put(25, "11001");
			reg.put(26, "11010");
			reg.put(27, "11011");
			reg.put(28, "11100");
			reg.put(29, "11101");
			reg.put(30, "11110");
			reg.put(31, "11111");

			byte[] codeAddress = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			bos.write(codeAddress);
			//int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);

			//String fCAt = Integer.toBinaryString(firstCodeAddress);
			//String fCA = new BigInteger(fCAt, 2).toString(16);
			//int t = fCA.length();
			//String con = "";
			//if ((8 - t) != 0) {
				//String st = "0";
				//int qt = 8 - t;
				//con = IntStream.range(0, qt).mapToObj(it -> st).collect(Collectors.joining(""));
			//}
			//String fCAProc = "";
			//fCAProc = fCAProc.concat(con);
			//fCAProc = fCAProc.concat(fCA);
			//System.out.print(fCAProc.substring(0, 4));
			//a.put(acount, fCAProc);
			//acount = acount + 1;
			//System.out.print(" ");
			//System.out.println(fCAProc.substring(4, 8));
			//a.put(acount,fCAProc.substring(4,8));
			//acount = acount+1;
			//int main = ParsedProgram.symtab.get("main");
			//for (int i = 0; i < main; i++) {
				//String dtt = Integer.toBinaryString(ParsedProgram.data.get(i));
				//String d = new BigInteger(dtt, 2).toString(16);
				//int tq = d.length();
				//String conq = "";
				//if ((8 - tq) != 0) {
					//String stq = "0";
					//int qtq = 8 - tq;
					//conq = IntStream.range(0, qtq).mapToObj(o -> stq).collect(Collectors.joining(""));
				//}
				//String dqq = "";
				//dqq = dqq.concat(conq);
				//dqq = dqq.concat(d);
				//System.out.println(dqq);
				//System.out.print(dqq.substring(0, 4));
				//a.put(acount, dqq);
				//acount = acount + 1;
				//System.out.print(" ");
				//System.out.println(dqq.substring(4, 8));
				//a.put(acount,dqq.substring(4,8));
				//acount = acount+1;
			//}
			for (int i=0; i<ParsedProgram.data.size(); i++) {
				byte[] dataUnit = ByteBuffer.allocate(4).putInt(ParsedProgram.data.get(i)).array();
				bos.write(dataUnit);
			}
			String x = "";
			for (int j = 0; j < ParsedProgram.code.size(); j++) {
				String op = ParsedProgram.code.get(j).getOperationType().toString();
				//System.out.println(op);
				if (op.equals("jmp")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					x = x.concat("00000");
					int pc = ParsedProgram.code.get(j).programCounter;
					int jk = 0;
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Label")){
						jk = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue);
					}
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Immediate")){
						jk = ParsedProgram.code.get(j).destinationOperand.value;
					}
					int val = jk - pc;
					if (val < 0) {
						String c = Integer.toBinaryString(val);
						c = c.substring(10, 32);
						x = x.concat(c);
					}
					if (val >= 0) {
						String c = Integer.toBinaryString(val);
						int limm = c.length();
						String lRepeated = "";
						if ((22 - limm) != 0) {
							String s = "0";
							int q = 22 - limm;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(c);
					}
				}
				if (op.equals("load") || op.equals("store")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}
					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Label")) {
						String immi = ParsedProgram.code.get(j).sourceOperand2.labelValue;
						int immis = ParsedProgram.symtab.get(immi);
						String imm = Integer.toBinaryString(immis);
						int limm = imm.length();
						String lRepeated = "";
						if ((17 - limm) != 0) {
							String s = "0";
							int q = 17 - limm;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(Integer.toBinaryString(immis));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Immediate")) {
						int immis = ParsedProgram.code.get(j).sourceOperand2.value;
						//int immis = ParsedProgram.symtab.get(immi);
						String imm = Integer.toBinaryString(immis);
						int limm = imm.length();
						String lRepeated = "";
						if ((17 - limm) != 0) {
							String s = "0";
							int q = 17 - limm;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(Integer.toBinaryString(immis));
					}
				}
				if (op.equals("sub") || op.equals("add") || op.equals("mul") || op.equals("div") || op.equals("and") || op.equals("or") || op.equals("xor") || op.equals("slt") || op.equals("srl") || op.equals("sra") || op.equals("sll")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand2.value));
					}
					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}
					x = x.concat("000000000000");
				}
				if (op.equals("subi") || op.equals("addi") || op.equals("muli") || op.equals("divi") || op.equals("andi") || op.equals("ori") || op.equals("xori") || op.equals("slti") || op.equals("srli") || op.equals("srai") || op.equals("slli")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}
					if (ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).destinationOperand.value));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Immediate")) {
						int immi = ParsedProgram.code.get(j).sourceOperand2.value;
						String imm = Integer.toBinaryString(immi);
						int limm = imm.length();
						String lRepeated = "";
						if ((17 - limm) != 0) {
							String s = "0";
							int q = 17 - limm;
							lRepeated = IntStream.range(0, q).mapToObj(i -> s).collect(Collectors.joining(""));
						}
						x = x.concat(lRepeated);
						x = x.concat(Integer.toBinaryString(immi));

					}
				}
				if (op.equals("beq") || op.equals("bgt") || op.equals("bne") || op.equals("blt")) {
					x = x.concat(r.get(ParsedProgram.code.get(j).operationType.toString()));
					if (ParsedProgram.code.get(j).sourceOperand1.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand1.value));
					}
					if (ParsedProgram.code.get(j).sourceOperand2.operandType.toString().equals("Register")) {
						x = x.concat(reg.get(ParsedProgram.code.get(j).sourceOperand2.value));
					}
					int n = 0;
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Label")){
						n = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue) - ParsedProgram.code.get(j).programCounter;
					}
					if(ParsedProgram.code.get(j).destinationOperand.operandType.toString().equals("Immediate")){
						n = ParsedProgram.code.get(j).destinationOperand.value - ParsedProgram.code.get(j).programCounter;
					}
					//n = ParsedProgram.symtab.get(ParsedProgram.code.get(j).destinationOperand.labelValue) - ParsedProgram.code.get(j).programCounter;
					if (n >= 0) {
						String np = Integer.toBinaryString(n);
						int npl = np.length();
						String npRepeated = "";
						if ((17 - npl) != 0) {
							String so = "0";
							int no = 17 - npl;
							npRepeated = IntStream.range(0, no).mapToObj(i -> so).collect(Collectors.joining(""));
						}
						x = x.concat(npRepeated);
						x = x.concat(np);
					}
					if (n < 0) {
						String c = Integer.toBinaryString(n);
						c = c.substring(15, 32);
						x = x.concat(c);
					}

				}
				if (op.equals("end")) {
					x = x.concat("11101000000000000000000000000000");
				}
				//System.out.println(x);
				int inst_intmap = (int) Long.parseLong(x, 2);
				//System.out.println(inst_intmap);
				byte[] inst_bitmap = ByteBuffer.allocate(4).putInt(inst_intmap).array();
				bos.write(inst_bitmap);
				//String hexString = new BigInteger(x, 2).toString(16);
				//int slen = hexString.length();
				//String nopRepeated = "";
				//if ((8 - slen) != 0) {
					//String sooo = "0";
					//int noo = 8 - slen;
					//nopRepeated = IntStream.range(0, noo).mapToObj(i -> sooo).collect(Collectors.joining(""));
				//}
				//String po = "";
				//po = po.concat(nopRepeated);
				//po = po.concat(hexString);
				//System.out.print(po.substring(0, 4));
				//a.put(acount, po);
				//acount = acount + 1;
				//System.out.print(" ");
				//System.out.println(po.substring(4, 8));
				//a.put(acount,po.substring(4,8));
				//acount = acount+1;

				x = "";
			}
			//System.out.println(a);
			bos.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

