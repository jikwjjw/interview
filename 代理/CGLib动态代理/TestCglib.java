package CgLib;

public class TestCglib {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        SayHelloImpl sayHelloImpl = (SayHelloImpl) proxy.getProxy(SayHelloImpl.class);//通过子类生成代理类
        sayHelloImpl.say();//调用业务类方法，被拦截器中增强的方法拦截了
    }
}
