package Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class BikeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String s = (String) msg;
        try {
            JSONObject jsonObject = new JSONObject(s);
            Client.id = jsonObject.getInt("id");
            System.out.println(Client.id);
        } catch (JSONException e) {

        }
        System.out.println(s);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            sendHeart(ctx);
        }
    }

    private void sendHeart(ChannelHandlerContext ctx) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("function","heart");
        ctx.channel().writeAndFlush(jsonObject.toString() + "\r\n");
        System.out.println(jsonObject.toString() + "\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "offline");
        System.out.println("reconnecting");
        TimeUnit.SECONDS.sleep(10);
        Client.doConnect();
    }
}
