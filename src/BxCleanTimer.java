public class BxCleanTimer extends BxCmd{

    public BxCleanTimer() {
        super(BxCmdCode.CMD_CLEAN_TIMER.group, BxCmdCode.CMD_CLEAN_TIMER.code);
    }

    @Override
    public byte[] build() {
        //
        BxByteArray array = new BxByteArray();

        // cmd
        //
        // cmd group
        array.add(getGroup());
        array.add(getCmd());

        //
        // response or not
        array.add(getReqResp());

        //
        // r0, r1
        array.add((byte)0x03);
        array.add((byte)0x04);

        return array.build();
    }
}
