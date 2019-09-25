package com.song.util;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Song on 2019/09/24.
 */
public class SessionUtil {

    static ConcurrentHashMap<Integer, Channel> sessionIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        sessionIdChannelMap.put(session.getId(), channel);
    }

    public static Channel getChannel(Integer sessionId) {
        if (sessionId == null) {
            return null;
        }

        return sessionIdChannelMap.get(sessionId);
    }
}
