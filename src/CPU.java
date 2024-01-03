
public class CPU {
	// declaration
	private enum ERegisters{
		ePC,
		eSP,
		eAC,
		eIR,
		eStatus,
		eMar,
		embr
	}
	
	private enum EOpCode {
		eHalt,
		eLDA,
		eADD,
		eSub,
		eBEq,//branch equal
		eBGT,//branch grater than
		eBranch //just go to branch
		
	}
	private class Register{
		protected short value;
		public short getValue() {
			return this.value;
		}
		public void setValue(short value) {
			this.value = value;
		}
	}
	
	class IR extends Register{
		public short getOpcode() {
			return (short)(this.value >> 8);
		}
		public short getOprand() {
			return (short)(this.value & 0x00FF);
		}
	}
	
	private class ALU{
		//calculate Opcode
	}
	private class CU{
		//control the PC
	}
	
	// components
	private int registers[];
	private IR ir;
	private ALU alu;
	private CU cu;
	
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
		this.registers = new int[ERegisters.values().length];
		this.alu = new ALU();
		this.cu = new CU();
	}
	private void fetch() {

		
	}
	private void decode() {
			
		}
	private void execute() {
		switch(ir.getOpCode()) {
		case eHalt:
			break;
			
		}
	}
	private void checkInterrupt() {
		
	}
	public void run() {
		while(this.isPowerOn()) {
			this.fetch();
			this.decode();
			this.execute();
			this.checkInterrupt();
		}
	}
	
}
