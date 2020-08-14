+ 单点登录就是在多个系统中，用户只需要登陆一次各个系统即可感知用户已经登录
+ 单点登录会有session不共享的问题
  + 把session数据放入到redis中，使用session通过redis
+ cookie跨域问题
  + 由于域名不同，用户向系统A登录后，系统A返回给浏览器的Cookie，用户再请求系统B的时候，不会将系统A的cookie带过去
----------------------
+ cookie
  + cookie是服务器向浏览器发送信息的，用来记录用户状态
  + cookie的不可跨域名性
    + baidu.com的域名不能带给google.com
    + cookie可以保存中文，中文属于Unicode，英文数据属于ACII字符
    + 使用URl.Encode.encode("name","utf-8")进行编码
  + cookie的有效性
    + cookie的有效期可以通过setMaxAge()来设置//MaxAge为正数，浏览器会把cookie写到硬盘中，只要还在MaxAge之前，登录该网站时，Cookie就有效
    + 如果cookie设置setMaxAge()为负数，则cookie是临时性，仅在本浏览器内有效，关闭浏览器cookie就失效了，cookie不会写入到硬盘中，cookie的默认值为-1；
    + 如果setMaxAge为0，代表删除cookie
  + cookie的域名
    + cookie的domain属性决定访问cookie的域名， 
      + 一级域名：com/cn
      + 二级域名: baidu.com,zhihu.com
      + 三级域名：abc.com.cn
      + 四级域名：www.baidu.com.cn
     +通过设置 domain属性，在一级域名相同的网页cookie之间可以相互访问
--------------------------
+ session
  + session是一个域名对象，存在服务器中，不可跨域名
  + session依据cookie来识别是否是同一个用户
  + 如果浏览器禁用了cookie，session可以通过URL地址重写来进行会话跟踪
+ 把cookie保存在硬盘上，可根据cookie来维护识别session，再次访问购物记录页面时，购物记录依然存在。
