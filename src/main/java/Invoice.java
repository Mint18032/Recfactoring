import java.util.ArrayList;
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
    public String customer;
    public List<Performance> performances = new ArrayList<>();

    public Invoice() {
    }

    public Invoice(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public void addPerformance(Performance performance) {
        performances.add(performance);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
