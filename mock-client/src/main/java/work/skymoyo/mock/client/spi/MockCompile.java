package work.skymoyo.mock.client.spi;

import java.util.Map;

/**
 * 由于在应用程序里面可能有加解密
 * 此处预留SPI
 * 应用服务自定义加解密
 */
public interface MockCompile {

    /**
     * 加密
     * <p>
     * 此处会把mock服务端返回加密返回，
     *
     * @param resp
     * @return
     */
    Object encode(Object resp);


    /**
     * 解密
     * <p>
     * 此处会把mock客户端加密报文解密后发送，
     *
     * @param req
     * @return
     */
    Map<String, Object> decode(Map<String, Object> req);

}
