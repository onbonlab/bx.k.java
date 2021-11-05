package bx.k;

import java.util.Calendar;
import java.util.Date;

public class BxCmdSystemClockCorrect extends BxCmd {
	private Date systemTime;
	
	protected int year;

    protected int month;

    protected int day;

    protected int hour;

    protected int minute;

    protected int second;

    protected byte week;
	
	public BxCmdSystemClockCorrect(Date time) {
        super(BxCmdCode.CMD_SYSTEM_CLOCK_CORRECT.group, BxCmdCode.CMD_SYSTEM_CLOCK_CORRECT.code);
        this.systemTime = time;

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH) + 1;
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                this.week = 1;
                break;
            case Calendar.TUESDAY:
                this.week = 2;
                break;
            case Calendar.WEDNESDAY:
                this.week = 3;
                break;
            case Calendar.THURSDAY:
                this.week = 4;
                break;
            case Calendar.FRIDAY:
                this.week = 5;
                break;
            case Calendar.SATURDAY:
                this.week = 6;
                break;
            case Calendar.SUNDAY:
                this.week = 7;
                break;
        }
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
        array.add((byte)0x00);
        array.add((byte)0x00);

        //BCD码：年（2）+月（1）+日（1）+时（1）+分（1）+秒（1）+星期（1）
    	byte[] contType = new byte[] {
                (byte) this.year,
                (byte) (this.year >>> 8),
                (byte) (this.year >>> 16),
                (byte) (this.year >>> 24) };
    	if(contType[0] == 0x00 && contType[1] == 0x00){
    		array.add(contType[2]);
    		array.add(contType[3]);
    	}
    	else{
    		array.add(contType[0]);
    		array.add(contType[1]);
    	}
    	//月
    	contType = new byte[] {
        (byte) this.month,
        (byte) (this.month >>> 8),
        (byte) (this.month >>> 16),
        (byte) (this.month >>> 24) };
    	array.add(contType[0]);
    	contType = new byte[] {
    	        (byte) this.day,
    	        (byte) (this.day >>> 8),
    	        (byte) (this.day >>> 16),
    	        (byte) (this.day >>> 24) };
    	array.add(contType[0]);
    	contType = new byte[] {
    	        (byte) this.hour,
    	        (byte) (this.hour >>> 8),
    	        (byte) (this.hour >>> 16),
    	        (byte) (this.hour >>> 24) };
    	array.add(contType[0]);
    	contType = new byte[] {
    	        (byte) this.minute,
    	        (byte) (this.minute >>> 8),
    	        (byte) (this.minute >>> 16),
    	        (byte) (this.minute >>> 24) };
    	array.add(contType[0]);
    	contType = new byte[] {
    	        (byte) this.second,
    	        (byte) (this.second >>> 8),
    	        (byte) (this.second >>> 16),
    	        (byte) (this.second >>> 24) };
    	array.add(contType[0]);
    	contType = new byte[] {
    	        (byte) this.week,
    	        (byte) (this.week >>> 8),
    	        (byte) (this.week >>> 16),
    	        (byte) (this.week >>> 24) };
    	array.add(contType[0]);

        return array.build();
    }
}
