
public class Memory {
	public class Process {
		private short header[] = { 
				0x001E,
				0x0010,
				0x0008
		};
		private short codeSegment[] = {
				0x0003, //SetAC 3
				0x0200 //STA A
		};
		private short dataSegment[] = {
				0x0000,
				0x0000
		};
	}

	public void load() {

	}

}
