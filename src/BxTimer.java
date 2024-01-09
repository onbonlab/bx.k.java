
import java.util.ArrayList;
import java.util.List;

public class BxTimer extends BxCmd{


    private List<Timer> timers = new ArrayList<>();

    public BxTimer(List<Timer> timers) {

        super(BxCmdCode.CMD_TIMER.group, BxCmdCode.CMD_TIMER.code);
        this.timers = timers;
    }

    @Override
    public byte[] build() {
        BxByteArray array = new BxByteArray();

        //
        // group, code
        array.add(getGroup());
        array.add(getCmd());

        //
        // response or not
        array.add(getReqResp());

        //
        // 2 bytes reserved data
        // r0, r1
        array.add((byte)0x00);
        array.add((byte)0x00);
        array.add((byte) timers.size());
        for (Timer timer : timers) {
            array.add(timer.getOpenBCD());
            array.add(timer.getCloseBCD());
        }

        return array.build();
    }
}
