package cn.ponyzhang.server.util;

import cn.ponyzhang.server.dto.BlogPraiseDto;

public class BlogPraiseUtil {

    public static String getUserPraiseKey(BlogPraiseDto dto){
        String key = dto.getUserId() + ":" + dto.getBlogId();
        return key;
    }
}
