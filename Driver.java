/**
 * Created by marco on 4/18/2017.
 */
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner reader =  new Scanner(System.in);
        int option = -1;

        Puzzle puzzle = null;

        //ALLOWS THE USER TO CREATE A PUZZLE RANDOMLY OR MANUALLY THEN CHECKS IF IT IS SOLVABLE
        while(option != 3){
            System.out.print("\n8-Puzzle Solver\n1) Randomly generate puzzle\n2) Manually create puzzle\n3) Run test cases\n4) Exit\n>");
            option = reader.nextInt();

            if(option == 1){
                puzzle = new Puzzle();
                subMenu(puzzle, reader);
            }else if(option == 2){
                puzzle = new Puzzle(reader);
                subMenu(puzzle, reader);
            }else if(option == 3){
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

                    System.out.println("Nodes generated: " + alg.getTreeSize());
                }else if(op == 2){
                    alg.setH(true);
                    alg.solve();

                    System.out.println("Nodes generated: " + alg.getTreeSize());
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
}