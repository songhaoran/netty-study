package com.song.handler;

import com.song.util.PacketAnalysisUtil;
import com.song.vo.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Song on 2019/09/23.
 */
public class PacketEncodeHandler extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        byte[] bytes = PacketAnalysisUtil.encode(msg);
        out.writeInt(PacketAnalysisUtil.MAGIC_NUMBER);
        out.writeInt(msg.getPacketType().getType());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
