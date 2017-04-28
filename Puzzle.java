/**
 * Created by marco on 4/18/2017.
 */
import java.util.Scanner;
import java.util.Random;

public class Puzzle {
    int[][] puzzle = {{-1, -1, -1},
                      {-1, -1, -1},
                      {-1, -1, -1}};

    //CONSTRUCTOR FOR A RANDOMLY GENERATED PUZZLE
    public Puzzle(){
        Random gen = new Random();

        int value = -1;

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                while(contains(value, puzzle)){
                    value = gen.nextInt(9);
                }

                puzzle[i][j] = value;
            }
        }
    }

    //CONSTRUCTOR FOR USER CONSTRUCTED PUZZLE
    public Puzzle(Scanner reader){
        int value = -1;

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                while(contains(value, puzzle) || value > 8 || value < 0){
                    System.out.print("Enter a number in position " + i + ", " + j + ": ");
                    value = reader.nextInt();

                    if(contains(value, puzzle))
                        System.out.println("No duplicates allowed.");
                    if(value > 8 || value < 0)
                        System.out.println("Number out of bounds [0-8]");
                }

                puzzle[i][j] = value;
            }
        }
    }

    //USED TO PREVENT DUPLICATE VALUES IN PUZZLE
    public static boolean contains(int n, int[][] values){
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                if(values[i][j] == n)
                    return true;
            }
        }

        return false;
    }

    //DETERMINES WHETHER THE PUZZLE IS SOLVABLE
    public boolean solvable(){
        int N = 0;

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                N += compare(puzzle[i][j], i, j);
            }
        }

        if(N % 2 == 0)
            return true;
        else
            return false;
    }

    //AUXILIARY METHOD USED TO COUNT HOW MANY TILES ARE OUT OF ORDER
    public int compare(int value, int x, int y){
        int count = 0, i = 2, j = 2;

        while(!(i == x && j == y)){
            if(puzzle[i][j] < value && puzzle[i][j] != 0)
                ++count;

            if(j > 0){
                --j;
            }else{
                j = 2;
                --i;
            }
        }

        return count;
    }

    public int[][] getPuzzle(){
        return puzzle;
    }

    public void printPuzzle(){
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                System.out.print(puzzle[i][j] + " ");
            }

            System.out.println();
        }
    }
}