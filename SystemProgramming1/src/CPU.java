
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
		eBEq,//브랜치이퀄
		eBGT,//브랜치 그래이터 댄
		eBranch //그냥 그대로 가라
		
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
		//계산하라고 오피코드를 주면 계산할거고
	}
	private class CU{
		// PC를 제어
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
