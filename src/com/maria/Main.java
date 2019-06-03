package com.maria;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static java.sql.Date.valueOf;

public class Main {

    private static GeneratorData generator;


    public static void main(String[] args) throws IOException, SQLException {

        System.out.println("--Welcome to my Picture Gallery--");
        System.out.println(
                "1 - add to the table Author \n" +
                        "2 - add to the table Museum \n" +
                        "3 - add to the table Picture \n" +
                        "4 - add to the table PictureMuseum \n" +
                        "5 - add to the table PictureAuthor \n" +
                        "6 - add to the table Auction \n" +
                        "7 - add to the table Theft \n" +
                        "8 - add to the table Exhibitions \n" +
                        "9 - add to the table PictureExhibition \n" +
                        "10 - add to the table Genre \n" +
                        "11 - add to the table ArtDirection \n" +
                        "12 - create all tables \n" +
                        "13 - add to all tables \n" +
                        "0 - exit"
        );
        Scanner in = new Scanner(System.in);
        Scanner in2 = new Scanner(System.in);

        int num = Input.inputN(in, "Enter number ", 0, 13);
        if (num != 0) {
            generator = new GeneratorData();
            generator.connect();
            switch (num) {
                case 1:
                    generator.addToAuthor(Input.inputN(in, "Enter count of Author ", 0));
                    break;
                case 2:
                    generator.addToMuseum(Input.inputN(in, "Enter count of Museum ", 0));
                    break;
                case 3:
                    generator.addToPicture(Input.inputN(in, "Enter count of Pictures ", 0));
                    break;
                case 4:
                    generator.addToPictureMuseum();
                    break;
                case 5:
                    generator.addToPictureAuthor(Input.inputN(in, "Enter count of PictureAuthor ", 0));
                    break;
                case 6:
                    generator.addToAuction(Input.inputN(in, "Enter count of Auction ", 0));
                    break;
                case 7:
                    generator.addToTheft(Input.inputN(in, "Enter count of Theft ", 0));
                    break;
                case 8:
                    generator.addToExhibitions(Input.inputN(in, "Enter count of Exhibition ", 0));
                    break;
                case 9:
                    generator.addToPictureExhibition(Input.inputN(in, "Enter count of PictureExhibition ", 0));
                    break;
                case 10:
                    generator.addToGenre(Input.inputS(in2, "Enter filename "));
                    break;
                case 11:
                    generator.addToArtDirection(Input.inputS(in2, "Enter filename "));
                    break;
                case 12:
                    generator.createTables(Input.inputS(in2, "Enter filename "));
                    break;
                case 13:
                    generator.addToGenre("../genre.txt");
                    generator.addToArtDirection("../art.txt");
                    generator.addToAuthor(1000);
                    generator.addToMuseum(100);
                    generator.addToPicture(10000);
                    generator.addToPictureMuseum();
                    generator.addToPictureAuthor(10);
                    generator.addToAuction(4000);
                    generator.addToTheft(200);
                    generator.addToExhibitions(100);
                    generator.addToPictureExhibition(1000);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
            generator.disconnect();
        }
    }
}

final class Input {
    public static String inputS(Scanner in, String str) throws InputMismatchException {

        String filename = " ";
        System.out.printf(str);
        try {
            filename = in.nextLine();
        } catch (InputMismatchException e) {
            System.out.printf("Input is wrong ");
        }
        return filename;
    }

    public static int inputN(Scanner in, String str, int min) throws InputMismatchException {

        System.out.printf(str);
        int num = min;
        try {
            num = in.nextInt();
            if (num < min) {
                System.out.printf("Input is wrong ");
                num = min;
            }
        } catch (InputMismatchException e) {
            System.out.printf("Input is wrong ");
        }
        return num;
    }

    public static int inputN(Scanner in, String str, int min, int max) throws InputMismatchException {

        System.out.printf(str);
        int num = min;
        try {
            num = in.nextInt();
            if (num > max || num < min) {
                System.out.printf("Input is wrong ");
                num = min;
            }
        } catch (InputMismatchException e) {
            System.out.printf("Input is wrong ");
        }
        return num;
    }
}
