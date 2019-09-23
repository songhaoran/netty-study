package com.song.client;

import com.song.client.handler.LoginResponseHandler;
import com.song.client.handler.MsgResponseHandler;
import com.song.handler.PacketDecodeHandler;
import com.song.handler.PacketEncodeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Song on 2019/09/23.
 */
public class NettyChatClient {

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new PacketDecodeHandler());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MsgResponseHandler());
                        ch.pipeline().addLast(new PacketEncodeHandler());
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
