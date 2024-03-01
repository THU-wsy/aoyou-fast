package com.thuwsy.aoyou.common.domain.dto;

import com.thuwsy.aoyou.common.request.PageParam;
import lombok.Data;

/**
 * ClassName: UmsMenuParamDto
 * Package: com.thuwsy.aoyou.common.domain.dto
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/26 21:47
 * @Version 1.0
 */
@Data
public class UmsMenuParamDto extends PageParam {
    private String menuName;
    private String perms;
}
