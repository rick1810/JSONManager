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

### JSONConfig

Use when not wanting to use the global settings,<br/>
Using `new JSONConfig()` to create a new config.<br/>
This will copy the current settings from JSONUtils.

### Threaded

Parse &amp; Stringify using Threads,<br/>
instead of single core.<br/>
Disabled by default.

> [!NOTE]
> Using `stringify(config)` `toString(config)` `toBytes(config)`<br/>
> `new JSONObject(config)` `new JSONArray(config)`<br/>
> For when you don't want to use the global settings

> [!WARNING]
> MultiThreading could be a bit slower for small JSON files,<br/>
> It's recommended to keep this off for smaller files.</br>
> And to use it for big long ones.

- `JSONUtils.threaded()` : Returns true/false for threaded suffix
- `JSONUtils.threaded(true/false)` : Set global threaded to true/false
- `new JSONConfig().threaded()` : Returns true/false for threaded
- `new JSONConfig().threaded(true/false)` : Set threaded to true/false

### Syntax

Add suffix's behind Value's when stringified,<br/>
Disabled by default.

> [!NOTE]
> Using `stringify(config)` `toString(config)` `toBytes(config)`<br/>
> For when you don't want to use the global settings

- `JSONUtils.suffix()` : Returns true/false for global suffix
- `JSONUtils.suffix(true/false)` : Set global suffix to true/false
- `new JSONConfig().suffix()` : Returns true/false for suffix
- `new JSONConfig().suffix(true/false)` : Set suffix to true/false

### Tabs

Add tabs when stringified,<br/>
Disabled by default.

> [!NOTE]
> Using `stringify(config)` `toString(config)` `toBytes(config)`<br/>
> For when you don't want to use the global settings

- `JSONUtils.tabs()` : Returns true/false for global tabs
- `JSONUtils.tabs(true/false)` : Set global tabs to true/false
- `new JSONConfig().tabs()` : Returns true/false for tabs
- `new JSONConfig().tabs(true/false)` : Set tabs to true/false

### ClassName

Add className when stringifying a class using toJSON(),<br/>
Enabled by default.

> [!NOTE]
> Using `stringify(config)` `toString(config)` `toBytes(config)`<br/>
> For when you don't want to use the global settings

- `JSONUtils.className()` : Returns true/false for global className
- `JSONUtils.className(true/false)` : Set global className to true/false
- `new JSONConfig().className()` : Returns true/false for className
- `new JSONConfig().className(true/false)` : Set className to true/false

<br/>
<br/>

## Custom Classes support

JSONManager can parse/stringify custom classes.
> [!NOTE]
> When a custom class does not have a `toJSON()`, `toString()` is used instead.<br/>
> className can be Disabled in the config, to prevent auto parsing

> [!WARNING]
> If a `toJSON()` is present & className is enabled in the config,<br/>
> the `__class` key is set/overwritten to the className

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
JSONUtils.tabs(true);
System.out.println(jsonD.stringify()); // {
                                            "BigNumber":5L
                                          }
System.out.println(jsonD.stringify(false, true)); // {
                                                       "BigNumber":5
                                                     }
System.out.println(jsonD.stringify(true, false)); // {"BigNumber":5L}
System.out.println(jsonD.stringify(false, false)); // {"BigNumber":5}

public class Car {
  private String brand;
  private String model;
  private int seats;
  public Car(String brand, String model, int seats) {
    this.brand = brand;
    this.model = model;
    this.seats = seats;
  };
  public Car(JSONObject json) {
    this.brand = json.getString("brand");
    this.model = json.getString("model");
    this.seats = json.getInt("seats");
  };
  public String getBrand() {
    return this.brand;
  };
  public String getModel() {
    return this.model;
  };
  public int getSeats() {
    return this.seats;
  };
  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("brand", this.brand);
    json.put("model", this.model);
    json.put("seats", this.seats);
    return json;
  };
};

JSONUtils.suffix(true);
JSONUtils.tabs(false);

JSONObject jsonE = new JSONObject();
jsonE.put("myCar", new Car("Fiat", "Panda", 5));
String carJsonStringified = jsonE.stringify();
System.out.println(carJsonStringified); // {"car1":{"brand":"Fiat","model":"Panda","seats":5I,"__class":"github.JSONManager.Car"}}

JSONObject jsonF = new JSONObject(carJsonStringified);

Car myCar = null;
if (jsonF.isValue("myCar", Car.class)) { //Check if it's a Car class
  myCar = (Car) jsonF.get("myCar");
} else if (jsonF.isJSONObject("myCar")) { //Check if it's a JSONObject
  myCar = new Car(jsonF.getJSONObject("myCar"));
};

if (myCar != null) {
  System.out.println("I found my car, it's a " + myCar.getBrand() + " " + myCar.getModel() + ", With " + myCar.getSeats() + " seats!");
} else {
  System.out.println("I could't find my car :(");
};
```
