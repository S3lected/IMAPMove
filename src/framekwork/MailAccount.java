package framekwork;


public class MailAccount {
	private String accountName;
	private String accountMail;
	private String imapServer;
	private String imapLoginName;
	private String imapLoginPassword;

	public MailAccount(String accountName, String accountMail, String imapServer, String imapLoginName,
			String imapLoginPassword) {
		super();
		this.accountName = accountName.trim();
		this.accountMail = accountMail.trim();
		this.imapServer = imapServer.trim();
		this.imapLoginName = imapLoginName.trim();
		this.imapLoginPassword = imapLoginPassword.trim();
	}
	
	

	@Override
	public String toString() {
		return "MailAccount [accountName=" + accountName + ", accountMail=" + accountMail + ", imapServer=" + imapServer
				+ ", imapLoginName=" + imapLoginName + ", imapLoginPassword=" + imapLoginPassword + "]";
	}



	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountMail() {
		return accountMail;
	}

	public void setAccountMail(String accountMail) {
		this.accountMail = accountMail;
	}

	public String getImapServer() {
		return imapServer;
	}

	public void setImapServer(String imapServer) {
		this.imapServer = imapServer;
	}

	public String getImapLoginName() {
		return imapLoginName;
	}

	public void setImapLoginName(String imapLoginName) {
		this.imapLoginName = imapLoginName;
	}

	public String getImapLoginPassword() {
		return imapLoginPassword;
	}

	public void setImapLoginPassword(String imapLoginPassword) {
		this.imapLoginPassword = imapLoginPassword;
	}
	
	
	
	
}
