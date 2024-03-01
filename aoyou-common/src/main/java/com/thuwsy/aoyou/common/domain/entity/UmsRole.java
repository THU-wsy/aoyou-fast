package com.thuwsy.aoyou.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: UmsRole
 * Package: com.thuwsy.aoyou.common.domain.entity
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 17:34
 * @Version 1.0
 */
@Data
@TableName("ums_role")
public class UmsRole implements Serializable {
    @TableId
    private Long roleId;
    private String roleLabel;
    private String roleName;
    private Integer sort;
    private Integer status;
    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private String updater;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private String remark;
    @TableLogic
    private Integer deleted;
}
