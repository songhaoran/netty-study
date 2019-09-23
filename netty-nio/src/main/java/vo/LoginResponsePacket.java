package vo;

import lombok.Data;

/**
 * Created by Song on 2019/09/22.
 */
@Data
public class LoginResponsePacket extends Packet {

    private Boolean isSuccess;

    private String reson;
}
