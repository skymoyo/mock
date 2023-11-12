package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.mock.core.resource.entity.MockRule;


@Spi("RESP_SPI1")
@Slf4j
public class MockResultBySPI1Service implements MockResultService {


    @Override
    public MockDataBo getResult(MockReq req, MockRule mockRule) {
        log.info("SPI1 执行结果处理");
        MockDataBo bo = new MockDataBo();
        bo.setData("[{\"name\":\"SPI_1\",\"age\":12,\"childs\":[{\"name\":\"SPI_11\",\"age\":1,\"childs\":[{\"name\":\"SPI_111\",\"age\":1}]}]},{\"name\":\"bar\",\"age\":23,\"childs\":[{\"name\":\"bar1\",\"age\":1,\"childs\":[{\"name\":\"bar11\",\"age\":1}]},{\"name\":\"bar2\",\"age\":2,\"childs\":[{\"name\":\"bar21\",\"age\":412}]}]}]");
        return bo;
    }


}
