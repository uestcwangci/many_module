# Activity启动流程 #

----------

![Activity启动流程](https://i.imgur.com/tx0p58B.png)

![通信过程](http://upload-images.jianshu.io/upload_images/3985563-cb3187996516846a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

名词解释

App进程|system_server进程
--|--
AMP:ActivityManagerProxy|AMS:ActivityManagerService
AT:ApplicationThread|ATP:ApplicationThreadProxy

启动流程：

1. 点击桌面App图标，Launcher进程采用Binder IPC向system_server进程发起startActivity请求；
1. system_server进程接收到请求后，向zygote进程发送创建进程的请求；
1. Zygote进程fork出新的子进程，即App进程；
1. App进程，通过Binder IPC向sytem_server进程发起attachApplication请求；
1. system_server进程在收到请求后，进行一系列准备工作后，再通过binder IPC向App进程发送scheduleLaunchActivity请求；
1. App进程的binder线程（ApplicationThread）在收到请求后，通过handler向主线程发送LAUNCH_ACTIVITY消息；
1. 主线程在收到Message后，通过发射机制创建目标Activity，并回调Activity.onCreate()等方法。
1. 到此，App便正式启动，开始进入Activity生命周期，执行完onCreate/onStart/onResume方法，UI渲染结束后便可以看到App的主界面。

参考：[https://lrh1993.gitbooks.io/android_interview_guide/content/android/advance/app-launch.html](https://lrh1993.gitbooks.io/android_interview_guide/content/android/advance/app-launch.html)