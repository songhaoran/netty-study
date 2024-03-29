package com.song.util;

import com.alibaba.fastjson.JSON;
import com.song.vo.*;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Song on 2019/09/22.
 */
public class PacketAnalysisUtil {
    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Integer, Class<? extends Packet>> packetTypeMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(PacketType.login_request.getType(), LoginRequestPacket.class);
        packetTypeMap.put(PacketType.login_response.getType(), LoginResponsePacket.class);
        packetTypeMap.put(PacketType.msg_request.getType(), MsgRequestPacket.class);
        packetTypeMap.put(PacketType.msg_response.getType(), MsgResponsePacket.class);
    }

    public static byte[] encode(Packet packet) {
        // 2. 序列化 java 对象
        return JSON.toJSONBytes(packet);
    }


    public static Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);

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
