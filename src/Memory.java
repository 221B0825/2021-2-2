import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Memory {
	private short memory[];
	
	public Memory() {
		this.memory = new short[512]; //1024 byte: allocate 2 bytes per space (short)
	}
	public short load(short mar) {
		return this.memory[mar];
	}

	public void store(short mar, short mbr) {
		this.memory[mar] = mbr;
	}
	
	public void readExe(String fileName) {
		try {
			Scanner scanner = new Scanner(new File("exe/"+fileName));
			short address = 0;
			while(scanner.hasNext()) {
				this.memory[address] = scanner.nextShort(16);
				address++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

