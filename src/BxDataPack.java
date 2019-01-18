/**
 *
 */
public class BxDataPack {

    private static final int WRAP_A5_NUM = 8;
    private static final int WRAP_5A_NUM = 1;


    // 目标地址
    public short dstAddr = (short) 0xfffe;

    //
    // 源地址
    public short srcAddr = (short) 0x8000;

    //
    // 保留字
    public byte r0 = 0x00;
    public byte r1 = 0x00;
    public byte r2 = 0x00;

    //
    // option
    // 不发送 barcode
    public byte option = 0x00;

    //
    // crc 模式
    // 默认无校验
    public byte crcMode = 0x02;

    //
    // 显示模式
    public byte dispMode;

    //
    // 设备类型
    public byte deviceType = (byte) 0xfe;

    //
    // 协议版本号
    public byte version = 0x02;

    //
    // 数据域长度
    public short dataLen;

    //
    // 数据
    public byte[] data;

    //
    // crc
    public short crc;

    public BxDataPack(byte[] data) {
        this.data = data;
        this.dataLen = (short) data.length;
    }


    /**
     * 对数据进行转义
     * @param src
     * @return
     */
    private static byte[] wrap(byte[] src) {


        int len = 0;

        len = src.length;

        for(byte d : src) {
            if((d == (byte)0xa5) || (d == (byte)0x5a) || (d == (byte)0xa6) || (d == (byte)0x5b)) {
                len++;
            }
        }

        //
        // 加上帧头和帧尾的A5,5A
        //len += 2;
        len += WRAP_5A_NUM;
        len += WRAP_A5_NUM;


        //
        // 开始转义

        byte[] dst;
        dst = new byte[len];

        int offset = 0;

        //
        // 帧头
        for(int i=0; i<WRAP_A5_NUM; i++){
            dst[offset++] = (byte) 0xa5;
        }


        for(byte data : src) {
            if(data == (byte)0xa5) {
                dst[offset++] = (byte) 0xa6;
                dst[offset++] = 0x02;
            }
            else if(data == (byte)0xa6) {
                dst[offset++] = (byte) 0xa6;
                dst[offset++] = 0x01;
            }
            else if(data == 0x5a) {
                dst[offset++] = 0x5b;
                dst[offset++] = 0x02;
            }
            else if(data == 0x5b) {
                dst[offset++] = 0x5b;
                dst[offset++] = 0x01;
            }
            else{
                dst[offset++] = data;
            }
        }

        // 帧尾
        for(int i=0; i<WRAP_5A_NUM; i++){
            dst[offset++] = 0x5a;
        }

        //
        return dst;
    }


    /**
     * 对数据进行封装，生成字节流
     * @return  数据帧的字节流
     */
    public byte[] pack() {

        BxByteArray bytes = new BxByteArray();

        //
        // 目标地址
        bytes.add(dstAddr, BxByteArray.Endian.LITTLE);

        //
        // 源地址
        bytes.add(srcAddr, BxByteArray.Endian.LITTLE);

        //
        // 保留字
        bytes.add(r0);
        bytes.add(r1);
        bytes.add(r2);

        //
        // option
        bytes.add(option);

        //
        // crc mode
        bytes.add(crcMode);

        //
        bytes.add(dispMode);

        //
        bytes.add(deviceType);

        //
        bytes.add(version);

        //
        bytes.add(dataLen);

        //
        // 数据域
        bytes.add(data);

        //
        // crc
        bytes.add(crc, BxByteArray.Endian.LITTLE);

        //
        // 进行转义
        byte[] result = wrap(bytes.build());

        //
        return result;

    }
}
