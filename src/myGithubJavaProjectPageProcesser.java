import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class myGithubJavaProjectPageProcesser implements PageProcessor{

	private Site site=Site.me();
	

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		String NextPage=page.getHtml().xpath("//a[@class='next_page']/@href").get();
		if(NextPage!=null){
			page.addTargetRequest(NextPage);
		}
		List<String> names=page.getHtml().xpath("//h3[@class='repo-list-name']/a/text()").all();
		List<String> descriptions=page.getHtml().xpath("//p[@class='repo-list-description']/text()").all();
		List<String> links=page.getHtml().xpath("//h3[@class='repo-list-name']/a/@href").all();
		
		List<Map<String, String >> ArticleList=new ArrayList<Map<String,String>>();
		for(int i=0;i<names.size();i++){
			Map<String, String > map=new HashMap<String, String>();
			map.put("name", names.get(i));
			map.put("descriptions", descriptions.get(i));
			map.put("links", links.get(i));
			System.out.println("name:"+names.get(i)+"\n"+"descriptions:"+descriptions.get(i)+"\n"+"links:"+links.get(i));
			System.out.println("");
			ArticleList.add(map);
		}
		page.putField("ArticleList", ArticleList);
	
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return this.site;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
    	String path="D:\\personal\\20160926 WebMagic\\data\\GithubJavaProject"+sdf.format(date);
    	File file=new File(path);
    	  if (!file.exists()) {
    		  file.mkdirs();
          }
		Spider.create(new myGithubJavaProjectPageProcesser()).addUrl("https://github.com/search?l=Java&p=1&q=stars%3A%3E1&s=stars&type=Repositories").addPipeline(new FilePipeline(path)).thread(5).run();
	}

}
