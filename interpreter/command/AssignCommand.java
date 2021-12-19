package interpreter.command;

import java.util.Vector;

import interpreter.expr.Expr;
import interpreter.expr.SetExpr;
import interpreter.value.Value;

public class AssignCommand extends Command {

    private Vector<SetExpr> lhs;
    private Vector<Expr> rhs;

    public AssignCommand(int line, Vector<SetExpr> lhs, Vector<Expr> rhs) {
        super(line);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void execute() {
        // FIXME: Implementar o resto.
        Expr right = rhs.get(0);
        Value<?> v = right.expr();

        SetExpr left = lhs.get(0);
        left.setValue(v);
    }

}
