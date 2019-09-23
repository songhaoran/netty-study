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
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);

        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketAnalysisUtil.decode(byteBuf);

        // 登录
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            System.out.println("服务端:接收:登录申请->" + JSON.toJSONString(loginRequestPacket));

            // 返回申请结果
            LoginResponsePacket loginResponsePacket = this.verifyLogin(loginRequestPacket);
            byte[] bytes = PacketAnalysisUtil.encode(loginResponsePacket);

            ByteBuf writeByteBuf = ctx.alloc().buffer();
            writeByteBuf.writeInt(PacketType.login_response.getType());
            writeByteBuf.writeInt(bytes.length);
            writeByteBuf.writeBytes(bytes);
            ctx.channel().writeAndFlush(writeByteBuf);
        }

    }

    private LoginResponsePacket verifyLogin(LoginRequestPacket packet) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setIsSuccess(true);
        return responsePacket;
    }
}
