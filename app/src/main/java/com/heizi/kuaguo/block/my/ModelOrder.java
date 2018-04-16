package com.heizi.kuaguo.block.my;

import com.heizi.mycommon.model.BaseModel;

/**
 * Created by leo on 17/12/6.
 */

public class ModelOrder extends BaseModel {
    private String order_no;//订单号
    private String amount;//金额
    private String user_name;
    private String integral_proportion;//使用积分
    private String pay_time;//支付时间
    private String obtain_integral;//获赠积分
    private String integral_ratio;//积分比例
    private String seller_name;//店铺名
    private int state;//订单状态 2-冻结中 3-退款中 5-已完成  9-已退款
    private String id;//订单编号
    private int type;//1-交易订单  9-评论    10-签到   11-首次注册

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIntegral_proportion() {
        return integral_proportion;
    }

    public void setIntegral_proportion(String integral_proportion) {
        this.integral_proportion = integral_proportion;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getObtain_integral() {
        return obtain_integral;
    }

    public void setObtain_integral(String obtain_integral) {
        this.obtain_integral = obtain_integral;
    }

    public String getIntegral_ratio() {
        return integral_ratio;
    }

    public void setIntegral_ratio(String integral_ratio) {
        this.integral_ratio = integral_ratio;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
