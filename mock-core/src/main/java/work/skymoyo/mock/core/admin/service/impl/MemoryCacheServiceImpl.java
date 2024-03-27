package work.skymoyo.mock.core.admin.service.impl;

import org.springframework.stereotype.Service;
import work.skymoyo.mock.core.admin.service.CacheService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryCacheServiceImpl implements CacheService {

    Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    @Override
    public void setString(String key, String value) {
        cacheMap.put(key, value);
    }
}
