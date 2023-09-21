import java.io.*;

public class SequentialFileCopy {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String sourceFilePath1 = "source_file1.txt";
        String sourceFilePath2 = "source_file2.txt";
        String destinationPath = "destination_folder/";

        copyFile(sourceFilePath1, destinationPath + "copied_file1.txt");
        copyFile(sourceFilePath2, destinationPath + "copied_file2.txt");

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Sequential execution time: " + executionTime + " milliseconds");
    }

    public static void copyFile(String sourcePath, String destinationPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
