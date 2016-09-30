
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class GeneralPageProcesser  implements PageProcessor{
    private Site site=Site.me();
    @Override
    public void process(Page page) {
        String RawData=page.getRawText();
        System.out.println(RawData);
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main (String[] args){
        String url="https://www.huxiu.com/v2_action/article_list";
        Request req=new Request(url);
        Map<String,Object>  map = new HashMap<>();
        map.put("huxiu_hash_code","c28f4cd8197a313a7972a8d8fae18b4c");
        map.put("page","2");
        map.put("last_dateline","1475123204");
        req.setExtras(map);

        Spider.create(new GeneralPageProcesser()).addRequest(req).run();
    }
}
