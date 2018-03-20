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
import cn.fodizi.myclass.NewVideoPanel;
import cn.fodizi.myclass.VideoList;
import cn.fodizi.res.JPBar;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

/**
 * 用于捕获网页视频页面的模块
 *
 * @author wp
 */
public class AllVideo extends BreadthCrawler {

    private static String basePath = "http://www.fodizi.com/fofa/";
    private static Elements elements;
    private static boolean flag = true;
    //需要查找的页数
    private static int pageNum = 0;
    private static int indexPage = 1;

    public static int getPageNum() {
        return pageNum;
    }

    private AllVideo(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        //获取容器
        ArrayList<NewVideoPanel> videos = VideoList.getVideoList();
        //获取对象
        elements = page.select(".k3");
        //获取需要读取的页面数
        if (pageNum == 0) {
            Elements spans = page.select("span");
            String ss = spans.first().text().replace(" ", "");
            int n = ss.indexOf("1/");
            int m = ss.indexOf("上", n);
            String numString = ss.subSequence(n + 2, m - 1).toString();
            pageNum = new Integer(numString);
            //RootWindow.setVideoNum(pageNum * 23);
        }

        for (Element element : elements) {
            String title = element.child(0).text();
            StringBuilder picUrl = new StringBuilder(element.child(0).child(0).attr("src"));
            if (picUrl.indexOf("http") == -1) {
                picUrl.insert(0, "http:");
            }
            if (picUrl.indexOf("fodizi") != -1) {
                picUrl = null;
            }
            if (picUrl == null) {
                String path = "file://" + System.getProperty("user.dir") + File.separator + "img" + File.separator + "2.jpg";
                picUrl = new StringBuilder(path);
            }
            String url = basePath + element.child(0).attr("href");
            //创建JPanel

            NewVideoPanel newVideoPanel = new NewVideoPanel(url, picUrl.toString(), title);

            //鼠标事件
            newVideoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    //newVideoPanel.sizesss();
                    final JPBar jpb = new JPBar(RootWindow.getRootWindow(),"正在查找视频·····");
                    NewVideoPanel panel = (NewVideoPanel) evt.getSource();
                    //RootWindow.getRootWindow().setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            jpb.play(87,30);
                            jpb.close();
                        }
                    }).start();
                    VideoInfo.delUrl(panel.getUrl());
                    
                }
            });
            //System.out.println(newVideoPanel);
            //装载Jpanel
            //VideoList.getSaveVideo().add(newVideoPanel);
            videos.add(newVideoPanel);
        }
    }

    public static void delUrl(String url) {
        AllVideo crawler = new AllVideo("video" + indexPage, true);
        indexPage++;
        crawler.addSeed(url);
        /*可以设置每个线程visit的间隔，这里是毫秒*/
        //crawler.setVisitInterval(1000);
        /*可以设置http请求重试的间隔，这里是毫秒*/
        //crawler.setRetryInterval(1000);
        crawler.setThreads(100);
        try {
            crawler.start(2);
        } catch (Exception ex) {
            Logger.getLogger(NavModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
