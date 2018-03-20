/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.model;

import javax.swing.JTextArea;

/**
 *
 * @author wp
 */
public class AboutText{
    
    private static JTextArea jTextArea1 = new JTextArea();

    public static void initAboutText() {
        
        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(255, 255, 153));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("           \n\n\n\n\n"
                + "                      感谢佛弟子网的视频资源\n"
                + "                     本软件供广大佛友学习交流\n"
                + "                           本软件免费使用\n"
                + "                      此软件献给我伟大的父母\n"
                + "                              阿弥陀佛\n\n\n\n\n\n\n");
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
    } 
    public static JTextArea getjTextArea1() {
        return jTextArea1;
    }
}
