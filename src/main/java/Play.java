/*
Tran Ngoc Truc Linh 20020113
Quach Ngoc Minh 20020261
 */
import com.google.gson.Gson;

import java.util.*;

public class Play {
    /*plays.json...
    {
        "hamlet": {"name": "Hamlet", "type": "tragedy"},
        "as-like": {"name": "As You Like It", "type": "comedy"},
        "othello": {"name": "Othello", "type": "tragedy"},
    }
    */

    private static final int BASE_AMOUNT_COMEDY = 30000;
    private static final int BASE_AUDIENCE_COMEDY = 20;
    private static final int BASE_AMOUNT_TRAGEDY = 40000;
    private static final int BASE_AUDIENCE_TRAGEDY = 30;
    private static final int BASE_BONUS_COMEDY = 10000;
    private static final int BASE_BONUS_TRAGEDY = 0;
    private static final int BONUS_AMOUNT_TRAGEDY = 1000;
    private static final int BONUS_AMOUNT_COMEDY = 500;
    private static final int EXTRA_AMOUNT_COMEDY = 300;
    private static final int EXTRA_AMOUNT_TRAGEDY = 0;

    private String name;
    private String type;

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getBaseAmount() {
        if (this.type == "tragedy") {
            return BASE_AMOUNT_TRAGEDY;
        }
        return BASE_AMOUNT_COMEDY;
    }

    public int getBaseAudience() {
        if (this.type == "tragedy") {
            return BASE_AUDIENCE_TRAGEDY;
        }
        return BASE_AUDIENCE_COMEDY;
    }
    public int getBaseBonus() {
        if (this.type == "tragedy") {
            return BASE_BONUS_TRAGEDY;
        }
        return BASE_BONUS_COMEDY;
    }
    public int getBonusAmount() {
        if (this.type == "tragedy") {
            return BONUS_AMOUNT_TRAGEDY;
        }
        return BONUS_AMOUNT_COMEDY;
    }

    public int getExtraAmount() {
        if (this.type == "tragedy") {
            return EXTRA_AMOUNT_TRAGEDY;
        }
        return EXTRA_AMOUNT_COMEDY;
    }
}
