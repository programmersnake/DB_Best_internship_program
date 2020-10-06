package com.kostin.water_pipeline_system.main;

import com.kostin.water_pipeline_system.model.Dijkstra;
import com.kostin.water_pipeline_system.model.Graph;
import com.kostin.water_pipeline_system.model.Node;
import com.kostin.water_pipeline_system.parser_and_configurator.Configurator;
import com.kostin.water_pipeline_system.parser_and_configurator.ConfiguratorInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String resultString = "ROUTE EXISTS;MIN LENGTH"+System.lineSeparator();
        ConfiguratorInterface configurator = new Configurator();
        Graph graph = new Graph();
        List<Node> allNodes = configurator.getAllNodesWithDestinations();

        for(Node oneNode:allNodes)
            graph.addNode(oneNode);

        List<Node[]> listWithArrayNodesToSearch = configurator.getAllNodesToSearch();

        for(Node[] arrNodes: listWithArrayNodesToSearch) {
            int resultFromDijkstra = Dijkstra.calculateShortestPathFromSource(graph, arrNodes[0], arrNodes[1]);
            if(resultFromDijkstra!=-1) {
                System.out.println("TRUE;" + resultFromDijkstra);
                resultString += ("TRUE;" + resultFromDijkstra + System.lineSeparator());
            }
            else {
                System.out.println("FALSE;");
                resultString += ("FALSE;" + System.lineSeparator());
            }
        }

        configurator.createResultFile(resultString);

    }

}
