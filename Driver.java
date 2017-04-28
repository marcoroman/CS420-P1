/**
 * Created by marco on 4/18/2017.
 */
import java.util.Scanner;

public class Driver {

    public static void main(String[] args){
        Scanner reader =  new Scanner(System.in);

        Puzzle puzzle = new Puzzle();

        puzzle.printPuzzle();

        if(puzzle.solvable()){
            System.out.println("Board is solvable!");

            AStar alg = new AStar(puzzle.getPuzzle());
            alg.solve();
        }else{
            System.out.println("Puzzle is not solvable.");
        }

        reader.close();
    }
}