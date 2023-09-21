import java.io.*;
import java.util.concurrent.Semaphore;

public class ParallelFileCopy {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String sourceFilePath1 = "source_file1.txt";
        String sourceFilePath2 = "source_file2.txt";
        String destinationPath = "destination_folder/";
        Semaphore semaphore = new Semaphore(1); // Создаем семафор с одним разрешением (только один поток может войти)

        Thread thread1 = new Thread(() -> {
            try {
                semaphore.acquire(); // Захватываем разрешение семафора
                copyFile(sourceFilePath1, destinationPath + "copied_file1.txt");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(); // Освобождаем разрешение семафора
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                semaphore.acquire(); // Захватываем разрешение семафора
                copyFile(sourceFilePath2, destinationPath + "copied_file2.txt");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(); // Освобождаем разрешение семафора
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Parallel execution time: " + executionTime + " milliseconds");
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
