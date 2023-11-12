package work.skymoyo.mock.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.resource.dao.MockConditionDao;
import work.skymoyo.mock.core.resource.dao.MockConfigDao;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockCondition;
import work.skymoyo.mock.core.resource.entity.MockConfig;
import work.skymoyo.mock.core.resource.entity.MockRule;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.rule.MockConditionService;
import work.skymoyo.mock.core.service.rule.MockHandleManager;
import work.skymoyo.mock.core.service.rule.MockResultService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Service
public class MockServiceImpl implements MockService {

    @Autowired
    private MockConfigDao mockConfigDao;
    @Autowired
    private MockRuleDao mockRuleDao;
    @Autowired
    private MockConditionDao mockConditionDao;
    @Autowired
    private MockHandleManager mockHandleManager;

    @Override
    public MockDataBo mock(MockReq req) {

        String route = req.getRoute();
        String realRoute = route.replace("/mock", "").replace("mock", "");
        MockConfig config = mockConfigDao.queryByRoute(realRoute);
        if (config == null) {
            throw new MockException("定义配置信息为空");
        }

        String mockCode = config.getMockCode();
        List<MockRule> mockRuleList = mockRuleDao.queryByMockCode(mockCode);

        if (CollectionUtils.isEmpty(mockRuleList)) {
            throw new MockException(mockCode + "规则配置信息为空");
        }

        for (MockRule mockRule : mockRuleList) {

            log.info("[{}]规则执行 \r\n 规则配置:[{}] \r\n 请求数据：[{}]  ", mockRule.getRuleName(), mockRule, req);

            List<MockCondition> mockConditionList = mockConditionDao.queryByRuleCode(mockRule.getRuleCode());
            if (CollectionUtils.isEmpty(mockConditionList)) {
                log.warn("[{}]未配置场景", mockCode);
                continue;
            }

            boolean isSend = true;
            for (MockCondition mockCondition : mockConditionList) {
                MockConditionService mockConditionService = mockHandleManager.selectorHandle(MockHandleTypeEnum.REQ, mockCondition.getConditionType(), MockConditionService.class);

                isSend = mockConditionService.mockConditionValue(req, mockCondition);
                log.info("[{}]规则执行 \r\n 匹配类型[{}] 条件[{}] \r\n 执行结果: [{}] ", mockRule.getRuleName(), mockCondition.getConditionType(), mockCondition.getConditionKey(), isSend);
                if (!isSend) {
                    break;
                }
            }

            if (isSend) {
                MockResultService mockResultService = mockHandleManager.selectorHandle(MockHandleTypeEnum.RESP, mockRule.getRuleType(), MockResultService.class);
                log.info("[{}]规则执行返回数据", mockRule.getRuleName());
                return mockResultService.getResult(req, mockRule);
            }
        }

        throw new MockException("匹配场景未有条件场景");
    }

    @Override
    public String mockHttp(HttpServletRequest request, HttpServletResponse response) {
        log.info("http接口请求");
        MockReq req = new MockReq();

        req.setRoute(request.getServletPath().replace("/mock", "").replace("mock", ""));
        req.setHead(this.getReqHead(request));
        req.setData(this.getReqData(request));
        req.setOpt(OptType.MOCK);
        return this.mock(req).getData();
    }
}
