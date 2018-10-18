package ca.martinda.veelox;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.Vector;

public class VeeloxMainProcessor {

    private boolean debug;

    void setDebug(boolean debug) {
        this.debug = debug;
    }

    /** Process all files listed as command line arguments. Returns 0 on success, 1 on failure. */
    public int process(String fileName) {
	int status = 0;

	try {
	  ANTLRInputStream ais = new ANTLRFileStream(fileName);
	  if (processFile(fileName, ais) != 0) {
	    status = 1;
	  }
	} catch (java.io.FileNotFoundException e) {
	  System.err.println("Error: Command line argument file not found: "+fileName);
	  status = 1;
	} catch (java.io.IOException e) {
	  System.err.println("Error: Command line argument file IO error: "+fileName);
	  status = 1;
	}
	return status;
    }

    /** Process one file. Returns 0 on success, 1 on failure. */
    public int processFile(String fileName, ANTLRInputStream ais) {

        VeeloxLexer lexer = new VeeloxLexer(ais);
	CommonTokenStream tokenStream = new CommonTokenStream(lexer);
	VeeloxParser parser = new VeeloxParser(tokenStream);

	ParseTree tree = parser.r();

        VeeloxVisitor v = new VeeloxVisitor();
	v.visit(tree);

	return 0;
    }
}
