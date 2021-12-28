package interpreter.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.TableValue;
import interpreter.value.NumberValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class GenericForCommand extends Command {
  private Variable var1;
  private Variable var2;
  private Expr expr;
  private Command cmds;

  public GenericForCommand(int line, Variable var1, Variable var2, Expr expr, Command cmds) {
    super(line);
    this.var1 = var1;
    this.var2 = var2 != null ? var2 : null;
    this.expr = expr;
    this.cmds = cmds;
  }

  @Override
  public void execute() {

    Value<?> v3 = expr.expr();

    if (v3.value() instanceof HashMap) {
      HashMap table = (HashMap) v3.value();

      for (Object key : table.keySet()) {
        var1.setValue(new StringValue(key.toString()));

        if (var2 != null) {
          var2.setValue((Value<?>) table.get(key));
        }
        cmds.execute();
      }
    }

    // for var1, var2 in expr do

    // for (Expr op : expr.expr()) {

    // }

  }
}