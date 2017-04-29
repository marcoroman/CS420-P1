import java.util.*;

/**
 * Created by marco on 4/19/2017.
 */
public class AStar {

    BoardNode root;

    //THE COMPARATOR CLASS ALLOWS THE PRIORITY QUEUE TO SORT BOARD NODES BY THEIR EVALUATION FUNCTIONS
    //FRONTIER HOLDS NODES THAT CAN BE EXPLORED, EXPLORED HASHMAP HOLDS NODES THAT HAVE BEEN EXPANDED
    //GOALH2 IS PASSED INTO BOARD NODE OBJECT FOR CALCULATION OF H2
    //SELECTH DETERMINES WHICH HEURISTIC WILL BE USED
    //GOAL_KEY IS USED TO CHECK IF GOAL STATE HAS BEEN REACHED
    //PATH IS USED TO RETURN THE SOLUTION PATH

    Comparator<BoardNode> fCompare = new functionCompare();
    PriorityQueue<BoardNode> frontier = new PriorityQueue<>(fCompare);
    HashMap<String, int[][]> explored = new HashMap<>();
    static ArrayList<int[]> goalH2 = new ArrayList<>();
    private static boolean selectH;
    private final String GOAL_KEY = "012345678";
    private ArrayList<BoardNode> path = new ArrayList<>();
    private static int treeSize;

    //CONSTRUCTOR INSTANTIATES MANHATTAN DISTANCES IF BOOLEAN = TRUE AND SETS ROOT NODE FOR SEARCH
    //IF BOOLEAN IS FALSE, # OF MISPLACED TILES IS USED
    public AStar(int[][] a, boolean b){

        selectH = b;
        treeSize = 0;

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
            BoardNode test = new BoardNode(state, bn);

            if(!explored.containsKey(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }

        if(y > 0){
            state = copy(bn.getBoard());

            moveLeft(state, x, y);
            BoardNode test = new BoardNode(state, bn);

            if(!explored.containsKey(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }

        if(x < 2){
            state = copy(bn.getBoard());

            moveDown(state, x, y);
            BoardNode test = new BoardNode(state, bn);

            if(!explored.containsKey(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
        }

        if(x > 0){
            state = copy(bn.getBoard());

            moveUp(state, x, y);
            BoardNode test = new BoardNode(state, bn);

            if(!explored.containsKey(makeKey(test.getBoard()))) {
                frontier.add(test);
                ++treeSize;
            }
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

    //RETURNS SIZE OF THE SEARCH TREE
    public int getTreeSize(){
        return treeSize;
    }

    //MAIN A* ALGORITHM LOOP
    public void solve(){
        frontier.add(root);
        ++treeSize;

        while(!frontier.isEmpty()){
            if(makeKey(frontier.peek().getBoard()).equals(GOAL_KEY)){
                BoardNode success = frontier.peek();

                trace(success);
                displaySolution();
                break;
            }else{
                BoardNode temp = frontier.remove();
                explored.put(makeKey(temp.getBoard()), temp.getBoard());
                getMoves(temp);
            }
        }
    }

    //A* USED FOR TEST CASES; DOES NOT TRACE PATH OR DISPLAY SOLUTIONS
    //MODIFY TO KEEP TRACK OF REQUIRED VARIABLES FOR COMPARISONS
    public void solveTest(){
        treeSize = 0;

        frontier.add(root);
        ++treeSize;

        while(!frontier.isEmpty()){
            if(makeKey(frontier.peek().getBoard()).equals(GOAL_KEY)){
                BoardNode success = frontier.peek();
                break;
            }else{
                BoardNode temp = frontier.remove();
                explored.put(makeKey(temp.getBoard()), temp.getBoard());
                getMoves(temp);
            }
        }
    }

    //TRACES THE ANCESTRY OF THE GOAL NODE AND STORES THIS PATH IN AN ARRAY LIST
    public void trace(BoardNode n){
        BoardNode step = n;

        while(step != null){
            path.add(step);
            step = step.getParent();
        }
    }

    //ITERATES THROUGH THE SOLUTION PATH TO PRINT EACH BOARD CONFIGURATION
    public void displaySolution(){
        Collections.reverse(path);

        for(int i = 0; i < path.size(); ++i){
            path.get(i).print();
        }
    }
}