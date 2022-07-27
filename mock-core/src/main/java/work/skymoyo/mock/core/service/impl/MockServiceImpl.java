package work.skymoyo.mock.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.resource.dao.MockConditionDao;
import work.skymoyo.mock.core.resource.dao.MockConfigDao;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockCondition;
import work.skymoyo.mock.core.resource.entity.MockConfig;
import work.skymoyo.mock.core.resource.entity.MockRule;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.rule.GetConditionValueManager;
import work.skymoyo.mock.core.service.rule.MockConditionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

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
    private GetConditionValueManager getConditionValueManager;

    @Override
    public String mock(MockReq<Object> req) {

        String route = req.getRoute();
        String realRoute = route.replace("mock", "").replace("/mock", "");

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

            List<MockCondition> mockConditionList = mockConditionDao.queryByRuleCode(mockRule.getRuleCode());
            if (CollectionUtils.isEmpty(mockConditionList)) {
                log.warn("[{}]未配置场景", mockCode);
                continue;
            }

            boolean isSend = true;
            for (MockCondition mockCondition : mockConditionList) {
                MockConditionService mockConditionService = getConditionValueManager.selectorGetCondition(mockCondition.getConditionType());
                Object mock = mockConditionService.mockConditionValue(req, mockCondition);
                String conditionValue = mockCondition.getConditionValue();

                isSend = Objects.equals(String.valueOf(mock), conditionValue);
                log.info("当前获取值[{}],配置[{}]，对比结果[{}]", mock, conditionValue, isSend);
                if (!isSend) {
                    break;
                }
            }

            if (isSend) {
                return mockRuleDao.queryById(mockRule.getId()).getRuleResult();
            }
        }

        throw new MockException("匹配场景未有条件场景");
    }

    @Override
    public String mockHttp(HttpServletRequest request, HttpServletResponse response) {
        log.info("http接口请求");
        MockReq<Object> req = new MockReq<>();

        req.setRoute(request.getServletPath());
        req.setHead(this.getReqHead(request));
        req.setData(this.getReqData(request));
        req.setOpt(OptType.MOCK);
        return this.mock(req);
    }
}
