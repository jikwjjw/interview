[Innodb中的事务隔离级别和锁的关系](https://tech.meituan.com/2014/08/20/innodb-lock.html)
--------------
+ 数据库遵循的是两段锁协议，将事务分成两个阶段，加锁阶段和解锁阶段（所以叫两段锁）
  + 加锁阶段：在该阶段可以进行加锁操作。在对任何数据进行读操作之前要申请并获得S锁（共享锁，其它事务可以继续加共享锁，但不能加排它锁），在进行写操作之前要申请并获得X锁（排它锁，其它事务不能再获得任何锁）。加锁不成功，则事务进入等待状态，直到加锁成功才继续执行。
  + 解锁阶段：当事务释放了一个封锁以后，事务进入解锁阶段，在该阶段只能进行解锁操作不能再进行加锁操作。
  + 这种方式虽然无法避免死锁，但是两段锁协议可以保证事务的并发调度是串行化（串行化很重要，尤其是在数据恢复和备份的时候）的。
--------------------------
+ MySQL中锁的种类
  + 表锁是对一整张表加锁，虽然可分为读锁和写锁，但毕竟是锁住整张表，会导致并发能力下降，一般是做ddl处理时使用。
  + 行锁则是锁住数据行，这种加锁方法比较复杂，但是由于只锁住有限的数据，对于其它数据不加限制，所以并发能力强，MySQL一般都是用行锁来处理并发事务。
-----------------
+ Read Committed（读取提交内容）
  + 在RC级别中，数据的读取都是不加锁的，但是数据的写入、修改和删除是需要加锁的。
  ```
  MySQL> show create table class_teacher \G\
  Table: class_teacher
  Create Table: CREATE TABLE `class_teacher` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `class_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
      `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
    KEY `idx_teacher_id` (`teacher_id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  1 row in set (0.02 sec)
  MySQL> select * from class_teacher;
+----+--------------+------------+
| id | class_name   | teacher_id |
+----+--------------+------------+
|  1 | 初三一班     |          1 |
|  3 | 初二一班     |          2 |
|  4 | 初二二班     |          2 |
+----+--------------+------------+
  ```
 
