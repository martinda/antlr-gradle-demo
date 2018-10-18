package ca.martinda.veelox;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class VeeloxCompiler {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers
            .newFor("VeeloxCompiler").build()
            .defaultHelp(true)
            .description("Compiles Veelox files");
        parser.addArgument("--debug")
            .required(false)
            .action(storeTrue());
        parser.addArgument("files")
            .nargs("+")
            .type(String.class)
            .help("Veelox files to compile");
        Namespace ns = null;
        ns = parser.parseArgsOrFail(args);
        for (String name : (ns.<String>getList("files"))) {
            Path path = Paths.get(name);
            VeeloxMainProcessor svmp = new VeeloxMainProcessor();
            svmp.setDebug(ns.getBoolean("debug"));
            svmp.process(name);
        }
    }
}
