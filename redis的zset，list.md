+ zset实现跳跃表：每相邻两个结点之间就增加一个指针，让指针指向下一个结点
  + 当链表足够多时，这样的多层链表结构可以帮助我们跳过很多下层结点，从而加快查找效率，使查询效率将为O(logN)
+ 插入时
  + 重塑表结构，每两个插入一个指针
  + 随机一个level，一个结点随机层数3，那么把它链如到第一层到第三层链表中，因为每一个结点层数(level)是随机出来的，而且新插入一个结点，并不会影响到其他结点层数，插入操作只需要修改节点前后的指针，而不需要对多个结点进行调整，降低复杂度
---------------------------
+ list
  + redis的列表相当于java的listedList，注意它是链表而不是数组，插入，删除很快，查询很慢。
---------------------------
+ hash
  + hashMap:数组+链表的链地址法来解决哈希冲突
---------------
+ set
  + hashSet：value为null
--------------
+ zsortset
  + set和hashmap结合，有内部value，并给value赋予一个score值，代表排序位重。
