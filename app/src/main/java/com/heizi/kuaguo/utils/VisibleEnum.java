package com.heizi.kuaguo.utils;

/**
 * Created by leo on 2018/5/25.
 */

public enum VisibleEnum {
    JUMP(1, "跳过，注册用"),
    NEXT(0, "下一步,注册用"),
    SAVE(2, "保存，资源方筛选用"),
    EDIT(3, "编辑，编辑用");

    int code;
    String des;

    VisibleEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
