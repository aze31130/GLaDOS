package aze.GLaDOS;

public class GLaDOS {
	public String version;
	public String token;
	public int requests;
	
	public void addRequest() {
		this.requests++;
	}
	
	public int getRequests() {
		return this.requests;
	}
	
	@Override
	public String toString() {
		return "";
	}
}
