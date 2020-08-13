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
