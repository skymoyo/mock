package work.skymoyo.mock.common.model;

import lombok.Data;

@Data
public class MockResp<T> extends BaseObject {

    private String uuid;

    private String msg;

    private T data;

    @Override
    public byte getObject() {
        return 2;
    }
}
