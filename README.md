webservices-template-scala: abandoned
==========================

This is a Webservices-Scala bootstrap. Everything
is keep very simple. The implementation of the
methods are not for the real world. This bootstrap
prevent not to learn programming scala.


These are the steps to get this template

Create GitHub Reop
- Initialize this repository with a README
- Add .gitignore => choose maven

Project anlegen:
mvn archetype:create -DgroupId=de.case.webservices.template -DartifactId=webservice-template-scala -Dversion=0.0.1-SNAPSHOT

Git Repo hinzufügen
cd webservice-template-scala
git init
git remote add origin git@github.hc.ag:slauer/webservice-template-scala.git
git pull origin master

Delete Maven default App.java and AppTest.java
find . -name App*.java | xargs rm

Add and commit pom.xml to git

Edit .gitignore add some more files
*.iml
.idea
._.DS_Store
.DS_Store
.project
.classpath
.metadata
mem.*
.settings
pom.releaseBackup
release.properties

Open Project in IntelliJ
- choose pom.xml in open dialog
- Register project Git root in IntelliJ

change folder:
src/main/java to src/main/scala
src/test/java to src/main/scala


Open pom.xml
- configure pom => see commits in slauer/webservice-template-scala.git


Reopen Project using pom.xml to activate scala nature in IDE

Create TemplateService:
- Create a ScalaClass
- Implement a getMethod

Deploy Service to Tomcat
