import java.util.Arrays;
import java.util.List;

public class Found {
    private String fileName;
    private String[] lines;

    public Found(String fileName, String[] lines) {
        this.fileName = fileName;
        this.lines = lines;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getLines() {
        return Arrays.asList(lines);
    }
    
    public String toString() {
    	String string = "";
    	for(String l : lines){
    		string = string.concat(l + "\n");
    	}
    	return string;
    }
}
