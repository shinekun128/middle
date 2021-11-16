package cn.ponyzhang.server.service.praise.cache;

import cn.ponyzhang.server.dto.BlogPraiseDto;

public interface IRedisService {
    /**
     * 缓存用户点赞数据
     * @param userId 用户id
     * @param blogId 博客id
     * @param status 点赞状态
     */
    void cachePraiseBlog(BlogPraiseDto dto, Byte status);

    /**
     * 查询博客点赞总数
     * @param blogId 博客id
     * @return
     */
    Integer getCacheTotalBlog(Integer blogId);


}
