package work.skymoyo.mock.common.model;

import lombok.Data;
import work.skymoyo.mock.common.enums.OptType;

import java.util.Map;


@Data
public class MockReq extends BaseObject {

    private Map<String, Object> head;

    private String uuid;

    private OptType opt;

    private String route;

    private Map<String, Object> data;

    @Override
    public byte getObject() {
        return 1;
    }
}
