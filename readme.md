Trần Ngọc Trúc Linh 20020113
Quách Ngọc Minh 20020261

# Báo cáo tái cấu trúc mã nguồn
## Move method - Moving Features between Objects
Phương thức `statement()` vốn là phương thức của `Invoice class` để tính toán và in ra báo cáo cho từng hoá đơn.
Tuy nhiên, `statement()` đang được để trong `Play class`, cần áp dụng
kỹ thuật **Move method** để chuyển phương thức `statement()` về `Invoice class`.
- `Invoice class` và `Play class` trước khi tái cấu trúc:
```
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
```

```
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

    public static String statement(Invoice invoice, HashMap<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.customer + "\n";
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            int thisAmount = 0;

            switch (play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20);
                    }
                    thisAmount += 300 * perf.audience;
                    break;
                default:
                    throw new Exception("unknow type " + play.type);
            }
            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.type) {
                volumeCredits += Math.floor(perf.audience / 5);
            }
            // print line for this order
            result += play.name + " " + perf.audience + " seats\n";
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + totalAmount / 100 + "\n";
        result += "You earned " + volumeCredits +  " " + "credits\n";
        return result;
    }
```
- `Invoice class` và `Play class` sau khi tái cấu trúc:
```
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

    public static String statement(Invoice invoice, HashMap<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.customer + "\n";
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            int thisAmount = 0;

            switch (play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20);
                    }
                    thisAmount += 300 * perf.audience;
                    break;
                default:
                    throw new Exception("unknow type " + play.type);
            }
            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.type) {
                volumeCredits += Math.floor(perf.audience / 5);
            }
            // print line for this order
            result += play.name + " " + perf.audience + " seats\n";
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + totalAmount / 100 + "\n";
        result += "You earned " + volumeCredits +  " " + "credits\n";
        return result;
    }
}
```
```
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
```
