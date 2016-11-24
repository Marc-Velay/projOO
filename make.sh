#! /bin/sh

javac -cp ".;sqlite-jdbc-3.15.1.jar" Library.java
java -cp "`pwd`:sqlite-jdbc-3.15.1.jar" Library