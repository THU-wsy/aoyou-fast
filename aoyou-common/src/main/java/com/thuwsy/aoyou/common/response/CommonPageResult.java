package com.thuwsy.aoyou.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: CommonPageResult
 * Package: com.thuwsy.aoyou.common.response
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/26 21:44
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPageResult<T> implements Serializable {
    private List<T> list;
    private long total;
}
