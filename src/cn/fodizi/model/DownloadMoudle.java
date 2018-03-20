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
public class DownloadMoudle {
        private static JTextArea jTextArea1 = new JTextArea();

    public static void initText() {
        
        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(204, 204, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("           \n\n\n\n\n\n\n"
                + "                              ！！！待开发！！！\n");
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
    }
    public static JTextArea getjTextArea1() {
        return jTextArea1;
    }
}
