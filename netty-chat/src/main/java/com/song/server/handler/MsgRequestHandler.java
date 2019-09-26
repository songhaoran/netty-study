package com.song.server.handler;

import com.song.util.PacketType;
import com.song.util.SessionUtil;
import com.song.vo.MsgRequestPacket;
import com.song.vo.MsgResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Song on 2019/09/23.
 */
public class MsgRequestHandler extends SimpleChannelInboundHandler<MsgRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRequestPacket msg) throws Exception {

        // 找到要发送的channel
        Integer toUserId = msg.getToUserId();
        Channel channel = SessionUtil.getChannel(toUserId);

        if (channel == null) {
            System.out.println("找不到要发送的渠道");
            return;
        }

        // 写消息
        MsgResponsePacket responsePacket = new MsgResponsePacket();
        responsePacket.setPacketType(PacketType.msg_response);
        responsePacket.setMsg(msg.getMsg());
        channel.writeAndFlush(responsePacket);
    }
}
