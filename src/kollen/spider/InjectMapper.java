package kollen.spider;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import kollen.spider.CrawlDatum;

public class InjectMapper implements Mapper<WritableComparable<?>, Text, Text, CrawlDatum>{

	int interval;
	String nutchScoreMDName="";
	String nutchFetchIntervalMDName="";
	URLNormalizers urlNormalizers=new URLNormalizers();
	Filter filter=new Filter();
	Scfilters scfilters=new Scfilters();
	long curTime=0;
	float socreInjected=1.0f;
	
	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void map(WritableComparable<?> key, Text value,
			OutputCollector<Text, CrawlDatum> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		String url=value.toString();
		
		if(url !=null && url.trim().startsWith("#")){
			/* Ignore line that start with #*/
			return;
		}
		
		// if tabs : metadata that could be stored
		//must be name = value and separated by \t
		float customScore=-1f;
		int customInterval=0;
		Map<String,String> metadata = new TreeMap<String,String>();
		if(url.indexOf("\t")!=-1){
			String[] splits=url.split("\t");	//
			url=splits[0];
			for(int s=1;s<splits.length;s++){
				//
				int indexEquals = splits[s].indexOf("=");
				if(indexEquals == -1){
					//
					continue;
				}
				String metaname =splits[s].substring(0,indexEquals);
				String metavalue=splits[s].substring(indexEquals+1);
				if(metaname.equals(nutchScoreMDName)){
					try{
						customScore=Float.parseFloat(metavalue);
					}catch(NumberFormatException nfe){}
				}
				else if(metaname.equals(nutchFetchIntervalMDName)){
					try{
						customInterval = Integer.parseInt(metavalue);
					}catch(NumberFormatException nfe){}
				}
				else metadata.put(metaname,metavalue);
			}
		}
		try{
			url=urlNormalizers.normalize(url,URLNormalizers.SCOPE_INJECT);
			url=filter.filter(url);
			
		}catch(Exception e){
			System.out.println(e.toString());
			url=null;
		}
		if(url != null){
			value.set(url);
			//这里生成一个CrawlDatum对象，设置一些url 的初始化数据
			CrawlDatum datum = new CrawlDatum(CrawlDatum.STATUS_INJECTED,customInterval);
			datum.setFetchTime(curTime);	//设置当前fetch的时间
			Iterator<String> keysIter = metadata.keySet().iterator();
			while(keysIter.hasNext()){	//配置其元数据
				String keymd = keysIter.next();
				String valuemd = metadata.get(keymd);
				datum.getMetaData().put(new Text(keymd),new Text(valuemd));
			}
			//设置初始化分数
			if(customScore != -1)datum.setScore(customScore);
			else datum.setScore(socreInjected);
			try{
				//这里对url的分数进行初始化
				scfilters.injectedScore(value,datum);
			}catch(Exception e){
				System.out.println(e.toString());
			}
			// Map收集相应的数据，类型为<Text,CrawlDatum>
			output.collect(value, datum);
		}
	}
	
}
