import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.xml.soap.SAAJResult;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class ELongHotelPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
   static  String baseUrl="http://hotel.elong.com/ajax/detail/gethotelreviews/?hotelId=%s&recommendedType=0&pageIndex=%s&mainTagId=0&subTagId=0&_=1475116265857";
    @Override
    public void process(Page page) {
        String urlStr=page.getUrl().get();
        int PageIndex=urlStr.indexOf("pageIndex=");
        int AndSepa=urlStr.indexOf('&',PageIndex+"pageIndex=".length());
        int PageNum=Integer.parseInt(urlStr.substring(PageIndex + "pageIndex=".length(), AndSepa));
        String str=page.getRawText();
        int sep1=str.indexOf(':');
        int sep2=str.indexOf(',');
        int totalNum=Integer.parseInt(str.substring(sep1+1,sep2));
        System.out.println(str);
        if(totalNum<=0){

        }else{
            PageNum++;
            page.addTargetRequest(String.format(baseUrl,PageNum));
        }




    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args){
        String url=String.format(baseUrl,"50301016","1");
        Spider.create(new ELongHotelPageProcessor()).addUrl(url).run();
        System.out.println("hello world");
    }
}
