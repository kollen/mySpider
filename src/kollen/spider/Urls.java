package kollen.spider;

public class Urls {
	private String url="";
	private Urls next=null;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Urls getNext() {
		return next;
	}
	public void setNext(Urls next) {
		this.next = next;
	}
}
