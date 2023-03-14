## **实验要求：**

设计一个运动健康小助手APP，可以每天提醒使用者健康打卡，通知采集使用者的相关运动信息，并将每个用户的健康信息和运动信息以Http POST传输方式上传到服务端。要求：

1）服务端程序请自行设计，能实现用户注册、用户登录、用户信息收集和展示；

2）APP必须具备用户登录、新用户注册、运动信息采集、健康打卡信息收集和定时上传（可以设定为每天晚上10点上传）。



## 需求分析

**整个程序分为三个部分，运动健康小助手客户端****APP****，运动健康小助手的服务端和本地存放数据的数据库。**客户端APP通过Http协议与服务端通信，通过服务端对本地数据库进行增删查改。

**通信过程分析：**

​                               <img src="picture\image-20230314222720817.png" alt="image-20230314222720817" style="zoom:67%;" />

图2-1 APP通信

①  2-1的流程图中的data存放在APP本地，APP可以通过SharedPreferences轻量级的存储类来操作本地的数据或者使用SQLite轻量数据库。由于该项目存放在本地的数据不多，所以我选择了SharedPreferences。

②  2-1的流程图中的Android可以使用Socket与web后台进行数据交互。由于题目中要求使用Http协议，所以选择了Http的相关方式，具体的方式有HttpClient和OKHttp3等。Google在Android的较高版本里面删除了HttpClient相关API，我实现完HttpClient后发现它的代码也比较繁琐，所以后续交互就没有使用HttpClient，使用了更加简单的OKHttp3。

③  使用eclipse开发Java web项目，需要将已经部署完成的Tomcat关联到eclipse，并创建Server服务。Server服务创建成功后，eclipse可以直接将项目发布到Tomcat，并启动Tomcat服务。

④  Java使用处理请求和发送响应的过程是由一种叫做Servlet的程序来完成的。Servlet是用Java编写的服务器端程序。其主要功能在于交互式地浏览和修改数据，生成动态Web内容。后续查找资料的时候发现最近推荐的方法是使用spring boot来制作后端，由于时间限制，就没有进一步尝试了。



## 运行

整个项目分为两个项目。Android studio实现的客户端Sportapp和Eclipse实现的服务端HttpClient。

运行比较简单，先打开HttpClient，在Tomcat的server上运行。再打开Sportapp，在模拟器上运行，在模拟机上操作。



## 效果图

#### 用户登录功能



<img src="picture\image-20230314222940230.png" alt="image-20230314222940230" style="zoom:67%;" />

<img src="picture\image-20230314222950561.png" alt="image-20230314222950561" style="zoom:67%;" />

<img src="picture\image-20230314223008986.png" alt="image-20230314223008986" style="zoom:67%;" />

<img src="picture\image-20230314223017491.png" alt="image-20230314223017491" style="zoom:67%;" />

<img src="picture\image-20230314223041258.png" alt="image-20230314223041258" style="zoom:67%;" />

<img src="picture\image-20230314223052308.png" alt="image-20230314223052308" style="zoom:67%;" />

<img src="picture\image-20230314223058312.png" alt="image-20230314223058312" style="zoom:67%;" />

<img src="picture\image-20230314223107886.png" alt="image-20230314223107886" style="zoom:67%;" />

<img src="picture\image-20230314223112885.png" alt="image-20230314223112885" style="zoom:67%;" />

