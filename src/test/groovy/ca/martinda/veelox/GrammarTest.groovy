package ca.martinda.veelox;

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class GrammarTest extends Specification {
    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File veeloxFile;

    def setup() {
        veeloxFile = testProjectDir.newFile('file.f')
    }

    def "hello world task prints hello world"() {
        given:
        veeloxFile << """hello world
        """

        when:
        def buffer = new ByteArrayOutputStream()
        System.out = new PrintStream(buffer)

        and:
        String[] args = ["--debug", "${veeloxFile}"] as String[]
        VeeloxCompiler.main(args)

        then:
        buffer.toString() == "helloworld"
    }
}
