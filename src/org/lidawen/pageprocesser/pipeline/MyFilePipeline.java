package org.lidawen.pageprocesser.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

public class MyFilePipeline extends FilePipeline {
	public MyFilePipeline(String path) {
		super(path);
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		Date date=new Date();
		this.TimeStamp=sdf.format(date);
		// TODO Auto-generated constructor stub
	}


	private Logger logger = LoggerFactory.getLogger(getClass());
	private String TimeStamp;
	public MyFilePipeline() {
		super();
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		Date date=new Date();
		this.TimeStamp=sdf.format(date);
	
	}

	
	@Override
	public void process(ResultItems resultItems, Task task) {
		  String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
	        try {
	            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + TimeStamp + ".txt"),true),"UTF-8"));
	            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
	            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
	                if (entry.getValue() instanceof Iterable) {
	                    Iterable value = (Iterable) entry.getValue();
	                    printWriter.println(entry.getKey() + ":");
	                    for (Object o : value) {
	                        printWriter.println(o);
	                    }
	                } else {
	                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
	                }
	            }
	            printWriter.close();
	        } catch (IOException e) {
	            logger.warn("write file error", e);
	        }
	}

}
