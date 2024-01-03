
public class CPU {
	// declaration
	private enum ERegister {
		ePC, eSP, eAC, eIR, eStatus, eMAR, eMBR
	}

	private enum EOpCode { //AC Adddress IR Address
		eHalt, eLDACC, eLDACA, eLDIRA, eSTA, eAdd, eSub, eEQ, eGT, eBEq, eBGT, eBranch

	}

	private class CU {
		// control the PC
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

	public void setPowerOn() {
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
		this.registers = new Register[ERegister.values().length];
		for(ERegister eRegister : ERegister.values()) {
			this.registers[eRegister.ordinal()] = new Register();
		}
		this.registers[ERegister.eIR.ordinal()] = new IR();
	}

	public void associate(Memory memory) {
		this.memory = memory;
	}

	private void fetch() {
		// PC --> MAR, MBR --> IR
		this.registers[ERegister.eMAR.ordinal()].setValue(this.registers[ERegister.ePC.ordinal()].getValue());
		// memory --> MBR
		this.load();
		//MBR --> IR
		this.registers[ERegister.eIR.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
	}

	private void loadACC() {
		short operand = (short)((IR) this.registers[ERegister.eIR.ordinal()]).getOperand();
		this.registers[ERegister.eAC.ordinal()].setValue(operand);
		
	}
	private void loadACA() {
		short address = (short)((IR) this.registers[ERegister.eIR.ordinal()]).getOperand();
		this.registers[ERegister.eMAR.ordinal()].setValue(address);
		short data = this.memory.load(this.registers[ERegister.eMAR.ordinal()].getValue());
		this.registers[ERegister.eMBR.ordinal()].setValue(data);
		this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
	}
	private void loadIRA() {
		short address = this.registers[ERegister.ePC.ordinal()].getValue();
		this.registers[ERegister.eMAR.ordinal()].setValue(address);
		short data = this.memory.load(this.registers[ERegister.eMAR.ordinal()].getValue());
		this.registers[ERegister.eMBR.ordinal()].setValue(data);
		this.registers[ERegister.eIR.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
	}
	
	private void store() {
		this.memory.store(this.registers[ERegister.eMAR.ordinal()].getValue(),this.registers[ERegister.eMBR.ordinal()].getValue());
		
	}
	private void add() {
		this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
		//this.load();
		this.alu.add();
		
	}

	private void execute() {
		switch (EOpCode.values()[((IR) this.registers[ERegister.eIR.ordinal()]).getOpCode()]) {
		case eHalt:
			break;
		case eLDACC:
			this.loadACC();
			break;
		case eLDACA:
			this.loadACA();
			break;
		case eLDIRA:
			this.loadIRA();
			break;
		case eSTA:
			this.store();
			break;
		case eAdd:
			this.add();
			
			break;
		case eSub:
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
			this.load();
			this.alu.subtract();
			break;
		case eEQ: //A = B
			this.registers[ERegister.eAC.ordinal()].setValue(this.registers[ERegister.eMBR.ordinal()].getValue());
			this.load();
			this.alu.equal(); //save result to status
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





	private void checkInterrupt() {

	}

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
