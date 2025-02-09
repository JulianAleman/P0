import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;


public class Lexer {
    private  String entrada;
    private final List<Token> tokens = new ArrayList<>();
    private final List<String> errors= new ArrayList<>();

    private static final Pattern patrones = Pattern.compile(
        
        "proc\\s+[a-zA-Z_][a-zA-Z0-9_]" +  
    "\\|" + 
    "goto(?![a-zA-Z])|move(?![a-zA-Z])|jump(?![a-zA-Z])|turn(?![a-zA-Z])|face(?![a-zA-Z])|put(?![a-zA-Z])|pick:(?![a-zA-Z])|" + 
    "canPut(?![a-zA-Z])|canPick(?![a-zA-Z])|canMove(?![a-zA-Z])|canJump(?![a-zA-Z])|" + 
    "toThe(?![a-zA-Z])|inDir(?![a-zA-Z])|ofType(?![a-zA-Z])|" + 
    "if:(?![a-zA-Z])|then(?![a-zA-Z])|else(?![a-zA-Z])|while(?![a-zA-Z])|repeatTimes(?![a-zA-Z])|" + 
    "not:(?![a-zA-Z])|facing(?![a-zA-Z])|blocked\\?(?![a-zA-Z])|" + 
    "#(north|south|west|east|front|back|left|right|balloons|chips)(?![a-zA-Z])|" + 
    "[a-zA-Z_][a-zA-Z0-9_]*|\\d+|[:{};,().=]", 
    Pattern.CASE_INSENSITIVE

    );

    public Lexer(String imput){
        this.entrada = imput;
        analizador();
    }

    private void analizador(){
        Matcher comparacion= patrones.matcher(entrada);
        int caracter_final= 0;
        
        while (caracter_final<entrada.length()){
            comparacion.region(caracter_final, entrada.length());

            if (comparacion.lookingAt()){
                String tokenText= comparacion.group();
                if (tokenText.trim().isEmpty()){
                    caracter_final= comparacion.end();
                    continue;
                }
                Type tipo= identifyType(tokenText);
                if (tipo==null){
                    tipo=Type.ERROR;
                    tokens.add(new Token(tipo, tokenText));
                } else {
                    tokens.add(new Token(tipo, tokenText));
                }
                caracter_final= comparacion.end();
            } 
            else {
                caracter_final++;
            } 
        }
    }
    private Type identifyType(String text) {
        switch (text.toLowerCase()) {
            case "proc": return Type.PROC;
            case "if": return Type.IF;
            case "then": return Type.THEN;
            case "else": return Type.ELSE;
            case "do": return Type.DO;
            case "while": return Type.WHILE;
            case "repeat": return Type.REPEAT;
            case "for": return Type.FOR;
            case "times": return Type.TIMES;
            case "not": return Type.NOT;
    
            case "move": return Type.MOVE;
            case "turn": return Type.TURN;
            case "face": return Type.FACE;
            case "jump": return Type.JUMP;
            case "drop": return Type.DROP;
            case "pick": return Type.PICK;
            case "grab": return Type.GRAB;
            case "letgo": return Type.LET_GO;
            case "pop": return Type.POP;
            case "nop": return Type.NOP;
            case "put": return Type.PUT;
            case "#balloons": return Type.BALLOONS;
            case "#chips": return Type.CHIPS;
    
            case "blocked?": return Type.BLOCKED_Q;
            case "facing?": return Type.FACING_Q;
            case "zero?": return Type.ZERO_Q;
            case "canmove": return Type.CAN_MOVE;
            case "canjump": return Type.CAN_JUMP;
            case "canput": return Type.CAN_PUT;
            case "canpick": return Type.CAN_PICK;
    
            case "#north": return Type.NORTH;
            case "#south": return Type.SOUTH;
            case "#west": return Type.WEST;
            case "#east": return Type.EAST;
            case "front": return Type.FRONT;
            case "back": return Type.BACK;
            case "left": return Type.LEFT;
            case "right": return Type.RIGHT;
            case "indir": return Type.INDIR;   
            case "tothe": return Type.TOTHE;
            case ".": return Type.PERIOD;
            case ":": return Type.COLON;   
            case ":=": return Type.ASSIGN;
            case ",": return Type.COMMA;
            case ";": return Type.SEMICOLON;
            case "{": return Type.LBRACE;
            case "}": return Type.RBRACE;
            case "(": return Type.LPAREN;
            case ")": return Type.RPAREN;
            case "=": return Type.EQUALS;
            case "[": return Type.OPEN_BRACKET;
            case "]": return Type.CLOSE_BRACKET;
            default:
                if (Pattern.matches("\\d+", text)) return Type.NUMBER;
                if (Pattern.matches("[a-zA-Z_][a-zA-Z0-9_]*", text)) return Type.VARIABLE;
                return Type.ERROR;
        }
    }

    public List<Token> getTokens(){
        return tokens; 
    }

    public List<String> getErrors(){
        return errors;
    }
    
    

}

