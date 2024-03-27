package work.skymoyo.mock.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.admin.model.MockRuleVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MockService {

    default Map<String, Object> getReqHead(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getHeaderNames();
        Map<String, Object> map = new HashMap<>(16);
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            map.put(key, request.getHeader(key));
        }
        return map;

    }

    default Map<String, Object> getReqData(HttpServletRequest request) {

        String contentType = request.getHeader("Content-Type");
        if ("get".equalsIgnoreCase(request.getMethod()) || (contentType != null && contentType.contains("form"))) {

            Enumeration<String> parameterNames = request.getParameterNames();
            Map<String, Object> map = new HashMap<>(8);
            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                map.put(key, request.getParameterValues(key));
            }
            return map;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            try (InputStream inputStream = request.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } catch (Exception ex) {
                throw new MockException(ex.getMessage());
            }
            return JSON.parseObject(stringBuilder.toString(), new TypeReference<Map<String, Object>>() {
            });
        }

    }


    MockDataBo mock(MockReq req);

    MockDataBo mock(MockReq req, List<MockRuleVO> ruleVOList);

    String mockHttp(HttpServletRequest request, HttpServletResponse response);

}
