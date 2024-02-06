rm -rf target;

➜  javac -d target src/java/fr/fortytwo/printer/*/*.java
➜  java -cp target fr.fortytwo.printer.app.Program --w=. --b=O $HOME/Desktop/42-javaModules/java04/ex00/it.bmp
