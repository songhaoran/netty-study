package com.song.server.handler;

import com.song.util.LoginUtil;
import com.song.util.PacketType;
import com.song.vo.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Song on 2019/09/24.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (LoginUtil.isLogin(ctx.channel())) {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        } else {
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setPacketType(PacketType.login_response);
            loginResponsePacket.setIsSuccess(false);
            loginResponsePacket.setReason("重新的登录");
            ctx.channel().writeAndFlush(loginResponsePacket);

            ctx.channel().close();
        }
    }
}
