rm -rf target;

➜  javac -d target src/java/fr/fortytwo/printer/*/*.java

➜  cp -rf src/resources target


➜  jar cvfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .
➜  
➜
➜  java -jar target/images-to-chars-printer.jar --w=. --b=O
  

