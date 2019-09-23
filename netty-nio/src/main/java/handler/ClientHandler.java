package handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import util.PacketAnalysisUtil;
import vo.LoginResponsePacket;
import vo.Packet;

/**
 * Created by Song on 2019/09/22.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketAnalysisUtil.decode(byteBuf);

        // 登录
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            System.out.println("客户端:接收:登录结果->" + JSON.toJSONString(loginResponsePacket));
        }
    }
}
