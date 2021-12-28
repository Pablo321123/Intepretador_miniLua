package lexical;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable {

    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();

        // SYMBOLS
        st.put(";", TokenType.SEMI_COLON);
        st.put(",", TokenType.COLON);
        st.put(".", TokenType.DOT);
        st.put("(", TokenType.OPEN_PAR);
        st.put(")", TokenType.CLOSE_PAR);
        st.put("[", TokenType.OPEN_BRA);
        st.put("]", TokenType.CLOSE_BRA);
        st.put("{", TokenType.OPEN_CUR);
        st.put("}", TokenType.CLOSE_CUR);

        // OPERATORS
        st.put("=", TokenType.ASSIGN);
        st.put("and", TokenType.AND);
        st.put("or", TokenType.OR);
        st.put("<", TokenType.LOWER_THAN);
        st.put(">", TokenType.GREATER_THAN);
        st.put("<=", TokenType.LOWER_EQUAL);
        st.put(">=", TokenType.GREATER_EQUAL);
        st.put("~=", TokenType.NOT_EQUAL);
        st.put("==", TokenType.EQUAL);
        st.put("..", TokenType.CONCAT);
        st.put("+", TokenType.ADD);
        st.put("-", TokenType.SUB);
        st.put("*", TokenType.MUL);
        st.put("/", TokenType.DIV);
        st.put("%", TokenType.MOD);
        st.put("#", TokenType.SIZE);
        st.put("not", TokenType.NOT);

        // KEYWORDS
        st.put("if", TokenType.IF);
        st.put("then", TokenType.THEN);
        st.put("elseif", TokenType.ELSEIF);
        st.put("else", TokenType.ELSE);
        st.put("end", TokenType.END);
        st.put("while", TokenType.WHILE);
        st.put("do", TokenType.DO);
        st.put("repeat", TokenType.REPEAT);
        st.put("until", TokenType.UNTIL);
        st.put("for", TokenType.FOR);
        st.put("in", TokenType.IN);
        st.put("print", TokenType.PRINT);
        st.put("false", TokenType.FALSE);
        st.put("true", TokenType.TRUE);
        st.put("nil", TokenType.NIL);
        st.put("read", TokenType.READ);
        st.put("tonumber", TokenType.TONUMBER);
        st.put("tostring", TokenType.TOSTRING);
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType find(String token) {
        return this.contains(token) ? st.get(token) : TokenType.ID;
    }
}
