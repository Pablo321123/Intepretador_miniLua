package interpreter.expr;

import interpreter.value.TableValue;
import interpreter.value.Value;

public class AccessExpr extends SetExpr {

    private Expr base;
    private Expr index;

    public AccessExpr(int line, Expr base, Expr index) {
        super(line);
        this.base = base;
        this.index = index;
    }

    @Override
    public Value<?> expr() {

        TableValue tv = (TableValue) this.base.expr();

        Value<?> indexKey = this.index.expr();
        Value<?> value = tv.value().get(indexKey);

        return value;
    }

    public void setValue(Value<?> value) {
        TableValue tv = (TableValue) this.base.expr();
        Value<?> indexKey = this.index.expr();
        tv.value().put(indexKey, value);
    }

}
