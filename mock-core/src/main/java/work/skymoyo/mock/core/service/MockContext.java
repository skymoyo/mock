package work.skymoyo.mock.core.service;


import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.model.MockReq;

@Component
public class MockContext {


    private ThreadLocal<MockReq> local = new ThreadLocal<>();


    public MockReq getLocalMockReq() {
        return local.get();
    }


    public void setLocalMockReq(MockReq req) {
        local.set(req);
    }

    public void remove() {
        local.remove();
    }


}
