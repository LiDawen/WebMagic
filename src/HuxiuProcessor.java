import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
public class HuxiuProcessor implements PageProcessor {
    public void process(Page page) {
        List<String> requests = page.getHtml().links().regex(".*article.*").all();
        page.addTargetRequests(requests);
        page.putField("title",page.getHtml().xpath("//div[@class='article-wrap']//h1/text()"));
        page.putField("content",page.getHtml().xpath("//div[@id='article_content']/tidyText()"));
    }

    public Site getSite() {
        return Site.me().setDomain("www.huxiu.com").addStartUrl("http://www.huxiu.com/");
    }

    public static void main(String[] args) {
    	Date date=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
    	String path="D:\\personal\\20160926 WebMagic\\data\\huxiu"+sdf.format(date);
    	File file=new File(path);
    	  if (!file.exists()) {
    		  file.mkdirs();
          }
        Spider.create(new HuxiuProcessor()).addPipeline(new FilePipeline(path)).addPipeline(new ConsolePipeline()).thread(5).run();
    }

}
