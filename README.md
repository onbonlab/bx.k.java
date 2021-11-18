# bx5k.api.java
本项目为仰邦科技 BX-5K/6K 系列的 JAVA 实现，本项目直接以源代码的形式提供。
对于本项目未实现功能，任何人都可直接在此代码上进行添加与修改。

本项目由纯JAVA编写，
因此，其可以正常运行于Windows, Linux 和 Android 等平台。本项目根据通讯协议，仅对命令进行了封装。
而采用网口或串口来通讯完全由应用层来决定，本 demo 中提供了 TCP 模式的简单使用方式。

## 已支持功能
此项目目前已支持的功能：
* 动态区（包含语音）
* 开机与关机
* 校时

## 待实现功能
* 文件维护
* 节目发送
* ...
> 注：对于本项目未实现功能，您可参考我们的通讯协议，自行添加。或者等待本项目更新 ^_^
## 使用方法

### 发送命令
发送一个命令，通常需要以下几个步骤：
1. 创建一个 BxCmd 对象
2. 创建 BxDataPack 对象，对 BxCmd 对象进行封装
3. 生成命令序列
4. 通过网口/串口发送命令序列
5. 接收返回的命令序列
6. 使用BxResp对象对返回的命令序列进行解析

下面以开关机命令为例进行说明：

```java
//
// 创建 BxCmdTurnOnOff 对象
BxCmd cmdTurnOn = new BxCmdTurnOnOff(true);
// 使用 BxDataPack 对象对命令进行封装
BxDataPack packTurnOn = new BxDataPack(cmdTurnOn);
// 生成命令序列
byte[] seqTurnOn = packTurnOn.pack();

// 通过 TCP 发送命令序列
out.write(seqTurnOn);
// 接收返回的命令序列
len = in.read(resp);
// 使用 BxResp 对象对返回的命令序列进行解析
bxResp = BxResp.parse(resp, len);
// 对返回状态进行处理
if(bxResp.isAck()) {
    System.out.println("turn on, ok");
}
else {
    System.out.println("turn on, failed");
}
```

```java
//
// 创建一个发送动态区命令
BxCmd cmd = new BxCmdSendDynamicArea(areas);

// 对命令进行封装
BxDataPack dataPack = new BxDataPack(cmd);

// 生成命令序列
byte[] seq = dataPack.pack();

```
```
// 校时
BxCmd systime = new BxCmdSystemClockCorrect(new Date());
BxDataPack timePack = new BxDataPack(systime);
byte[] seqtime = timePack.pack();
out.write(seqtime);
len = in.read(resp);
bxResp = BxResp.parse(resp, len);
if(bxResp.isAck()) {
	System.out.println("system clock correct, ok");
}
else {
	System.out.println("system clock correct, failed");
}
```



### 动态区的使用

#### 什么是动态区？
动态区与普通节目相比，其数据存储在 RAM 中。因此，其掉电不保存，但是对于刷新次数没有限制。
其主要应用于更新频次高，更新速度比较快的场合。

#### 如何创建动态区？
如下所示，创建一个动态区非常简单，创建一个 BxAreaDynamic 对象，并设置好相关属性即可。
```java
//
// 创建一个区域 1
byte id = 0x00;
short x = 0;
short y = 0;
short w = 64;
short h = 16;

//
// 要显示的内容
// 采用 GB2312 码
// 注：\\C2 对应转义为 "\C2" 表示颜色为绿色
String s1 = "\\C20123";
byte [] data1 = new byte[0];
try {
    data1 = s1.getBytes("gb2312");
    //五代K卡最后一个参数给true、非5代的给false
    BxAreaDynamic area1 = new BxAreaDynamic(id, x, y, w, h, data1, true);
    // 添加到列表
    areas.add(area1);

} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
}
```

#### 如何在动态区中使用语音功能？
下面代码列出了动态区语音功能的相关配置接口：
```java
//
// 语音模式
area2.setSoundMode((byte) 0x02);
// 人声模式
area2.setSoundPerson((byte) 0x00);
// 重复次数
area2.setSoundRepeat((byte) 0x03);
// 发音速度
area2.setSoundSpeed((byte) 0x02);
// 音量
area2.setSoundVolume((byte) 10);
// 要发声的文字，仅在 soundMode = 0x02时有效
area2.setSoundData(soundData);
```
其中：
语音模式的定义如下：
* 0x00 - 关闭语音功能
* 0x01 - 打开语音功能，且显示内容与语音内容一致，此种模式下无需设置 soundData
* 0x02 - 打开语音功能，且显示内容与语音内容不一致，语音内容可通过 soundData 来设置

#### 如何发送动态区？
发送动态区命令与发送其它命令的流程一致, 创建一个 BxCmdSendDynamicArea 对象，
并按通用命令发送流程实现即可。
```java
//
// 创建一个发送动态区命令
BxCmd cmd = new BxCmdSendDynamicArea(areas);

// 对命令进行封装
BxDataPack dataPack = new BxDataPack(cmd);

// 生成命令序列
byte[] seq = dataPack.pack();

......

```
