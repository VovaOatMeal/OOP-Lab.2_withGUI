package sample;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBQueries {

    public static void selectQuery() throws SQLException {

        final String QUERY = "SELECT * FROM subscribers";
        ResultSet resultSet = Main.statement.executeQuery(QUERY);

        //ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();

        // write to file
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter("src/sample/results.txt");

            bw = new BufferedWriter(fw);
            String line = "";

            while (resultSet.next()) {
                int id = resultSet.getInt("Subscriber_ID");
                String First_Name = resultSet.getString("First_Name");
                String Last_Name = resultSet.getString("Last_Name");
                String mobile = resultSet.getString("Mobile");
                boolean contract = resultSet.getBoolean("Contract");

                line = String.format("%-15s%-28s%-38s%-15s%-8s%n", id, First_Name, Last_Name, mobile, contract);
                bw.write(line);

                Main.subscriberArrayList.add(new Subscriber(id, First_Name, Last_Name, mobile, contract));

            }
        }
        catch(Exception e) {
            e.printStackTrace();
        } finally {
            if( bw != null ) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        resultSet.close();
        resultSet = null;

    }

    public static void updateQuery(int id, String First_Name, String Last_Name, String mobile, boolean contract) {

        try {
            final String QUERY = "UPDATE subscribers SET " +
                    "First_Name = '" + First_Name +
                    "', Last_Name = '" + Last_Name +
                    "', Mobile = '" + mobile +
                    "', Contract = " + contract +
                    " WHERE Subscriber_ID = " + id;
            Main.statement.executeUpdate(QUERY);
        } catch (SQLException e) {
            System.out.println(e.toString() +
                    "\nHappened in loadQuery()");

        }
    }

    public static ResultSet loadQuery(int id) throws SQLException {
        try {
            final String QUERY = "SELECT * FROM subscribers WHERE Subscriber_ID = " + id;
            ResultSet resultSet = Main.statement.executeQuery(QUERY);

            return resultSet;
        } catch (SQLException e) {
            System.out.println(e.toString() +
                    "\nHappened in loadQuery()");
            return null;
        }
    }
}
