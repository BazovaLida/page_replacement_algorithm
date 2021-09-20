import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Kernel kernel = new Kernel(5, 10);
        kernel.modeling();
        writeLogsFile();
    }

    private static void writeLogsFile(){
        try {
            FileWriter logs = new FileWriter("logs.txt");
            logs.write(sb.toString());
            logs.close();
        } catch (IOException e){
            System.out.println("An error occurred while writing logs to the file.");
            e.printStackTrace();
            System.out.println("Logs: \n" + sb.toString());
        }
    }
}