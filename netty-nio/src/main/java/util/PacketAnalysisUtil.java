package util;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import vo.LoginRequestPacket;
import vo.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2019/09/22.
 */
public class PacketAnalysisUtil {
    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Integer, Class<? extends Packet>> packetTypeMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(PacketType.login_request.getType(), LoginRequestPacket.class);
    }

    public static byte[] encode(Packet packet) {
        // 2. 序列化 java 对象
        byte[] bytes = JSON.toJSONBytes(packet);
        return bytes;
    }


    public static Packet decode(ByteBuf byteBuf) {
        // 指令
        int packetType = byteBuf.readInt();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> packetClazz = getRequestType(packetType);

        if (packetClazz != null) {
            return JSON.parseObject(bytes, packetClazz);
        }

        return null;
    }

    private static Class<? extends Packet> getRequestType(Integer command) {

        return packetTypeMap.get(command);
    }
}
