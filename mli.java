import interpreter.command.Command;
import lexical.LexicalAnalysis;
import syntatic.SyntaticAnalysis;

public class mli {

    public static void main(String[] args) {
        /*if (args.length != 1) {
            System.out.println("Usage: java mli [miniLua file]");
            return;
        }*/

        try (LexicalAnalysis l = new LexicalAnalysis("teste.lua")) {
            // O código a seguir é dado para testar o interpretador.
            // TODO: descomentar depois que o analisador léxico estiver OK.
            SyntaticAnalysis s = new SyntaticAnalysis(l);
            Command c = s.start();
            c.execute();

            // // O código a seguir é usado apenas para testar o analisador léxico.
            // // TODO: depois de pronto, comentar o código abaixo.
            // Lexeme lex;
            // do {
            //     lex = l.nextToken();
            //     System.out.printf("%02d: (\"%s\", %s)\n", l.getLine(),
            //         lex.token, lex.type);
            // } while (lex.type != TokenType.END_OF_FILE &&
            //          lex.type != TokenType.INVALID_TOKEN &&
            //          lex.type != TokenType.UNEXPECTED_EOF);
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
