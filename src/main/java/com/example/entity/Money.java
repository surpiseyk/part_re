package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("P_money")
public class Money extends Model<Money> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 缴费时间 
      */
    private Date createTime;

    /**
      * 描述 
      */
    private String description;

    /**
      * 缴费数 
      */
    private String money;

    /**
      * 用户名 
      */
    private String name;

    /**
      * 用户职位 
      */
    private String role;

}