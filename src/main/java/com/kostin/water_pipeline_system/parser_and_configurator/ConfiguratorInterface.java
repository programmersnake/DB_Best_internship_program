package com.kostin.water_pipeline_system.parser_and_configurator;

import com.kostin.water_pipeline_system.model.Node;

import java.util.List;

public interface ConfiguratorInterface {

    void createResultFile(String resultString);

    List<Node> getAllNodesWithDestinations();

    List<Node[]> getAllNodesToSearch();

}
