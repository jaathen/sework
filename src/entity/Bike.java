package entity;

import java.util.Date;

/**
 * Created by ASUS on 2017/3/30.
 */
public class Bike {
    volatile static int newid = 0;
    final int id = newid++;
    Date lastConn;
     int state;
    //位置
    public static int getNewid() {

        return ++newid;
    }
}
