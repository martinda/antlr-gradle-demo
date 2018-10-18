package ca.martinda.veelox;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;

public class UnderlineErrorListener extends BaseErrorListener {
  public VeeloxLexer lexer;

  public UnderlineErrorListener(VeeloxLexer lexer) {
    this.lexer = lexer;
  }

  public void syntaxError(Recognizer<?, ?> recognizer,
    Object offendingSymbol,
    int line, int charPositionInLine,
    String msg,
    RecognitionException exception)
  {

    // Prepare and print an error message
    if (recognizer instanceof Parser) {
      // Parser error: the offendingSymbol is set
      CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
      String afterMsg = "";

      // TODO: do not put an after message for no viable alternative exception NoViableAltException
      if (exception instanceof InputMismatchException) {
        String prevTokenText = tokens.LT(-1).getText();
        afterMsg = " after token '"+prevTokenText+"'";

        if (prevTokenText.matches("[a-zA-Z_]\\w*")) {
          afterMsg = " after identifier '"+prevTokenText+"'";
          String[] tokenNames = this.lexer.getTokenNames();
          for (String name : tokenNames) {
            if (name.regionMatches(1,prevTokenText,0,prevTokenText.length())) {
              afterMsg = " after keyword "+name;
              break;
            }
          }
        }
      }
      System.err.println(tokens.getSourceName()+":"+line+":"+charPositionInLine+": "+msg+afterMsg);
    } else {
      // Lexer error: the offendingSymbol is not set
      Lexer lexer = (Lexer)recognizer;
      System.err.println(lexer.getSourceName()+":"+line+":"+charPositionInLine+": "+msg);
    }

    // Fetch the file (input) where the error occurs, store it in "input"
    // TODO: deal with input that does not fit in memory
    String input;
    if (recognizer instanceof Parser) {
      CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
      input = tokens.getTokenSource().getInputStream().toString();
    } else {
      Lexer lexer = (Lexer)recognizer;
      input = lexer.getInputStream().toString();
    }
    String[] lines = input.split("\n");

    // Underline the error: print the line with the error
    // Antlr needs to see the token that follows an error in order to report the error
    // When that token is the EOF, the line and position for reporting are outside the file
    // We need to bring those references back into the file to report them
    // debug: System.err.println(lines.length);
    // debug: System.err.println("line before correction:"+line);
    if (line >= lines.length) {
      line = lines.length - 1;
    } else {
      line = line - 1;
    }
    String errorLine = lines[line];
    System.err.println(errorLine);
    // debug: System.err.println("charPositionInLine"+charPositionInLine);

    // Underline the error
    if (recognizer instanceof Parser) {
      // Parser error: the offendingSymbol is set
      Token offendingToken = (Token)offendingSymbol;
      int start = offendingToken.getStartIndex();
      int stop = offendingToken.getStopIndex();
      // debug: System.err.println("start:"+start);
      // debug: System.err.println("stop:"+stop);

      if (offendingToken.getType() == Token.EOF) {
        // debug: System.err.println("EOF");
        for (int i=0; i<errorLine.length(); i++) System.err.print(" ");
        System.err.print("^");
      } else if ( start>=0 && stop>=0 ) {
        for (int i=0; i<charPositionInLine; i++) System.err.print(" ");
        for (int i=start; i<=stop; i++) System.err.print("^");
      }
    } else {
      // Lexer error: the offendingSymbol is not set
      for (int i=0; i<charPositionInLine; i++) System.err.print(" ");
      System.err.print("^");
    }
    System.err.println();
  }
}
