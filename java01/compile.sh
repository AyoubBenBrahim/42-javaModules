#!/bin/bash

if [ $# -eq 0 ]; then
    exec  echo "Please provide the project folder as an argument."
fi

cd "$1" || exit 1

#Find the name of the main class by looking for a file with a main() method
MAIN_FILE=$(find . -name "*.java" -print0 | xargs -0 grep -l 'public static void main' | head -n 1)
MAIN_CLASS=$(basename "$MAIN_FILE" .java)

#Extract the package name, if present
PACKAGE_NAME=$(grep -o -m 1 'package [a-zA-Z0-9_.]*;' "$MAIN_FILE" | cut -d' ' -f2 | sed 's/;//')

#Compile all .java files in the current directory and subdirectories
if [ -z "$PACKAGE_NAME" ]; then
    javac "$MAIN_FILE"
else
    javac -d . $(find . -name "*.java" ! -name "$MAIN_CLASS.java" -print) "$MAIN_FILE"
fi

#Run the main class
if [ -z "$PACKAGE_NAME" ]; then
    java "$MAIN_CLASS"
else
    java -cp . "$PACKAGE_NAME.$MAIN_CLASS"
fi




#Delete the generated class files
if [ -z "$PACKAGE_NAME" ]; then 
    /bin/rm  "$MAIN_CLASS.class"
else
    /bin/rm -rf "$PACKAGE_NAME"
fi

if [ -d $(echo $PACKAGE_NAME | cut -d'.' -f1) ]; then
    /bin/rm -rf $(echo $PACKAGE_NAME | cut -d'.' -f1)
fi