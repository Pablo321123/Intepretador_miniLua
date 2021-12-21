package interpreter.command;

import java.util.Vector;

import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.TableValue;
import interpreter.value.NumberValue;
import interpreter.value.Value;

public class GenericForCommand extends Command {
    private Variable var;
    private Vector<Variable> names;
    private Vector<Expr> list;
    private Command cmds;

    public GenericForCommand(int line, Vector<Variable> var, Vector<Expr> expr, Command cmds) {
        super(line);
        this.names = var;
        this.list = expr;
        this.cmds = cmds;
    }

    @Override
    public void execute() {
        Value<?> v = var.expr();
        

    }
}