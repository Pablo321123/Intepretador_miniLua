package interpreter.command;

import interpreter.expr.Expr;

/**
 * RepeatCommand
 */
public class RepeatCommand extends Command {

    private Command cmd;
    private Expr expr;

    public RepeatCommand(int line, Command cmd, Expr expr) {
        super(line);
        this.cmd = cmd;
        this.expr = expr;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

}