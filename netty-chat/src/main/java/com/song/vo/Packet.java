package com.song.vo;

import com.song.util.PacketType;
import lombok.Data;

/**
 * Created by Song on 2019/09/21.
 */
@Data
public abstract class Packet {

    private PacketType packetType;

    public PacketType getPacketType() {
        return packetType;
    }
}
