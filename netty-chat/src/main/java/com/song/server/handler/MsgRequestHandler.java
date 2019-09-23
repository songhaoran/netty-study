package com.song.server.handler;

import com.song.util.PacketType;
import com.song.vo.MsgRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Song on 2019/09/23.
 */
public class MsgRequestHandler extends SimpleChannelInboundHandler<MsgRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRequestPacket msg) throws Exception {
        // 发消息
        MsgRequestPacket msgRequestPacket = new MsgRequestPacket();
        msgRequestPacket.setPacketType(PacketType.msg_request);
        msgRequestPacket.setMsg("你好,我是服务端");

        ctx.channel().write(msgRequestPacket);
    }
}
