package com.kostin.water_pipeline_system.parser_and_configurator;

import com.kostin.water_pipeline_system.model.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configurator implements ConfiguratorInterface {

    private Parser parser;
    private List<Node> nodeListWithAllNodes;
    private List<Node[]> nodeListToSearch;
    private String[] urlsToFiles = new String[] {
            this.getClass().getClassLoader().getResource( "csv/CSV_FileDescribeTheWaterPipelineSystem.csv" ).getFile().replaceFirst( "/C", "C" ).replaceAll( "%20", " " ),
            this.getClass().getClassLoader().getResource( "csv/CSV_FileSetOfPoints.csv" ).getFile().replaceFirst( "/C", "C" ).replaceAll( "%20", " " )
    };

    public Configurator () {
        parser = new ParserCSV();
        nodeListWithAllNodes = new ArrayList<>();
        nodeListToSearch = new ArrayList<>();
    }

    @Override
    public void createResultFile(String resultString) {
        try {
            String urlToNewResultFile = urlsToFiles[0].substring( 0, urlsToFiles[0].indexOf( "program/" ) ) + "program\\src\\main\\resources\\csv\\result.csv";
            File newResultFile = new File( urlToNewResultFile );

            System.out.println("File with results created ["+newResultFile.getPath()+"].");
            BufferedWriter writer = new BufferedWriter( new FileWriter( newResultFile ) );
            writer.write( resultString );
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Node[]> getAllNodesToSearch() {
        List<String> stringsWithAllNodes = parser.parse( new File( urlsToFiles[1] ) );
        System.out.println("Configurator.class allNodesToSearch: " + stringsWithAllNodes);

        createNodesToSearch( stringsWithAllNodes );

        System.out.println("Configurator.class NodeListToSearch: ");

        for(Node[] pairToNodes:nodeListToSearch) {
            System.out.println("[ "+pairToNodes[0].getName()+", "+pairToNodes[1].getName()+" ]");
        }

        return nodeListToSearch;
    }

    private void createNodesToSearch(List<String> stringsWithAllNodes) {
        for(String oneString:stringsWithAllNodes) {
            Node[] pairOfTwoNodes = new Node[2];

            if (oneString.startsWith( "id" )) continue;

            String[] stringsAfterSplit = oneString.split( ";" );
            String firstNameOfNode = stringsAfterSplit[0];
            String secondNameOfNode = stringsAfterSplit[1];

            for (Node oneNode:nodeListWithAllNodes) {
                if (oneNode.getName().equals( firstNameOfNode )) {
                    pairOfTwoNodes[0] = oneNode;
                }
                else if (oneNode.getName().equals( secondNameOfNode )) {
                    pairOfTwoNodes[1] = oneNode;
                }
            }
            nodeListToSearch.add(pairOfTwoNodes);
        }
    }

    @Override
    public List<Node> getAllNodesWithDestinations() {
        List<String> stringsWithAllNodes = parser.parse( new File( urlsToFiles[0] ) );

        createAllNodes( stringsWithAllNodes );

        addDestinationsToEachNode( stringsWithAllNodes );

        System.out.println("Configurator.class NodeList: " + nodeListWithAllNodes );

        return nodeListWithAllNodes;

    }

    private void addDestinationsToEachNode(List<String> stringsWithAllNodes) {
        for(String oneString:stringsWithAllNodes) {

            if (oneString.startsWith( "id" )) continue;

            String[] stringsAfterSplit = oneString.split( ";" );
            String nameOfFirstNode = stringsAfterSplit[0];
            String nameOfSecondNode = stringsAfterSplit[1];
            int length = Integer.valueOf( stringsAfterSplit[2] );

            for (Node oneNode: nodeListWithAllNodes)
                if(oneNode.getName().equals( nameOfFirstNode ))
                    for (Node oneNodeToDestination: nodeListWithAllNodes)
                        if (oneNodeToDestination.getName().equals( nameOfSecondNode ))
                            oneNode.addDestination( oneNodeToDestination, length );

        }
    }

    private void createAllNodes(List<String> stringsWithAllNodes) {
        Set<String> allNameOfNodes = new HashSet<>();
        for(String oneString:stringsWithAllNodes) {

            if (oneString.startsWith( "id" )) continue;

            String[] stringsAfterSplit = oneString.split( ";" );
            allNameOfNodes.add( stringsAfterSplit[0] );
            allNameOfNodes.add( stringsAfterSplit[1] );

        }

        for(String oneNameOfNode:allNameOfNodes)
            nodeListWithAllNodes.add( new Node(oneNameOfNode) );

    }

}