package syntatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import interpreter.command.AssignCommand;
import interpreter.command.BlocksCommand;
import interpreter.command.Command;
import interpreter.command.GenericForCommand;
import interpreter.command.IfCommand;
import interpreter.command.NumericForCommand;
import interpreter.command.PrintCommand;
import interpreter.command.RepeatCommand;
import interpreter.command.WhileCommand;
import interpreter.expr.ArithExpr;
import interpreter.expr.ArithOp;
import interpreter.expr.ComparativeExpr;
import interpreter.expr.ComparativeOp;
import interpreter.expr.ConstExpr;
import interpreter.expr.Expr;
import interpreter.expr.SetExpr;
import interpreter.expr.UnaryExpr;
import interpreter.expr.UnaryOp;
import interpreter.expr.Variable;
import interpreter.value.BooleanValue;
import interpreter.value.NumberValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.TokenType;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() {
        BlocksCommand cmds = procCode();
        eat(TokenType.END_OF_FILE);

        return cmds;
    }

    private void advance() {
        System.out.println("Advanced (\"" + current.token + "\", " + current.type + ")");
        current = lex.nextToken();
    }

    private void eat(TokenType type) {
        System.out.println("Expected (..., " + type + "), found (\"" + current.token + "\", " + current.type + ")");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }

    // <code> ::= { <cmd> }
    private BlocksCommand procCode() {
        int line = lex.getLine();
        List<Command> cmds = new ArrayList<Command>();
        while (current.type == TokenType.IF || current.type == TokenType.WHILE || current.type == TokenType.REPEAT
                || current.type == TokenType.FOR || current.type == TokenType.PRINT || current.type == TokenType.ID) {
            Command cmd = procCmd();
            cmds.add(cmd);
        }

        BlocksCommand bc = new BlocksCommand(line, cmds);
        return bc;
    }

    // <cmd> ::= (<if> | <while> | <repeat> | <for> | <print> | <assign>) [';']
    private Command procCmd() {
        Command cmd = null;
        if (current.type == TokenType.IF) {
            cmd = procIf();
        } else if (current.type == TokenType.WHILE) {
            cmd = procWhile();
        } else if (current.type == TokenType.REPEAT) {
            cmd = procRepeat();
        } else if (current.type == TokenType.FOR) {
            cmd = procFor();
        } else if (current.type == TokenType.PRINT) {
            cmd = procPrint();
        } else if (current.type == TokenType.ID) {
            cmd = procAssign();
        } else {
            showError();
        }

        if (current.type == TokenType.SEMI_COLON)
            advance();

        return cmd;
    }

    // <if> ::= if <expr> then <code> { elseif <expr> then <code> } [ else <code> ]
    // end
    private IfCommand procIf() {

        int line = lex.getLine();
        IfCommand ifc = null;

        eat(TokenType.IF);

        Expr expr = procExpr();

        eat(TokenType.THEN);
        Command thenCmds = procCode();
        ifc = new IfCommand(line, expr, thenCmds);

        while (current.type == TokenType.ELSEIF) {
            advance();
            procExpr();
            eat(TokenType.THEN);
            thenCmds = procCode();
        }

        if (current.type == TokenType.ELSE) {
            advance();
            ifc.setElseCommand(procCode());
        }

        eat(TokenType.END);
        return ifc;
    }

    // <while> ::= while <expr> do <code> end
    private WhileCommand procWhile() {
        eat(TokenType.WHILE);
        int line = lex.getLine();

        Expr expr = procExpr();
        eat(TokenType.DO);
        Command cmd = procCode();
        eat(TokenType.END);

        WhileCommand wc = new WhileCommand(line, expr, cmd);
        return wc;
    }

    // <repeat> ::= repeat <code> until <expr>
    private RepeatCommand procRepeat() {

        int line = lex.getLine();
        eat(TokenType.REPEAT);
        Command cmd = procCode();
        eat(TokenType.UNTIL);
        Expr expr = procExpr();

        RepeatCommand rc = new RepeatCommand(line, cmd, expr);

        return rc;
    }

    // <for> ::= for <name> (('=' <expr> ',' <expr> [',' <expr>]) | ([',' <name>] in
    // <expr>)) do <code> end
    private Command procFor() {

        eat(TokenType.FOR);

        int line = lex.getLine();
        Command forCommand = null;
        boolean isNumericFor = false;

        Vector<Expr> exprs = new Vector<Expr>();
        Vector<Variable> names = new Vector<Variable>();

        Variable var = procName();

        names.add(var);

        if (current.type == TokenType.ASSIGN) {
            eat(TokenType.ASSIGN);
            Expr start = procExpr();
            exprs.add(start);
            advance();
            Expr end = procExpr();
            exprs.add(end);

            if (current.type == TokenType.COLON) {
                advance();
                Expr step = procExpr();
                exprs.add(step);
            }

            isNumericFor = true;

        } else {

            if (current.type == TokenType.COLON) {
                advance();
                Variable var2 = procName();
                names.add(var2);
            }

            eat(TokenType.IN);
            Expr expr = procExpr();
            exprs.add(expr);

            isNumericFor = false;
        }

        eat(TokenType.DO);

        Command cmds = procCode();
        forCommand = isNumericFor ? new NumericForCommand(line, var, exprs.get(0), exprs.get(1), exprs.get(2), cmds)
                : new GenericForCommand(line, names.get(0), names.get(1), exprs.get(0), cmds);

        eat(TokenType.END);

        return forCommand;
    }

    // <print> ::= print '(' [ <expr> ] ')'
    private PrintCommand procPrint() {
        eat(TokenType.PRINT);
        int line = lex.getLine();
        eat(TokenType.OPEN_PAR);

        Expr expr = null;
        if (current.type == TokenType.OPEN_PAR || current.type == TokenType.SUB || current.type == TokenType.SIZE
                || current.type == TokenType.NOT || current.type == TokenType.NUMBER || current.type == TokenType.STRING
                || current.type == TokenType.FALSE || current.type == TokenType.TRUE || current.type == TokenType.NIL
                || current.type == TokenType.READ || current.type == TokenType.TONUMBER
                || current.type == TokenType.TOSTRING || current.type == TokenType.OPEN_CUR
                || current.type == TokenType.ID) {
            expr = procExpr();
        }

        eat(TokenType.CLOSE_PAR);

        PrintCommand pc = new PrintCommand(line, expr);
        return pc;
    }

    // <assign> ::= <lvalue> { ',' <lvalue> } '=' <expr> { ',' <expr> }
    private AssignCommand procAssign() {
        Vector<SetExpr> lhs = new Vector<SetExpr>();
        Vector<Expr> rhs = new Vector<Expr>();

        lhs.add(procLValue());
        while (current.type == TokenType.COLON) {
            advance();
            lhs.add(procLValue());
        }
        eat(TokenType.ASSIGN);
        int line = lex.getLine();

        rhs.add(procExpr());
        while (current.type == TokenType.COLON) {
            advance();
            rhs.add(procExpr());
        }

        AssignCommand ac = new AssignCommand(line, lhs, rhs);
        return ac;
    }

    // <expr> ::= <rel> { (and | or) <rel> }
    private Expr procExpr() {
        int line = lex.getLine();
        Expr expr = procRel(), expr2;

        ComparativeOp op = null;
        ComparativeExpr compExpr = null;

        while (current.type == TokenType.AND || current.type == TokenType.OR) {
            if (current.type == TokenType.AND) {
                op = ComparativeOp.AND;
            } else {
                op = ComparativeOp.OR;
            }
            advance();
            expr2 = procRel();
            compExpr = new ComparativeExpr(line, expr, op, expr2);
            expr = compExpr;
        }
        return expr;
    }

    // <rel> ::= <concat> [ ('<' | '>' | '<=' | '>=' | '~=' | '==') <concat> ]
    private Expr procRel() {
        Expr expr = procConcat(), expr2;

        ComparativeOp op = null;
        ComparativeExpr compExpr = null;

        if (current.type == TokenType.LOWER_THAN) {
            advance();
            expr2 = procConcat();
            op = ComparativeOp.LOWER_THAN;
            compExpr = new ComparativeExpr(lex.getLine(), expr, op, expr2);
            return compExpr;
        } else if (current.type == TokenType.GREATER_THAN) {
            advance();
            expr2 = procConcat();
            op = ComparativeOp.GREATER_THAN;
            compExpr = new ComparativeExpr(lex.getLine(), expr, op, expr2);
            return compExpr;
        } else if (current.type == TokenType.LOWER_EQUAL) {
            advance();
            expr2 = procConcat();
            op = ComparativeOp.LOWER_EQUAL;
            compExpr = new ComparativeExpr(lex.getLine(), expr, op, expr2);
            return compExpr;
        } else if (current.type == TokenType.GREATER_EQUAL) {
            advance();
            expr2 = procConcat();
            op = ComparativeOp.GREATER_EQUAL;
            compExpr = new ComparativeExpr(lex.getLine(), expr, op, expr2);
            return compExpr;
        } else if (current.type == TokenType.NOT_EQUAL) {
            advance();
            expr2 = procConcat();
            op = ComparativeOp.NOT_EQUAL;
            compExpr = new ComparativeExpr(lex.getLine(), expr, op, expr2);
            return compExpr;
        } else if (current.type == TokenType.EQUAL) {
            advance();
            expr2 = procConcat();
            op = ComparativeOp.EQUAL;
            compExpr = new ComparativeExpr(lex.getLine(), expr, op, expr2);
            return compExpr;
        } else {
            return expr;
        }
    }

    // <concat> ::= <arith> { '..' <arith> }
    private Expr procConcat() {
        Expr expr = procArith();
        int line = lex.getLine();

        ArithExpr concatExpr = null;
        ArithOp op = null;

        while (current.type == TokenType.CONCAT) {
            advance();
            Expr expr2 = procArith();
            op = ArithOp.CONCAT;
            concatExpr = new ArithExpr(line, expr, op, expr2);
            expr = concatExpr;
        }

        return expr;
    }

    // <arith> ::= <term> { ('+' | '-') <term> }
    private Expr procArith() {
        Expr expr = procTerm(), expr2;

        ArithExpr arithExpr = null;
        ArithOp op = null;

        while (current.type == TokenType.ADD || current.type == TokenType.SUB) {
            if (current.type == TokenType.ADD) {
                op = ArithOp.ADD;
            } else {
                op = ArithOp.SUB;
            }
            advance();
            expr2 = procTerm();
            arithExpr = new ArithExpr(lex.getLine(), expr, op, expr2);
            expr = arithExpr;
        }

        return expr;
    }

    // <term> ::= <factor> { ('*' | '/' | '%') <factor> }
    private Expr procTerm() {
        Expr expr = procFactor(), expr2;

        ArithExpr arithExpr = null;
        ArithOp op = null;

        while (current.type == TokenType.MUL || current.type == TokenType.DIV || current.type == TokenType.MOD) {
            if (current.type == TokenType.MUL) {
                op = ArithOp.MUL;
            } else if (current.type == TokenType.DIV) {
                op = ArithOp.DIV;
            } else {
                op = ArithOp.MOD;
            }
            advance();
            expr2 = procFactor();
            arithExpr = new ArithExpr(lex.getLine(), expr, op, expr2);
            expr = arithExpr;
        }

        return expr;
    }

    // <factor> ::= '(' <expr> ')' | [ '-' | '#' | not ] <rvalue>
    private Expr procFactor() {
        Expr expr = null;
        if (current.type == TokenType.OPEN_PAR) {

            advance();
            procExpr();
            eat(TokenType.CLOSE_PAR);

        } else {
            UnaryOp op = null;
            if (current.type == TokenType.SUB) {
                advance();
                op = UnaryOp.Neg;
            } else if (current.type == TokenType.SIZE) {
                advance();
                op = UnaryOp.Size;
            } else if (current.type == TokenType.NOT) {
                advance();
                op = UnaryOp.Not;
            }
            int line = lex.getLine();

            expr = procRValue();

            if (op != null)
                expr = new UnaryExpr(line, expr, op);
        }

        return expr;
    }

    // <lvalue> ::= <name> { '.' <name> | '[' <expr> ']' }
    private SetExpr procLValue() {
        Variable var = procName();

        while (current.type == TokenType.DOT || current.type == TokenType.OPEN_BRA) {
            if (current.type == TokenType.DOT) {
                advance();
                procName();
            } else {
                advance();
                procExpr();
                eat(TokenType.CLOSE_BRA);
            }
        }

        return var;
    }

    // <rvalue> ::= <const> | <function> | <table> | <lvalue>
    private Expr procRValue() {
        Expr expr = null;
        if (current.type == TokenType.NUMBER || current.type == TokenType.STRING || current.type == TokenType.FALSE
                || current.type == TokenType.TRUE || current.type == TokenType.NIL) {
            Value<?> v = procConst();
            int line = lex.getLine();
            expr = new ConstExpr(line, v);
        } else if (current.type == TokenType.READ || current.type == TokenType.TONUMBER
                || current.type == TokenType.TOSTRING) {
            expr = procFunction();
        } else if (current.type == TokenType.OPEN_CUR) {
            procTable();
        } else if (current.type == TokenType.ID) {
            expr = procLValue();
        } else {
            showError();
        }

        return expr;
    }

    // <const> ::= <number> | <string> | false | true | nil
    private Value<?> procConst() {
        Value<?> v = null;
        if (current.type == TokenType.NUMBER) {
            v = procNumber();
        } else if (current.type == TokenType.STRING) {
            v = procString();
        } else if (current.type == TokenType.FALSE) {
            advance();
            v = new BooleanValue(false);
        } else if (current.type == TokenType.TRUE) {
            advance();
            v = new BooleanValue(true);
        } else if (current.type == TokenType.NIL) {
            advance();
            v = null;
        } else {
            showError();
        }

        return v;
    }

    // <function> ::= (read | tonumber | tostring) '(' [ <expr> ] ')'
    private UnaryExpr procFunction() {

        Expr expr = null;

        UnaryOp op = null;

        if (current.type == TokenType.READ) {
            advance();
            op = UnaryOp.Read;
        } else if (current.type == TokenType.TONUMBER) {
            advance();
            op = UnaryOp.ToNumber;
        } else if (current.type == TokenType.TOSTRING) {
            advance();
            op = UnaryOp.ToString;
        } else {
            showError();
        }

        int line = lex.getLine();

        eat(TokenType.OPEN_PAR);

        if (current.type == TokenType.OPEN_PAR || current.type == TokenType.SUB || current.type == TokenType.SIZE
                || current.type == TokenType.NOT || current.type == TokenType.NUMBER || current.type == TokenType.STRING
                || current.type == TokenType.FALSE || current.type == TokenType.TRUE || current.type == TokenType.NIL
                || current.type == TokenType.READ || current.type == TokenType.TONUMBER
                || current.type == TokenType.TOSTRING || current.type == TokenType.OPEN_CUR
                || current.type == TokenType.ID) {
            expr = procExpr();
        }

        eat(TokenType.CLOSE_PAR);

        UnaryExpr unaryExpr = new UnaryExpr(line, expr, op);
        return unaryExpr;

    }

    // <table> ::= '{' [ <elem> { ',' <elem> } ] '}'
    private void procTable() {
        eat(TokenType.OPEN_CUR);
        if (current.type == TokenType.CLOSE_CUR) {
            eat(TokenType.CLOSE_CUR);
        } else {
            procElem();
            while (current.type == TokenType.COLON) {
                advance();
                procElem();
            }
            eat(TokenType.CLOSE_CUR);
        }

    }

    // <elem> ::= [ '[' <expr> ']' '=' ] <expr>
    private void procElem() {
        if (current.type == TokenType.OPEN_BRA) {
            advance();
            procExpr();
            eat(TokenType.CLOSE_BRA);
        }
        eat(TokenType.EQUAL);
        procExpr();
    }

    private Variable procName() {
        String name = current.token;
        eat(TokenType.ID);
        int line = lex.getLine();

        Variable var = new Variable(line, name);
        return var;
    }

    private NumberValue procNumber() {
        String tmp = current.token;
        eat(TokenType.NUMBER);

        Double d = Double.valueOf(tmp);
        NumberValue nv = new NumberValue(d);
        return nv;
    }

    private StringValue procString() {
        String tmp = current.token;
        eat(TokenType.STRING);

        StringValue sv = new StringValue(tmp);
        return sv;
    }

}
