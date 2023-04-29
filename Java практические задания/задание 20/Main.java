import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int m = 5;
        int n = 6;

        int[][] arr = new int[m][n];

        for (int i = 0; i < m / 2 + 1; i++) {
            for (int j = i; j < n - i; j++) {
                arr[i][j] = n - 2 * i;
                arr[m - i - 1][j] = n - 2 * i;
            }
            for (int j = i + 1; j < m - i - 1; j++) {
                arr[j][i] = m - 2 * i;
                arr[j][n - i - 1] = m - 2 * i;
            }
        }

        
        saveArrayToFile(arr);
    }

    private static void saveArrayToFile(int[][] arr) {
        try (FileWriter writer = new FileWriter("array.txt")) {
            for (int[] row : arr) {
                for (int cell : row) {
                    writer.write(cell + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
