Glydar
==========

An open source Java implementation of Cube World's server in PVP and PVE versions.

**Status:** 
Clients will connect (if it's the correct version) and be dropped into a world, be welcomed, and entity updates can be sent.
PVP Server - has pvp working in basic form (No Npcs etc) (29/10/2014)
PVE Server - all working just like the Orignal Cubeworld Server including NPC's(29/10/2014)


Building and running
-----
If you are using *Maven* from the commandline, simply run `mvn clean package`. The jar will be in the "target" folder.

If you are using *Eclipse* first install the m2e (Maven to Eclipse) plugin then:

1. Import the project from File -> Import -> Project from Git.
2. When asked, import as a general project.
3. Right click on the project, select Configure -> Convert to a Maven Project.
4. Have fun :)

If you are using *IntelliJ* or any other Maven-enabled IDE, the project should work out of the box.

Special Thanks
-----
Mat^2 - structures and data (https://github.com/matpow2/cuwo)
Uwee - packet 13 variables

---
and The original Glydar Dev Team.


