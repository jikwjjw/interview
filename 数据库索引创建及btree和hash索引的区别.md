+ 普通索引
  + create index indexName on Mytable(userName(length))
+ 在创建表时，直接建立索引
    ```
    Create table Nytable(
      Id int Not Null;
      username varchar(16) Not null;
      index [indexName] (username(length))
    )
    ```
+ 删除索引
  + Drop index[indeName] on Mytable
+ 使用Alter Table 命令去增加索引
  + alter table table_name ADD index index_name(column_list)
  + alter table table_name ADD index UNIQUE (column_list)//唯一索引
  + alter table table_name ADD primay_key (column_list)//主键索引
+ 全文索引(FulTEXT),普通索引(Normal),唯一索引(UNIQUE),逐渐索引(Primay_key)
+ 索引方法：HASH，B-tree
+ 显示索引信息 ：show Index from table_name
------------------
+ btree 和hash索引区别
  + hash索引仅满足“=”，“in”，“<=>”查询，不能使用范围查询
    + 由于hash索引比较的是进行hash运算之后的hash值，所以仅使用等值过滤
  + hash索引无法排序
    + hash值的索引顺序和实际顺序不一致，无法排序
  + hash索引不能利用来避免部分索引键值查询
    + 对于组合索引，hash索引在计算hash值的时候，组合索引键合并后进行hash值，并不单独计算hash值，所以通过组合索引时，无法利用单独hash。
  + 无法避免全表扫描
    + hash计算是将hash值和数据指针存在hash表中，相同hash值情况下，还是要进行比较才能取得查询值。
  + Btree索引就是通过B+树或者B树
    + innodb和myisam不一样，一次索引和二次索引
    + innodb时聚集索引，myisam是非聚集索引
