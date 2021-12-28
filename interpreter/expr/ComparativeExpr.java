package interpreter.expr;

import interpreter.value.BooleanValue;
import interpreter.value.NumberValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import interpreter.util.Utils;

public class ComparativeExpr extends Expr {

    private Expr expr1;
    private Expr expr2;
    private ComparativeOp op;

    public ComparativeExpr(int line, Expr expr1, ComparativeOp op, Expr expr2) {
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
            case LOWER_THAN:
                ret = lowerThanOp(v1, v2);
                break;
            case GREATER_THAN:
                ret = greaterThanOp(v1, v2);
                break;
            case LOWER_EQUAL:
                ret = lowerEqualOp(v1, v2);
                break;
            case GREATER_EQUAL:
                ret = greaterEqualOp(v1, v2);
                break;
            case NOT_EQUAL:
                ret = notEqualOp(v1, v2);
                break;
            case EQUAL:
                ret = equalOp(v1, v2);
                break;
            case AND:
                ret = andOp(v1, v2);
                break;
            case OR:
                ret = orOp(v1, v2);
                break;
            default:
                Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> lowerThanOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            ret = new BooleanValue(d1 < d2);
        } else if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1 = sv1.value();
            String s2 = sv2.value();
            ret = new BooleanValue(s1.compareTo(s2) < 0);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> greaterThanOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            ret = new BooleanValue(d1 > d2);
        } else if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1 = sv1.value();
            String s2 = sv2.value();
            ret = new BooleanValue(s1.compareTo(s2) > 0);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> lowerEqualOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            ret = new BooleanValue(d1 <= d2);
        } else if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1 = sv1.value();
            String s2 = sv2.value();
            ret = new BooleanValue(s1.compareTo(s2) <= 0);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> greaterEqualOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            ret = new BooleanValue(d1 >= d2);
        } else if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1 = sv1.value();
            String s2 = sv2.value();
            ret = new BooleanValue(s1.compareTo(s2) >= 0);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> notEqualOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            ret = new BooleanValue(!(d1.equals(d2)));
        } else if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1 = sv1.value();
            String s2 = sv2.value();
            ret = new BooleanValue(!s1.equals(s2));
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> equalOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof NumberValue && v2 instanceof NumberValue) {
            NumberValue nv1 = (NumberValue) v1;
            NumberValue nv2 = (NumberValue) v2;
            Double d1 = nv1.value();
            Double d2 = nv2.value();
            ret = new BooleanValue(d1.equals(d2));
        } else if (v1 instanceof StringValue && v2 instanceof StringValue) {
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1 = sv1.value();
            String s2 = sv2.value();
            ret = new BooleanValue(s1.equals(s2));
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> andOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 instanceof BooleanValue && v2 instanceof BooleanValue) {
            BooleanValue bv1 = (BooleanValue) v1;
            BooleanValue bv2 = (BooleanValue) v2;
            boolean b1 = bv1.value();
            boolean b2 = bv2.value();
            ret = new BooleanValue(b1 && b2);
        } else {
            Utils.abort(super.getLine());
        }

        return ret;
    }

    private Value<?> orOp(Value<?> v1, Value<?> v2) {
        Value<?> ret = null;
        if (v1 != null && ((v1 instanceof BooleanValue) && (Boolean) v1.value() != false)) {
            // BooleanValue bv1 = (BooleanValue) v1;
            // BooleanValue bv2 = (BooleanValue) v2;
            // boolean b1 = bv1.value();
            // boolean b2 = bv2.value();
            ret = v1;
        } else {
            ret = v2;
        }

        return ret;
    }
}
