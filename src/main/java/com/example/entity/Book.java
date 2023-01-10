package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("book")
public class Book extends Model<Book> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 类型 
      */
    private String category;

    /**
      * 入库时间 
      */
    private Date createTime;

    /**
      * 名称 
      */
    private String name;

    /**
      * 存放位置 
      */
    private String place;

}