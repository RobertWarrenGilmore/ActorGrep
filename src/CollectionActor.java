import akka.actor.ActorRef;
import akka.actor.UntypedActor;
//import akka.actor.Actors;

import java.util.*;

public class CollectionActor extends UntypedActor {
	private int fileCount;
	private boolean didReceiveFileCount = false;

	private int returnedFiles = 0;
	public void onReceive(Object message) throws Exception {

		if (message instanceof FileCount && !didReceiveFileCount) {
			this.fileCount = ((FileCount)message).getFileCount();
			this.didReceiveFileCount = true;

		} else if (message instanceof Found) {

			Found foundObj = (Found)message;
			List<String> foundStrings = new ArrayList<String>();
			String regexStr;
			String fileName;

			foundStrings = foundObj.getLines();
			System.out.println("For file: \"" + foundObj.getFileName() + "\" found:");
			
			for(String ls : foundStrings) {
				System.out.printf("\t%s\n", ls);
			}

			if(foundStrings.size() == 0) {
				System.out.println("\tNone");
			}

			this.returnedFiles += 1;

//			if(this.returnedFiles == this.fileCount) {
//				Actors.registry().shutdown();
//			}
			
		} else {
			throw new IllegalArgumentException("Unknown message: " + message);
		}
	}
}
