package util;

/**
 * Created by Song on 2019/09/22.
 */
public enum PacketType {
    login_request(1),
    login_response(2);

    private Integer type;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    PacketType(Integer type) {
        this.type = type;
    }

    public static PacketType getByType(Integer type) {
        if (type==null) {
            return null;
        }

        for (PacketType e : PacketType.values()) {
            if (type.equals(e.getType())) {
                return e;
            }
        }
        return null;
    }
}
