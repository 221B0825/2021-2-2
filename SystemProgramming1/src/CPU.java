
public class CPU {
	// declaration
	private enum ERegisters{
		ePC,
		eSP,
		eAC,
		eStatus,
		mar,
		mbr
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
	
	class IR {
		private EOpCode eOpCode;
		private int operand;
	}
	
	private class ALU{
		
	}
	private class CU{
		
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
