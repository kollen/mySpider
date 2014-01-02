package kollen.spider;

public class TestFetch {
	public static void main(String[] args){
		Fetch fetch=new Fetch();
		boolean success=false;
		String saveAddress="/home/jeff/tmp/";
		Urls head=new Urls();
		head.setUrl("http://www.gdut.edu.cn/");
		StringBuffer sb=null;
		String destFileName=saveAddress+changeFileName(head.getUrl());
		success=fetch.getPage(head.getUrl(),destFileName);
		if(!success){
			System.out.println("no pass...");
		}else{
			System.out.println();
			sb=Analyze.getUrls(destFileName);
			
		}
	}
	public static String changeFileName(String url){
		String result="";
		byte[] a=url.getBytes();
		for(byte word : a){
			if(((word > ((byte)'0'))&&(word < ((byte)'9')))||((word > ((byte)'a'))&&(word < ((byte)'z')))){
				result=result+(char)word;
			}else{
				result=result+'_';
			}
		}
		return result;
	}
}
