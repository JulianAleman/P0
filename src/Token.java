
public class Token {
    public final Type tipo;
    public final String text;
    
    public Token(Type type, String text) {
        this.tipo = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Token { " + tipo + ": '" + text + "' }";
    }

	public Type getType() {
		return tipo;
	}

	public String getText() {
		return text;
	}
}
