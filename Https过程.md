+ Https：超文本传输安全协议(非对称加密和对称加密)
  + Https在传统的Http和Tcp之间加了一层用于加密解密的SSL/TSL套接字。使用Https必须要有一套自己的数字证书(包括公钥和私钥)
+ Https解决的问题
  + 信息加密传输：第三方无法窃听
  + 校验机制：校验机制
  + 身份证书：防止身份被冒充
+ Https加密过程
  + client发起一个Https的请求，连接serve端的443端口
  + server把事先配置好的公钥证书返回给客户端(server向ca认证机构申请认证过的)
  + client验证公钥证书
  + client根据伪随机数生成加密所使用的对称密钥，然后用证书的公钥加密这个对称密钥，发送给server(这部分传送的是用证书加密后的随机值，目的是服务端得到这个随机值，以后客户端和服务端的通信就可以通过这个随机值来进行加密解密了)
  + server使用自己的私钥解密这个消息，得到对称密钥，至此，client和server双方都拥有相同的对称密钥
  + server将明文信息和随机值混合在一起进行对称加密，发送给客户端
  + client利用对称密钥解密
------------
+ 非对称加密的特点是一对多，服务器只需要维持要维持一个私钥就能够和多个客户端进行加密通信
+ Https采用非对称和对称结合的方式
  + 在密钥交换环节使用非对称加密方式，之后的建立通信交换报文阶段则使用对称加密方式
