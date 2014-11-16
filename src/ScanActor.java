import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.LinkedList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ScanActor extends UntypedActor {

	public void onReceive(Object message) throws Exception {
		if (message instanceof Configure) {
			Configure config = (Configure)message;
			
			BufferedReader br;
			if(config.getFilename() != null) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(config.getFilename())));
			} else {
				br = new BufferedReader(new InputStreamReader(System.in));
			}

			List<String> lines = find(br, config.getPattern());
			
			Found found = new Found(config.getFilename(), lines.toArray(new String[lines.size()]));
			
			config.getActorRef().tell(found, null);

		} else {
			throw new IllegalArgumentException("Scan actor Unknown message: " + message);
		}
	}

	public List<String> find(BufferedReader br, String regex) {
		List<String> foundLines = new LinkedList<String>();

		String line;
		Pattern pattern = Pattern.compile(regex);

		try {
		while((line = br.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);

			if(matcher.find()) {
				foundLines.add(line);
			}
		}
		br.close();
		} catch (Exception e) {}
		return foundLines;
	}
}
