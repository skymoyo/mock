package work.skymoyo.mock.core.admin.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import work.skymoyo.mock.common.enums.OptType;

import java.util.List;


@Data
public class AdminMockReq {

    private String appId;

    private String appName;

    private long threadId;

    private String client;

    /**
     * todo json String 前端不会传map
     */
    private String head;

    private String uuid;

    private OptType opt;

    private String route;
    /**
     * todo json String 前端不会传map
     */
    private String data;


    private MockRuleVO[] ruleList;

    private String ruleListStr;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
