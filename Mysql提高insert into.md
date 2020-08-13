+ 数据库属于I/O密集型的应用程序，其主要职责就是是数据库管理及存储工作，优化IO，尽可能保证磁盘优化为内存IO
+ key_buff_size(global)
  + 默认的MySQL配置文件中设置最大的一个内存参数，key_buff_size参数用来设置用于缓存MyISAM存储引擎中索引文件的内存区域大小，如果我们有足够的内存，这个缓存区域最好是能够存放下我们所有MyISAM引擎表的所有索引，以尽可能提高性能
  + 由于MyISAM引擎的特性限制了他仅仅只会索引到内存中，而并不会缓存表数据库块，所以我们的SQL一定尽可能让过滤条件都在索引中，以便让缓存帮助我们提高查询效率
+ bulk_insert_buff_size(thread)
  + 和key_buff_size一样，这个参数同样以仅作用于MyISAM存储引擎，用来缓存批量插入数据的时候临时写入数据，使用内存区域来缓存批量结构数据以帮助批量写入数据文件。
+ 改写insert into 为insert delayed into
  + 采用延迟插入，服务立刻返回结果，后台进入插入
+ 使用insert values替代insert
+ 分库，分表，MyISAM存储引擎
