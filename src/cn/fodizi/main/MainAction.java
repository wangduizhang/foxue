/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.main;

import cn.fodizi.Crawler.AllVideo;
import cn.fodizi.Crawler.NavModule;
import cn.fodizi.model.ShowVideoPanel;
import cn.fodizi.res.JPBar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wp
 */
public class MainAction {

    private static JPBar jpb = new JPBar(RootWindow.getRootWindow(),"正在联网搜索视频····");

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //窗口初始化
                RootWindow.myShow();
            }
        });
        
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //窗口初始化
                jpb.play(90,30);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                AllVideo.delUrl("http://www.fodizi.com/fofa/1.htm");
                ShowVideoPanel.showNextPanel();
                RootWindow.setVideoNum();
                RootWindow.getRootWindow().setEnabled(true);
                jpb.close();
                AllVideo.delUrl("http://www.fodizi.com/fofa/2.htm");
                AllVideo.delUrl("http://www.fodizi.com/fofa/3.htm");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Nav爬虫初始化
                NavModule.delUrl();
            }
        }).start();
    }
}
