package work.skymoyo.test.spi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.client.spi.AbstractClassDeserialize;
import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;
import java.util.Optional;

@Spi("org.springframework.http.ResponseEntity")
public class ClassDeserializeByResponseEntity extends AbstractClassDeserialize<ResponseEntity<Object>> {

    @Override
    public ResponseEntity deserializer(String jsonStr, Type type, String dataClass) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        LinkedMultiValueMap headers = JSON.parseObject(jsonObject.getString("headers"), LinkedMultiValueMap.class);

        //fastjson 1.2.83   jackson 2.13.3
        Integer statusCodeValue = jsonObject.getInteger("statusCodeValue");
        if (statusCodeValue == null) {
            //gson  2.10.1
            statusCodeValue = jsonObject.getInteger("status");
        }

        HttpStatus httpStatus = HttpStatus.resolve(statusCodeValue);

        Object body;


        dataClass = Optional.ofNullable(dataClass)
                .map(s -> s.replace("org.springframework.http.ResponseEntity", "")
                        .replace("<", "")
                        .replace(">", "")
                ).orElse("");

        if (StringUtils.hasLength(dataClass)) {
            try {
                Class<?> aClass = Class.forName(dataClass);
                body = JSON.parseObject(jsonObject.getString("body"), aClass);
            } catch (Exception e) {
                body = JSON.parseObject(jsonObject.getString("body"), type);
            }
        } else {
            body = JSON.parseObject(jsonObject.getString("body"), type);
        }

        return new ResponseEntity<Object>(body, headers, httpStatus);
    }


}
