package interpreter.command;

import interpreter.expr.Expr;

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
        if (expr.expr().eval()) {
            thenCmds.execute();
        } else {
            if (elseCmds != null) {
                elseCmds.execute();
            }
        }

    }

}
