package handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import util.PacketAnalysisUtil;
import util.PacketType;
import vo.LoginRequestPacket;
import vo.LoginResponsePacket;
import vo.Packet;

/**
 * Created by Song on 2019/09/22.
 */
public class ClientLoginHandler extends ChannelInboundHandlerAdapter {
    private static final int MAGIC_NUMBER = 0x12345678;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(2021064);
        packet.setUsername("宋浩然");
        packet.setPassword("666666");
        byte[] bytes = PacketAnalysisUtil.encode(packet);

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeInt(PacketType.login_request.getType());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        ctx.channel().writeAndFlush(byteBuf);
    }


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
