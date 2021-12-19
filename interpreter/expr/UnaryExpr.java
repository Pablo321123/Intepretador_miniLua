package interpreter.expr;

import interpreter.value.BooleanValue;
import interpreter.value.NumberValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import interpreter.util.Utils;

public class UnaryExpr extends Expr {

    private Expr expr;
    private UnaryOp op;

    public UnaryExpr(int line, Expr expr, UnaryOp op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }

    @Override
    public Value<?> expr() {
        Value<?> v = expr.expr();

        Value<?> ret = null;
        switch (op) {
            case Neg:
                ret = negOp(v);
                break;
            case Size:
                // FIXME: implement me!
                break;
            case Not:
                ret = notOp(v);
                break;
            default:
                Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> negOp(Value<?> v) {
        Value<?> ret = null;
        if (v instanceof NumberValue) {
            NumberValue nv = (NumberValue) v;
            Double d = -nv.value();
            ret = new NumberValue(d);
        } else if (v instanceof StringValue) {
            StringValue sv = (StringValue) v;
            String tmp = sv.value();

            try {
                Double d = -Double.valueOf(tmp);
                ret = new NumberValue(d);
            } catch (Exception e) {
                Utils.abort(super.getLine());
            }
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> notOp(Value<?> v) {
        boolean b = (v == null || !v.eval());
        BooleanValue bv = new BooleanValue(b);
        return bv;
    }

}
