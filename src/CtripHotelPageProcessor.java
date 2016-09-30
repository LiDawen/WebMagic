import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class CtripHotelPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setCharset("GBK");
    public static  String baseUrl="http://hotels.ctrip.com/Domestic/tool/AjaxHotelCommentList.aspx?MasterHotelID=2605097&hotel=2605097&property=0&card=0&cardpos=0&NewOpenCount=0&AutoExpiredCount=0&RecordCount=1639&OpenDate=&abForComLabel=False&viewVersion=c&productcode=&currentPage=%s&contyped=0&eleven=25f3e9f431d4b3a53572ecf873eb4717&callback=CASewPgwsTDdHVYNa&_=1475111086424";

    @Override
    public void process(Page page) {
        String str=page.getRawText();
        System.out.println(str);
        //System.out.println(comment);

    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args){
        String url=String.format(baseUrl,'1');
        Spider.create(new CtripHotelPageProcessor()).addUrl(url).run();
        System.out.println("hello world");
    }


}
