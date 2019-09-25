package com.song.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by Song on 2019/09/24.
 */
public class LoginUtil {

    public static final AttributeKey loginAttrKey = AttributeKey.newInstance("attr_login_status");

    public static boolean isLogin(Channel channel) {
        Attribute attr = channel.attr(loginAttrKey);
        return attr.get() != null;
    }
}
