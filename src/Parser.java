
import java.util.List;
import java.util.Iterator;

public class Parser {
    private final List<Token> tokens;
    private Iterator<Token> tokenIterator;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenIterator = tokens.iterator();
        advance();
    }

    private void advance() {
        if (tokenIterator.hasNext()) {
            currentToken = tokenIterator.next();
        } else {
            currentToken = null;  // Fin de los tokens
        }
    }

    private boolean expect(Type type) {
        if (currentToken != null && currentToken.getType() == type) {
            advance();
            return true;
        }
        return false;
    }

    public void parse() {
    
        while (currentToken != null) {
			if (expect(Type.PIPE)) {
				parseVariableDeclaration();
			}
            else if (expect(Type.PROC)) {
                parseProcedure();
            } else if (expect(Type.IF)) {
                parseIfStatement();
            } else if (expect(Type.WHILE)) {
                parseWhileStatement();
            } else if (expect(Type.VARIABLE)) {
                parseProcedureCall();
            } else {
                error("Token inesperado: " + currentToken.getText());
            }
        }
        System.out.println("✅ Código válido: No se encontraron errores.");
    }

    
    private void parseVariableDeclaration() {
        while (currentToken != null && currentToken.getType() == Type.VARIABLE) {
            advance(); 
        }

        if (!expect(Type.PIPE)) {
            error("Se esperaba '|' para cerrar la declaración de variables.");
        }
    }

    private void parseProcedure() {
        while (currentToken!=null && currentToken.getType()==Type.VARIABLE){
			if (!expect(Type.VARIABLE)) {
				error("Falta el nombre del procedimiento.");
			}
	
			if (expect(Type.COLON)) {
				if (!expect(Type.VARIABLE)) {
					error("Se esperaba un parámetro después de ':'.");
				}
			}
			
		}
		
        if (!expect(Type.OPEN_BRACKET)) {
            error("Se esperaba '[' después del procedimiento.");
        }
		if (expect(Type.PIPE)) {  
			while (expect(Type.VARIABLE) || expect(Type.COMMA)) {} 
			if (!expect(Type.PIPE)) {
				error("Falta cerrar '|' en la declaración de variables locales.");
			}
		}
		while (currentToken!=null && currentToken.getType()==Type.VARIABLE) {
			if(expect(Type.VARIABLE)){}
			if (expect(Type.ASSIGN) ){}
			if(expect(Type.VARIABLE)){}
			if (expect(Type.PERIOD)) {}
			else{}
		}
		
        while (currentToken != null && currentToken.getType() != Type.CLOSE_BRACKET) {
            parseStatement();
        }

        if (!expect(Type.CLOSE_BRACKET)) {
            error("Se esperaba ']' al final del procedimiento.");
        }
    }

    private void parseStatement() {
        if (expect(Type.IF)) {
            parseIfStatement();
        } else if (expect(Type.WHILE)) {
            parseWhileStatement();
        } else if (expect(Type.REPEAT)) {
            parseRepeatStatement();
        } else if (expect(Type.VARIABLE)) {
            parseProcedureCall();
		
        } else if (expect(Type.PUT)){
			parsePutStatement();
		}
		else {
            error("Instrucción desconocida.");
        }
    }
	private void parsePutStatement(){
		while(currentToken != null && expect(Type.COLON)|| expect(Type.VARIABLE) || expect(Type.OT)|| expect(Type.CHIPS)||expect(Type.BALLOONS))
		
		if (!expect(Type.PERIOD)){}
		
	}
	
    private void parseProcedureCall() {
        while (currentToken != null && (expect(Type.COLON) || expect(Type.NUMBER) || expect(Type.VARIABLE))) {}

        if (!expect(Type.PERIOD)) {
            error("Se esperaba '.' al final de la llamada al procedimiento.");
        }
    }

    private void parseIfStatement() {
        parseCondition();
        if (!expect(Type.THEN)) {
            error("Falta 'then:' después de la condición.");
        }
        parseBlock();

        if (expect(Type.ELSE)) {
            parseBlock();
        }
    }

    private void parseWhileStatement() {
        parseCondition();
        if (!expect(Type.DO)) {
            error("Falta 'do:' después de la condición.");
        }
        parseBlock();
    }

    private void parseRepeatStatement() {
        if (!expect(Type.NUMBER) && !expect(Type.VARIABLE)) {
            error("Se esperaba un número o variable en 'repeatTimes:'.");
        }
        if (!expect(Type.REPEAT)) {
            error("Falta 'repeat:' en la declaración del ciclo.");
        }
        parseBlock();
    }

    private void parseBlock() {
        if (!expect(Type.OPEN_BRACKET)) {
            error("Se esperaba '[' al inicio del bloque.");
        }

        while (currentToken != null && currentToken.getType() != Type.CLOSE_BRACKET) {
            parseStatement();
        }

        if (!expect(Type.CLOSE_BRACKET)) {
            error("Se esperaba ']' al final del bloque.");
        }
    }

    private void parseCondition() {
        if (!(expect(Type.FACING_Q) || expect(Type.BLOCKED_Q) || expect(Type.CAN_MOVE))) {
            error("Condición inválida.");
        }

        if (expect(Type.COLON)) {
            if (!expect(Type.NUMBER) && !expect(Type.VARIABLE)) {
                error("Se esperaba un número o variable después de ':'.");
            }
        }
    }

    private void error(String message) {
        throw new RuntimeException("❌ Error de sintaxis: " + message + " en " + currentToken);
    }

}
