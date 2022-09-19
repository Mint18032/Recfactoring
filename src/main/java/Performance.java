public class Performance {
    private String playID;
    private int audience;

    public Performance() {
    }
    public Performance(String playID, int audiences) {
        this.playID = playID;
        this.audience = audiences;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public String getPlayID() {
        return playID;
    }

    public void setPlayID(String playID) {
        this.playID = playID;
    }
}
