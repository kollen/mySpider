package kollen.spider;
//status, date, interval, failures, linkCount
//Status: {db_unfetched, db_fetched, db_gone,linked, fetch_success, fetch_fail, fetch_gone}
public class CrawlDatum {
	
	Status status;
	String date;
	int interval;
	int failures;
	int linkCount;
	public CrawlDatum(){
		status=new Status();
		date="";
		interval=0;
		failures=0;
		linkCount=0;
	}
}
final class Status{
	boolean db_unfetched;
	boolean db_fetched;
	boolean db_gone;
	boolean linked;
	boolean fetch_success;
	boolean fetch_fail;
	boolean fetch_gone;
	public Status(){
		db_unfetched=false;
		db_fetched=false;
		db_gone=false;
		linked=false;
		fetch_success=false;
		fetch_fail=false;
		fetch_gone=false;
	}
}
