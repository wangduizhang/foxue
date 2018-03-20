/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.myclass;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author wp
 */
public class NewVideoPanel extends JPanel implements Serializable {

    private static final long serialVersionUID = 12L;
    private String url;
    private String picUrl;
    private String title;
    

    public NewVideoPanel(String url, String picUrl, String title) {
        this.picUrl = picUrl;
        this.url = url;
        this.title = title;

        JTextArea jTextArea = new JTextArea();
        JLabel jLabel;jLabel = new JLabel();
        this.setPreferredSize(new Dimension(200, 163));
        this.setBackground(new java.awt.Color(204, 255, 204));
        this.setLayout(new java.awt.BorderLayout());
        jTextArea.setBackground(new java.awt.Color(204, 255, 204));
        jTextArea.setColumns(7);
        jTextArea.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jTextArea.setLineWrap(true);
        jTextArea.setRows(3);
        jTextArea.setText(title);
        jTextArea.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextArea.setEnabled(false);
        this.add(jTextArea, java.awt.BorderLayout.CENTER);
        jLabel.setPreferredSize(new Dimension(200, 112));
        jLabel.setIcon(new javax.swing.ImageIcon(defPic())); // NOI18N
        //System.out.println(defPic());
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.add(jLabel, java.awt.BorderLayout.PAGE_START);
    }

    public String getTitle() {
        return this.title;
    }

    public String getPicUrl() {

        return this.picUrl;
    }

    public String getUrl() {
        return this.url;
    }

    private URL defPic() {
        URL picpath = null;

        try {
            picpath = new URL(picUrl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(NewVideoPanel.class.getName()).log(Level.SEVERE, null, ex);
            String path ="file://"+System.getProperty("user.dir")+File.separator+"img"+File.separator+"2.jpg";
            try {
                picpath = new URL(path);
            } catch (MalformedURLException ex1) {
                Logger.getLogger(NewVideoPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return picpath;
    }

    public void sizesss() {
        //System.out.println(this.jLabel.getSize());
    }
}
