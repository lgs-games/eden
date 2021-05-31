# Generation

### Generate JRE (Windows only)

`Run 'jlink' gradle task`.

### Generate JAR

``Run 'make jar' gradle task``.

### Generate exe

```bash
jpackage --name eden --type exe --input eden --dest eden\dist --main-jar eden.jar --icon docs\icon.ico --java-options -Dfile.encoding=UTF-8 --runtime-image eden\myjre --vendor "Legendary Games Studio" --app-version 1.0.0 --description "eden" --win-shortcut --win-menu
```

To write a linux version of this command, you should

* replace `\ `
* add quotes ?
* add linux options
* <https://openjdk.java.net/jeps/343>

### Generate installer

* Execute the jpackage installer and install eden.
* Copy the exe files in ``/eden/exe`` (project root)
* Install [InnoSetup](https://jrsoftware.org/isdl.php)
* Open ``eden.iss`` and compile
* done