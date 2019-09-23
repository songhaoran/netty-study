package com.song.server.handler;

import com.alibaba.fastjson.JSON;
import com.song.util.PacketType;
import com.song.vo.LoginRequestPacket;
import com.song.vo.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Song on 2019/09/23.
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        // 登录申请
        System.out.println("*****************");
        System.out.println("登录请求:" + JSON.toJSONString(msg));


        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setPacketType(PacketType.login_response);
        loginResponsePacket.setIsSuccess(true);
        loginResponsePacket.setReson("登录成功");
        ctx.channel().writeAndFlush(loginResponsePacket);
    }
}
