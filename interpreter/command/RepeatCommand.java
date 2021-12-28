package interpreter.command;

import interpreter.expr.Expr;
import interpreter.value.Value;

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

        Value<?> v = null;

        do{
          cmd.execute();
          v = expr.expr();
       
        } while ((v = expr.expr()) != null && !v.eval());
    }

}