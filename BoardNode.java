import java.util.ArrayList;

/**
 * Created by marco on 4/19/2017.
 */
public class BoardNode {
    private BoardNode parent;
    private int[][] board = new int[3][3];
    private int g, h = 0, f = 0;

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
        g = 0;

        if(AStar.getH()) {
            setH2();
        }else{
            setH1();
        }

        f = g + h;
    }

    //CONSTRUCTOR FOR CHILD NODES
    public BoardNode(int[][] b, BoardNode p){
        board = b;
        parent = p;
        g = parent.getG() + 1;

        if(AStar.getH()) {
            setH2();
        }else{
            setH1();
        }

        f = g + h;
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
                    ++h;
            }
        }
    }

    //CALCULATING MANHATTAN DISTANCES FOR EACH TILE
    public void setH2(){
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                if(board[i][j] != 0)
                    h += Math.abs(goalH2.get(board[i][j])[0] - i) + Math.abs(goalH2.get(board[i][j])[1] - j);
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

    //RETURNING VALUE OF FIRST HEURISTIC FUNCTION H1(N)
    public int getH(){
        return h;
    }


    //TO BE ACCESSED BY CHILDREN NODES TO KEEP TRACK OF G(N)
    public int getG(){return g;}

    //RETURNS F(N) = G(N) + H1(N) | H2(N)
    public int getF(){return f;}

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