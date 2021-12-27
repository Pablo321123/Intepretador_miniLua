package interpreter.command;

import interpreter.expr.Expr;
import interpreter.value.Value;

public class IfCommand extends Command {

    private Expr expr;
    private Command thenCmds;
    private Command elseCmds;

    public IfCommand(int line, Expr expr, Command thenCmds) {
        super(line);
        this.expr = expr;
        this.thenCmds = thenCmds;
    }

    public void setElseCommand(Command elseCmds) {
        if (elseCmds != null) {
            this.elseCmds = elseCmds;
        }
    }

    @Override
    public void execute() {

        Value<?> v = expr.expr();

        if(v.eval() == true){
            thenCmds.execute();
        }

        if(elseCmds != null && v.eval() == false){
            elseCmds.execute();
        }

    }

}
