package work.skymoyo.mock.core.resource.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (MockCondition)实体类
 *
 * @author skymoyo
 * @since 2022-07-23 09:13:56
 */
@Data
public class MockCondition implements Serializable {
    private static final long serialVersionUID = -52451648062990191L;
    /**
     * ID
     */
    private Long id;
    /**
     * 规则编码
     */
    private String ruleCode;
    /**
     * 类型
     */
    private String conditionType;
    /**
     * 条件
     */
    private String conditionKey;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUser;

}
