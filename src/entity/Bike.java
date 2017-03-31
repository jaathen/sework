package entity;


public class Bike {
    private volatile static int newid = 0;
    //位置
    public static int getNewid() {

        return ++newid;
    }
}
