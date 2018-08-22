package dynamic;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;


public class AppMain {
    private static final String PACKAGE_NAME = "dynamic";

    // for unique class names
    private static int classNameSuffix = 0;

    // the Java source template
    private static String template;

    // Create a CharSequenceCompiler instance which is used to compile
    // expressions into Java classes which are then used to create the XY plots.
    // The -target 1.8 options are simply an example of how to pass javac
    // compiler
    // options (the generated source in this example is Java 1.8 compatible.)
    private static final CharSequenceCompiler<Function> compiler = new CharSequenceCompiler<Function>(
            AppMain.class.getClass().getClassLoader(), Arrays.asList(new String[]{"-target","1.8"}));


    private static final Random random = new Random();
    // the Java source template


    public static void main(String[] args) {
        Function function;

        

        final String source = "IntStream.range(0,x).average().getAsDouble()";
        function = newFunction(source);
        double res = function.f(5);

        System.out.println(res);
    }


    private static String digits() {
        return '_' + Long.toHexString(random.nextLong());
    }


    public static Function newFunction(final String expr) {
        try {
            // generate semi-secure unique package and class names
            final String packageName = PACKAGE_NAME + digits();
            final String className = "Fx_" + (classNameSuffix++) + digits();
            final String qName = packageName + '.' + className;
            // generate the source class as String
            final String source = fillTemplate(packageName, className, expr);
            // compile the generated Java source
            final DiagnosticCollector<JavaFileObject> errs = new DiagnosticCollector<JavaFileObject>();
            Class<Function> compiledFunction = compiler.compile(qName, source, errs,
                    new Class<?>[]{Function.class});
            log(errs);
            return compiledFunction.newInstance();
        } catch (CharSequenceCompilerException e) {
            log(e.getDiagnostics());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NULL_FUNCTION;
    }


    private static String fillTemplate(String packageName, String className, String expression)
            throws IOException {
        if (template == null)
            template = readTemplate();
        // simplest "template processor":
        String source = template.replace("$packageName", packageName)//
                .replace("$className", className)//
                .replace("$expression", expression);
        return source;
    }

    private static String readTemplate() throws IOException {
        InputStream is = new FileInputStream("/opt/Function.java.template");
        int size = is.available();
        byte bytes[] = new byte[size];
        if (size != is.read(bytes, 0, size))
            throw new IOException();
        return new String(bytes, "US-ASCII");
    }


    private static void log(final DiagnosticCollector<JavaFileObject> diagnostics) {
        final StringBuilder msgs = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
                .getDiagnostics()) {
            msgs.append(diagnostic.getMessage(null)).append("\n");
        }
        System.out.println(msgs.toString());

    }

    static final Function NULL_FUNCTION = new Function() {
        public double f(final double x) {
            return 0.0;
        }
    };

}
