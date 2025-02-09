import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filename = "resources/codigoBIEN.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        Lexer lexer = new Lexer(sb.toString());
        for (Token token : lexer.getTokens()) {
            System.out.println(token);
        }
        System.out.println("/////////////////////////////ERRORES/////////////////////////////////////////");
        for (String token : lexer.getErrors()) {
            System.out.println(token);
        }
            //*Parser parser = new Parser(lexer.getTokens());
            //*parser.parse();
    }
}
