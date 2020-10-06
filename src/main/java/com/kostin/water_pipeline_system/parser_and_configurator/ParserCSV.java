package com.kostin.water_pipeline_system.parser_and_configurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParserCSV implements Parser {

    @Override
    public List<String> parse(File file) {
        try {
            String line = "";
            List<String> listLines= new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader( new FileReader( file ) );
            while((line = bufferedReader.readLine()) != null){
                listLines.add( line );
            }
            bufferedReader.close();

            return listLines; 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
