package ir.peeco.pline.pline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Component
@Scope("singleton")
public class PlineTools {

    @Value("${pline.core}")
    private String core;

    @Value("${pline.version}")
    private String version;

    @Value("${pline.debug}")
    private boolean debug;

    private final Logger logger;

    public String getCore() {
        return this.core;
    }

    public String getVersion() {
        return version;
    }

    public String getBanner() {
        return "\n;##############################################\n"
                + ";#                                            #\n"
                + ";#      Name:       P-Line PBX                #\n"
                + ";#      Version:    " + version + "                     #\n"
                + ";#      Website:    http://p-line.ir/         #\n"
                + ";#      Support:    support@p-line.ir         #\n"
                + ";#      Powered by: Asterisk-Core 18          #\n"
                + ";#                                            #\n"
                + ";##############################################\n";
    }

    @Autowired
    public PlineTools() {
        logger = LoggerFactory.getLogger("P-Line-PBX-Ver-" + this.version);
    }

    public String getConfigPath() {
        String path = "";
        if (debug)
            path = System.getProperty("user.home") + "/etc/" + this.core + "/";
        else
            path = "/etc/" + this.core + "/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return path;
    }

    public void plineLogger(String message) {
        logger.info(message);
    }

    public String ExecLinuxCli(String command, boolean wantResult) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            if (wantResult) {
                BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String result = "";
                String line = "";
                while ((line = buf.readLine()) != null) {
                    result += line + "\n";
                }
                buf.close();
                return result;
            }
            return "";
        } catch (Exception ex) {
            this.plineLogger(ex.getLocalizedMessage());
            return null;
        }
    }

    public String execCoreCommand(String command) {
        this.plineLogger("Exec Core Command: " + command);
        String result = this.ExecLinuxCli("/usr/sbin/" + this.core + " -x '" + command + "'", true);
        this.plineLogger("Exec Core Command Result: " + result);
        return result;
    }

    public void reloadConfigurations() {
        execCoreCommand("module reload");
    }

    public boolean checkSipUid(String uid) {
        var pattern = "/^[a-zA-Z0-9_.-]*$/";
        return uid.matches(pattern);
    }

    public String getRandomString(int length) {
        String used_symbols = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcefghijklmnopqrstuvwxyzm";
        var result = "";
        Random rnd = new Random(new Date().getTime());
        for (int i = 0; i < length; i++) {
            int r = rnd.nextInt(used_symbols.length());
            result += used_symbols.charAt(r);
        }
        return result;
    }

    public void deleteAllFileInFolder(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listOfFiles[i].delete();
            }
        }
    }

    public boolean writeinfoFile(String folder, String filename, ArrayList<InfoConfiguration> iniConfigurations) {
        try {
            this.plineLogger("Start write info file: " + filename);
            if (!filename.endsWith(".info")) {
                filename += ".info";
            }

            if (!folder.endsWith("/")) {
                folder = folder + "/";
            }
            String path = this.getConfigPath() + folder;

            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }

            File infoFile = new File(path + filename);
            if (infoFile.exists()) {
                infoFile.delete();
                this.plineLogger("Delete info file: " + filename);
            }

            if (infoFile.createNewFile()) {
                this.plineLogger("Create info file: " + filename);
            }

            PrintWriter fileWriter = new PrintWriter(infoFile);

            boolean bannerSetInFirst = false;
            for (InfoConfiguration ic : iniConfigurations) {
                if (ic.getBanner() && bannerSetInFirst == false) {
                    fileWriter.println(this.getBanner());
                    bannerSetInFirst = true;
                }
                if (!ic.getDescription().isEmpty()) {
                    String[] descs = ic.getDescription().split("\n");
                    fileWriter.println();
                    for (String desc : descs) {
                        fileWriter.println(";" + desc);
                    }
                }
                if (!ic.getTemplate().isEmpty())
                    fileWriter.println("[" + ic.getContext() + "](" + ic.getTemplate() + ")");
                else
                    fileWriter.println("[" + ic.getContext() + "]");

                ic.getElements().forEach((key, value) -> {
                    switch (key) {
                        case "include":
                            fileWriter.println("#include " + value);
                            break;
                        default:
                            fileWriter.println(key + "=" + value);
                            break;
                    }
                });

                fileWriter.println();
            }
            fileWriter.flush();
            fileWriter.close();
            this.plineLogger("Create info file successful: " + filename);
            return true;
        } catch (Exception ex) {
            this.plineLogger("Error in write info file: " + ex.getMessage());
        }
        return false;
    }

}
