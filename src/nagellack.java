package com.demo.lektionsqlitecrud;

import java.sql.*;
import java.util.Scanner;

public class nagellack {

    private static Scanner scanner = new Scanner(System.in);

    private static Connection connect() {

        String url = "jdbc:sqlite:C:\\Users\\needt\\sqlite\\labb2";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void printActions() {
        System.out.println("\nVälj:\n");
        System.out.println("0  - Stäng av\n" +
                "1  - Visa alla nagellack\n" +
                "2  - Lägga till ett nytt nagellack\n" +
                "3  - Uppdatera ett nagellack\n" +
                "4  - Ta bort ett nagellack\n" +
                "5  - Visa en lista över alla val.");
    }


    private static void deleteNagellack(){
        System.out.println("Skriv in id:t på nagellacken som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
    }

    private static void selectAll(){
        String sql = "SELECT * FROM nagellack";

        try {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getInt("nagellackId") +  "\t" +
                        rs.getString("nagellackNamn") + "\t" +
                        rs.getString("nagellackTillverkare") + "\t" +
                        rs.getString("tillverkareId") + "\t" +
                        rs.getString("nagellackFarg") + "\t" +
                        rs.getString("nagellackPris"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insert(String nagellackNamn, String nagellackTillverkare, int tillverkareId, String nagellackFarg, int nagellackPris) {
        String sql = "INSERT INTO nagellack(nagellackNamn,nagellackTillverkare,tillverkareId,nagellackFarg,nagellackPris) VALUES(?,?,?,?,?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nagellackNamn);
            pstmt.setString(2, nagellackTillverkare);
            pstmt.setInt(3, tillverkareId);
            pstmt.setString(4, nagellackFarg);
            pstmt.setInt(5, nagellackPris);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till ett nytt nagellack");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void update(String nagellackNamn, String nagellackTillverkare, int tillverkareId, String nagellackFarg, int nagellackPris, int nagellackId) {
        String sql = "UPDATE nagellack SET nagellackNamn = ? , "
                + "nagellackTillverkare = ? , "
                + "tillverkareId = ? , "
                + "nagellackFarg = ? , "
                + "nagellackPris = ? "
                + "WHERE nagellackId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nagellackNamn);
            pstmt.setString(2, nagellackTillverkare);
            pstmt.setInt(3, tillverkareId);
            pstmt.setString(4, nagellackFarg);
            pstmt.setInt(5, nagellackPris);
            pstmt.setInt(6, nagellackId);

            pstmt.executeUpdate();
            System.out.println("Du har uppdaterat valt nagellack");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void delete(int nagellackId) {
        String sql = "DELETE FROM nagellack WHERE nagellackId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setInt(1, nagellackId);

            pstmt.executeUpdate();
            System.out.println("Du har tagit bort nagellacken");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        boolean quit = false;
        printActions();
        while(!quit) {
            System.out.println("\nVälj (5 för att visa val):");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("\nStänger ner...");
                    quit = true;
                    break;

                case 1:
                    selectAll();
                    break;

                case 2:
                    String nagellackNamn;
                    String nagellackTillverkare;
                    int tillverkareId;
                    String input;
                    String nagellackFarg;
                    int nagellackPris;
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("Ange nagellacks namn");
                    nagellackNamn= sc1.nextLine();
                    System.out.println("Ange nagellacks tillverkare");
                    nagellackTillverkare=sc1.nextLine();
                    System.out.println("Ange tillverkar id");
                    tillverkareId=sc1.nextInt();
                    System.out.println("Ange nagellacks färg");
                    input=sc1.nextLine();  // Consume newline left-over
                    nagellackFarg= sc1.nextLine();
                    System.out.println("Ange nagellack pris");
                    nagellackPris=sc1.nextInt();
                    insert(nagellackNamn,nagellackTillverkare,tillverkareId,nagellackFarg,nagellackPris);
                    break;

                case 3:
                    String nNamn;
                    String ntillverkare;
                    int tId;
                    String nFarg;
                    int nPris;
                    int nId;
                    Scanner sc2 = new Scanner(System.in);
                    System.out.println("Ange nytt nagellacks namn");
                    nNamn= sc2.nextLine();
                    System.out.println("Ange nytt nagellacks tillverkare");
                    ntillverkare=sc2.nextLine();
                    System.out.println("Ange nytt tillverkar id");
                    tId=sc2.nextInt();
                    System.out.println("Ange ny nagellacks färg");
                    nFarg= sc2.nextLine();  // Consume newline left-over
                    nFarg= sc2.nextLine();
                    System.out.println("Ange nytt pris");
                    nPris=sc2.nextInt();
                    System.out.println("Ange vilket nagellacks id som ska ändras");
                    nId=sc2.nextInt();

                    update(nNamn, ntillverkare, tId, nFarg, nPris, nId);
                    break;

                case 4:

                    deleteNagellack();
                    break;

                case 5:
                    printActions();
                    break;
            }
        }

    }

}


