import java.io.Serializable;

public class Timer implements Serializable {
    private int openHour;
    private int openMinute;
    private int closeHour;
    private int closeMinute;

    public Timer(int openHour, int openMinute, int closeHour, int closeMinute) {
        this.openHour = openHour;
        this.openMinute = openMinute;
        this.closeHour = closeHour;
        this.closeMinute = closeMinute;
    }

    public Timer() {
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getOpenMinute() {
        return openMinute;
    }

    public void setOpenMinute(int openMinute) {
        this.openMinute = openMinute;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }

    public int getCloseMinute() {
        return closeMinute;
    }

    public void setCloseMinute(int closeMinute) {
        this.closeMinute = closeMinute;
    }


    public byte[] getOpenBCD() {
        int bcdHour = ((openHour / 10) << 4) | (openHour % 10); // 将小时转换为BCD码
        int bcdMinute = ((openMinute / 10) << 4) | (openMinute % 10); // 将分钟转换为BCD码
        byte[] bcdTime = new byte[2]; // 创建一个长度为2的字节数组用于存储BCD码
        bcdTime[0] = (byte) bcdHour; // 将小时的BCD码存储在数组的第一个字节
        bcdTime[1] = (byte) bcdMinute; // 将分钟的BCD码存储在数组的第二个字节
        return bcdTime;
    }


    public byte[] getCloseBCD() {
        int bcdHour = ((closeHour / 10) << 4) | (closeHour % 10); // 将小时转换为BCD码
        int bcdMinute = ((closeMinute / 10) << 4) | (closeMinute % 10); // 将分钟转换为BCD码
        byte[] bcdTime = new byte[2]; // 创建一个长度为2的字节数组用于存储BCD码
        bcdTime[0] = (byte) bcdHour; // 将小时的BCD码存储在数组的第一个字节
        bcdTime[1] = (byte) bcdMinute; // 将分钟的BCD码存储在数组的第二个字节
        return bcdTime;
    }
}
