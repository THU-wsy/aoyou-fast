package com.thuwsy.aoyou.common.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: RouterVO
 * Package: com.thuwsy.aoyou.common.domain.vo
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/22 13:47
 * @Version 1.0
 */
@Data
public class RouterVO implements Serializable {
    private Long id;
    private Long parentId;
    private String menuName;
    private String path;
    private String componentPath;
    private String icon;
    private Integer menuType;
    private List<RouterVO> children = new ArrayList<>();
}
