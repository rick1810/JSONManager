# JSONManager

JSONManager is a Java based JSON Manager.<br/>
<br/>
With support for parsing custom classes<br/>
& Threaded parsing & stringifying.<br/>
<br/>
<br/>

## Maven

> [!NOTE]
> Replace `version` with the latest version
```xml
<dependency>
  <groupId>com.dutch_computer_technology</groupId>
  <artifactId>jsonmanager</artifactId>
  <version>x.x.x</version>
</dependency>
```

## Jar download

You can download any stable or newest version in [/releases](https://github.com/rick1810/JSONManager/releases)<br/>
<br/>
<br/>

## Settings

### Syntax

Add suffix's behind Value's when stringified,<br/>
Disabled by default.

> [!NOTE]
> Using `stringify(syntax)` `toString(syntax)` `toBytes(syntax)`<br/>
> For when you don't want to use the global settings

- `suffix()` : Returns true/false for global suffix
- `suffix(true/false)` : Set global suffix to true/false

### Tabs

Add tabs when stringified,<br/>
Disabled by default.

> [!NOTE]
> Using `stringify(syntax, tabs)` `toString(syntax, tabs)` `toBytes(syntax, tabs)`<br/>
> For when you don't want to use the global settings

- `beautifyTabs()` : Returns true/false for global tabs
- `beautifyTabs(true/false)` : Set global tabs to true/false

<br/>
<br/>

## Custom Classes support

JSONManager can parse/stringify custom classes.
> [!NOTE]
> When a custom class does not have a `toJSON()`, `toString()` is used instead

> [!WARNING]
> If a `toJSON()` is present, the `__class` key is set/overwritten to the className

### Convert to JSON

Called when stringifying a JSONObject.
```java
public class Car {
  public JSONObject toJSON() {
    <!-- You're code here -->
  };
};
```

### Convert from JSON

Called when parsing a JSONObject.
```java
public class Car {
  public Car(JSONObject json) {
    <!-- You're code here -->
  };
};
```

<br/>

## Example

```java
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
