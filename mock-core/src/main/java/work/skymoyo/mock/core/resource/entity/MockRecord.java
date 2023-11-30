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
    private static final long serialVersionUID = -82198451755590630L;
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
    private long threadId;
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
