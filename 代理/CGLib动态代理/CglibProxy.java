package CgLib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//切面代理类
public class CglibProxy implements MethodInterceptor {
    //子类(cglib库的子类) ->利用enhancer来创建代理类
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class cla) {
        //为目标类对象指定父类，继承代理类
        enhancer.setSuperclass(cla);
        //设置回调函数
        enhancer.setCallback(this);
        //返回生成的代理类对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before:" + method.getName());
        //通过父类，调用代理类方法
        Object result = methodProxy.invokeSuper(obj, objects);
        System.out.println("before:" + method.getName());
        return result;
    }
}
