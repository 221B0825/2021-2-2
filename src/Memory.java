
public class Memory {
	private short memory[] = new short[256];
	
	public short load(short mar) {
		return this.memory[mar];
	}

	public void store(short mar, short mbr) {
		this.memory[mar] = mbr;
	}
	
	public void read(String fileName) {
		//Read and put into memory
		//Create early when CPU is running
	}
}

