import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sliyanag
 * @created 09/10/2022 - 9:01 AM
 * @project Assignment
 */
public class Assignment3 {
    public static void main(String[] args) {
        int s = 15;
        int sortedArray[] = {1, 2, 4, 7, 11, 15};
        boolean isSumInSeq = SumInSequence(sortedArray, s);
        if(isSumInSeq){
            System.out.println(Arrays.toString(sortedArray)+" is a sum in sequence array");
        }else {
            System.out.println(Arrays.toString(sortedArray)+ " is not a sum in sequence array");
        }
    }
    /**
     * This method not supported for 0 value and negative numbers.
     * @param sortedArray
     * @param s
     * @return
     */
    private static boolean SumInSequence(int sortedArray[], int s) {
        //remove numbers grater than or equal s(therefor this method not valid for 0 and negative includes arrays.
        //if 0 and negative included, please remove below line.
        List<Integer> filteredList = Arrays.stream(sortedArray).filter(val -> val < s).boxed().collect(Collectors.toList());
        //loop filtered list
        for (Integer firstNum : filteredList) {
            //by deducting match second number
            Integer secondNum = filteredList.stream().filter(val -> val == (s - firstNum)).findFirst().orElse(null);
            if (null != secondNum) {
                System.out.println(firstNum + " + " + secondNum + " = " + s);
                return true;
            }
        }
        return false;
    }
}
