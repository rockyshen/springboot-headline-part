package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

/**
 * @TableName news_user
 */

@Data
public class User implements Serializable {
    @TableId
    private Integer uid;

    private String username;

    private String userPwd;

    private String nickName;

    @Version
    @JsonIgnore
    private Integer version;    // 乐观锁

    @JsonIgnore
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

}