+ ConCurrentHashMap是Java.util.ConCurrentHashMap包的重要成员，支持线程安全，支持高效并发的线程操作。
  + 在进行读时，不需要加锁，在写操作时，通过锁分段技术只对所操作的段加锁，而不影响其他线程访问。
  + ConCurrentHashMap本质上是一个Segment数组，而一个Segment数组实例又包含若干个桶，每个桶中都包含若干个HashEntry对象链接起来的链表，每个HashEntry都包含hash值，key，val，和next
  + 在ConCurrentHashMap中，散列发生"碰撞"将采用"拉链法"来处理碰撞，把碰撞的HashEntry对象链接成一个链表，由于HashEntry的next域为final，所以新节点，只能在链表的表头处插入。
  + 由于只能在链表头插入，所以链表中节点的顺序和插入的顺序相反
  + Segment类继承于RenntranLock类，从而使得Segment对象能充当锁的角色，每个Segment对象用来守护其成员对象table，包含若干的桶。
