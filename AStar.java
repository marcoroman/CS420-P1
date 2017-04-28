import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by marco on 4/19/2017.
 */
public class AStar {

    BoardNode root;

    //FRONTIER HOLDS NODES THAT CAN BE EXPLORED, EXPLORED HASHMAP HOLDS NODES THAT HAVE BEEN EXPANDED
    PriorityQueue<BoardNode> frontier = new PriorityQueue<>();
    HashMap<String, int[][]> explored = new HashMap<>();

    //PASSED INTO BOARD NODE OBJECT FOR CALCULATION OF H2
    static ArrayList<int[]> goalH2 = new ArrayList<>();

    //CONSTRUCTOR INSTANTIATES MANHATTAN DISTANCES AND SETS ROOT NODE FOR SEARCH
    public AStar(int[][] a){

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                int[] arr = new int[2];
                arr[0] = i; arr[1] = j;
                goalH2.add(arr);
            }
        }

        root = new BoardNode(a);
    }

    //GENERATES CHILDREN OF GIVEN NODE
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
            test.print();
            System.out.println("H2 = " + test.getH2());
        }

        if(y > 0){
            state = copy(bn.getBoard());

            moveLeft(state, x, y);
            BoardNode test = new BoardNode(state);
            test.print();
            System.out.println("H2 = " + test.getH2());
        }

        if(x < 2){
            state = copy(bn.getBoard());

            moveDown(state, x, y);
            BoardNode test = new BoardNode(state);
            test.print();
            System.out.println("H2 = " + test.getH2());
        }

        if(x > 0){
            state = copy(bn.getBoard());

            moveUp(state, x, y);
            BoardNode test = new BoardNode(state);
            test.print();
            System.out.println("H2 = " + test.getH2());
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