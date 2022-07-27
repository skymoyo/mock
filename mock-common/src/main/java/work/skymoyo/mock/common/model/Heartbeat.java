package work.skymoyo.mock.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Heartbeat extends BaseObject {

    private String data;


    @Override
    public byte getObject() {
        return 0;
    }


}
