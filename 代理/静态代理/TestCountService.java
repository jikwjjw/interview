package letcode.proxy.staticP;

/**
 * 测试
 */
public class TestCountService {
    public static void main(String[] args) {
        CountServiceImpl countServiceImpl = new CountServiceImpl();
        CountServiceProxy countServiceProxy = new CountServiceProxy(countServiceImpl);
        countServiceProxy.queryCount();
        countServiceProxy.updateCount();
    }
}
