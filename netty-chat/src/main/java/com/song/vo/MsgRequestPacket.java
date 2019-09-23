package com.song.vo;

import lombok.Data;

/**
 * Created by Song on 2019/09/23.
 */
@Data
public class MsgRequestPacket extends Packet {
    private String msg;
}
