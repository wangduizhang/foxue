/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.Crawler;

import cn.fodizi.main.JiSuFrame;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.fodizi.main.RootWindow;
import cn.fodizi.model.DelPath;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 点击视频后用于处理新的链接
 *
 * @author wp
 */
public class VideoInfo extends BreadthCrawler {

    private static Elements jishu;
    private static Element video;
    private static int num = 0;
    private static HashMap<String, String> UrlMap = new HashMap<String, String>();

    private VideoInfo(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    public static HashMap<String, String> getUrlMap() {
        return UrlMap;
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
    public void visit(Page page, CrawlDatums next) {
        Elements vidospath = page.select("iframe");
        Element jishus = page.select(".jisu").first();
        //System.out.println("======================    "+ (vidospath.isEmpty()));
        if (vidospath.isEmpty()) {
            //JOptionPane.showMessageDialog(null, "暂不支持音频");
            RootWindow.getRootWindow().setEnabled(true);
        } else {
            final JiSuFrame jsf = new JiSuFrame(page.select(".k2").select("b").first().text());
            jsf.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    RootWindow.getRootWindow().setEnabled(true);
                    jsf.dispose();
                }
            });
            jsf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Element vidopath = vidospath.first();
            //可以解析
            if (vidopath.attr("name").equals("fodizi") && !vidopath.attr("src").startsWith("yk.htm")) {
                Elements aaa = jishus.select("a");
                jsf.addLable("               共 " + aaa.size() + " 个视频，调整窗口大小显示全部视频               ", null);
                //单视频
                if (aaa.first().text().endsWith("全集")) {
                    String s = "http://www.fodizi.com/fofa/list/" + vidopath.attr("src");
                    jsf.addLable(aaa.first().text(), s);
                    DelPath.delUrl(s);
                    //有经文
                    if (aaa.size() > 1) {
                        jsf.addLable(aaa.last().text(), aaa.last().attr("href"));
                    }
                    jsf.myShow();
                    //jsf.setAlwaysOnTop(false);
                } else {
                    //多视频
                    String s = "http://www.fodizi.com/fofa/list/" + vidopath.attr("src");
                    DelPath.delUrl(s);
                    String base = DelPath.getUrlMap().get(s);
                    jsf.addLable(aaa.first().text(), s);
                    jsf.myShow();
                    //System.out.println(aaa);
                    for (int i = 2; i <= aaa.size(); i++) {
                        jsf.addLable(aaa.get(i - 1).text(), s + i);
                        DelPath.addKV(s + i, base.replaceAll("-1", "-" + i));
                    }
                }
            } else {
                //不可解析
                //JOptionPane.showMessageDialog(null, "暂不支持此地址视频");
                System.out.println("不可解析,尝试获取下载地址");
                RootWindow.getRootWindow().setEnabled(true);
            }
        }

    }

    public static void delUrl(String url) {
        VideoInfo crawler = new VideoInfo("cewlwe" + num, true);
        num++;
        crawler.addSeed(url);
        /*可以设置每个线程visit的间隔，这里是毫秒*/
        //crawler.setVisitInterval(1000);
        /*可以设置http请求重试的间隔，这里
        是毫秒*/
        //crawler.setRetryInterval(1000);
        crawler.setThreads(30);
        try {
            crawler.start(1);
        } catch (Exception ex) {
            Logger.getLogger(NavModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
