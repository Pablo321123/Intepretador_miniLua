package interpreter.expr;

import interpreter.util.Memory;
import interpreter.value.Value;

public class Variable extends SetExpr {

    private String name;

    public Variable(int line, String name) {
        super(line);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Value<?> expr() {
        return Memory.read(this.name);
    }

    @Override
    public void setValue(Value<?> value) {
        Memory.write(this.name, value);
    }
    
}
