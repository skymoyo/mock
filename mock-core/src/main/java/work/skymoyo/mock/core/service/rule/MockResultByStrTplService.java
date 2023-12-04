package work.skymoyo.mock.core.service.rule;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.utils.SpelUtil;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockRecord;
import work.skymoyo.mock.core.resource.entity.MockRule;

import java.util.List;
import java.util.Map;


@Slf4j
@MockHandle(type = MockHandleTypeEnum.RESP, method = "spel")
public class MockResultByStrTplService implements MockResultService {

    @Autowired
    private MockRuleDao mockRuleDao;
    @Autowired
    private MockRecordDao mockRecordDao;

    @Override
    public MockDataBo getResult(MockReq req, MockRule mockRule) {
        log.info("MockResultByStrTplService 执行结果处理开始");
        MockDataBo bo = mockRuleDao.queryResultById(mockRule.getId());
        String spel = bo.getData();
        if (!spel.startsWith("'")) {
            spel = "'" + spel;
        }
        if (!spel.endsWith("'")) {
            spel = spel + "'";
        }

        Map<String, Object> data = req.getData();

        List<MockRecord> list = mockRecordDao.select(req.getAppId(), req.getThreadId());
        for (int i = 0; i < list.size(); i++) {
            MockRecord mockRecord = list.get(i);
            data.put("req" + i, JSON.parseObject(mockRecord.getMockReq(), Map.class));
            data.put("resp" + i, JSON.parseObject(mockRecord.getMockResp(), Map.class));
        }

        bo.setData(SpelUtil.compileParams(data, spel, String.class));
        return bo;
    }

}
