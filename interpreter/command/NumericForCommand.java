package interpreter.command;

import interpreter.expr.Expr;
import interpreter.expr.Variable;

/**
 * NumericForCommand
 */
public class NumericForCommand extends Command {

    private Variable var;
    private Expr expr1;
    private Expr expr2;
    private Expr expr3;
    private Command cmd;

    public NumericForCommand(int line, Variable var, Expr expr1, Expr expr2, Expr expr3, Command cmd) {
        super(line);
        this.var = var;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.expr3 = expr3;
        this.cmd = cmd;
    }

    public void execute() {

    }

}