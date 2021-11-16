package cn.ponyzhang.server.service.praise.db;

import cn.ponyzhang.server.dto.BlogPraiseDto;

public interface IPraiseService {
    void addPraise(BlogPraiseDto dto);

    void addPraiseByLock(BlogPraiseDto dto);

    void cancelPraise(BlogPraiseDto dto);

    Integer getBlogPraiseTotal(Integer blogId);
}
