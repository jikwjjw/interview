+ BinLog
  + binlog记录了数据库表结构和表数据结构，比如update/delete/insert/create等。不会记录select，因为没有对表进行变更。
  + binlog存储这每条变更的SQL语句，主要做复制和恢复作用。
+ redolog
  + redolog 记录着在某页做了什么修改，属于物理记录
  + redolog的存在是为了当我们修改数据的时候，已经在内存写完了，但数据还没有真正写到磁盘的时候，此时，数据库挂了，可以根据redolog来对数据进行恢复，因为redolog是顺序I/O，所以写入速度很快，并且redolog记载的是物理变化(xx页做了修改)，文件体积很小，恢复速度很快。
+ undolog
  + 主要是用来回滚和多版本控制，也是存储逻辑日志，若insert，则undolog记录一条delete日志，一条相反的记录，undolog存储着修改之前的数据，相当于一个之前的版本。
  
+ 区别
  + 存储内容不同：binlog记录逻辑执行，SQL语句，redolog记录物理执行。
  + 功能不同：
    + redolog的作用是为了持久化而生的，写完内存，如果数据库挂了，可以通过redolog来恢复内存，还没来得及刷到磁盘的数据可以通过redolog再次加载到内存中。
    + binlog是为复制和恢复而生的
    + redolog是事务开始的时候就执行，记录每次变更消息，而binlog在提交事务的时候才记录
    + 通过redolog和binlog来保证数据一致，redolog在前，binlog在后。
