package work.skymoyo.mock.core.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.admin.model.MockRuleVO;
import work.skymoyo.mock.core.resource.dao.MockConfigDao;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockCondition;
import work.skymoyo.mock.core.resource.entity.MockConfig;
import work.skymoyo.mock.core.resource.entity.MockRecord;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.rule.MockConditionService;
import work.skymoyo.mock.core.service.rule.MockHandleManager;
import work.skymoyo.mock.core.service.rule.MockResultService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MockServiceImpl implements MockService {

    @Autowired
    private MockConfigDao mockConfigDao;
    @Autowired
    private MockRuleDao mockRuleDao;
    @Autowired
    private MockHandleManager mockHandleManager;
    @Autowired
    private MockRecordDao mockRecordDao;


    @Override
    public MockDataBo mock(MockReq req) {

        String route = req.getRoute();
        String realRoute = route.replace("/mock", "").replace("mock", "");
        MockConfig config = mockConfigDao.queryByRoute(realRoute);
        if (config == null) {
            throw new MockException("定义配置信息为空");
        }

        String mockCode = config.getMockCode();
        List<MockRuleVO> mockRuleList = mockRuleDao.queryAllByMockCode(mockCode);

        if (CollectionUtils.isEmpty(mockRuleList)) {
            throw new MockException(mockCode + "规则配置信息为空");
        }

        return this.mock(req, mockRuleList);

    }

    @Override
    public MockDataBo mock(MockReq req, List<MockRuleVO> ruleVOList) {
        final String reqRoute = req.getRoute();

        for (MockRuleVO mockRule : ruleVOList) {

            log.info("[{}]规则执行 \r\n 规则配置:[{}] \r\n 请求数据：[{}]  ", mockRule.getRuleName(), mockRule, req);

            List<MockCondition> mockConditionList = mockRule.getConditionList();

            if (CollectionUtils.isEmpty(mockConditionList)) {
                log.warn("[{}]未配置场景", reqRoute);
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
                Long blockTime = mockRule.getBlockTime();
                if (blockTime != null) {
                    log.info("[{}]规则执行需等待[{}]秒后返回数据:", mockRule.getRuleName(), blockTime);
                    try {
                        Thread.sleep(blockTime * 1000);
                    } catch (Exception e) {
                        log.info("[{}]规则执行需等待[{}]秒后返回数据,等待异常:{}", mockRule.getRuleName(), blockTime, e.getMessage());
                    }
                } else {
                    log.info("[{}]规则执行返回数据", mockRule.getRuleName());
                }

                return mockResultService.getResult(req, mockRule);
            }
        }

        throw new MockException("匹配场景未有条件场景");
    }

    @Override
    public String mockHttp(HttpServletRequest request, HttpServletResponse response) {
        log.info("http接口请求");
        MockRecord record = new MockRecord();
        try {
            MockReq req = new MockReq();
            req.setUuid(UUID.randomUUID().toString());
            req.setThreadId(Thread.currentThread().getId());
            req.setClient("http接口请求");

            req.setRoute(request.getServletPath().replace("/agent", "").replace("agent", ""));
            req.setHead(this.getReqHead(request));
            req.setData(this.getReqData(request));
            req.setOpt(OptType.MOCK);

            record.setAppId(req.getAppId());
            record.setAppName(req.getAppName());
            record.setClient(req.getClient());
            record.setThreadId(req.getThreadId());
            record.setUuid(req.getUuid());
            record.setRoute(req.getRoute());
            record.setMockReq(JSON.toJSONString(req));
            mockRecordDao.insert(record);

            MockDataBo mock = this.mock(req);

            record.setMockResp(mock.getData());
            mockRecordDao.update(record);

            return mock.getData();
        } catch (Exception e) {
            if (record.getId() != null) {
                record.setMockResp(e.getMessage());
                mockRecordDao.update(record);
            }
            throw e;
        }
    }
}
