package com.song.server;

import com.song.handler.PacketDecodeHandler;
import com.song.handler.PacketEncodeHandler;
import com.song.handler.Spliter;
import com.song.server.handler.AuthHandler;
import com.song.server.handler.LoginRequestHandler;
import com.song.server.handler.MsgRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Song on 2019/09/23.
 */
public class NettyChatServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecodeHandler());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MsgRequestHandler());
                        ch.pipeline().addLast(new PacketEncodeHandler());
                    }
                })
                .bind(8000)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("服务端启动成功!");
                    } else {
                        System.out.println("服务端启动失败!");
                    }
                });
    }
}
