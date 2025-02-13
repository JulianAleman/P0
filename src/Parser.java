import java.util.List;

public class Parser {
	private List<Token> tokens;
	private int posicion = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	private Token actual() {
		if (posicion < tokens.size()) {
			return tokens.get(posicion);
		}
		return null;
	}
	
	private boolean aparece(String tipo_esp) {
		Token x = actual();
		if (x != null && x.tipo.equals(tipo_esp)) {
			posicion++;
			return true;
		}
		return false;
	}
	
	public boolean analisis() {
		return estudiarBlocks() && estudiarVariables() && estudiarProcedures();
		}
	
	private boolean estudiarBlocks() {
		if (!aparece("LBRACKET")) {
			return false;
		}
		
		boolean instructs = false;
		while (estudiarInstructions()) {
			instructs = true;
			if (!aparece("DOT")) {
				return false;
			}
		}
		
		if (!aparece("RBRACKET")) {
			return false;
		}
		
		return instructs;
	}
	
	private boolean estudiarVariables() {
		boolean vars = false;
		if (aparece("PIPE")) {
			while (aparece("IDENTIFIER")) {
				vars = true;
			}
		}
		if (!aparece("PIPE")) {
			vars = false;
			}
		return vars;
	}
	
	private boolean estudiarProcedures() {
		boolean procs = false;
		while (aparece("PROC")) {
			if (!aparece("IDENTIFIER")) {
				return false;
			}
			procs = true;
			if (!estudiarParameters() || !estudiarBlocks()) {
				return false;
			}
		}
		return procs;
	}
	
	private boolean estudiarInstructions() {
		return estudiarAssigments() || estudiarProcsCalls() || estudiarComands();
	}
	
	private boolean estudiarParameters() {
		boolean params = false;
		while (!aparece("IDENTIFIER")) {
			params = true;
		}
		return true;
	}
	
	private boolean estudiarAssigments() {
		if (aparece("IDENTIFIER")) {
			if (!aparece("ASSIGN")) {
				return false;
			}
			return aparece("NUMBER") || aparece("IDENTIFIER");
		}
		return false;
	}
	
	private boolean estudiarProcsCalls() {
		if (aparece("IDENTIFIER")) {
			boolean parameters = false;
			while (aparece("NUMBER") || aparece("IDENTIFIER") ) {
				parameters = true;
			}
			return true;
		}
		return false;
	}
	
	private boolean estudiarComands() {
		return aparece("MOVE") || aparece("TURN") || aparece("FACE") || aparece("PICK") || aparece("PUT");
	}
	
	
	
	
	
}