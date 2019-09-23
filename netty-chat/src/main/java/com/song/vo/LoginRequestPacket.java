package com.song.vo;

import lombok.Data;

/**
 * Created by Song on 2019/09/21.
 */
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String username;

    private String password;
}
