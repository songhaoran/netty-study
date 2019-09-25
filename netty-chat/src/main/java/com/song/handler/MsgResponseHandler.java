package com.song.handler;

import com.song.vo.MsgResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Song on 2019/09/23.
 */
public class MsgResponseHandler extends SimpleChannelInboundHandler<MsgResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgResponsePacket msg) throws Exception {
        System.out.println(msg.getMsg());
    }
}
