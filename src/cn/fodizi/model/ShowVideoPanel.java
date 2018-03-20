/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.model;

import cn.fodizi.Crawler.AllVideo;
import cn.fodizi.main.RootWindow;
import cn.fodizi.myclass.NewVideoPanel;
import cn.fodizi.myclass.VideoList;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * 工具类，用于向窗口输出videopael
 *
 * @author wp
 */
public class ShowVideoPanel {

    private static int indexEnd = 0;
    private static int indexStart = 0;
    private static int indexlib = 1;
    //获取面板
    private static final JPanel PANEL = RootWindow.getJPanel();
    //获取库
    private static ArrayList<NewVideoPanel> videos;
    //获取暂存库
    private static final ArrayList<NewVideoPanel> VIDEOSAVE = VideoList.getSaveVideo();

    //下一页能否删库的判断条件
    private static boolean flag = false;

    public static void showSavePanel() {
        System.out.println(VIDEOSAVE.size());
        for (int i = 0; i < VIDEOSAVE.size(); i++) {
            PANEL.add(VIDEOSAVE.get(i));
        }
    }

    public static void showNextPanel() {
        //System.out.println(VIDEOSAVE.size());
        //System.out.println("==============");
        //System.out.println("==================NNNNNNNNNNNNNN=======================");
        VIDEOSAVE.clear();
        indexStart = indexEnd;
        videos = VideoList.getVideoList(indexlib % 3);
        //System.out.println(videos.get(indexEnd));
        //System.out.println(videos.size());
        //下一步需要跨库
        if (indexStart == videos.size()) {
            indexlib++;
            if (!flag && indexlib % 3 == 0) {
                //此语句只执行一次
                flag = true;
            }
            //获取下一个库
            videos = VideoList.getVideoList(indexlib % 3);
            if (flag) {
                //三个库存满过一次才可以执行清除;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("清理库");
                        VideoList.remowLib();
                    }
                }).start();
            }
        }
        NewVideoPanel nvp;
        for (int i = 0; i < 9 && indexEnd < videos.size(); i++) {
            //System.out.println(indexEnd);
            nvp = videos.get(indexEnd);
            //System.out.println(indexEnd);
            PANEL.add(nvp);
            //System.out.println(RootWindow.getJPanel());
            //WINDOW.add(new NewVideoPanel(null,null,"adsfdasfas"));
            VIDEOSAVE.add(nvp);
            indexEnd++;
        }
        //跨库，或者全部视频已经捕捉完毕
        int LLL = indexEnd - indexStart;
        if (LLL < 9) {
            if (indexlib == AllVideo.getPageNum()) {
                //全部视频已经捕捉完毕
                RootWindow.setnextDisable();
                // 视频捕捉完毕后所有的库都有元素
            } else {
                //跨库
                indexlib++;
                if (!flag && indexlib % 3 == 0) {
                    //此语句只执行一次
                    flag = true;
                }
                //获取下一个库
                videos = VideoList.getVideoList(indexlib % 3);
                if (flag) {
                    //三个库存满过一次才可以执行清除;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("清理库");
                            VideoList.remowLib();
                        }
                    }).start();
                }
                indexEnd = 0;
                for (int j = 0; j < 9 - LLL && j < videos.size(); j++) {
                    //System.out.println(indexEnd);
                    nvp = videos.get(indexEnd);
                    PANEL.add(nvp);
                    VIDEOSAVE.add(nvp);
                    indexEnd++;
                }
            }
        } else {//到最后一页，下一步需要跨库，   ==0时
            if (indexlib == AllVideo.getPageNum()) {
                //全部视频已经捕捉完毕
                RootWindow.setnextDisable();
                // 视频捕捉完毕后所有的库都有元素
            }
        }
        //System.out.println("当前库:    " + indexlib + "   运行完start指针   " + indexStart + "运行完end指针  " + indexEnd);
        //System.out.println("=======================================================");
    }

    public static void showLastPanel() {
        //System.out.println("==================LLLLLLLLLLLLLL========================");
        VIDEOSAVE.clear();
        NewVideoPanel nvp;
        if (indexStart - 9 < 0) {
            //System.out.print("start跨库");
            indexEnd = indexStart;
            int size = VideoList.getVideoList((indexlib - 1) % 3).size();
            //输出前一个库
            videos = VideoList.getVideoList((indexlib - 1) % 3);
            for (int i = size - (9 - indexStart); i < size; i++) {
                //System.out.println(i);
                nvp = videos.get(i);
                PANEL.add(nvp);
                VIDEOSAVE.add(nvp);
            }
            indexStart = size - (9 - indexStart);
            //输出后一个库
            for (int i = 0; i < indexEnd; i++) {
                nvp = VideoList.getVideoList(indexlib % 3).get(i);
                PANEL.add(nvp);
                VIDEOSAVE.add(nvp);
            }
        } else if (indexEnd < indexStart && indexEnd <= 8) {
            //System.out.print("end跨库");
            indexlib--;
            indexEnd = indexStart;
            videos = VideoList.getVideoList(indexlib % 3);
            for (int i = 9; i > 0; i--) {
                nvp = videos.get(indexEnd - i);
                PANEL.add(nvp);
                VIDEOSAVE.add(nvp);
                indexStart--;
            }
            //跨库
            if (indexlib >= 2 && flag) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("清理库");
                        VideoList.remowLiblast();
                    }
                }).start();
            }
            if (indexlib == 2) {
                flag = false;
            }
        } else if (indexStart < indexEnd) {
            //System.out.print("不跨库");
            //不跨库
            indexEnd = indexStart;
            for (int i = 9; i > 0; i--) {
                nvp = videos.get(indexEnd - i);
                PANEL.add(nvp);
                VIDEOSAVE.add(nvp);
                indexStart--;
            }
        }
        //System.out.println("当前库:    " + indexlib + "   运行完start指针   " + indexStart + "运行完end指针  " + indexEnd);
        //System.out.println("=======================================================");
    }

}
