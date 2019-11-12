package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class TextClockText extends JavaBean {

    private String content;
    private int color;

    public TextClockText(){

    }

    public TextClockText(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
