import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepCallable implements Callable<Found> {

    private String fileName;
    private Pattern pattern;

    public GrepCallable(String fileName, String pattern) {
        this.fileName = fileName;
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public Found call() throws Exception {
        List<String> foundLines = new LinkedList<String>();

        // Iterate over lines of the file.
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;
        int lineNumber = 0;
        while ((line = br.readLine()) != null) {
            lineNumber++;
            // If the line matches, add the line number to it and add it to the list.
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String numberedLine = lineNumber + " " + line;
                foundLines.add(numberedLine);
            }
        }
        br.close();

        // Create the Found object using the list and the file name.
        Found result = new Found(fileName, foundLines.toArray(new String[foundLines.size()]));
        return result;
    }
}
