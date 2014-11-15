import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class CGrep {

    public static void main(String[] args) { 
        String pattern = args[0];
        String[] fileNames = Arrays.copyOfRange(args, 1, args.length);
		
		ActorSystem system = ActorSystem.create("CGrep");
		
		ActorRef collectionActor = system.actorOf(Props.create(CollectionActor.class));
		collectionActor.tell(fileNames.length, collectionActor);

		if (fileNames.length != 0){
			for(String fn : fileNames) {
				Configure conf = new Configure(fn, collectionActor, pattern);
				ActorRef scanActor = system.actorOf(Props.create(ScanActor.class));
				scanActor.tell(conf, null);
			}
		} else {
			Configure conf = new Configure(null, collectionActor, pattern);
			ActorRef scanActor = system.actorOf(Props.create(ScanActor.class));
			scanActor.tell(conf, null);
		}
    }
}
