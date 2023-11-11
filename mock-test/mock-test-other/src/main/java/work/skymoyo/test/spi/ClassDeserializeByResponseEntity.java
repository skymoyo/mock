package work.skymoyo.test.spi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import work.skymoyo.mock.client.spi.AbstractClassDeserialize;
import work.skymoyo.mock.common.spi.Spi;

@Spi("org.springframework.http.ResponseEntity")
public class ClassDeserializeByResponseEntity extends AbstractClassDeserialize<ResponseEntity<Object>> {

    @Override
    public ResponseEntity deserializer(String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        LinkedMultiValueMap headers = JSON.parseObject(jsonObject.getString("headers"), LinkedMultiValueMap.class);

        //fastjson 1.2.83   jackson 2.13.3
        Integer statusCodeValue = jsonObject.getInteger("statusCodeValue");
        if (statusCodeValue == null) {
            //gson  2.10.1
            statusCodeValue = jsonObject.getInteger("status");
        }

        HttpStatus httpStatus = HttpStatus.resolve(statusCodeValue);
        return new ResponseEntity<Object>(jsonObject.get("body"), headers, httpStatus);
    }


}
