/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.fodizi.res;


import java.awt.Color;
/**
 *
 * @author wp
 */
public enum Colors {
    //颜色
    Colors1(225,153,204),
    Colors2(153,153,225),
    Colors3(225,225,102),
    Colors4(255,153,255),
    Colors5(255,204,204),
    Colors6(51,255,51),
    Colors7(255,204,0)
    ;
    
    private final int red;
    private final int green;
    private final int blue;
    
    private Colors(int i,int j,int k){
        this.red = i;
        this.green = j;
        this.blue = k;
    }
    
    public Color getColor(){
        return new Color(this.red, this.green, this.blue);
    }
}
