# Báo cáo tái cấu trúc mã nguồn

---
### Thông tin nhóm:
1. Trần Ngọc Trúc Linh 20020113
2. Quách Ngọc Minh 20020261
---

Để thực hành tái cấu trúc mã nguồn, nhóm bọn em sử dụng 6 kỹ thuật tái cấu trúc khác nhau,
bao gồm:
- [Move method](#move-method)
- [Self encapsulate field](#encapsulate)
- [Extract method](#extract-method)
- [Replace Magic Number with Symbolic Constant](#replace-magic-number)
- [Consolidate Conditional Expression](#consolidate-conditional-expression)
- [Rename Method](#rename-method)

## Move method - Moving Features between Objects <a name="move-method"></a>
Phương thức `statement()` vốn là phương thức của `Invoice class` để tính toán và in ra báo cáo cho từng hoá đơn.
Tuy nhiên, `statement()` đang được để trong `Play class`, cần áp dụng
kỹ thuật **Move method** để chuyển phương thức `statement()` về `Invoice class`.
- `Invoice class` và `Play class` trước khi tái cấu trúc:
```java
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

```java
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
    volumeCredits += Math.floor(perf.audience * 1.0 / 5);
   }
   // print line for this order
   result += play.name + " " + perf.audience + " seats\n";
   totalAmount += thisAmount;
  }
  result += "Amount owed is " + totalAmount / 100 + "\n";
  result += "You earned " + volumeCredits + " " + "credits\n";
  return result;
 }
}
```
- `Invoice class` và `Play class` sau khi tái cấu trúc:
```java
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
                volumeCredits += Math.floor(perf.audience * 1.0 / 5);
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
```java
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
}
```

## Self encapsulate field - Organizing Data <a name="encapsulate"></a>
Các thuộc tính của các class đều đang để `public` và truy cập trực tiếp vào, tái cấu
trúc bằng cách chuyển về `private` và tạo các hàm `getter`, `setter`.
```java
public class Performance {
    public java.lang.String playID;
    public int audience;
    ...
}
// After Refactoring
public class Performance {
 private java.lang.String playID;
 private int audience;

 public int getAudience() { /* compiled code */ }

 public void setAudience(int audience) { /* compiled code */ }

 public java.lang.String getPlayID() { /* compiled code */ }

 public void setPlayID(java.lang.String playID) { /* compiled code */ }
}
``` 
```java
public class Play {
    public java.lang.String name;
    public java.lang.String type;
    ...
}
// After Refactoring
public class Play {
    private java.lang.String name;
    private java.lang.String type;
    ...
}
```
```java
public class Invoice {
    public java.lang.String customer;
    public java.util.List<Performance> performances;
}
// After Refactoring
public class Invoice {
    private java.lang.String customer;
    private java.util.List<Performance> performances;
}
```

## Extract method - Composing Methods <a name="extract-method"></a>
Trong phương thức `statement()`, để dễ hiểu, tiện sử dụng và đảm bảo 
mỗi phương thức chỉ thực hiện một công việc, ta tách riêng
phương thức tính `countAmount()` ra ngoài cho từng `Performance`.
- Đoạn code tính Amount trong phương thức `statement()` ban đầu:
```java 
public static String statement(Invoice invoice, HashMap<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.customer + "\n";
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.getPlayID());
            int thisAmount = 0;

            switch (play.getType()) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.getAudience() > 30) {
                        thisAmount += 1000 * (perf.getAudience() - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perf.getAudience() > 20) {
                        thisAmount += 10000 + 500 * (perf.getAudience() - 20);
                    }
                    thisAmount += 300 * perf.getAudience();
                    break;
                default:
                    throw new Exception("unknow type " + play.getType());
            }
            // add volume credits
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.getType()) {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }
            // print line for this order
            result += play.getName() + " " + perf.getAudience() + " seats\n";
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + totalAmount / 100 + "\n";
        result += "You earned " + volumeCredits +  " " + "credits\n";
        return result;
    }
```
- Phương thức `countAmount()` được tách riêng trong `Performance class`:
```java
public int countAmount(Performance p, Play play) throws Exception {
        int thisAmount = 0;
        if (play.getType() != "comedy" || play.getType() != "tragedy")
            throw new Exception("unknow type " + play.getType());
        thisAmount = play.getBaseAmount();
        if (p.getAudience() > play.getBaseAudience()) {
            thisAmount += play.getBaseBonus() + play.getBonusAmount() * (p.getAudience() - play.getBaseAudience());
        }
        thisAmount += play.getExtraAmount() * p.getAudience();
        return thisAmount;
    }
```
- Phương thức `statement()` mới:
```java
public static String statement(Invoice invoice, HashMap<String, Play> plays) throws Exception {
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
            totalAmount += perf.countAmount(perf,play); // use new countAmount() method 
        }
        result += "Amount owed is " + totalAmount / 100 + "\n";
        result += "You earned " + volumeCredits +  " " + "credits\n";
        return result;
    }
```
## Replace Magic Number with Symbolic Constant - Organizing Data <a name="replace-magic-number"></a>
Trong phương thức`statement()`để tính bill details, các con số cụ thể được
đưa vào trong các phép tính mà không có chú thích gì cả. Điều này khiến người đọc code
rất khó hiểu và việc sửa các số liệu sau này cũng sẽ rất lằng nhằng khi phải sửa
từng số trong phép tính. Giải pháp tái cấu trúc là tạo ra các thuộc tính
 mới là hằng số với tên gọi dễ hiểu.
```java
public class Play {
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
    ...
}
```
Vốn các hằng số là thuộc tính riêng của`Play class`nên các
hằng số cần dùng cho việc tính toán được thêm vào `Play class`. <br/>

Sử dụng các hàm `getter` để lấy giá trị các hằng số từ bên ngoài:
```java
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
```


## Consolidate Conditional Expression
Để dễ hiểu và có thể sử dụng lại trong phương thức nào đó được phát triển sau, trong phương thức `countAmount()`,
ta chuyển mệnh đề trong hàm if thành một phương thức riêng với mục đích xác minh kiểu `type` thuộc `Play class`.
- Phương thức trước khi chuyển đổi:
```java
public class Performance {
 public int countAmount(Performance p, Play play) throws Exception {
  int thisAmount = 0;
  if (play.getType() != "comedy" && play.getType() != "tragedy") {
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
```

- Sau khi thay đổi mệnh đề if:
```java
public class Performance {
 public int countAmount(Performance p, Play play) throws Exception {
  int thisAmount = 0;
  if (!play.isValidType()) {
   throw new Exception("unknown type " + play.getType());
  }
  ...
 }
}
```

- Đồng thời, thêm phương thức `isValidType()` vào `Play class`:
```java
public class Play {
 public boolean isValidType() {
  return this.type.equals("tragedy") || this.type.equals("comedy");
 }  
}
```

## Rename Method
Tên phương thức `statement()` trong `Invoice class` chưa được rõ ràng, 
chưa thể hiện được công dụng của phương thức. 
Cần đổi tên phương thức này thành `getCustomerInfo()`.

Giờ đây, người đọc sẽ dễ hiểu phương thức này hơn:
```java
    public static String getCustomerInfo(Invoice invoice, HashMap<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.customer + "\n";
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.getPlayID());
            // add volume credits
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.getType()) {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }
            // print line for this order
            result += play.getName() + " " + perf.getAudience() + " seats\n";
            totalAmount += perf.countAmount(perf,play);
        }
        result += "Amount owed is " + totalAmount / 100 + "\n";
        result += "You earned " + volumeCredits +  " " + "credits\n";
        return result;
    }
```