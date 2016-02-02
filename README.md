#ChatRoom
实现一个简单的C/S聊天室功能

服务器端要点:

1. 客户端的每个Socket链接到服务器，服务器的启动一条线程与之对应，读取Socket输入流的数据

2. 服务器读取Socket数据后，需要将数据广播给全部客户端。因此需要在服务器端使用List来保存所有的Socket

3. 当客户端关闭时，服务器端读取Socket时出现异常，则将该Socket从List列表中去除

客户端要点:

1. 客户端有两条线程，一条为UI界面，将用户输入的数据写入到Socket对应的输出流中；另一条读取Socket对应输入流中的数据，即从服务器端发送过来的数据，显示在界面中

2. Handler在传递过程中，注意创建对象，防止出现空指针的情况

3. 需要在AndroidManifest.xml加上<uses-permission android:name="android.permission.INTERNET" />，即网络访问权限