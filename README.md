# Urbanism Generator

- Author: Mujtaba Zaidi

## How to install?
## NOTE: TESTS COMPILE/EXECUTE WHEN THE BELOW COMMAND IS CALLED. THEY DO NOT WORK SEPARATELY - I.E. YOU CANNOT CALL TESTSUITE SEPARATELY.

```
user A4 % mvn clean install
```

It creates three jars:

1. `generator/generator.jar` to generate meshes
2. `island/island.jar` to generate islands
3. `visualizer/visualizer.jar` to visualize such meshes as SVG files

### Generate an irregular mesh

```
user A4 % java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -s 20 -o input.mesh
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Visualizing an island, (sandbox mode)

```
user A4 % java -jar island/island.jar -i input.mesh -o lagoon.mesh -mode lagoon 
```

### Visualizing an island with various parameters, (island mode). Examples of execution.

```
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s circle -b arctic -e hills -l 11 -a 5 -r 11 -soil dry -c 5
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s rectangle -b arctic -e flatland -l 11 -a 5 -r 11 -soil wet -c 20
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s circle -b desert -e hills -l 11 -a 5 -r 11 -soil dry -c 10
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s circle -b arctic -e volcano -l 11 -a 5 -r 11 -soil dry -c 15
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s rectangle -b desert -e flatland -l 11 -a 5 -r 11 -soil dry -c 25
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s circle -b tropical -e hills -l 11 -a 5 -r 11 -soil wet -c 30
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -s rectangle -b tropical -e flatland -l 11 -a 5 -r 11 -soil wet -c 35
```

### Command line arguments that can be used with java -jar island/island.jar (not for sandbox mode)

```
-i        <arg>      Input mesh filename
-o        <arg>      Output mesh filename
-s        <arg>      Shape (circle, rectangle)
-b        <arg>      Biome type (arctic, desert, circle)
-e        <arg>      Elevation (volcano, hills, flatland)
-l        <arg>      Number of lakes
-a        <arg>      Number of aquifiers
-r        <arg>      Number of rivers
-soil     <arg>      Type of soil (wet, dry)
-seed     <arg>      Seed to regenerate island
-c        <arg>      Number of cities
```

Cities are represented as yellow dots, with the hub city represented by a red dot. Roads are black segments. The size of
the dots are representative of the relative sizes of the cities.

### Seed regeneration. Each time an island is produced (not including sandbox mode), it generates a seed which the user can call to regenerate the exact same island. In the example below, the seed of the island to be regenerated is 232258.

```
user A4 % java -jar island/island.jar -i input.mesh -o island.mesh -seed 232258
```

### Command to run visualizer subproject once island mesh is generated:
```
java -jar visualizer/visualizer.jar -i island.mesh -o island.svg
```
