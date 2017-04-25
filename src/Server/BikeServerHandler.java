package Server;

import Dao.RegisterDao;
import entity.Bike;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BikeServerHandler extends ChannelInboundHandlerAdapter {
    private int counter = 0;
    private static Map<Integer, Channel> mapId = new HashMap<>();
    private static Map<Channel, Integer> mapChannel = new HashMap<>();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        counter = 0;
        String s = (String) msg;
        JSONObject jsonObject = new JSONObject(s);
        if (jsonObject.getString("function").equals("heart")) {
            System.out.println("heart");
            heartHandler(ctx, jsonObject);
        } else if (jsonObject.getString("function").equals("Init")){
            System.out.println("Init");
            int bid = Bike.getNewid();
            mapId.put(bid, ctx.channel());
            mapChannel.put(ctx.channel(), bid);
            JSONObject tmp = new JSONObject();
            tmp.put("id", bid);
            tmp.put("function", "postbid");
            ctx.channel().writeAndFlush(tmp.toString() + "\r\n");
        } else if (jsonObject.getString("function").equals("sent")) {
            System.out.println("sent");
            if (mapId.get(jsonObject.getInt("bid")).isActive()) {
                mapId.get(jsonObject.getInt("bid")).writeAndFlush(jsonObject.toString() + "\r\n");
            }

        }
        String function = jsonObject.getString("function");
        JSONObject jsonOut = new JSONObject();
        switch (function) {
            case "register": jsonOut = RegisterDao.registerDao(jsonObject);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (counter >= 3) {
                ctx.channel().close().sync();
                int bid = mapChannel.get(ctx.channel());
                mapChannel.remove(ctx.channel());
                mapId.remove(bid);
                System.out.println(ctx.channel().remoteAddress() + "down line" + "and removed");
            } else {
                counter++;
                System.out.println(ctx.channel().remoteAddress() + "lost " + counter + " heart");
            }
        }
    }

    public void heartHandler(ChannelHandlerContext ctx, JSONObject jsonObject) {
        System.out.println(ctx.channel().remoteAddress() + "heartHandler");
    }
}
