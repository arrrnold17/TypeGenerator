# XML to Java generator
### Introduction
A very simple generator that will analyze provided XML extensions and generate model Java classes corresponding to them.
For example, for the given input XML

```xml
<extension type="PythonStep">
    <name>Python step</name>
    <version>3.10</version>
    <command>pytest</command>
</extension>
```

This program have to generate Java file with a given content:

```java
class PythonStep {

  private String name;
  private String version;
  private String command;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }
}
```
### Usage
To run this program you only need to provide one argument - path to the XML file. 

Path to the directory where generated Java file is default: src/main/resources/