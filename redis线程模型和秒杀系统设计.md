+ 单线程模型
  + redis内部使用文件事件处理器，file event handle，这个文件事件处理器是单线程的，所以redis才叫做单线程模型，它采用IO多路复用机制同时监听socket，根据socket上的时间来选择对应的事件处理器进行处理
