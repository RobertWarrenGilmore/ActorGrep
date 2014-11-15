import akka.actor.ActorRef;

public class Configure {
	private String filename;
	private ActorRef ref;
	private String pattern;

	public Configure(String fn, ActorRef r, String p) {
		this.filename = fn;
		this.ref = r;
		this.pattern = p;
	}

	public String getFilename() {
		return this.filename;
	}

	public ActorRef getActorRef() {
		return this.ref;
	}

	public String getPattern() {
		return this.pattern;
	}
}
