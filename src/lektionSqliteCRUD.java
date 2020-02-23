package com.demo.lektionsqlitecrud;

import java.sql.*;
import java.util.Scanner;

public class lektionSqliteCRUD {

    private static Scanner scanner = new Scanner(System.in);

    private static Connection connect() {
        // SQLite connection string
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
                "1  - Visa alla böcker\n" +
                "2  - Lägga till en ny bok\n" +
                "3  - Uppdatera en bok\n" +
                "4  - Ta bort en bok\n" +
                "5  - Visa en lista över alla val.");
    }


    private static void deleteBook(){
        System.out.println("Skriv in id:t på boken som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
    }

    private static void selectAll(){
        String sql = "SELECT * FROM bok";

        try {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("bokId") +  "\t" +
                        rs.getString("bokTitel") + "\t" +
                        rs.getString("bokForfattare") + "\t" +
                        rs.getString("bokPris"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insert(String titel, String forfattare, int pris) {
        String sql = "INSERT INTO bok(bokTitel, bokForfattare, bokPris) VALUES(?,?,?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, titel);
            pstmt.setString(2, forfattare);
            pstmt.setInt(3, pris);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny bok");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void update(String titel, String forfattare, int pris, int id) {
        String sql = "UPDATE bok SET bokTitel = ? , "
                + "bokForfattare = ? , "
                + "bokPris = ? "
                + "WHERE bokId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, titel);
            pstmt.setString(21
                    , forfattare);

            pstmt.setInt(3, pris);
            pstmt.setInt(4, id);
            // update
            pstmt.executeUpdate();
            System.out.println("Du har uppdaterat vald bok");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void delete(int id) {
        String sql = "DELETE FROM bok WHERE bokId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Du har tagit bort boken");
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
                    String titel;
                    String forfattare;
                    int pris;
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("Ange boktitel");
                    titel= sc1.nextLine();
                    System.out.println("Ange författarnamn");
                    forfattare=sc1.nextLine();
                    System.out.println("Ange bokpris");
                    pris=sc1.nextInt();
                    insert(titel,forfattare,pris);
                    break;

                case 3:
                    String btitel;
                    String forf;
                    int ppris;
                    int bid;
                    Scanner sc2 = new Scanner(System.in);
                    System.out.println("Ange ny boktitel");
                    btitel= sc2.nextLine();
                    System.out.println("Ange nytt författarnamn");
                    forf=sc2.nextLine();
                    System.out.println("Ange nytt bokpris");
                    ppris=sc2.nextInt();
                    System.out.println("Ange vilket bokId");
                    bid=sc2.nextInt();

                    update(btitel, forf, ppris, bid);
                    break;

                case 4:
                    //delete(1);
                    deleteBook();
                    break;

                case 5:
                    printActions();
                    break;
            }
        }

    }

}


