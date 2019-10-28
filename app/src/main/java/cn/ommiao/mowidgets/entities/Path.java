package cn.ommiao.mowidgets.entities;

import cn.ommiao.bean.JavaBean;

public class Path extends JavaBean {

    private String fileName;

    private boolean selected;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
