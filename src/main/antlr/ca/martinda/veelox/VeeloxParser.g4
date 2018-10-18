parser grammar VeeloxParser;

options {
    tokenVocab=VeeloxLexer;
}

@parser::header {
    package ca.martinda.veelox;
}

@members {
}

r  : (HELLO ID)+ ;
