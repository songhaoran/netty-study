package com.song.vo;

import com.song.util.PacketType;
import lombok.Data;

/**
 * Created by Song on 2019/09/23.
 */
@Data
public class MsgRequestPacket extends Packet {

    private Integer toUserId;

    private String msg;

    @Override
    public void setPacketType(PacketType packetType) {
        super.setPacketType(PacketType.msg_request);
    }
}
