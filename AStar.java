import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by marco on 4/19/2017.
 */
public class AStar {

    BoardNode root;

    //THE COMPARATOR CLASS ALLOWS THE PRIORITY QUEUE TO SORT BOARD NODES BY THEIR EVALUATION FUNCTIONS
    //FRONTIER HOLDS NODES THAT CAN BE EXPLORED, EXPLORED HASHMAP HOLDS NODES THAT HAVE BEEN EXPANDED
    Comparator<BoardNode> fCompare = new functionCompare();
    PriorityQueue<BoardNode> frontier = new PriorityQueue<>(fCompare);
    HashMap<String, int[][]> explored = new HashMap<>();

    //PASSED INTO BOARD NODE OBJECT FOR CALCULATION OF H2
    static ArrayList<int[]> goalH2 = new ArrayList<>();

    //DETERMINES WHICH HEURISTIC WILL BE USED
    private static boolean selectH;

    //CONSTRUCTOR INSTANTIATES MANHATTAN DISTANCES IF BOOLEAN = TRUE AND SETS ROOT NODE FOR SEARCH
    //IF BOOLEAN IS FALSE, # OF MISPLACED TILES IS USED
    public AStar(int[][] a, boolean b){

        selectH = b;

        if(selectH) {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    int[] arr = new int[2];
                    arr[0] = i;
                    arr[1] = j;
                    goalH2.add(arr);
                }
            }
        }

        root = new BoardNode(a);
    }

    public static boolean getH(){
        return selectH;
    }

    //GENERATES CHILDREN OF GIVEN NODE
    //MODIFY TO ADD CHILDREN NODES TO FRONTIER INSTEAD OF PRINTING
    public void getMoves(BoardNode bn){
        int x = 0, y = 0;
        int[][] state = new int[3][3];

        //DETERMINING THE POSITION OF THE BLANK AND CREATING A COPY OF THE BOARD FOR MODIFICATION
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                state[i][j] = bn.getBoard()[i][j];

                if(state[i][j] == 0){
                    x = i; y = j;
                }
            }
        }

        //THE FOLLOWING CONDITIONS DETERMINE THE NEXT VALID STATES FOR THE CURRENT BOARD
        if(y < 2){
            moveRight(state, x, y);
            BoardNode test = new BoardNode(state);
        }

        if(y > 0){
            state = copy(bn.getBoard());

            moveLeft(state, x, y);
            BoardNode test = new BoardNode(state);
        }

        if(x < 2){
            state = copy(bn.getBoard());

            moveDown(state, x, y);
            BoardNode test = new BoardNode(state);
        }

        if(x > 0){
            state = copy(bn.getBoard());

            moveUp(state, x, y);
            BoardNode test = new BoardNode(state);
        }
    }

    //THESE METHODS MOVE THE BLANK AROUND THE BOARD
    public void moveUp(int[][] arr, int x, int y){
        arr[x][y] = arr[x - 1][y];
        arr[x - 1][y] = 0;
    }

    public void moveDown(int[][] arr, int x, int y){
        arr[x][y] = arr[x + 1][y];
        arr[x + 1][y] = 0;
    }

    public void moveLeft(int[][] arr, int x, int y){
        arr[x][y] = arr[x][y - 1];
        arr[x][y - 1] = 0;
    }

    public void moveRight(int[][] arr, int x, int y){
        arr[x][y] = arr[x][y + 1];
        arr[x][y + 1] = 0;
    }

    //CREATING THE KEY FOR STORING SEQUENCE OF 2D ARRAY IN HASHMAP
    public static String makeKey(int[][] arr){
        String str = "";

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j) {
                str += arr[i][j];
            }
        }

        return str;
    }

    //USED BY BOARD NODE CLASS TO CALCULATE MANHATTAN DISTANCES FOR EACH NODE
    public static ArrayList<int[]> getGoalH2() {
        return goalH2;
    }

    //AUXILIARY METHOD FOR CREATING A COPY OF THE BOARD
    public int[][] copy(int[][] arr){
        int[][] copied = new int[3][3];

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                copied[i][j] = arr[i][j];
            }
        }

        return copied;
    }

    //BEGIN MAIN ALGORITHM LOOP HERE
    /*
    * 1. Initialize frontier with root node
    * 2. While frontier is not empty
    *       1) Assign first element of frontier to "N"
    *       2) If N is goal, return SUCCESS (and path)
    *       3) Remove N from frontier
    *       4) Add children of N to frontier
    *       5) Sort Q by f(n)
    * 3. return FAILURE
    * */
    public void solve(){
        getMoves(root);
    }
}