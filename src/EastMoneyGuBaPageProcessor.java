/**
 * Created by hp on 2016/9/27.
 */



import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;



public class EastMoneyGuBaPageProcessor implements PageProcessor  {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static String base_url = "http://m.guba.eastmoney.com/getdata/articlelist?code=%s&count=200&type=&id=&sort=&thispage=%s";


    public void process(Page page) {

        int count = Integer.parseInt(new JsonPathSelector("$.count").select(page.getRawText()));
        List<String> ids = new JsonPathSelector("$.re").selectList(page.getRawText());
        String stock_code = new JsonPathSelector("$.re[0].post_guba.stockbar_external_code").select(page.getRawText());
        page.putField("re", ids);

        if (page.getResultItems().get("re")==null){
            //skip this page
            page.setSkip(true);
        }else{
            double TotalPage = Math.ceil(count / 200);
            int StartPage = 2;
            while (StartPage  < TotalPage){
                System.out.println("Current Page Number is " + StartPage + "; Total Page is  " + TotalPage);
                page.addTargetRequest(String.format(base_url, stock_code, StartPage));
                StartPage += 1;
            }
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String code_url = String.format(base_url, "600779","1");
        Spider.create(new EastMoneyGuBaPageProcessor())
                .addUrl(code_url)
                .addPipeline(new MyFilePipeline("E:\\02_Development\\EastMoney\\TextData\\"))
                .thread(5)
                .run();
    }
}
