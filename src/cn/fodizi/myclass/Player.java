/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.myclass;

import cn.fodizi.main.RootWindow;
import cn.fodizi.res.JPBar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wp
 */
public class Player {
    public static void play(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JPBar jpb = new JPBar(RootWindow.getRootWindow(),"正在打开视频····");
                jpb.close();
            }
        }).start();
        
        String string = System.getProperty("user.dir");
        String cmd = "cmd /K cd winmpv&& .\\mpv.exe " + url;
        try {  
            Process p = Runtime.getRuntime().exec(cmd);
            //
            final InputStream is1 = p.getInputStream();
            final InputStream is2= p.getErrorStream();
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    String line1 = null;
                    try {
                        while((line1 = br1.readLine())!= null){
                            if(line1!=null){}
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                        try {
                            is1.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
            ).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                    try {
                        String line1 = null;
                        while((line1 = br2.readLine())!= null){
                            if(line1!=null){}
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                        try {
                            is2.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
            ).start();
        } catch (IOException e) {
            //mac系统
        }  
    }  
}
