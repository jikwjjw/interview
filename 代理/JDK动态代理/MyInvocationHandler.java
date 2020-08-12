package letcode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类，对接口提供的方法实现增强
 */
class MyInvocationHandler implements InvocationHandler {
    //目标对象
    private Object target;

    MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    //目标对象方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("----before---");
        //执行目标对象
        Object result = method.invoke(target, args);
        System.out.println("--after----");
        return result;
    }

    //获取目标对象的代理对象
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), this.target.getClass().getInterfaces(), this::invoke);
    }
}
