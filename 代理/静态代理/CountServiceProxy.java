package letcode.proxy.staticP;

/**
 * 代理类：代理委托类
 */
public class CountServiceProxy implements CountService {
    //委托对象
    private CountServiceImpl countServiceImpl;

    public CountServiceProxy(CountServiceImpl countServiceImpl) {
        this.countServiceImpl = countServiceImpl;
    }


    @Override
    public void queryCount() {
        System.out.println("查询前");
        countServiceImpl.queryCount();
        System.out.println("查询后");
    }

    @Override
    public void updateCount() {
        System.out.println("更新前");
        countServiceImpl.updateCount();
        System.out.println("更新后");
    }
}
