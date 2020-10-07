package com.kostin.water_pipeline_system;

import com.kostin.water_pipeline_system.model.Dijkstra;
import com.kostin.water_pipeline_system.model.Graph;
import com.kostin.water_pipeline_system.model.Node;
import com.kostin.water_pipeline_system.parser_and_configurator.Configurator;
import com.kostin.water_pipeline_system.parser_and_configurator.ConfiguratorInterface;
import com.kostin.water_pipeline_system.parser_and_configurator.Parser;
import com.kostin.water_pipeline_system.parser_and_configurator.ParserCSV;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;


public class TestMain {

    @Test
    public void mainTestAll() {

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

    @Test
    public void testParse() {
        Parser parser = new ParserCSV();
        List<String> parse = parser.parse( new File( "C:\\Users\\kosti\\IdeaProjects\\DB Best internship program\\src\\main\\resources\\csv\\CSV_FileSetOfPoints.csv" ) );
        assert (parse.size() > 0);
    }

}
