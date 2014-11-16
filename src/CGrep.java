import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static akka.actor.Actors.*;
import akka.actor.ActorRef;

public class CGrep {

    public static void main(String[] args) { 
        String pattern = args[0];
        String[] fileNames = Arrays.copyOfRange(args, 1, args.length);
		
		ActorRef collectionActor = actorOf(CollectionActor.class);
		collectionActor.start();
		FileCount fc = new FileCount(fileNames.length);
		collectionActor.tell(fc, collectionActor);

		if (fileNames.length != 0){
			for(String fn : fileNames) {
				Configure conf = new Configure(fn, collectionActor, pattern);
				ActorRef scanActor = actorOf(ScanActor.class);
				scanActor.start();
				scanActor.tell(conf, null);
			}
		} else {
			Configure conf = new Configure(null, collectionActor, pattern);
			ActorRef scanActor = actorOf(ScanActor.class);
			scanActor.start();
			scanActor.tell(conf, null);
		}
    }
}
