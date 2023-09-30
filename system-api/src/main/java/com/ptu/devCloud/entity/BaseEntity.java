package com.ptu.devCloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共字段实体类
 * @author Yang Fan
 * @since 2023/9/30 14:53
 */
@Data
public abstract class BaseEntity implements Serializable {

    /** 主键id */
    @TableId(value = "id")
    private Long id;

    /** 创建时间 */
    @TableField(value = "create_date")
    private Date createDate;

    /** 创建人 */
    @TableField(value = "create_by")
    private Long createBy;

    /** 更新时间 */
    @TableField(value = "update_date")
    private Date updateDate;

    /** 更新人 */
    @TableField(value = "update_by")
    private Long updateBy;

}
