import java.io.FileNotFoundException;
import java.io.IOException;

public class CPU {
	// declaration
	private enum ERegisters {
		ePC, eSP, eAC, eIR, eStatus, eMAR, eMBR
	}

	private enum EOpCode {
		eHalt, eLDA, eSTA, eAdd, eSub, eEQ, eGT, eBEq, eBGT, eBranch

	}

	private class CU {
		// PC를 제어
	}

	private class ALU {

		public void add() {

		}

		public void subtract() {
			
		}

		public void equal() {
			
		}

		public void greaterThan() {
			
		}
	}

	private class Register {
		protected short value;

		public short getValue() {
			return this.value;
		}

		public void setValue(short value) {
			this.value = value;
		}
	}

	class IR extends Register {
		public short getOpCode() {
			return (short) (this.value >> 8);
		}

		public short getOperand() {
			return (short) (this.value & 0x00FF);
		}
	}

	// components
	private CU cu;
	private ALU alu;
	private Register registers[];
	
	// associations
	private Memory memory;
	
	// status
	private boolean bPowerOn;

	private boolean isPowerOn() {
		return this.bPowerOn;
	}

	public void setPowerOn() throws FileNotFoundException {
		this.bPowerOn = true;
		this.run();
	}

	public void shutDown() {
		this.bPowerOn = false;
	}

	// constructor
	public CPU() {
		this.cu = new CU();
		this.alu = new ALU();
		this.registers = new Register[ERegisters.values().length];
		for(int i=0; i<ERegisters.values().length;i++) {
			if(i==ERegisters.eIR.ordinal()) {
				this.registers[i] = new IR();
			}
			this.registers[i] = new Register();
		}
	}

	public void associate(Memory memory) {
		this.memory = memory;
	}

	private void fetch() throws FileNotFoundException {
		// PC --> MAR
	
		// memory header setting
		this.registers[ERegisters.ePC.ordinal()].setValue(this.memory.setPC());
		this.registers[ERegisters.eSP.ordinal()].setValue(this.memory.setSP());
		
		this.registers[ERegisters.eMAR.ordinal()].setValue(this.registers[ERegisters.ePC.ordinal()].getValue());
		// MAR과 MBR을 연결해야 함
		this.memory.load(this.registers[ERegisters.eMAR.ordinal()].getValue());
		this.registers[ERegisters.eIR.ordinal()].setValue(this.registers[ERegisters.eMBR.ordinal()].getValue());
	}

	private void load() {
		this.registers[ERegisters.eMAR.ordinal()]
				.setValue((short) (((IR) this.registers[ERegisters.eIR.ordinal()]).getOperand()
						+ this.registers[ERegisters.eSP.ordinal()].getValue()));

		//this.memory.load();
	}

	private void execute() {
		switch (EOpCode.values()[((IR) this.registers[ERegisters.eIR.ordinal()]).getOpCode()]) {
		case eHalt:
			break;
		case eLDA:
			this.load();
			break;
		case eSTA:
			break;
		case eAdd:
			this.registers[ERegisters.eAC.ordinal()].setValue(this.registers[ERegisters.eMBR.ordinal()].getValue());
			this.load();
			this.alu.add();
			break;
		case eSub:
			this.registers[ERegisters.eAC.ordinal()].setValue(this.registers[ERegisters.eMBR.ordinal()].getValue());
			this.load();
			this.alu.subtract();
			break;
		case eEQ: //A = B
			this.registers[ERegisters.eAC.ordinal()].setValue(this.registers[ERegisters.eMBR.ordinal()].getValue());
			this.load();
			this.alu.equal(); //결과값이 status에 저장됨
			break;
		case eGT:
			this.registers[ERegisters.eAC.ordinal()].setValue(this.registers[ERegisters.eMBR.ordinal()].getValue());
			this.load();
			this.alu.greaterThan();
			break;
		case eBEq:
			break;
		case eBGT:
			break;
		case eBranch:
			break;

		}

	}

	private void checkInterrupt() {

	}

	public void run() throws FileNotFoundException {
		while (this.isPowerOn()) {
			this.fetch();
			this.execute();
			this.checkInterrupt();
		}
	}

	public static void main(String[] args) throws IOException {
		CPU cpu = new CPU();
		Memory memory = new Memory();
		cpu.associate(memory);
		cpu.setPowerOn();
	}
}
