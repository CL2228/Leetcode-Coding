public class Test {

    class multiDimensional
    {

    }

    public static void main(String[] args) {
        int arr[][] = {{2, 4, 6}, {1, 3, 5}, {10, 9, 8}};
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
                System.out.print(arr[i][j] + " " + "\n");

            System.out.println();
        }
    }

}
