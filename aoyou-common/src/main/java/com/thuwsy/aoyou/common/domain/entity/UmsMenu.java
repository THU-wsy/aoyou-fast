package com.thuwsy.aoyou.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: UmsMenu
 * Package: com.thuwsy.aoyou.common.domain.entity
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 17:35
 * @Version 1.0
 */
@Data
@TableName("ums_menu")
public class UmsMenu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @NotNull(message = "上级菜单不能为空")
    private Long parentId;
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    private Integer sort;
    private Integer menuType;
    @NotBlank(message = "路由地址不能为空")
    private String path;
    @NotBlank(message = "组件名称不能为空")
    private String componentPath;
    @NotBlank(message = "权限标识不能为空")
    private String perms;
    @NotBlank(message = "菜单图标不能为空")
    private String icon;
    private Integer status;
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String updater;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String remark;
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<UmsMenu> children = new ArrayList<>();
}
