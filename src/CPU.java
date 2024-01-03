
public class CPU {
	// declaration
	private enum ERegister {
		ePC, eSP, eAC, eIR, eStatus, eMAR, eMBR
	}

	private enum EOpCode {
		eHalt, eSETAC, eLDA, eSTA, eAdd, eSub, eEQ, eGT, eBEq, eBGT, eBranch

	}

	private class CU {
		private Register[] registers;

		public CU(Register[] registers) {
			this.registers = registers;
		}

		public void control() {
			this.registers[ERegister.ePC.ordinal()]
					.setValue((short) (this.registers[ERegister.ePC.ordinal()].getValue() + 1));
		}
	}

	private class ALU {
		private Register[] registers;

		public ALU(Register[] registers) {
			this.registers = registers;
		}

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

	public void setPowerOn() {
		this.bPowerOn = true;
		this.run();
	}

	public void shutDown() {
		this.bPowerOn = false;
	}

	// constructor
	public CPU() {
		this.registers = new Register[ERegister.values().length];
		for (ERegister eRegister : ERegister.values()) {
			this.registers[eRegister.ordinal()] = new Register();
		}
		this.registers[ERegister.eIR.ordinal()] = new IR();

		this.cu = new CU(this.registers);
		this.alu = new ALU(this.registers);

		// now SP value is 0 ...
		// this.registers[ERegister.eSP.ordinal()].setValue((short) 8);
	}

	public void associate(Memory memory) {
		this.memory = memory;
	}

	// ---------------------------------- run ----------------------------------
	private void fetch() {
		// PC --> MAR MBR --> IR
		System.out.println("PC: "+this.registers[ERegister.ePC.ordinal()].getValue());
		this.registers[ERegister.eMAR.ordinal()].setValue(this.registers[ERegister.ePC.ordinal()].getValue());

		this.registers[ERegister.eMBR.ordinal()]
				.setValue(this.memory.load(this.registers[ERegister.eMAR.ordinal()].getValue()));
		System.out.println("MBR: "+this.registers[ERegister.eMBR.ordinal()].getValue());

		this.registers[ERegister.eIR.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
	}

	// -------------------------------------------------------------------------
	private void execute() {
		switch (EOpCode.values()[((IR) this.registers[ERegister.eIR.ordinal()]).getOpCode()]) {
		case eHalt:
			break;
		case eSETAC:
			// Store the operand value of the current IR (the value to be placed in the AC, not the address) in the AC
			this.registers[ERegister.eAC.ordinal()]
					.setValue(((IR) this.registers[ERegister.eIR.ordinal()]).getOperand());
			break;
		case eLDA:
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
			break;
		case eSTA: // (x) <-- AC
			this.load();
			this.memory.store(this.registers[ERegister.eMBR.ordinal()].getValue(),
					this.registers[ERegister.eAC.ordinal()].getValue());
			break;
		case eAdd:
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
			this.load();
			this.alu.add();
			break;
		case eSub:
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
			this.load();
			this.alu.subtract();
			break;
		case eEQ: // A = B
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
			this.load();
			this.alu.equal(); // save result to status
			break;
		case eGT:
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
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

	private void load() {
		// IR.operand + SP --> MAR MBR
//		this.registers[ERegister.eMAR.ordinal()]
//				.setValue((short) (((IR) this.registers[ERegister.eIR.ordinal()]).getOperand()
//						+ this.registers[ERegister.eSP.ordinal()].getValue()));
		
		this.registers[ERegister.eMAR.ordinal()]
				.setValue((short) (((IR) this.registers[ERegister.eIR.ordinal()]).getOperand()));
		
		this.registers[ERegister.eMBR.ordinal()]
				.setValue(this.memory.decode(this.registers[ERegister.eMAR.ordinal()].getValue()));
		
	}

	// -------------------------------------------------------------------------
	private void checkInterrupt() {
		this.cu.control();
	}

	// ---------------------------------- run ----------------------------------
	public void run() {
		while (this.isPowerOn()) {
			this.fetch();
			this.execute();
			this.checkInterrupt();
		}
	}

	public static void main(String[] args) {
		CPU cpu = new CPU();
		Memory memory = new Memory();
		cpu.associate(memory);
		cpu.setPowerOn();
	}
}
