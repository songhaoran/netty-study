package com.song.util;

import io.netty.util.AttributeKey;

/**
 * Created by Song on 2019/09/24.
 */
public interface AttrKey {
    AttributeKey sessionKey = AttributeKey.newInstance("session");
}
