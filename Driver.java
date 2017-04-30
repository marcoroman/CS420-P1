/**
 * Created by marco on 4/18/2017.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner reader =  new Scanner(System.in);
        int option = -1;

        Puzzle puzzle = null;

        //ALLOWS THE USER TO CREATE A PUZZLE RANDOMLY OR MANUALLY THEN CHECKS IF IT IS SOLVABLE
        while(option != 4){
            System.out.print("\n8-Puzzle Solver\n1) Randomly generate puzzle\n2) Manually create puzzle\n3) Run test cases\n4) Exit\n>");
            option = reader.nextInt();

            if(option == 1){
                puzzle = new Puzzle();
                subMenu(puzzle, reader);
            }else if(option == 2){
                puzzle = new Puzzle(reader);
                subMenu(puzzle, reader);
            }else if(option == 3){
                test();
            }else if(option == 4){
                System.out.println("Program exited.");
            }else{
                System.out.println("Please enter a valid option.");
            }
        }

        reader.close();
    }

    //ALLOWS THE USER TO SOLVE THE PUZZLE WITH THE HEURISTIC OF THEIR CHOOSING
    public static void subMenu(Puzzle puzzle, Scanner r){
        puzzle.printPuzzle();
        int op = -1;

        if(puzzle.solvable()){
            System.out.println("\nPuzzle is solvable!");
            AStar alg = new AStar(puzzle.getPuzzle());

            while(op != 3){
                System.out.print("\n1) Solve using h1(n) = number of misplaced tiles" +
                        "\n2) Solve using h2(n) = Manhattan distance\n3) Back\n>");
                op = r.nextInt();

                if(op == 1){
                    alg.setH(false);
                    alg.solve();
                }else if(op == 2){
                    alg.setH(true);
                    alg.solve();
                }else if(op == 3){
                    break;
                }else{
                    System.out.println("Please enter a valid option.");
                }
            }
        }else{
            System.out.println("Puzzle is not solvable.");
        }
    }

    //THIS PART OF THE PROGRAM HANDLES TESTING 100 TEST CASES AT DEPTHS STARTING FROM
    //2 AND INCREMENTING BY 2 UNTIL 20.
    public static void test() throws FileNotFoundException{
        File file = new File("cases2.txt");
        Scanner r = new Scanner(file);
        String line;
        int[] sizesH1 = new int[10];
        int[] sizesH2 = new int[10];
        long[] timeH1 = new long[10];
        long[] timeH2 = new long[10];

        //ARRAY LIST STORES 2D ARRAYS FROM FILE THAT WILL BE USED TO CREATE
        //NEW ASTAR OBJECTS
        ArrayList<int[][]> cases = new ArrayList<>();

        while(r.hasNextLine()){

            line = r.nextLine();

            if(line.matches("^[0-8]{9}$")){
                cases.add(convert(line));
            }
        }

        //SOLVING 200 TEST CASES AT DEPTHS 2 - 20 (EVEN INTERVALS)
        for(int i = 0; i < 2000; ++i){
            AStar a = new AStar(cases.get(i));
            a.isTesting(true);

            //TESTING BY THE FIRST HEURISTIC
            a.solve();
            sizesH1[i / 200] += a.getTreeSize();
            timeH1[i / 200] += a.getTime();

            //SWITCHING OVER TO THE SECOND HEURISTIC
            a.setH(true);

            a.solve();
            sizesH2[i / 200] += a.getTreeSize();
            timeH2[i / 200] += a.getTime();
        }

        for(int i = 0; i < sizesH1.length; ++i){
            sizesH1[i] /= 200;
            sizesH2[i] /= 200;
        }

        System.out.printf("%-20s%-50s\n", "Heuristic 1 Depths: ", " 2, 4, 6, 8, 10, 12, 14, 16, 18, 20");
        System.out.printf("%-20s%-50s\n", "Nodes generated: ", Arrays.toString(sizesH1));
        System.out.printf("%-20s%-50s\n", "Time: ", Arrays.toString(timeH1));

        System.out.printf("\n%-20s%-50s\n", "Heuristic 2 Depths: ", " 2, 4, 6, 8, 10, 12, 14, 16, 18, 20");
        System.out.printf("%-20s%-50s\n", "Nodes generated: ", Arrays.toString(sizesH2));
        System.out.printf("%-20s%-50s\n", "Time: ", Arrays.toString(timeH2));
    }

    //CREATES A 2D ARRAY FROM A STRING OF NUMBERS
    public static int[][] convert(String s){
        String[] n = s.split("");

        int[][] arr = { {Integer.parseInt(n[0]), Integer.parseInt(n[1]), Integer.parseInt(n[2])},
                        {Integer.parseInt(n[3]), Integer.parseInt(n[4]), Integer.parseInt(n[5])},
                        {Integer.parseInt(n[6]), Integer.parseInt(n[7]), Integer.parseInt(n[8])} };

        return arr;
    }
}