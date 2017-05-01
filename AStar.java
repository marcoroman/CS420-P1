import java.util.*;

/**
 * Created by marco on 4/19/2017.
 */
public class AStar {

    //THE COMPARATOR CLASS ALLOWS THE PRIORITY QUEUE TO SORT BOARD NODES BY THEIR EVALUATION FUNCTIONS
    //FRONTIER HOLDS NODES THAT CAN BE EXPLORED, EXPLORED HASHSET HOLDS NODES THAT HAVE BEEN EXPANDED
    //GOALH2 IS PASSED INTO BOARD NODE OBJECT FOR CALCULATION OF H2
    //SELECTH DETERMINES WHICH HEURISTIC WILL BE USED
    //GOAL_KEY IS USED TO CHECK IF GOAL STATE HAS BEEN REACHED
    //PATH IS USED TO RETURN THE SOLUTION PATH

    private BoardNode root;
    private Comparator<BoardNode> fCompare = new functionCompare();
    private PriorityQueue<BoardNode> frontier = new PriorityQueue<>(fCompare);
    private HashSet<String> explored = new HashSet<>();
    static ArrayList<int[]> goalH2 = new ArrayList<>();
    private static boolean selectH;
    private static boolean testing;
    private final String GOAL_KEY = "012345678";
    private ArrayList<BoardNode> path = new ArrayList<>();
    private static int treeSize;
    private static long time;

    //CONSTRUCTOR INSTANTIATES MANHATTAN DISTANCES IF BOOLEAN = TRUE AND SETS ROOT NODE FOR SEARCH
    //IF BOOLEAN IS FALSE, # OF MISPLACED TILES IS USED
    public AStar(int[][] a){

        selectH = false;
        testing = false;
        treeSize = 0;

        root = new BoardNode(a);
    }

    //************************************MUTATORS & ACCESSORS************************************

    //BOOLEAN VALUE THAT ACTS AS A SWITCH TO INDICATE WHETHER TEST CASES ARE BEING RUN
    public void isTesting(boolean b){testing = b;}

    //BOOLEAN VALUE THAT ACTS AS A SWITCH TO INDICATE WHICH HEURISTIC IS BEING USED
    //FALSE = H1, TRUE = H2
    public void setH(boolean b){
        selectH = b;
    }

    //ACCESSED BY BOARDNODE TO DETERMINE WHICH HEURISTIC TO USE
    public static boolean getH(){
        return selectH;
    }

    //RETURNS EXECUTION TIME OF A* SEARCH (FOR TESTING)
    public static long getTime(){
        return time;
    }

    //RETURNS SIZE OF THE SEARCH TREE
    public int getTreeSize(){
        return treeSize;
    }

    //USED BY BOARD NODE CLASS TO CALCULATE MANHATTAN DISTANCES FOR EACH NODE
    public static ArrayList<int[]> getGoalH2() {
        return goalH2;
    }

    //************************************A* METHODS************************************

    //MAIN A* ALGORITHM LOOP
    public void solve(){
        reset();

        frontier.add(root);
        ++treeSize;

        if(selectH)
            useH2();

        long start = System.currentTimeMillis();

        //A* SEARCH
        while(!frontier.isEmpty()){
            if(makeKey(frontier.peek().getBoard()).equals(GOAL_KEY)){
                BoardNode success = frontier.peek();

                long stop = System.currentTimeMillis();
                time  = stop - start;

                if(!testing) {
                    trace(success);
                    displaySolution();
                }
                break;
            }else{
                BoardNode temp = frontier.remove();
                explored.add(makeKey(temp.getBoard()));
                getMoves(temp);
            }
        }
    }

    //GENERATES CHILDREN OF GIVEN NODE
    private void getMoves(BoardNode bn){
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
            BoardNode test = new BoardNode(state, bn);

            if(!explored.contains(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }

        if(y > 0){
            state = copy(bn.getBoard());

            moveLeft(state, x, y);
            BoardNode test = new BoardNode(state, bn);

            if(!explored.contains(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }

        if(x < 2){
            state = copy(bn.getBoard());

            moveDown(state, x, y);
            BoardNode test = new BoardNode(state, bn);

            if(!explored.contains(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }

        if(x > 0){
            state = copy(bn.getBoard());

            moveUp(state, x, y);
            BoardNode test = new BoardNode(state, bn);

            if(!explored.contains(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }
    }

    //THESE METHODS MOVE THE BLANK AROUND THE BOARD
    private void moveUp(int[][] arr, int x, int y){
        arr[x][y] = arr[x - 1][y];
        arr[x - 1][y] = 0;
    }

    private void moveDown(int[][] arr, int x, int y){
        arr[x][y] = arr[x + 1][y];
        arr[x + 1][y] = 0;
    }

    private void moveLeft(int[][] arr, int x, int y){
        arr[x][y] = arr[x][y - 1];
        arr[x][y - 1] = 0;
    }

    private void moveRight(int[][] arr, int x, int y){
        arr[x][y] = arr[x][y + 1];
        arr[x][y + 1] = 0;
    }

    //************************************AUXILIARY METHODS************************************

    //CREATING THE KEY FOR STORING SEQUENCE OF 2D ARRAY IN HASHSET
    private static String makeKey(int[][] arr){
        String str = "";

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j) {
                str += arr[i][j];
            }
        }

        return str;
    }

    //AUXILIARY METHOD FOR CREATING A COPY OF THE BOARD
    private int[][] copy(int[][] arr){
        int[][] copied = new int[3][3];

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                copied[i][j] = arr[i][j];
            }
        }

        return copied;
    }

    //TRACES THE ANCESTRY OF THE GOAL NODE AND STORES THIS PATH IN AN ARRAY LIST
    private void trace(BoardNode n){
        BoardNode step = n;

        while(step != null){
            path.add(step);
            step = step.getParent();
        }
    }

    //ITERATES THROUGH THE SOLUTION PATH TO PRINT EACH BOARD CONFIGURATION
    private void displaySolution(){
        Collections.reverse(path);

        for(int i = 0; i < path.size(); ++i){
            path.get(i).print();
        }
    }

    //CLEARING ALL FIELDS TO ALLOW FOR REPEATING ALGORITHM
    private void reset(){
        treeSize = 0;
        frontier.clear();
        explored.clear();
        path.clear();
    }

    //INITIALIZES GOAL POSITIONS FOR NUMBERS ONLY IN THE CASE OF THE SECOND HEURISTIC
    private void useH2(){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int[] arr = new int[2];
                arr[0] = i;
                arr[1] = j;
                goalH2.add(arr);
            }
        }
    }
}