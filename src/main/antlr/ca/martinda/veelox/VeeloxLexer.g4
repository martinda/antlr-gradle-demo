lexer grammar VeeloxLexer;

@lexer::header {
    package ca.martinda.veelox;
}

@members {
    private long tokenIndex     = 0;
}

HELLO : 'hello';
ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;

