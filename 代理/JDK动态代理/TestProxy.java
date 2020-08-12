package letcode.proxy;

public class TestProxy {
    public static void testProxy() {
        UserService userService = new UserServiceImpl();
        //实例化invocation
        MyInvocationHandler in = new MyInvocationHandler(userService);
        //生成代理对象
        UserService proxy = (UserService) in.getProxy();
        //由代理对象执行切面方法
        proxy.advice();
    }

    public static void main(String[] args) {
        testProxy();
    }
}
