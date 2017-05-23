package zph.zhjx.com.bddemo.bean;

import java.io.Serializable;

/**
 * Created by adminZPH on 2017/5/23.
 */

public class BaseEntity<E> implements Serializable{
    private int state;
    private String msg;
    private E data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
