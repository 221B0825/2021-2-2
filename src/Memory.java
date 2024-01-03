
public class Memory {
	
	private Process process;
	
	public Memory() {
		this.process = new Process();
	}
	public class Process {
		
		private short header[] = { 
				0x001E,
				0x0010,
				0x0008
		};
		private short codeSegment[] = {
				0x0103, //SETAC 3
				0x0300, //STA A
//				0x0104, //SETAC 4
//				0x0301, //STA B
//				0x0200, //LDA A
//				0x0401, //ADD B
//				0x0301, //STA B
//				0x0000 //HALT return
		};
		private short dataSegment[] = {
				0x0000, // A , address 0
				0x0000 // B , address 1
		};
	}

	public short load(short MAR) { // fetch
		short MBR = this.process.codeSegment[MAR];
		return MBR;	
	}
	
	public short decode(short MAR) {
		short MBR = this.process.dataSegment[MAR];
		return MBR;
	}
	
	public void store(short address, short data) {
		this.process.dataSegment[address] = data;
	}
}
