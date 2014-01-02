package kollen.spider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Analyze {
	public static StringBuffer getUrls(String path){
		StringBuffer urls=new StringBuffer();
		String temp="";
		InputStream in=null;
		try {
			in= new FileInputStream(path);
			StringBuffer sb=new StringBuffer();
			byte[] b = new byte[1024];
			while(in.read(b,0,1024)>0){
				sb.append(new String(b));
			}
			int count=0;
			int begin;
			int end;
			//获取链接
			while(((count=sb.indexOf("<a ",count))>0)&&((begin=sb.indexOf("href=",count))>0)){
				if((end=sb.indexOf("\"",begin+6))>0){
					if(begin+6<0)break;
					temp=sb.substring(begin+6,end);
					if(temp.indexOf("http",0)!=-1){
						System.out.println(temp);
						urls.append("\n"+temp);
					}
					count=end;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return urls;
	}
}
