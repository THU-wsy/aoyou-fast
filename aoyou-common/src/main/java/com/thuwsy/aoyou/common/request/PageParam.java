package com.thuwsy.aoyou.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: PageParam
 * Package: com.thuwsy.aoyou.common.request
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/26 21:35
 * @Version 1.0
 */
@Data
public class PageParam implements Serializable {
    private Long pageNum = 1L;   // 第几页
    private Long pageSize = 10L; // 每页几条数据
}
