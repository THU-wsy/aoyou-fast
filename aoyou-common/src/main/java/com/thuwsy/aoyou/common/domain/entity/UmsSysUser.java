package com.thuwsy.aoyou.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: UmsSysUser
 * Package: com.thuwsy.aoyou.common.domain.entity
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 17:33
 * @Version 1.0
 */
@Data
@TableName("ums_sys_user")
public class UmsSysUser implements Serializable {
    @TableId
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    private Integer sex;
    private String avatar;
    private String password;
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
    // 角色信息
    @TableField(exist = false)
    private List<UmsRole> roleList = new ArrayList<>();
    // 权限信息
    @TableField(exist = false)
    private List<String> perms = new ArrayList<>();
}
