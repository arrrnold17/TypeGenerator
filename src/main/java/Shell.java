import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Generator generator = new Generator();
        while (true) {
            System.out.println("Please input the path of xml file:");
            Scanner scanner = new Scanner(System.in);
            String path = scanner.nextLine();
            generator.XMLParser(path);
            if (path.equals("exit")) {
                break;
            }
        }
    }
}
