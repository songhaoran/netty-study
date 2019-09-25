package com.song.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Song on 2019/09/24.
 */
@Data
@NoArgsConstructor
public class Session {
    private Integer id;

    public Session(Integer id) {
        this.id = id;
    }
}
