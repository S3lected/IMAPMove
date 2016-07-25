package framekwork;


public class MailAccountMap {
	private MailAccount from;
	private MailAccount to;
	
	public MailAccountMap(MailAccount from, MailAccount to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public MailAccount getFrom() {
		return from;
	}
	public void setFrom(MailAccount from) {
		this.from = from;
	}
	public MailAccount getTo() {
		return to;
	}
	public void setTo(MailAccount to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "MailAccountMap [from=" + from + ", to=" + to + "]";
	}
	
	
}
