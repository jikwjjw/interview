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
