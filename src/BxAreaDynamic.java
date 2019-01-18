public class BxAreaDynamic extends BxArea {

    private static final byte TYPE = 0x00;


    //
    // id
    // 动态区域编号
    // 注意：该参数只对动态区有效，其他区域为默认
    // 值，动态区必须统一编号，编号从 0 开始递增。
    private byte id = 0x00;

    //
    // 行间距
    private byte lineSpace = 0x00;

    //
    // 动态区运行模式
    //0—动态区数据循环显示。
    //1—动态区数据显示完成后静止显示最后一页数
    //据。
    //2—动态区数据循环显示，超过设定时间后数据仍
    //未更新时不再显示
    //3—动态区数据循环显示，超过设定时间后数据仍
    //未更新时显示 Logo 信息,Logo 信息即为动态区域
    //的最后一页信息
    //4—动态区数据顺序显示，显示完最后一页后就不
    //再显示
    //5—动态区数据顺序显示，超过设定次数后数据仍
    //未更新时不再显示
    private byte runMode = 0x00;

    //
    // 动 态 区 数 据 超 时 时 间 ， 单 位 为 秒 / 次 数 （ 若
    // RunMode=5，则表示更新次数）
    private short timeout = 5;

    //
    // 是否使能语音播放
    //0 表示不使能语音
    //1 表示播放下文中 Data 部分内容
    //2 表示播放下文中 SoundData 部分内容
    private byte soundMode = 0x00;

    //
    // extend para len
    private byte extendParaLen = 0x00;

    //
    // type setting
    private byte typeSetting = 0x00;

    //
    // text alignment
    private byte alignment = 0x00;

    //
    // single line
    private byte singleLine = 0x02;

    //
    // 是否自动换行
    // 是否自动换行
    // 0x01——不自动换行，显示数据在换行时必须插入
    // 换行符
    // 0x02——自动换行，显示内容不需要换行符，但是
    // 只能使用统一的中文字体和英文字体
    private byte autoNewLine = 0x01;

    //
    // 显示方式
    // 0x01——静止显示
    //0x02——快速打出
    //0x03——向左移动
    //0x04——向右移动
    //0x05——向上移动
    //0x06——向下移动
    private byte dispMode = 0x02;

    //
    private byte exitMode = 0x00;

    //
    // speed
    private byte speed = 0x00;

    //
    // hold time
    private byte holdTime = 0x08;

    //
    // data
    private byte[] data;


    public BxAreaDynamic(byte id, short x, short y, short w, short h, byte[] data) {
        super(TYPE, x, y, w, h);
        this.id = id;
        this.data = data;
    }


    @Override
    public byte[] build() {

        BxByteArray array = new BxByteArray();

        // 区域类型
        array.add(TYPE);

        // x, y, w, h
        array.add(getX());
        array.add(getY());
        array.add(getW());
        array.add(getH());

        //
        // 动态区编号
        array.add(id);

        //
        // 行间距
        array.add(lineSpace);

        //
        // 运行模式
        array.add(runMode);

        //
        // timeout
        array.add(timeout);

        //
        // sound mode
        array.add(soundMode);

        //
        // extendParaLen
        array.add(extendParaLen);

        //
        // type setting
        array.add(typeSetting);

        //
        // alignment
        array.add(alignment);

        //
        // single line
        array.add(singleLine);

        //
        // new line
        array.add(autoNewLine);

        //
        // display mode
        array.add(dispMode);
        array.add(exitMode);
        array.add(speed);
        array.add(holdTime);

        //
        int dataLen = data.length;
        array.add(dataLen);;

        //
        array.add(data);

        return array.build();
    }
}
