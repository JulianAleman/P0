
import java.util.ArrayList;
import java.util.List;


public class Lexer {
    private final String input;
    private final List<Token> tokens = new ArrayList<>();
    private final List<Token> errorTokens = new ArrayList<>();
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
        Analizador();
    }

    private char revision() {
        return position < input.length() ? input.charAt(position) : '\0';
    }

    private char avanzar() {
        return position < input.length() ? input.charAt(position++) : '\0';
    }

    private void QuitarlosEspacios() {
        while (Character.isWhitespace(revision())) {
            avanzar();
        }
    }

    private String readWord() {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetter(revision()) || revision() == '#') {
            sb.append(avanzar());
        }
        return sb.toString();
    }

    private String readNumber() {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(revision())) {
            sb.append(avanzar());
        }
        return sb.toString();
    }

    public void Analizador() {
        while (position < input.length()) {
            QuitarlosEspacios();
            char current = revision();

            if (Character.isLetter(current) || current == '#') {
                String word = readWord();
                tokens.add(new Token(identifyType(word), word));
            } else if (Character.isDigit(current)) {
                String number = readNumber();
                tokens.add(new Token(Type.NUMBER, number));
            } else {
                switch (current) {
                    case '|': tokens.add(new Token(Type.PIPE, "|")); break;
                    case ':': tokens.add(new Token(Type.COLON, ":")); break;
                    case '.': tokens.add(new Token(Type.PERIOD, ".")); break;
                    case '(': tokens.add(new Token(Type.LPAREN, "(")); break;
                    case ')': tokens.add(new Token(Type.RPAREN, ")")); break;
                    case '{': tokens.add(new Token(Type.LBRACE, "{")); break;
                    case '}': tokens.add(new Token(Type.RBRACE, "}")); break;
                    case '[': tokens.add(new Token(Type.OPEN_BRACKET, "[")); break;
                    case ']': tokens.add(new Token(Type.CLOSE_BRACKET, "]")); break;
                    case ',': tokens.add(new Token(Type.COMMA, ",")); break;
                    case ';': tokens.add(new Token(Type.SEMICOLON, ";")); break;
                    case '=': tokens.add(new Token(Type.EQUALS, "=")); break;
                    default: errorTokens.add(new Token(Type.ERROR, String.valueOf(current)));
                }
                avanzar();
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
            case "pop": return Type.POP;
            case "nop": return Type.NOP;
            case "put": return Type.PUT;
            case "#balloons": return Type.BALLOONS;
            case "#chips": return Type.CHIPS;

            case "blocked?": return Type.BLOCKED_Q;
            case "facing?": return Type.FACING_Q;
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
            default:
                if (Character.isLetter(text.charAt(0))) return Type.VARIABLE;
                return Type.ERROR;
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }
    public List<Token> getErrors(){
        return errorTokens;
    }

}
