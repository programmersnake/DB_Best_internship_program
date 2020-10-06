package com.kostin.water_pipeline_system.parser_and_configurator;

import java.io.File;
import java.util.List;

public interface Parser {

    List<String> parse(File file);

}
