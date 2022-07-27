package work.skymoyo.mock.client.spi;

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
     * @param o
     * @return
     */
    Object encode(Object o);


    /**
     * 解密
     * <p>
     * 此处会把mock客户端加密报文解密后发送，
     *
     * @param o
     * @return
     */
    Object decode(Object o);

}
