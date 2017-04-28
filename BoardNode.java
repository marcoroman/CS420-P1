import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by marco on 4/19/2017.
 */
public class BoardNode {
    private BoardNode parent;
    private int[][] board = new int[3][3];
    private int g = 0, h1 = 0, h2 = 0;

    private int[][] goalH1 = {{0 ,1, 2},
                              {3, 4, 5},
                              {6, 7, 8}};

    ArrayList<int[]> goalH2 = new ArrayList<>();

    //*******************************CONSTRUCTORS*******************************

    //CONSTRUCTOR FOR ROOT NODE
    public BoardNode(int[][] b){
        board = b;
        parent = null;
        goalH2 = AStar.getGoalH2();

        setH2();
    }

    //CONSTRUCTOR FOR CHILD NODES
    public BoardNode(int[][] b, BoardNode p){
        board = b;
        parent = p;
    }

    //*******************************MUTATORS***********************************

    public void setParent(BoardNode p){
        parent = p;
    }

    //CALCULATING THE NUMBER OF MISPLACED TILES ON BOARD
    public void setH1(){
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                if(board[i][j] != goalH1[i][j] && board[i][j] != 0)
                    ++h1;
            }
        }
    }

    //CALCULATING MANHATTAN DISTANCES FOR EACH TILE
    public void setH2(){
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                if(board[i][j] != 0)
                    h2 += Math.abs(goalH2.get(board[i][j])[0] - i) + Math.abs(goalH2.get(board[i][j])[1] - j);
            }
        }
    }

    //********************************ACCESSORS**********************************

    public int[][] getBoard(){
        return board;
    }

    public BoardNode getParent(){
        return parent;
    }

    public int getH1(){
        return h1;
    }

    public int getH2(){
        return h2;
    }

    public void print(){
        System.out.println();

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();
    }
}