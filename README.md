# DB_Best_internship_program

#### Hello everyone! This is a test task for DB Best Technologies.

##### The app runs several primary stages:

0) Starting app. 
1) Creating H2 DB and connecting.
1) Reading csv files.
2) Getting data from [1] and creating Nodes.
3) Uploading data from files into H2 DB.
3) Creating graph from Nodes [2].
4) Search route between the nodes into Graph.
5) Saving results in new result.csv file.
7) End of the app.

<br>

Let's describe the necessary steps in more detail.

1) Creating H2 Database. 

The application uses the embedded view of the Database. URL is "jdbc:h2:mem:".

2) Parsing of CSV files.

I was thinking to use the openxv library for parsing. But, in order to reduce the number of project dependencies (reduce the size of the project, increase the speed of parsing by reducing the amount of unnecessary functionality), a lightweight version of the parser was written.
For the sake of portability and flexibility, in case of substitution for another parser / mock, it is implemented from the interface.

As soon as the nodes are created, the relationships between them are added. The main object does not interact directly with the parser and the database. It interacts with the configurator. The configurator works with a parser and creates lists with nodes that it generates based on data from files.
```
List<Node> allNodes = configurator.getAllNodesWithDestinations();
```
3) Creating graph from Nodes and search route between two points.

The graph is formed by simply adding all the nodes from the listWithAllNodes to the graph structure.
[Graph realization](https://github.com/programmersnake/DB_Best_internship_program/blob/master/src/main/java/com/kostin/water_pipeline_system/model/Graph.java)
