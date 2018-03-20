/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.Crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.fodizi.main.KindVideoFrame;
import cn.fodizi.main.RootWindow;
import cn.fodizi.myclass.NewVideoPanel;
import cn.fodizi.res.JPBar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 用于捕获分类视频页中的视频
 *
 * @author wp
 */
public class KindVideo extends BreadthCrawler {

    private static Elements elements;
    private static int num = 0;
    private static String basePath = "http://www.fodizi.com/fofa/";
    private static String title = "标题";

    private KindVideo(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        System.out.println("========================" + num);
    }

    public void visit(Page page, CrawlDatums next) {
        Elements elements1 = page.select(".k3");
        Elements elements2 = page.select(".k4");
        Elements elements3 = page.select(".k6");
        elements = !elements1.isEmpty() ? elements1 : !elements2.isEmpty() ? elements2 : elements3;
        delKKK();
    }

    public static void delUrl(String url, String s) {
        title = s;
        KindVideo crawler = new KindVideo("kind" + num, true);
        num++;
        crawler.addSeed(url);
        crawler.setThreads(21);
        try {
            crawler.start(1);
        } catch (Exception ex) {
            Logger.getLogger(NavModule.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void delKKK() {

        final KindVideoFrame kindvf = new KindVideoFrame(title);
        kindvf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RootWindow.getRootWindow().setEnabled(true);
                kindvf.dispose();
            }

        });
        kindvf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        for (Element element : elements) {
            if (element.select("a").isEmpty()) {
                continue;
            }
            String tString = element.child(0).attr("title");
            if (tString.isEmpty()) {
                tString = element.select("a").text();
            }
            //System.out.println(tString);
            StringBuilder picUrl = new StringBuilder(element.select("img").first().attr("src"));
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
            NewVideoPanel newVideoPanel = new NewVideoPanel(url, picUrl.toString(), tString);
            newVideoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    final JPBar jpb = new JPBar(RootWindow.getRootWindow(),"正在查找····");
                    NewVideoPanel panel = (NewVideoPanel) evt.getSource();
                    //panel.getParent().getParent().getParent().getParent().getParent().setEnabled(false);
                    //panel.getParent().getParent().getParent().getParent().setEnabled(false);
                    //RootWindow.getRootWindow().setEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            jpb.play(87, 30);
                        }
                    }).start();
                    VideoInfo.delUrl(panel.getUrl());
                    jpb.close();
                }
            });
            kindvf.add(newVideoPanel);
        }
        kindvf.myShow();
    }
}
