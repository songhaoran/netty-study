package com.song.client;

import com.song.client.handler.LoginResponseHandler;
import com.song.handler.MsgResponseHandler;
import com.song.handler.PacketDecodeHandler;
import com.song.handler.PacketEncodeHandler;
import com.song.server.handler.MsgRequestHandler;
import com.song.util.LoginUtil;
import com.song.vo.LoginRequestPacket;
import com.song.vo.MsgRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Console;
import java.util.Scanner;

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

    public static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (Thread.interrupted() == false) {
                if (LoginUtil.isLogin(channel)) {
                    System.out.println("请输入用户名:");
                    LoginRequestPacket packet = new LoginRequestPacket();
                    packet.setUsername(scanner.nextLine());
                    packet.setPassword("pwd");
                    channel.writeAndFlush(packet);
                } else {
                    MsgRequestPacket packet = new MsgRequestPacket();
                    packet.setMsg(scanner.nextLine());
                    channel.writeAndFlush(packet);
                }
            }
        }).start();
    }
}
