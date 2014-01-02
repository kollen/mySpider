package kollen.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// 获取网页
public class Fetch {
	//获取webAddress的网页，并存到destFileName的文件里
	//自动获取webAddress的网页类型
	public boolean getPage(String webAddress,String destFileName){
		File DestFile=null;
		if(webAddress!=null && (!webAddress.equals(""))){
			try{
				URL url = new URL(webAddress);
				HttpURLConnection httpConn=null;
				httpConn = (HttpURLConnection)url.openConnection();

				httpConn.setRequestMethod("GET");  
		      httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.14) Gecko/20080404 Firefox/2.0.0.14");  
		      InputStream in = httpConn.getInputStream(); 
		      if((destFileName!=null)&&(destFileName!="")){
		    	  	String contentOfPage = httpConn.getContentType().toString(); 
			      System.out.println(contentOfPage);
			      
			      //网页 
			      if(contentOfPage.substring(0,9).equals("text/html")){
			    	  DestFile=new File(destFileName);
			      }
			      // 图片
			      else if(contentOfPage.substring(0,10).equals("image/jpeg")){
			    	  DestFile=new File(destFileName);
			      }
			      // text/plain
			      else if(contentOfPage.substring(0,10).equals("text/plain")){
			    	  DestFile=new File(destFileName);
			      }
			      	// 无法识别是什么网页类型，无格式输出
			      else if(DestFile==null){
			    	  System.out.println("无法识别是什么网页类型，无格式输出");
			    	  DestFile=new File(destFileName);
			      }
			    	OutputStream out=null;
				   out=new FileOutputStream(DestFile);
				   int bytesRead = 0;
				   byte[] buffer = new byte[8192];
				   while((bytesRead = in.read(buffer,0,8192))>0)
				    	 out.write(buffer,0,bytesRead);
				   out.close();
				   in.close();
		      }
			}catch(Exception e){
				System.out.println(e.toString());
				return false;
			}
		}
		return true;
	}
}
