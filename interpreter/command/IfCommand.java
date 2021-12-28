package interpreter.command;

import java.util.ArrayList;

import interpreter.expr.Expr;
import interpreter.value.Value;

public class IfCommand extends Command {

    private ArrayList<Expr> expr;
    private ArrayList<Command> thenCmds;
    private Command elseCmds;

    public IfCommand(int line, ArrayList<Expr> expr, ArrayList<Command> thenCmds) {
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

        int indice_cmd = 0;
        for (Expr expr2 : expr) {
            Value<?> v = expr2.expr();

            if (v.eval() == true) {
                thenCmds.get(indice_cmd).execute();
                break;
            }

            if (elseCmds != null && v.eval() == false) {
                elseCmds.execute();
                break;
            }
            indice_cmd++;
        }
    }

}
