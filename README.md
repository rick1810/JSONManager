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
System.out.println(jsonD.toString()); // {"BigNumber":5}
JSONUtils.suffix(true);
System.out.println(jsonD.toString()); // {"BigNumber":5L}
JSONUtils.beautifyTab(1);
System.out.println(jsonD.toString()); // {
                                            "BigNumber":5L
                                          }
```
