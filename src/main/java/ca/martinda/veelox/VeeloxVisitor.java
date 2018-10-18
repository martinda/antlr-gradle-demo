package ca.martinda.veelox;

import ca.martinda.veelox.VeeloxParserVisitor;

public class  VeeloxVisitor extends VeeloxParserBaseVisitor {

    @Override
    public String visitR(VeeloxParser.RContext context) {
	System.out.print(context.getText());
        return null;
    }
}
