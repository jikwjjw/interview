+ 基于Map接口实现，允许创建null键值对，非同步，不保证有序。
+ 当bucket填充的数目(即HashMap中元素的个数)大于Capacity*Load factor时就需要调整buckets中的数目为当前的两倍
+ put函数实现
  + 对key的hashcode()做hash，然后在计算index
  + 若无碰撞直接放在bucket里
  + 如果碰撞了，采用拉链法，放在bucket后
  + 如果链表过长，就把链表转化成红黑树(8个)
  + 节点存在就替换old value
  + 如果bucket超过了0.75*capatcity就实现扩容
+ get函数实现
  + 若在bucket，则取出
  + 若有冲突，则通过key.equals()去查找的对应的key
  + 若在树中，则O(logN)，若在链表中，则O(n)
+ hash函数的实现
  + h=hashcode()——> h>>>16 ——> hash = h^(h>>>16);(高16位不变，低16位和高16位做异或)
    + index = hash&(n-1)   ： n时table长度
  
