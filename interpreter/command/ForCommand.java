package interpreter.command;


import java.util.ArrayList;

import interpreter.expr.Expr;
import interpreter.expr.Variable;
/*import interpreter.util.Utils;
import interpreter.value.TableValue;
import interpreter.value.NumberValue;*/
import interpreter.value.Value;

public class ForCommand extends Command {
    private Variable var;
    private ArrayList<Expr> list;
    private ArrayList<Variable> names;
    private Command cmds;

    public ForCommand(int line, ArrayList<Variable> var, ArrayList<Expr> expr, Command cmds){
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