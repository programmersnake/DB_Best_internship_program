package com.kostin.water_pipeline_system.main;

import com.kostin.water_pipeline_system.model.Dijkstra;
import com.kostin.water_pipeline_system.model.Graph;
import com.kostin.water_pipeline_system.model.Node;
import com.kostin.water_pipeline_system.parser_and_configurator.Configurator;
import com.kostin.water_pipeline_system.parser_and_configurator.ConfiguratorInterface;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        StringBuilder resultString = new StringBuilder( "ROUTE EXISTS;MIN LENGTH" + System.lineSeparator() );
        ConfiguratorInterface configurator = new Configurator();

        List<Node> allNodes = configurator.getAllNodesWithDestinations();
        List<Node[]> listWithArrayNodesToSearch = configurator.getAllNodesToSearch();
        Graph graph = new Graph();

        for (Node oneNode : allNodes)
            graph.addNode( oneNode );

        for (Node[] arrNodes : listWithArrayNodesToSearch) {
            int resultFromDijkstra = Dijkstra.calculateShortestPathFromSource( graph, arrNodes[0], arrNodes[1] );
            if ( resultFromDijkstra != -1 ) {
                System.out.println( "TRUE;" + resultFromDijkstra );
                resultString.append( "TRUE;" ).append( resultFromDijkstra ).append( System.lineSeparator() );
            } else {
                System.out.println( "FALSE;" );
                resultString.append( "FALSE;" ).append( System.lineSeparator() );
            }
        }

        configurator.createResultFile( resultString.toString() );

    }

}
