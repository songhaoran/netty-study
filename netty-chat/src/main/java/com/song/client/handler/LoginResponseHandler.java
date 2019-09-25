package com.song.client.handler;

import com.alibaba.fastjson.JSON;
import com.song.util.PacketType;
import com.song.vo.LoginRequestPacket;
import com.song.vo.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Song on 2019/09/23.
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setPacketType(PacketType.login_request);
        loginRequestPacket.setUserId(2021064);
        loginRequestPacket.setUsername("宋浩然");
        loginRequestPacket.setPassword("666666");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        // 登录结果
        System.out.println("接收到登录结果:" + JSON.toJSONString(msg));
    }
}
