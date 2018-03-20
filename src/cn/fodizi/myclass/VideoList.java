/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.myclass;

import cn.fodizi.Crawler.AllVideo;
import java.util.ArrayList;

/**
 * 用于videopanel的存储
 *
 * @author wp
 */
public class VideoList {

    //默认存放3组面板
    private static ArrayList<NewVideoPanel> videos = new ArrayList<NewVideoPanel>();
    private static ArrayList<NewVideoPanel> videos2 = new ArrayList<NewVideoPanel>();
    private static ArrayList<NewVideoPanel> videos3 = new ArrayList<NewVideoPanel>();
    //暂存当前九(??)个面板
    private static ArrayList<NewVideoPanel> videos4 = new ArrayList<NewVideoPanel>();
    //removehow()
    private static int indexlib = 3;
    private final static String BASEPATH = "http://www.fodizi.com/fofa/";

    private VideoList() {
    }

    public static ArrayList<NewVideoPanel> getVideoList() {
        ArrayList<NewVideoPanel> l = videos.isEmpty() ? videos : videos2.isEmpty() ? videos2 : videos3;
        return l;
    }

    public static ArrayList<NewVideoPanel> getVideoList(int index) {
        switch (index) {
            case 1:
                return videos;
            case 2:
                return videos2;
            case 0:
                return videos3;
            default:
                return null;
        }
    }

    public static ArrayList<NewVideoPanel> getSaveVideo() {
        return videos4;
    }

    public static synchronized void remowLib() {
        indexlib++;
        getVideoList(indexlib % 3).clear();
        AllVideo.delUrl(BASEPATH + indexlib + ".htm");

    }

    public static synchronized void remowLiblast() {
        getVideoList((indexlib-3)%3).clear();
        AllVideo.delUrl(BASEPATH + (indexlib-3) + ".htm");
        indexlib--;
    }
}
