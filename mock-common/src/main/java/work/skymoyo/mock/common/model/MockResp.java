package work.skymoyo.mock.common.model;

import lombok.Data;

@Data
public class MockResp<T> extends BaseObject {

    private boolean success;

    private String uuid;

    private String msg;

    private String dataClass;

    private T data;

    @Override
    public byte getObject() {
        return 2;
    }
}
