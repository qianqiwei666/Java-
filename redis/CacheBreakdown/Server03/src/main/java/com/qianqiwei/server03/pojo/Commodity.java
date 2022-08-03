package com.qianqiwei.server03.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName commodity
 */
@TableName(value ="commodity")
@Data
public class Commodity implements Serializable {
    /**
     * 
     */
    private Integer cid;

    /**
     * 
     */
    private String cname;

    /**
     * 
     */
    private Double crice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}