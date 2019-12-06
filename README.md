# bx5k.api.java
Java SDK for bx-5k/bx-6k

#1. 动态区语音功能

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
