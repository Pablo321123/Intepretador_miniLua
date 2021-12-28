package interpreter.command;

import interpreter.expr.ConstExpr;
import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.NumberValue;
import interpreter.value.Value;
import lexical.Lexeme;
import interpreter.util.Utils;

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
        this.expr3 = expr3 == null ? new ConstExpr(line, new NumberValue(1.0)) : expr3;
        this.cmd = cmd;
    }

    // for <name> (('=' <expr> ',' <expr> [',' <expr>]) do <code> end
    public void execute() {
        // for i=2,#exp-1,2 do

        Value<?> v1 = expr1.expr();
        Value<?> v2 = expr2.expr();
        Value<?> v3 = expr3.expr();

        if (v1 instanceof NumberValue && v2 instanceof NumberValue && v3 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            NumberValue nv3 = (NumberValue) v3;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            Double d3 = nv3.value();
            Double d = d1;

            while (d <= d2) {
                // v.setValue(new NumberValue(d));
                cmd.execute();
                d += d3;
            }

        } else {
            Utils.abort(super.getLine());
        }
    }

}