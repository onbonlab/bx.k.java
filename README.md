# bx5k.api.java
本项目为仰邦科技 BX-5K/6K 系列的 JAVA 实现。本项目由纯JAVA编写，
因此，其可以正常运行于Windows, Linux 和 Android 等平台。本项目根据通讯协议，仅对命令进行了封装。
而采用网口或串口来通讯完全由应用层来决定，而 demo 中使用了 TCP 模式。

## 已支持功能
此项目目前已支持的功能：
* 动态区（包含语音）
* 开机与关机
* 清除屏幕

## 待实现功能
* 文件维护
* 节目发送
* ...

## 使用方法
```java
// 是否使能语音
area2.setSoundMode((byte) 0x01);
// 人声模式
area2.setSoundPerson((byte) 0x00);
// 重复次数
area2.setSoundRepeat((byte) 0x03);
// 发音速度
area2.setSoundSpeed((byte) 0x02);
// 音量
area2.setSoundVolume((byte) 10);
```
