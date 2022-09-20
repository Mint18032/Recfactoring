import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Invoice {
    /*
    {
      "customer": "BigCo",
      "performances": [
          {"playID": "Hamlet", "audience": 55},
          {"playID": "as-like", "audience": 35},
          {"playID": "othello", "audience": 40},
      ]
  }
     */
    private String customer;
    private List<Performance> performances = new ArrayList<>();

    public Invoice() {
    }

    public Invoice(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public static String getBillDetails(Invoice invoice, HashMap<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.customer + "\n";
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.getPlayID());
            // add volume credits
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.getType()) {
                volumeCredits += Math.floor(perf.getAudience() * 1.0 / 5);
            }
            // print line for this order
            result += play.getName() + " " + perf.getAudience() + " seats\n";
            totalAmount += perf.countAmount(perf,play);
        }
        result += "Amount owed is " + totalAmount / 100 + "\n";
        result += "You earned " + volumeCredits +  " " + "credits\n";
        return result;
    }
}
