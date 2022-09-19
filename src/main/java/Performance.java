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

    public String getPlayID() {
        return playID;
    }

    public int countAmount(Performance p, Play play) throws Exception {
        int thisAmount = 0;
        if (!play.isValidType()) {
            throw new Exception("unknown type " + play.getType());
        }
        thisAmount = play.getBaseAmount();
        if (p.getAudience() > play.getBaseAudience()) {
            thisAmount += play.getBaseBonus() + play.getBonusAmount() * (p.getAudience() - play.getBaseAudience());
        }
        thisAmount += play.getExtraAmount() * p.getAudience();
        return thisAmount;
    }
}
