/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.myclass;

import cn.fodizi.Crawler.KindVideo;
import cn.fodizi.main.RootWindow;
import cn.fodizi.res.Colors;
import cn.fodizi.res.JPBar;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author wp
 */
public class NewLabel extends JLabel implements Serializable {

    private static final long serialVersionUID = 11L;
    private final String titleString;
    private final String urlString;
    private static final RootWindow WINDOW = RootWindow.getRootWindow();

    public NewLabel(String title, String url) {
        //super();
        this.titleString = title;
        this.urlString = url;
        //使用this设置格式
        this.setRandomBackgroud();
        this.setFont(new java.awt.Font("Lucida Grande", 0, 25)); // NOI18N
        this.setText(this.titleString);
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.setOpaque(true);
        this.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                final JPBar jpb = new JPBar(RootWindow.getRootWindow(), "正在查找····");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        jpb.play(87, 30);
                    }
                }).start();
                NewLabel newLabel = (NewLabel) evt.getSource();
                KindVideo.delUrl("http://www.fodizi.com/fofa/" + newLabel.getUrl(), newLabel.getTitle());
                jpb.close();
            }
        });
    }

    public String getTitle() {
        return this.titleString;
    }

    public String getUrl() {
        return this.urlString;
    }

    private void setRandomBackgroud() {
        //随机获取颜色7种
        int numOfColor = 7;
        int num = (int) (Math.random() * numOfColor) + 1;

        switch (num) {
            case 1:
                this.setBackground(Colors.Colors1.getColor());
                break;
            case 2:
                this.setBackground(Colors.Colors2.getColor());
                break;
            case 3:
                this.setBackground(Colors.Colors3.getColor());
                break;
            case 4:
                this.setBackground(Colors.Colors4.getColor());
                break;
            case 5:
                this.setBackground(Colors.Colors5.getColor());
                break;
            case 6:
                this.setBackground(Colors.Colors6.getColor());
                break;
            case 7:
                this.setBackground(Colors.Colors7.getColor());
                break;
            default:
                this.setBackground(Colors.Colors1.getColor());
                break;
        }
    }
}
