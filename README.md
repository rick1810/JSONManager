# JSONManager

JSONManager is a simple Java based JSON-Manager.<br/>
<br/>
It allows a JSON String to be parsed / stringified.<br/>

## Jar download

You can download any stable or newest version in [/releases](https://github.com/rick1810/JSONManager/releases)

## Usage

```
JSONObject jsonA = new JSONObject();
System.out.println(jsonA.toString()); // {}

String str = "{\"A"\:\"B\"}";
JSONObject jsonB = new JSONObject(str);
System.out.println(jsonB.toString()); // {"A":"B"}

JSONObject jsonC = new JSONObject(jsonB);
jsonC.put("C", "D");
System.out.println(jsonC.toString()); // {"A":"B","C":"D"}

JSONObject jsonD = new JSONObject();
long l = 5;
jsonD.put("BigNumber", l);
System.out.println(jsonD.stringify()); // {"BigNumber":5}
JSONUtils.suffix(true);
System.out.println(jsonD.toString()); // {"BigNumber":5L}
System.out.println(jsonD.stringify()); // {"BigNumber":5L}
JSONUtils.beautifyTabs(true);
System.out.println(jsonD.stringify()); // {
                                            "BigNumber":5L
                                          }
System.out.println(jsonD.stringify(false, true)); // {
                                                       "BigNumber":5
                                                     }
System.out.println(jsonD.stringify(true, false)); // {"BigNumber":5L}
System.out.println(jsonD.stringify(false, false)); // {"BigNumber":5}

public class Car {
  private int model;
  public Car(int model) {
    this.model = model;
  };
  public Car(JSONObject json) {
    this.model = json.getInt("model");
  };
  public int getModel() {
    return this.model;
  };
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("model", this.model);
    return json;
  };
};

JSONUtils.suffix(true);
JSONUtils.beautifyTabs(false);

JSONObject jsonE = new JSONObject();
jsonE.put("car1", new Car(2));
String carJsonStringified = jsonE.stringify();
System.out.println(carJsonStringified); // {"car1":{"model":2I,"__class":"github.JSONManager.Car"}}

JSONObject jsonF = new JSONObject(carJsonStringified);

Car myCar = null;
if (jsonF.isValue("car1", Car.class)) { //Check if it's a Car class
  myCar = (Car) jsonF.get("car1");
} else if (jsonF.isJSONObject("car1")) { //Check if it's a JSONObject
  myCar = new Car(jsonF.getJSONObject("car1"));
};

if (myCar != null) {
  System.out.println("I found my car!, it's a model " + myCar.getModel());
} else {
  System.out.println("I could't find my car :(");
};
```
