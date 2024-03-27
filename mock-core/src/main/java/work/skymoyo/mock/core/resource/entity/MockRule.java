package work.skymoyo.mock.core.resource.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mock配置表(MockRule)实体类
 *
 * @author skymoyo
 * @since 2022-07-25 20:19:32
 */
@Data
public class MockRule implements Serializable {
    private static final long serialVersionUID = 646209587586411502L;
    /**
     * ID
     */
    private Long id;
    /**
     * 配置编码
     */
    private String mockCode;
    /**
     * 规则编码
     */
    private String ruleCode;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 结果生成类型 10 直接返回 20 动态生成
     */
    private String ruleType;
    /**
     * 反序列化类
     */
    private String ruleClass;
    /**
     * 返回结果
     */
    private String ruleResult;
    /**
     * 堵塞时长
     */
    private Long blockTime;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
