package Client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.json.JSONObject;

/**
 * Created by ASUS on 2017/3/28.
 */
public class Client implements Runnable{
    private static Channel ch;
    private static Bootstrap bootstrap;
    static int id;
    public  void run() {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new BikeClientInitializer());

            // 连接服务器
            doConnect();
            while (true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    /**
     * 抽取出该方法 (断线重连时使用)
     *
     * @throws InterruptedException
     */
    public static void doConnect() throws InterruptedException {
        ch = bootstrap.connect("127.0.0.1", 8080).sync().channel();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("function", "Init");
        String s = jsonObject.toString() + "\r\n";
        ch.writeAndFlush(s);
        System.out.println(s);
    }
    public static void main(String[] args) throws Exception{
        new Client().run();
    }
}
