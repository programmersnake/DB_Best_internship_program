package com.kostin.water_pipeline_system.h2;

import java.sql.*;
import java.util.List;

// Embedded DATABASE

public class H2DriverToUse {
    private final String CREATE_DESCRIPTIONS_QUERY = "CREATE TABLE DESCRIPTIONS (idx INT, idy INT, length INT)";

    private final String CREATE_POINTS_QUERY = "CREATE TABLE POINTS (ida INT, idb INT)";

    private final String CREATE_SCHEMA = "CREATE SCHEMA test_schema";

    private final String INSERT_DATA_INTO_DESCRIPTIONS_QUERY = "INSERT INTO DESCRIPTIONS VALUES(";

    private final String INSERT_DATA_INTO_POINTS_QUERY = "INSERT INTO POINTS VALUES(";

    private final String SELECT_DATA_FROM_TABLE_QUERY = "SELECT * FROM ";

    private Connection dbConnection;

    public H2DriverToUse () {
        try {
            dbConnection = DriverManager.getConnection( "jdbc:h2:mem:" );
            createTables();
            System.out.println("H2: DATABASE created!");
            System.out.println("H2: Tables created!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert_DataToTableFromDescriptionFile (List<String> stringsWithAllNodes) {
        try {
            for(String oneString: stringsWithAllNodes) {
                if(oneString.startsWith( "id" )) continue;

                String[] arrStr;
                arrStr = oneString.split( ";" );
                String idx = arrStr[0];
                String idy = arrStr[1];
                String length = arrStr[2];

                String insert_data_into_descriptions_query = INSERT_DATA_INTO_DESCRIPTIONS_QUERY;
                insert_data_into_descriptions_query+=idx+","+idy+","+length+")";

                Statement dataQuery = dbConnection.createStatement();
                dataQuery.execute( insert_data_into_descriptions_query );
            }
            System.out.println("H2: Data into table [Descriptions] uploaded!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insert_DataToTableFromPointsFile (List<String> stringsWithNodes) {
        try {
            for(String oneString: stringsWithNodes) {
                if(oneString.startsWith( "id" )) continue;

                String[] arrStr;
                arrStr = oneString.split( ";" );
                String ida = arrStr[0];
                String idb = arrStr[1];

                String insert_data_into_points_query = INSERT_DATA_INTO_POINTS_QUERY;
                insert_data_into_points_query+=ida+","+idb+")";

                Statement dataQuery = dbConnection.createStatement();
                dataQuery.execute( insert_data_into_points_query );
            }
            System.out.println("H2: Data into table [Points] uploaded!");
            System.out.println();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTables() {
        try {
            Statement dataQuery = dbConnection.createStatement();
            dataQuery.execute( CREATE_DESCRIPTIONS_QUERY );
            dataQuery.execute( CREATE_POINTS_QUERY );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectDataFromTheTables () {
        selectAndPrintDataFromTheTable("DESCRIPTIONS");
        selectAndPrintDataFromTheTable( "POINTS" );
    }

    private void selectAndPrintDataFromTheTable(String labelOfTable) {
        try {
            String select_data_from_table_query = SELECT_DATA_FROM_TABLE_QUERY+" "+labelOfTable;

            PreparedStatement query = dbConnection.prepareStatement(select_data_from_table_query);
            ResultSet rs = query.executeQuery();
            if (labelOfTable.equals( "POINTS" )) {
                System.out.println("___________POINTS___________");
                while (rs.next()) {
                    System.out.println( String.format( "%s, %s", rs.getInt( "ida" ), rs.getInt( "idb" ) ) );
                }
            }
            else if (labelOfTable.equals( "DESCRIPTIONS" )) {
                System.out.println("________DESCRIPTIONS________");
                while (rs.next()) {
                    System.out.println( String.format( "%s, %s, %s", rs.getInt( "idx" ), rs.getInt( "idy" ), rs.getInt( "length" ) ) );
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}