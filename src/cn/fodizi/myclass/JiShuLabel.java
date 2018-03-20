/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.myclass;

import javax.swing.JLabel;

/**
 *
 * @author wp
 */
public class JiShuLabel extends JLabel{
    private String url;
    private String title;
    
    private JiShuLabel(){
        super();
    }
    public JiShuLabel(String tString,String u) {
        this.title = tString;
        this.url = u;
        this.setFont(new java.awt.Font("宋体", 1, 15)); // NOI18N
        this.setText(this.title);
        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    public void setUrl(String s){
        this.url = s;
    }
    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
