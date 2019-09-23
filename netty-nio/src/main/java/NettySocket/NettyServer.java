package NettySocket;

import handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * Created by Song on 2019/09/20.
 */
public class NettyServer {

    public static void main(String[] args) {
        // 老板线程组,accept新的链接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 员工线程组,处理每个链接的读写操作
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                // 指定IO类型
                .channel(NioServerSocketChannel.class)
                // 给服务端的channel指定一些参数, 这些参数可以再channel中通过attr()方法获取
                .attr(AttributeKey.newInstance("serverName"), "song netty")
                .attr(AttributeKey.newInstance("serverPort"), "8000")
                // 给每一条链接指定属性
                .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 给每条链接设置TCP属性
                .childOption(ChannelOption.SO_KEEPALIVE, true)//是否开启心跳机制
                .childOption(ChannelOption.TCP_NODELAY, false)//true:高实时性，有数据发送时就马上发送; false:减少发送次数减少网络交互
                // 服务端启动过程中的一些逻辑的处理, 可以省略
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("netty server starting!......");
                    }
                })
                // 定义每个链接的读写操作,业务逻辑部分
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//
//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf byteBuf = (ByteBuf) msg;
//
//                                System.out.println(new Date() + ": 服务端读到数据-> " + byteBuf.toString(Charset.forName("utf-8")));
//
//                                System.out.println(new Date()+": 服务端写出数据**********");
//                                ByteBuf buffer = ctx.alloc().buffer();
//                                buffer.writeBytes("你好,我是服务端!".getBytes(Charset.forName("utf-8")));
//
//                                ctx.channel().writeAndFlush(buffer);
//                            }
//                        });

                        ch.pipeline().addLast(new ServerHandler());
                    }
                })
                .bind(8000).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务端启动成功!");
            } else {
                System.out.println("服务端启动失败!");
            }
        });


    }

}
