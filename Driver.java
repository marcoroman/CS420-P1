/**
 * Created by marco on 4/18/2017.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner reader =  new Scanner(System.in);
        int option = -1;

        Puzzle puzzle = null;

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

    public static void subMenu(Puzzle puzzle, Scanner r){
        puzzle.printPuzzle();
        int op = -1;

        if(puzzle.solvable()){
            System.out.println("\nPuzzle is solvable!");

            while(op != 3){
                System.out.print("\n1) Solve using h1(n) = number of misplaced tiles" +
                        "\n2) Solve using h2(n) = Manhattan distance\n3) Back\n>");
                op = r.nextInt();

                if(op == 1){
                    AStar alg = new AStar(puzzle.getPuzzle(), false);
                    alg.solve();
                }else if(op == 2){
                    AStar alg = new AStar(puzzle.getPuzzle(), true);
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
        File file = new File("cases.txt");
        Scanner r = new Scanner(file);
        String line;
        int size = 0;

        //SEPARATE ARRAY LISTS STORE THE DIFFERENT HEURISTIC SOLUTIONS
        ArrayList<AStar> h1_Cases = new ArrayList<>();
        ArrayList<AStar> h2_Cases = new ArrayList<>();

        while(r.hasNextLine()){

            line = r.nextLine();

            if(line.matches("^[0-8]{9}$")){
                h1_Cases.add(new AStar(convert(line), false));
                h2_Cases.add(new AStar(convert(line), true));
            }
        }

        //SOLVING 100 TEST CASES AT DEPTH D = 2
        for(int i = 0; i < 100; ++i){
            h1_Cases.get(i).solveTest();
            size += h1_Cases.get(i).getTreeSize();
        }

        System.out.println("Average tree size: " + size / 100);
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