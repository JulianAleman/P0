
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
        System.out.println("True");
    }

    


    private void parseVariableDeclaration() {
        while (currentToken != null && currentToken.getType() == Type.VARIABLE) {
            advance(); 
        }

        if (!expect(Type.PIPE)) {
            error("");
        }
    }

    private void parseProcedure() {
        while (currentToken!=null && currentToken.getType()==Type.VARIABLE){
			if (!expect(Type.VARIABLE)) {
				error("");
			}
	
			if (expect(Type.COLON)) {
				if (!expect(Type.VARIABLE)) {
					error("");
				}
			}
			
		}
		
        if (!expect(Type.OPEN_BRACKET)) {
            error("");
        }
		if (expect(Type.PIPE)) {  
			while (expect(Type.VARIABLE) || expect(Type.COMMA)) {} 
			if (!expect(Type.PIPE)) {
				error("");
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
            error("");
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
            error("");
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
		if (expect(Type.COLON)){error("");}
		if (expect(Type.VARIABLE)){error("");}
	}
	private void indir(){
		if (!expect(Type.COLON)){error("");}
		if (expect(Type.NORTH) ||expect(Type.SOUTH)||expect(Type.WEST)||expect(Type.EAST)){}
		else{error("");}
	}
	private void tothe(){
		if (expect(Type.COLON)){}
		if (expect(Type.LEFT)||expect(Type.RIGHT)||expect(Type.FRONT)||expect(Type.BACK)){}
		else{error("");}
	}
	private void parsePutStatement(){
		if (!expect(Type.COLON)){error("");}
		if (!expect(Type.VARIABLE)){error("");}
		if (!expect(Type.OT)){error("");}
		if (!expect(Type.COLON)){error("");}
		if (expect(Type.CHIPS)||expect(Type.BALLOONS)){}
		else{error("");}
		if (!expect(Type.PERIOD)){
			error("");
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
			error("");

			}
		if (!expect(Type.COLON)){
			error("");
		} 
		if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
		else {
			error("");
		}
		if (!expect(Type.PERIOD)) {
	        error("");
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
			error("");
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
        error("");
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
            error("");
        }
        if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
        else {
			error("");
		}
        while (expect(Type.AND)) {
            if (!expect(Type.COLON)) {
                error("");
            }
            if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
            else {
    			error("");
    		}
        }
        if (!expect(Type.PERIOD)) {
            error("");
        }
    }

    private void parseIfStatement() {
    	if (!expect(Type.COLON)) {
            error("");
    	}
    	parseCondition();
        if (!expect(Type.THEN)) {
            error("");
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
				error("");
			}
			parseCondition();
			if (!expect(Type.DO)) {
				error("");
			}
			if(!expect(Type.COLON)){
				error("");
			}
			
			parseBlock();
		
    }

    private void parseRepeatStatement() {
    	if (!expect(Type.COLON)) {
            error("");
    	}
    	if (expect(Type.NUMBER) || expect(Type.VARIABLE)) {}
    	else {
			error("");
		}
        if (!expect(Type.REPEAT)) {
            error("");
        }
        parseBlock();
	
    }

    private void parseBlock() {
        if (!expect(Type.OPEN_BRACKET)) {
            error("");
        }

        while (currentToken != null && currentToken.getType() != Type.CLOSE_BRACKET) {
            parseStatement();
        }

        if (!expect(Type.CLOSE_BRACKET)) {
            error("");
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
    	throw new RuntimeException("False!!");
    }
   
}
