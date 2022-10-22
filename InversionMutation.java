//import java.util.Arrays;
//import java.util.*;
//
//public class InversionMutation {
//    public static List<Integer> inversionMutation(List<Integer> arr){
//
//        List<Integer> temp = arr;
//        int pivot1 = Configuration.INSTANCE.randomGenerator.nextInt(arr.size()-1);
//        int pivot2 = Configuration.INSTANCE.randomGenerator.nextInt(arr.size()-1);
//
//        if (pivot1 > pivot2){
//            int temp3 = pivot2;
//            pivot2 = pivot1;
//            pivot1 = temp3;
//        } else if (pivot1 == pivot2){
//            if(pivot1 == arr.size()-1){
//                pivot1--;
//            } else{
//                pivot2++;
//            }
//
//        }
//
//        List<Integer> temp2 = arr.subList(pivot1,pivot2);
//        for(int customer : temp2){
//            temp.set(pivot1, customer);
//            pivot1++;
//        }
//        return temp;
//    }
//}