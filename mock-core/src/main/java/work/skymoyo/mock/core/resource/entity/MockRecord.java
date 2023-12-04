package work.skymoyo.mock.core.resource.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mock记录(MockRecord)实体类
 *
 * @author skymoyo
 * @since 2023-11-30 23:24:42
 */
@Data
public class MockRecord implements Serializable {
    private static final long serialVersionUID = 946235190331156735L;
    /**
     * id
     */
    private Long id;
    /**
     * 项目id
     */
    private String appId;
    /**
     * 项目名称
     */
    private String appName;
    /**
     * 线程id
     */
    private Long threadId;
    /**
     * 请求流水
     */
    private String uuid;
    /**
     * 请求客户端
     */
    private String client;
    /**
     * 请求路由
     */
    private String route;
    /**
     * 请求数据
     */
    private String mockReq;
    /**
     * 返回数据
     */
    private String mockResp;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


}
