package NettySocket;

import handler.ClientHandler;
import handler.ClientLoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * Created by Song on 2019/09/20.
 */
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 设置线程模型
                .group(workerGroup)
                // 指定IO类型
                .attr(AttributeKey.newInstance("clientName"), "song client")
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                            @Override
//                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
////                                super.channelActive(ctx);
//
//                                System.out.println(new Date()+":客户端写出数据 ************");
//
//                                ByteBuf buffer = ctx.alloc().buffer();
//                                buffer.writeBytes("你好,服务端,我是客户端!".getBytes(Charset.forName("utf-8")));
//
//                                ctx.channel().writeAndFlush(buffer);
//                            }
//                        });
//
//                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
////                        super.channelRead(ctx, msg);
//
//                                ByteBuf byteBuf = (ByteBuf) msg;
//
//                                System.out.println(new Date() + ": 客户端收到服务端信息->" + byteBuf.toString(Charset.forName("utf-8")));
//                            }
//                        });

                        ch.pipeline().addLast(new ClientLoginHandler());
                        ch.pipeline().addLast(new  ClientHandler());
                    }
                });

        conect(bootstrap, "127.0.0.1", 8000, 0);

    }

    static int maxRetryTimes = 5;

    public static void conect(Bootstrap bootstrap, String ip, Integer port, Integer retry) {
        bootstrap.connect(ip, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("客户端链接成功!");
            } else {
                if (retry <= maxRetryTimes) {
                    System.out.println("客户端链接失败!");
                    if (retry != 0) {
                        System.out.println("第" + retry + "次重试!");
                    }
                    Thread.sleep(2000);
                    conect(bootstrap, ip, port, retry + 1);
                }
            }
        });
    }
}
