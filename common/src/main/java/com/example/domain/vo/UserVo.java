package com.example.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String id;
    private String userName;
    private String nickName;
    private Date updateTime;
    private Long updateBy;
    private String status;
    private String sex;
    private String phonenumber;
    private String email;
    private Date createTime;
    private String avatar;
}
