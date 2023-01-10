package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("P_conference")
public class Conference extends Model<Conference> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 会议名称 
      */
    private String conferName;

    /**
      * 会议时间 
      */
    private Date createTime;

    /**
      * 描述 
      */
    private String description;

    /**
      * 参与人数 
      */
    private String numParticipant;

    /**
      * 参与人 
      */
    private String participantName;

}