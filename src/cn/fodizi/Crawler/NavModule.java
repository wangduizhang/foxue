/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.Crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.fodizi.main.RootWindow;
import cn.fodizi.myclass.NewLabel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

/**
 * 用于捕获网页导航栏的模块
 *
 * @author wp
 */
public class NavModule extends BreadthCrawler {

    private static Elements elements;
    //正则表达式,不接受的字段
    private static final String string = "首页|下载|文章|更多法师|佛教音乐";
    private static NavModule crawler = new NavModule("labe", true);
    private static List<NewLabel> labels = new ArrayList<NewLabel>();

    private NavModule(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    /*
        可以往next中添加希望后续爬取的任务，任务可以是URL或者CrawlDatum
        爬虫不会重复爬取任务，从2.20版之后，爬虫根据CrawlDatum的key去重，而不是URL
        因此如果希望重复爬取某个URL，只要将CrawlDatum的key设置为一个历史中不存在的值即可
        例如增量爬取，可以使用 爬取时间+URL作为key.
        新版本中，可以直接通过 page.select(css选择器)方法来抽取网页中的信息，等价于
        page.getDoc().select(css选择器)方法，page.getDoc()获取到的是Jsoup中的
        Document对象，细节请参考Jsoup教程
     */
    @Override
    public void visit(Page page, CrawlDatums next) {
        Elements elements = page.select(".tt").select("a");
        for (Element element : elements) {
            if (!(element.text().matches(string))) {
                NewLabel newLabel = new NewLabel(element.text(), element.attr("href"));
                //写入标签组
                labels.add(newLabel);
            }
        }
    }

    public static void showLable() {
        for (NewLabel lable : labels) {
            //输出标签
            RootWindow.getJPanel().add(lable);
        }
    }

    public static void delUrl() {
        crawler.addSeed("http://www.fodizi.com/fofa/");
        /*可以设置每个线程visit的间隔，这里是毫秒*/
        //crawler.setVisitInterval(1000);
        /*可以设置http请求重试的间隔，这里是毫秒*/
        //crawler.setRetryInterval(1000);
        crawler.setThreads(20);
        try {
            crawler.start(1);
        } catch (Exception ex) {
            Logger.getLogger(NavModule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
