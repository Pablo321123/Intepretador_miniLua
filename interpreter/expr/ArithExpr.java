package interpreter.expr;

import interpreter.value.NumberValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import interpreter.util.Utils;

public class ArithExpr extends Expr {

    private Expr expr1;
    private Expr expr2;
    private ArithOp op;

    public ArithExpr(int line, Expr expr1, ArithOp op, Expr expr2) {
        super(line);
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;
    }

    @Override
    public Value<?> expr() {
        Value<?> v1 = expr1 != null ? expr1.expr() : null;
        Value<?> v2 = expr2 != null ? expr2.expr() : null;

        Value<?> ret = null;
        switch (op) {
            case ADD:
                ret = addOp(v1, v2);
                break;
            case SUB:
                ret = subOp(v1, v2);
                break;
            case MUL:
                ret = mulOp(v1, v2);
                break;
            case DIV:
                ret = divOp(v1, v2);
                break;
            case MOD:
                ret = modOp(v1, v2);
                break;
            case CONCAT:
                ret = concatOp(v1, v2);
                break;
            default:
                Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> addOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d = nv1.value() + nv2.value();
            ret = new NumberValue(d);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> subOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d = nv1.value() - nv2.value();
            ret = new NumberValue(d);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> mulOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d = nv1.value() * nv2.value();
            ret = new NumberValue(d);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> divOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d = nv1.value() / nv2.value();
            ret = new NumberValue(d);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> modOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d = nv1.value() % nv2.value();
            ret = new NumberValue(d);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> concatOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s = sv1.value() + sv2.value();
            ret = new StringValue(s);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

}