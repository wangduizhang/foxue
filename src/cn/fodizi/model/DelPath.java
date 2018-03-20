/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.model;

import cn.fodizi.Crawler.NavModule;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.nodes.Element;

/**
 * 用于捕获视频真实地址
 *
 * @author wp
 */
public class DelPath extends BreadthCrawler {

    private static int num = 0;
    private static HashMap<String, String> urlMap = new HashMap<String, String>();

    private DelPath(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    public static HashMap<String, String> getUrlMap() {
        return urlMap;
    }
    public static void addKV(String k, String v){
        urlMap.put(k,v);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        Element element = page.select("embed").first();
        String string = element.attr("flashvars");
        int begin = string.indexOf("http://");
        //文件格式
        int end = string.indexOf(".mp4", begin);
        int end2 = end == -1 ? string.indexOf(".flv", begin) : end;
        int end3 = end2 == -1 ? string.indexOf("m3u8", begin) : end2;
        String url = string.subSequence(begin, end3 + 4).toString();
        urlMap.put(page.url(), url);
    }

    public static void delUrl(String url) {
        if (!urlMap.containsKey(url)) {
            DelPath crawler = new DelPath("delpath" + num, true);
            num++;
            crawler.addSeed(url);
            /*可以设置每个线程visit的间隔，这里是毫秒*/
            //crawler.setVisitInterval(1000);
            /*可以设置http请求重试的间隔，这里是毫秒*/
            //crawler.setRetryInterval(1000);
            crawler.setThreads(30);
            try {
                crawler.start(2);
            } catch (Exception ex) {
                Logger.getLogger(NavModule.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
