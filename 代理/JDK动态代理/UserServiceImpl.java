package letcode.proxy;

/**
 * 切面，实现业务接口的类
 */
public class UserServiceImpl implements UserService {
    @Override
    public void advice() {
        System.out.println("切面");
    }
}
