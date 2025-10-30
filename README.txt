Download java 14
https://www.oracle.com/ca-en/java/technologies/javase/jdk14-archive-downloads.html


--=============
Version Java dans Intelli-J :
Project structure... - Project Settings - Project → SDK: Java 14 et Project language level aussi
Project structure... - Project Settings -Modules → Dependencies: MModule SDK matches the Project SDK 14

Optional: Check compiler settings
File → Settings → Build, Execution, Deployment → Compiler → Java Compiler
For your module, set Target bytecode version to 14 (ou same as language level)
--=================
Changer Environment system variable (ask CHATGPT)



FACULTATIF?

1) Renomme le fichier Client.java
2) Compile dans le terminal : javac Client.java
	=> Client.class
3) Creer un serveur (en attendant le vrai serveur)

4) Compiler: javac Server.java
5) Lancer le serveur: java Server
6) Lancer le client: java Client