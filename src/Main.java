import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        byte [] data = {0x30, 0x31, 0x32, 0x33};

        byte id = 0x00;
        short x = 0;
        short y = 0;
        short w = 32;
        short h = 16;

        BxArea area = new BxAreaDynamic(id, x, y, w, h, data);

        List<BxArea> areas = new ArrayList<BxArea>();
        areas.add(area);

        BxCmd cmd = new BxCmdSendDynamicArea(areas);
        byte[] seq = cmd.build();

        //
        // 创建 Socket
        Socket client = new Socket();

        //
        // 创建 socket 地址
        SocketAddress address = new InetSocketAddress("192.168.88.168", 5005);

        //
        // 建立 TCP 连接
        try {
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


            out.write(seq);

            byte[] resp = new byte[1024];
            in.read(resp);

            out.close();
            in.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
