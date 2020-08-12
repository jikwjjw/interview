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
    + index = hash&(n-1)   ： n是table长度
+ 扩容的实现
  + resize的过程，简单说就是bucket扩展为原来的两倍，之后重新计算index，把节点在放入新的bucket中
  + 因为扩展成原来的两倍，元素要么在原位置，要么在原位置在移动2次幂的位置，因此在扩充HashMap时，不需要重新计算hash，只需要看原来的hash值新增的bit是1还是0。是0的话，索引未变，是1的话索引变成"原位+bit" ——>(n-1)&h ->n变成两倍，高位多1个bit，看是0还是1.即省去重新计算Hash值的时间，且0和1是随机的，因此在扩容时，可均匀把之前冲突的节点分散到bucket中。
  + resize时造成的不安全
    + put的时候：多线程导致数据put不一致，线程A插入Key/value，首先计算桶的索引坐标，然后获得链表头节点，此时A时间片用完了，B进行Key/value，B成功插入，A获得时间片且获得相同的index或hash，把B覆盖，造成数据不一致
    + 死锁->transfer()方法引起死锁：因为采用头插法，转移的时候都是逆序的，两个线程调度链表成为环形链表，造成死锁。
    
  
