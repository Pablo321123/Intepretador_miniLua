package lexical;

public enum TokenType {
    // SPECIALS
    UNEXPECTED_EOF,
    INVALID_TOKEN,
    END_OF_FILE,

    // SYMBOLS
    SEMI_COLON,     // ;
    COLON,          // ,
    DOT,            // .
    OPEN_PAR,       // (
    CLOSE_PAR,      // )
    OPEN_BRA,       // [
    CLOSE_BRA,      // ]
    OPEN_CUR,       // {
    CLOSE_CUR,      // }

    // OPERATORS
    ASSIGN,         // =
    AND,            // and
    OR,             // or
    LOWER_THAN,     // <
    GREATER_THAN,   // >
    LOWER_EQUAL,    // <=
    GREATER_EQUAL,  // >=
    NOT_EQUAL,      // ~=
    EQUAL,          // ==
    CONCAT,         // ..
    ADD,            // +
    SUB,            // -
    MUL,            // *
    DIV,            // /
    MOD,            // %
    SIZE,           // size
    NOT,            // not

    // KEYWORDS
    IF,             // if
    THEN,           // then
    ELSEIF,         // elseif
    ELSE,           // else
    END,            // end
    WHILE,          // while
    DO,             // do
    REPEAT,         // repeat
    UNTIL,          // until
    FOR,            // for
    IN,             // in
    PRINT,          // print
    FALSE,          // false
    TRUE,           // true
    NIL,            // nil
    READ,           // read
    TONUMBER,       // tonumber
    TOSTRING,       // tostring

    // OTHERS
    ID,             // identifier
    NUMBER,         // integer
    STRING          // string

};
