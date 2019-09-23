package com.song.handler;

import com.song.util.PacketAnalysisUtil;
import com.song.vo.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Song on 2019/09/23.
 */
public class PacketDecodeHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = PacketAnalysisUtil.decode(in);
        out.add(packet);
    }
}
