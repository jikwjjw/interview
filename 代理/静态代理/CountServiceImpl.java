package letcode.proxy.staticP;

/**
 * 委托类：实现业务接口
 */
public class CountServiceImpl implements CountService {
    @Override
    public void queryCount() {
        System.out.println("查看");
    }

    @Override
    public void updateCount() {
        System.out.println("更新");
    }
}
