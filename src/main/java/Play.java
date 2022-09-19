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

    public String name;
    public String type;

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void main(String[] args) {
        System.out.println("JSON:");
        Play hamlet = new Play("Hamlet", "tragedy");
        Play as_like = new Play("As You Like It", "comedy");
        Play othello = new Play("othello", "comedy");
        HashMap<String, Play> plays = new HashMap<>();

        plays.put("Hamlet", hamlet);
        plays.put("as_like", as_like);
        plays.put("othello", othello);
        String json = new Gson().toJson(plays, plays.getClass());
        System.out.println(json);

        List<Performance> performances = new ArrayList<>();
        performances.add(new Performance("Hamlet", 55));
        performances.add(new Performance("as_like", 35));
        performances.add(new Performance("othello", 40));
        Invoice invoice = new Invoice("BigCo", performances);
        String json2 = new Gson().toJson(invoice, Invoice.class);
        System.out.println(json2);

        try {
            String result = Invoice.statement(invoice, plays);
            System.out.println("Result:\n" + result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
