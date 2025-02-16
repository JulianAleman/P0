
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
            } else if (currentToken.getType() == Type.OPEN_BRACKET){
				parseBlock();
			}else {
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
		}  else if (expect(Type.GOTO)) {
			parseGoToStatement();
		} else if (expect(Type.MOVE)){
			parseMOVEStatement();
		} else if (expect(Type.JUMP)){
			parseJUMPtatement();
		} else if (expect(Type.TURN)) {
			parseTurnStatement();
		} else if (expect(Type.FACE)) {
			parseFaceStatement();
		} else if (expect(Type.PICK)) {
			parsePickStatement();
		}else if (expect(Type.NOP)) {  
			expect(Type.PERIOD);  
		}else {
            error("Instrucción desconocida.");
        }
    }

	private void parseMOVEStatement(){
		if (expect(Type.COLON)){}
		if (expect(Type.VARIABLE)||expect(Type.NUMBER)){}
		if (expect(Type.WHERE)){
			where();
		} else if(expect(Type.INDIR)){
			indir();
		} else if(expect(Type.TOTHE)){
			tothe();
		}
		if(expect(Type.PERIOD)){}

	}
	private void parseJUMPtatement(){
		if (expect(Type.COLON)){}
		if (expect(Type.VARIABLE)||expect(Type.NUMBER)){}
		if (expect(Type.WHERE)){
			where();
		} else if(expect(Type.INDIR)){
			indir();
		} else if(expect(Type.TOTHE)){
			tothe();
		}
		if(expect(Type.PERIOD)){}
	}
	private void where(){
		if (expect(Type.COLON)){}
		if (expect(Type.VARIABLE)){}
	}
	private void indir(){
		if (expect(Type.COLON)){}
		if (expect(Type.NORTH) ||expect(Type.SOUTH)||expect(Type.WEST)||expect(Type.EAST)){}
	}
	private void tothe(){
		if (expect(Type.COLON)){}
		if (expect(Type.LEFT)||expect(Type.RIGHT)||expect(Type.FRONT)||expect(Type.BACK)){}
	}
	private void parsePutStatement(){
		if (expect(Type.COLON)){}
		if (expect(Type.VARIABLE)){}
		if (expect(Type.OT)){}
		if (expect(Type.COLON)){}
		if (expect(Type.CHIPS)||expect(Type.BALLOONS)){}
		if (expect(Type.PERIOD)){
		}
		else{
			error("A");
		}
		
	}
	
	private void parseGoToStatement() {
		if (!expect(Type.COLON)) {
			error("");
		    }
		if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
		else {
			error("");
		}
		if (!expect(Type.WITH)) {
			error("Falta 'with:' en la instrucción 'goto:'.");

			}
		if (!expect(Type.COLON)){
			error("");
		} 
		if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
		else {
			error("");
		}
		if (!expect(Type.PERIOD)) {
	        error("Se esperaba '.' al final de la instrucción 'goto:'.");
	    }
	}

	private void parseTurnStatement() {
		if (!expect(Type.COLON)) {
			error("");
	    }
		if (expect(Type.LEFT) || expect(Type.RIGHT) || expect(Type.AROUND)) {}
		else {
			error("");
		}
		if (!expect(Type.PERIOD)) {
			error("Se esperaba '.' al final de la instrucción 'goto:'.");
		}
	}
	
	private void parseFaceStatement() {
		if (!expect(Type.COLON)) {
			error("");
	    	}
		if (expect(Type.NORTH) || expect(Type.EAST) || expect(Type.WEST) || expect(Type.SOUTH) ) {}
		else {
			error("");
		}
		if (!expect(Type.PERIOD)) {
        error("Se esperaba '.' al final de la instrucción 'goto:'.");
			}
	}	
	
	private void parsePickStatement() {
		if (!expect(Type.COLON)) {
			error("");
	    }
		if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
		else {
			error("");
		}
		if (!expect(Type.OT)) {
			error("");
		}
		if (expect(Type.BALLOONS) || expect(Type.CHIPS)) {}
		else {
			error("");
		}
	}
	
    private void parseProcedureCall() {
    	
        if (!expect(Type.COLON)) {
            error("Se esperaba ':' después del nombre del procedimiento.");
        }
        if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
        else {
			error("");
		}
        while (expect(Type.AND)) {
            if (!expect(Type.COLON)) {
                error("Se esperaba ':' después de 'and'.");
            }
            if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
            else {
    			error("");
    		}
        }
        if (!expect(Type.PERIOD)) {
            error("Se esperaba '.' al final de la llamada al procedimiento.");
        }
    }

    private void parseIfStatement() {
    	if (!expect(Type.COLON)) {
            error("Se esperaba ':' después del nombre del procedimiento.");
    	}
    	parseCondition();
        if (!expect(Type.THEN)) {
            error("Falta 'then:' después de la condición.");
        }
		if(!expect(Type.COLON)){
			error("");
		}
        parseBlock();

        if (expect(Type.ELSE)) {
			if(!expect(Type.COLON)){
				error("");
			}
            parseBlock();
        }
    }

    private void parseWhileStatement() {
			if (!expect(Type.COLON)) {
				error("Se esperaba ':' después de 'while'.");
			}
			parseCondition();
			if (!expect(Type.DO)) {
				error("Falta 'do' después de la condición en 'while:'.");
			}
			if(!expect(Type.COLON)){
				error("");
			}
			
			parseBlock();
		
    }

    private void parseRepeatStatement() {
    	if (!expect(Type.COLON)) {
            error("Se esperaba ':' después del nombre del procedimiento.");
    	}
    	if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
    	else {
			error("");
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
    	if (expect(Type.FACING_Q)) {
            parseFacingCondition();
        } else if (expect(Type.CAN_PUT)) {
            parseCanPutCondition();
        } else if (expect(Type.CAN_PICK)) {
            parseCanPickCondition();
        } else if (expect(Type.CAN_MOVE)) {
            parseCanMoveCondition();
        } else if (expect(Type.CAN_JUMP)){
			parseCanJumpCondition();
		} else if (expect(Type.CAN_JUMP)){
			parseNotCondition();
		}
    }
    	
    
    private void parseFacingCondition() {
    	if (!expect(Type.COLON)) {
    		error("");
    	}
    	if (expect(Type.NORTH) || expect(Type.EAST) || expect(Type.WEST) || expect(Type.SOUTH)) {}
    	else {
			error("");
		}
    		
    }
    
    private void parseCanPutCondition() {
    	if (!expect(Type.COLON)) {
    		error("");
    	}
    	if (expect(Type.VARIABLE) || expect(Type.NUMBER)) {}
    	else {
			error("");
		}
    	if (!expect(Type.OT)) {
    		error("");
    	}
    	if (expect(Type.BALLOONS) || expect(Type.CHIPS)) {}
    	else {
			error("");
		}
    }

    private void parseCanPickCondition() {
    	if (!expect(Type.COLON)) {
    		error("");
    	}
    	if (expect(Type.VARIABLE) || expect(Type.NUMBER)) {}
    	else {
			error("");
		}
    	if (!expect(Type.OT)) {
    		error("");
    	}
    	if (expect(Type.BALLOONS) || expect(Type.CHIPS)) {}
    	else {
			error("");
		}
    }

    private void parseCanMoveCondition() {
    	if (!expect(Type.COLON)){
    		error("");
    	}
		if (expect(Type.VARIABLE)||expect(Type.NUMBER)){}
		else {
			error("");
		}
		if(expect(Type.INDIR)){
			indir();
		} else if(expect(Type.TOTHE)){
			tothe();
		}
		else {
			error("");
		}
    }

    private void parseCanJumpCondition() {
    	if (!expect(Type.COLON)){
    		error("");
    	}
		if (expect(Type.VARIABLE)||expect(Type.NUMBER)){}
		else {
			error("");
		}
		if(expect(Type.INDIR)){
			indir();
		} else if(expect(Type.TOTHE)){
			tothe();
		}
		else {
			error("");
		}
    }
    
    private void parseNotCondition () {
    	if (!expect(Type.COLON)){
    		error("");
    	}
    	parseCondition();
    }

    private void error(String message) {
        throw new RuntimeException("❌ Error de sintaxis: " + message + " en " + currentToken);
    }

}
