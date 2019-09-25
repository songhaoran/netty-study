package com.song.server.handler;

import com.alibaba.fastjson.JSON;
import com.song.util.AttrKey;
import com.song.util.PacketType;
import com.song.util.Session;
import com.song.util.SessionUtil;
import com.song.vo.LoginRequestPacket;
import com.song.vo.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * Created by Song on 2019/09/23.
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        // 登录申请
        System.out.println("接收到登录请求:" + JSON.toJSONString(msg));

        boolean loginResult = checkPwd(msg.getUsername(), msg.getPassword());
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setPacketType(PacketType.login_response);
        if (loginResult) {
            loginResponsePacket.setIsSuccess(true);

            // 存session
            Session session = new Session(msg.getUserId());
            SessionUtil.bindSession(session, ctx.channel());
            // 标记渠道
            ctx.channel().attr(AttrKey.sessionKey).set(session);
        } else {
            loginResponsePacket.setIsSuccess(false);
            loginResponsePacket.setReason("登录失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);

    }

    private boolean checkPwd(String userName, String pwd) {
        return true;
    }
}
