import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        //
        // 添加到列表
        List<BxArea> areas = new ArrayList<BxArea>();

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



        //
        // 创建区域2
        id = 1;
        x = 0;
        y = 16;
        w = 64;
        h = 16;
        String s2 = "新年快乐！";
        byte[] data2;

        try {
            data2 = s2.getBytes("gb2312");
			//五代K卡最后一个参数给true、非5代的给false
            BxAreaDynamic area2 = new BxAreaDynamic(id, x, y, w, h, data2, true);


            String hello = "元宵节快乐";
            byte[] soundData = hello.getBytes("gb2312");

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

            //
            // 显示方式，其定义如下：
            // 0x01——静止显示
            // 0x02——快速打出
            // 0x03——向左移动
            // 0x04——向右移动
            // 0x05——向上移动
            // 0x06——向下移动
            // 设置显示方式为 0x03 - 向左移动
            area2.setDispMode((byte) 0x03);

            // 设置停留时间
            area2.setHoldTime((byte) 0);

            areas.add(area2);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //
        // 创建区域2
        id = 2;
        x = 32;
        y = 0;
        w = 32;
        h = 16;
        String s3 = "小猪";
        byte[] data3;

        try {
            data3 = s3.getBytes("gb2312");
			//五代K卡最后一个参数给true、非5代的给false
            BxAreaDynamic area3 = new BxAreaDynamic(id, x, y, w, h, data3, true);

            //
            // 显示方式，其定义如下：
            // 0x01——静止显示
            // 0x02——快速打出
            // 0x03——向左移动
            // 0x04——向右移动
            // 0x05——向上移动
            // 0x06——向下移动
            // 设置显示方式为 0x03 - 向左移动
            area3.setDispMode((byte) 0x02);

            // 移动速度
            area3.setSpeed((byte) 0x02);

            // 设置停留时间
            area3.setHoldTime((byte) 0);

            areas.add(area3);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //
        // 创建一个发送动态区命令
        BxCmd cmd = new BxCmdSendDynamicArea(areas);

        // 对命令进行封装
        BxDataPack dataPack = new BxDataPack(cmd);

        // 生成命令序列
        byte[] seq = dataPack.pack();


        //
        // 创建 Socket
        Socket client = new Socket();

        //
        // 创建 socket 地址
        SocketAddress address = new InetSocketAddress("192.168.88.8", 5005);


        try {
            //
            // 建立 TCP 连接
            client.connect(address, 3000);
            //
            // 设置读超时时间
            client.setSoTimeout(3000);

            //
            // 创建输出流
            OutputStream out = client.getOutputStream();

            //
            // 创建输入流
            InputStream in = client.getInputStream();

            // 写入数据
            out.write(seq);

            //
            // 回读返回帧
            byte[] resp = new byte[1024];
            int len = in.read(resp);

            //
            // parse
            BxResp bxResp = BxResp.parse(resp, len);

            //
            // 判断命令是否被正确执行
            if(bxResp.isAck()) {
                System.out.println("Well done!!");
            }
            else {
                System.out.println("I am sorry!");
            }

            //
            // 关机
            BxCmd cmdTurnOff = new BxCmdTurnOnOff(false);
            BxDataPack packTurnOff = new BxDataPack(cmdTurnOff);
            byte[] seqTurnOff = packTurnOff.pack();
            out.write(seqTurnOff);
            len = in.read(resp);
            bxResp = BxResp.parse(resp, len);
            if(bxResp.isAck()) {
                System.out.println("turn off, ok");
            }
            else {
                System.out.println("turn off, failed");
            }

            //
            // 开机
            BxCmd cmdTurnOn = new BxCmdTurnOnOff(true);
            BxDataPack packTurnOn = new BxDataPack(cmdTurnOn);
            byte[] seqTurnOn = packTurnOn.pack();
            out.write(seqTurnOn);
            len = in.read(resp);
            bxResp = BxResp.parse(resp, len);
            if(bxResp.isAck()) {
                System.out.println("turn on, ok");
            }
            else {
                System.out.println("turn on, failed");
            }
			
			//校时
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

            /*
            //
            // 生成节目
            String str = "1234";
            BxArea area1 = new BxAreaDynamic((byte)0, (short)0, (short)0, (short)64, (short)32, str.getBytes("gb2312"));
            List<BxArea> pareas = new ArrayList<BxArea>();
            pareas.add(area1);

            // 节目文件
            BxFileProgram program = new BxFileProgram(0, pareas);
            byte[] pfile = program.build();
            */

            //
            // 清屏
            BxCmd cmdClearScreen = new BxCmdClearScreen();
            BxDataPack packClearScreen = new BxDataPack(cmdClearScreen);
            byte[] seqClearScreen = packClearScreen.pack();

            out.write(seqClearScreen);
            len = in.read(resp);
            bxResp = BxResp.parse(resp, len);
            if(bxResp.isAck()) {
                System.out.println("clear screen, ok");
            }
            else {
                System.out.println("clear screen, failed");
            }


            //
            out.close();
            in.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
