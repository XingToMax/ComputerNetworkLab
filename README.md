# ComputerNetworkLab
基于NIO、Netty实现一些计算机网络交互中常见的协议与应用，通过springboot提供服务

### List

- [ ] 滑动窗口协议的模拟
- [ ] 建立聊天工具
- [ ] 传输文件
- [ ] 发送邮件
- [ ] 实现ping命令
- [ ] 解析DNS改成模拟DNS
- [ ] 仿真telnet
- [ ] 模拟CSMA/CD协议
- [ ] 模拟令牌总线协议
- [ ] 模拟交换机工作
- [ ] 模拟RIP
- [ ] 模拟OSPF
- [ ] 模拟路由器原理
- [ ] 模拟隐蔽站和暴露站问题
- [ ] arp协议的模拟
- [ ] 模拟nat协议
- [ ] 模拟三种交换方式

### 实验要求

#### 实验一 滑动窗口协议的模拟 

1、实验目的：

要求学生掌握Socket编程 及滑动窗口协议
 2、实验内容：

i.               有发送端和接收端两个进程

ii.            实现滑动窗口协议，发送和接收窗口大小为5

iii.         模拟采用三次握手机制，显示出ACK、ack、Seq等标识位和参数

iv.          必须采用应答机制、超时计数器技术、帧编号判重技术、重传技术

v.            校验和技术

a)    校验和s的计算：设要发送n字节，bi为第i个字节，s=(b0+b1+…+bn) mod 256

vi.          在接收端，设置随机数，根据随机数执行相关操作，0代表正常，1代表帧丢失，2代表帧出错，3代表应答帧丢失（即不发生应答帧）

vii.       必须使用图形界面，

a)    按批次（发送端一次发送的报文）显示相关内容

b)    发送端：显示发送的数据、是否重传、本次帧序号、接收到的应答帧的序号

c)      接收端：显示接收到的数据、本次帧序号、本次随机选择的出错情况、发送应答帧的序号、是否重复

#### 实验二 建立聊天工具

1、实验目的：

要求学生掌握Socket编程中流套接字的技术
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            每个用户为1个进程

iii.         有注册功能

iv.          具有用户登录服务器验证的功能，用户名保存在本地文件中，不在此文件中的不允许登录

v.            要求每个用户有一个列表（保存在文件中），表示好友，不是好友的，不能聊天

vi.          必须掌握多线程技术，保证双方可以同时发送

vii.       建立聊天工具

viii.    可以打开多个窗口和多个人同时进行聊天

ix.          可以发送显示图片

x.            必须使用图形界面，显示双方的语录

xi.          当新用户上来时，可以自动把离线聊天记录发给新用户进行展示

xii.       如果是发给在线的用户，不能经过服务器，实现P2P

xiii.    发给指定用户的，不能发给其他结点

xiv.        可以群聊

#### 实验三 传输文件

1、实验目的：

要求学生掌握Socket编程中流套接字的技术
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            两种进程：服务器、客户端

iii.         要求客户端可以动态罗列服务器文件列表（客户端界面选择/输入服务器目录），选择一个进行下载

iv.          文件下载的过程，需要用线程来实现，保证多个客户端可以同时下载

v.            对文件进行分割（每片256字节），分别打包传输

a)    发送前，通过协商，发送端告诉接收端发送片数

b)    报头为学号、姓名、本次分片在整个文件中的位置

c)    报尾为校验和：校验和s的计算：设要发送n字节，bi为第i个字，s=(b0+b1+…+bn) mod 256

vi.          接收方进行合并

vii.       必须采用图形界面

a)    发送端可以选择文件，本次片数

b)    接收端显示总共的片数，目前已经接收到的文件片数，收完提示完全收到

#### 实验四 发送邮件

1、实验目的：

要求学生掌握Socket编程中流套接字的技术，以及邮件的发送和接收
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            可以发送和接收邮件

iii.         不能采用现有的工具/函数/API，必须根据协议自己一步一步，进行操作实现与服务器进行交互

iv.          要求每一次操作，在自己的界面上显示当前状态，并且必须点击下一步才能继续

v.            了解邮件发送格式

vi.          必须采用图形界面

vii.       客户端可以编辑发送内容，可以保存已发送的邮件，以便后续通过客户端可以查询自己已经发送了什么邮件

viii.    可选，建立自己的邮件服务器，发送邮件可以发给自己的邮件服务器，也可以发给已知邮件服务器

ix.          要求可以查看得到发送的邮件

#### 实验五 实现ping命令

1、实验目的：

要求学生掌握Socket编程技术，以及ICMP协议
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            不能采用现有的工具，必须自己一步一步，根据协议进行操作

iii.         了解ping报文的格式和步骤，要求符合ICMP协议并组建报文

iv.          在一秒钟内，如果收到，则为成功，如果收不到，则失败

v.            必须采用图形界面，查看收到回应的结果

vi.          可以通过程序，查看/搜索子网中有哪些主机可以ping通，并进行展示（显示ip地址和主机名）

vii.       在Vi操作时，采用多线程技术，保证用户点击暂停时，可以停止搜索

#### 实验六 解析DNS改成模拟DNS

1、实验目的：

要求学生掌握Socket编程技术，以及DNS协议
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            建立一个简单的DNS服务器（进程代替，下同），接收客户端的请求，查询本地地址表（保存在文件中），查不到，则向现有的DNS服务器（自己输入）发起请求

iii.         最终需要和现有的dns服务器（比如南航的dns服务器）进行交互

iv.          不能采用现有的工具，必须自己一步一步，根据协议进行操作

v.            可以让用户选择查询模式（迭代、递归）

vi.          针对迭代方式，要求每一次步骤，必须点击下一步才能继续，每次都假设查不到，直至最后一个服务器

vii.       了解DNS报文的格式和步骤

viii.    必须采用图形界面，查看收到回应的结果

ix.          把查询的结果，保存在当地文件中，以备后续查询

x.            需要记录缓存，并在窗口中展示

#### 实验七 仿真telnet

1、实验目的：

要求学生掌握Socket编程技术 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            服务器和客户机是两个进程

iii.         要求必须有登录过程，被允许登录的用户保存在一个文件中

**iv.**          **客户端敲的字符，必须立即在服务器端及时显示**

v.            可以通过服务器来启动一些应用程序（比如画板、写字板等）

vi.          客户端远程登录并连接一台计算机，把要求对方执行的程序（服务器端准备加减乘除四则运算的程序）发送给服务器端（界面上选择文件，点击发送后发送到服务器端）。

vii.       要求对方执行发送的程序、参数传给对方，然后执行完毕，把运行结果传回

viii.    必须采用图形界面，查看收到回应的结果

ix.          把一个程序发给服务器端，让服务器进行运行。

#### 实验八  模拟CSMA/CD协议

1、实验目的：

要求学生掌握Socket编程及CSMA/CD工作过程 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            程序模拟主机发送过程，发送内容由人来输入（模拟过程开始前输入，设为A，针对不同的“主机”，可以输入不同的A）

iii.         8台主机（进程代替，下同），主机号为1-8

iv.          发送前，查看当前是否有其它程序在发送；

v.            如果没有其它程序发送，则发送自己的数据内容（内容格式为：源目主机号+“：”+目的主机号+“：”+A，发送3秒钟，），进行广播（也可以采用点对点发给每一个其它程序）

vi.          假定数据传输过程为1秒（即接收端延迟一秒才能感知到有数据传输。相应的，前面的探测过程是指在接到数据一秒前，均认为没有其它节点发送）

vii.       发送过程中，如果发现碰撞（即，在收到其它程序发送的数据一秒之前，自己已经发送数据了），则停止重发

viii.    等待一个随机时间（4秒）重新开始

ix.          必须采用图形界面，每个程序可以设置自己的主机号，查看运行过程

#### 实验九  模拟令牌环协议

1、实验目的：

要求学生掌握Socket技术及令牌环工作过程 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            8台主机（用进程来实现），主机号为1-8

iii.         产生一个令牌，绕着8台主机依照主机号进行轮转（即1号发给2号，2号发给3号，...，8号发给1号），一秒钟转移一次

iv.          程序模拟主机随机发送（6秒内选择随机数），（内容格式为：源目主机号+“：”+目的主机号+“：”+Hello Baby）

v.            程序模拟主机发送过程，发送内容是上面产生的随机数

vi.          发送前，查看当前是否有有令牌，如果没有令牌，则等待

vii.       如果有令牌，可以发送数据（目的主机号随机产生），假定数据传输过程为2秒，此时不再发送令牌，只发送数据

viii.    数据依然按照主机号发送

ix.          接收方接受后，显示内容

x.            发送主机最后收到自己的数据，重新发送令牌

xi.          必须采用图形界面，每个程序可以设置自己的主机号，查看运行过程

#### 实验十  模拟交换机工作

1、实验目的：

要求学生掌握Socket技术及交换机工作过程 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            产生8个进程，其中两个代表交换机，6个代表主机

完成下面拓扑

![1554379283577](https://github.com/XingToMax/ComputerNetworkLab/blob/master/images/1554379283577.png?raw=true)

iii.         任何一个主机可以通过Socket发送数据给另外一个

iv.          交换机显示处理过程，包括查表过程、发送给谁、以及自学习过程

v.            自学习的结果，保存到文件中，并记录登记时间，如果超出时间，自动删除

vi.          必须采用图形界面，每个主机/交换机可以设置自己的标识

#### 实验十一  模拟RIP

1、实验目的：

要求学生掌握Socket技术及RIP工作过程 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            产生5个进程，每个进程代表一个路由器，保存有自己的路由表

iii.         网络的拓扑如下

![1554379268631](https://github.com/XingToMax/ComputerNetworkLab/blob/master/images/1554379268631.png?raw=true) 

iv.          可以人工指定哪个网络崩溃了

v.            每隔1分钟，进程交换自己的路由表

vi.          必须使用图形界面，显示双方的语录

vii.       每一步必须显示收到了谁的路由表，是进行了如何的操作（替换、更改、不用）

viii.    展示最新的路由表

#### 实验十二  模拟OSPF

1、实验目的：

要求学生掌握Socket技术及OSPF工作过程 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            产生4个进程，每个进程代表一个路由器，保存有自己的路由表

iii.         网络的拓扑如下

![](https://github.com/XingToMax/ComputerNetworkLab/blob/master/images/1554379224798.png?raw=true)

iv.          每隔1分钟，进程交换自己的路由表

v.            可以人工输入网络之间的“距离”

vi.          必须使用图形界面，显示双方的语录

vii.       必须展示出谁和谁是最短路径树上是连通的，如果能够画出拓扑图更好

viii.    每一步必须显示找到了哪一个节点，本节点到它有多远，调整了的其他节点的信息（距离）

展示最新的路由表

#### 实验十三  模拟路由器原理

1、实验目的：

要求学生掌握Socket技术及路由器工作过程 
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            产生5个进程，一个进程代表路由器，其他代表主机，均链接到路由器上

iii.         主机每秒钟发送10个报文（报文内容：进程id+“：”+端口号+“：”+发送序号+“：”+自己的学号），目的地址随机（用随机数产生）

iv.          路由器每个接口，设置接收存储器，数据进入路由器后，进入存储器

v.            自己事先设定一个路由表，模拟出查表的过程，要求：找出ip地址（自己假设，但是结构必须和真是的ip地址一样）的net id，进行查表。

vi.          路由器采用多线程技术，轮流从存储器中读取数据分组

vii.       路由器统计每个存储器的使用情况

viii.    主机将收到的报文进行显示，并保存到文件中

ix.          主机统计自己发送和接收的情况

必须使用图形界面

#### 实验十四 模拟隐蔽站和暴露站问题

1、实验目的：

要求学生掌握Socket编程技术，以及模拟隐蔽站和暴露站的过程
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            初始设置32个节点（进程代替，下同），如何定义会产生隐蔽站和暴露站问题，可用自由发挥

iii.         用图形进行显示隐蔽站和暴露站发生的过程，最少应该实现如下程度：对于隐蔽站问题，产生碰撞附近的接收节点变色，对于暴露站，可以发送但是不敢发送信息的节点变色。

扩展，用rts/cts帧来减少隐蔽站和暴露站问题

#### 实验十五 arp协议的模拟

1、实验目的：

要求学生掌握Socket编程技术，以及模拟分配IP地址的过程
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            客户端（进程代替，下同）获取自身的MAC地址，根据MAC地址，向服务器获取IP地址。

iii.         建立一个简单的IP地址分配服务器，接收客户端的请求，查询本地可用的地址（最初的保存在文件中），查不到，发给客户端，如果没有则拒绝客户端的请求

iv.          服务器必须采用多线程技术，

v.            iv.  服务器必须采用图形界面，查看收到请求和处理的结果

客户端必须采用图形界面，查看收到回应的结果

#### 实验十六 模拟nat协议

1、实验目的：

要求学生掌握Socket编程技术，以及模拟nat的工作过程
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            系统有3个内部主机（进程代替，下同）、一个NAT代理主机、一个外部主机。

iii.         内部主机A向外部主机发动连接发送报文。

iv.          NAT代理主机获取报文，找到空闲的端口，替换IP地址和端口，发送给外部主机

v.            外部主机返回应答报文，

vi.          NAT代理主机获取报文，找到A的信息，替换IP地址和端口，发送给外部主机

必须采用图形界面，查看处理的过程

#### 实验十七 模拟三种交换方式

1、实验目的：

要求学生掌握Socket编程技术，以及模拟nat的工作过程

![1554379251420](https://github.com/XingToMax/ComputerNetworkLab/blob/master/images/1554379251420.png?raw=true)
 2、实验内容：

i.               要求学生掌握利用Socket进行编程的技术

ii.            系统有4个主机（进程代替，下同）。

iii.         对于电路交换，体现出连接建立的过程。

iv.          发送内容要足够大（比如100M，可以事先选择一个文件）

v.            对于分组交换，划分片数可以通过界面让人工输入

vi.          必须采用图形界面，查看处理的过程

统计出整个过程的时延