package org.revengi;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.revengi.apksign.V2V3SchemeSigner;
import org.revengi.apksign.key.JksSignatureKey;
import org.revengi.zip.DataMultiplexing;

/**
 * @author Abhi
 */
@Command(name = "ApkDataMultiplex", mixinStandardHelpOptions = true, description = "Performs APK data multiplexing and signs the output.")
public class ApkDataMultiplex implements Runnable {

    @Parameters(index = "0", paramLabel = "<inputApk>", description = "Path to the input APK file")
    private File inputApk;

    @Parameters(index = "1", paramLabel = "<outputApk>", description = "Path to the output APK file")
    private File outputApk;

    @Parameters(index = "2", paramLabel = "<baseApk>", description = "Path to the duplicate base APK inside input APK path")
    private File baseApk;

    @Option(names = { "-ns", "--no-sign" }, description = "Do not sign the output APK", defaultValue = "false")
    private boolean noSign;

    @Override
    public void run() {
        try {
            System.out.println("Running optimization...");
            DataMultiplexing.optimize(
                    inputApk.getAbsolutePath(),
                    outputApk.getAbsolutePath(),
                    baseApk.getPath(),
                    true);

            System.out.println("Optimization complete.");

            if (!noSign) {
                System.out.println("Now signing...");
                InputStream jksStream = ApkDataMultiplex.class
                        .getClassLoader()
                        .getResourceAsStream("test.jks");
                if (jksStream == null) {
                    System.err.println("Keystore not found in resources!");
                    return;
                }
                File tempJks = File.createTempFile("test", ".jks");
                tempJks.deleteOnExit();
                Files.copy(jksStream, tempJks.toPath(), StandardCopyOption.REPLACE_EXISTING);
                V2V3SchemeSigner.sign(
                        outputApk,
                        new JksSignatureKey(tempJks.getAbsolutePath(), "123456",
                                "123456", "123456"),
                        true,
                        true);
                System.out.println("Signing complete!");
            }
        } catch (Exception e) {
            System.err.println("Operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ApkDataMultiplex()).execute(args);
        System.exit(exitCode);
    }
}
