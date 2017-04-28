/**
 * Created by marco on 4/27/2017.
 */
import java.util.Comparator;

public class functionCompare implements Comparator<BoardNode>{
    public int compare(BoardNode x, BoardNode y){
        if(x.getF() < y.getF()){
            return -1;
        }

        if(x.getF() > y.getF()){
            return 1;
        }

        return 0;
    }
}