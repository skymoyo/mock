package work.skymoyo.mock.core.resource.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (mock_config)实体类
 *
 * @author skymoyo
 * @since 2022-07-23 09:21:06
 */
@Data
public class MockConfig implements Serializable {
    private static final long serialVersionUID = 467973095007217539L;
    /**
     * ID
     */
    private Long id;
    /**
     * 配置编码
     */
    private String mockCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 路由
     */
    private String route;
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
