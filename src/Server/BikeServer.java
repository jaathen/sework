package Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class BikeServer {
   int PORT;
   public BikeServer(int port) {
       PORT = port;
   }
   public void run() throws Exception{
       EventLoopGroup bossGroup = new NioEventLoopGroup();
       EventLoopGroup workerGroup = new NioEventLoopGroup();
       try {
           ServerBootstrap b = new ServerBootstrap();
           b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                   .childHandler(new BikeServerInitializer())
                   .option(ChannelOption.SO_BACKLOG, 128)
                   .childOption(ChannelOption.SO_KEEPALIVE, true);

           ChannelFuture f = b.bind(PORT).sync();
           f.channel().closeFuture().sync();
       } finally {
           bossGroup.shutdownGracefully();
           workerGroup.shutdownGracefully();
       }
   }
   public static void main(String[] args) throws Exception{
       new BikeServer(8080).run();
   }
}
