import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Memory {
	private short[] segment;
	private short segmentSize;
	
	public Memory() throws IOException {
		this.segmentSize = 10;
		this.segment = new short[segmentSize];
		int index = 0;
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader("./exe/exe1"));
		while(bufferedReader.ready()) {
			String line = bufferedReader.readLine();
			if(line.equals(""))
				break;
			else {
				this.segment[index] = Short.parseShort(line,2);
				System.out.println(segment[index]);
				index++;
			}
		}
	}
	// header setting
	public short setPC() {
		return (short) (this.segment[0]+5);
	}

	public short setSP() {
		return this.segment[1];
	}

	public void load(short value) throws FileNotFoundException {
		System.out.println(value);
	}
}
//BufferedReader exe1 = new BufferedReader(new FileReader("./exe/exe1"));
//this.segment = new ArrayList<Integer>();
//while (exe1.ready()) {
//	String line = exe1.readLine();
//	if (!line.equals("")) {
//		segment.add(line);
//	}
//}
//exe1.close();
