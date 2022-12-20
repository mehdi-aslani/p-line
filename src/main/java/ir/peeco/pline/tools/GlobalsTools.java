package ir.peeco.pline.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.peeco.pline.models.TblSipParameter;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GlobalsTools {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getSipParameters() throws Exception {
        byte[] buff = Files.readAllBytes(Path.of(GlobalsTools.getBaseDir() + "/SipParameters.json"));
        String json = new String(buff);
        return new ObjectMapper().readValue(json, HashMap.class);
    }

    // @SuppressWarnings("unchecked")
    public static TblSipParameter[] getpjSipParameters() throws Exception {
        byte[] buff = Files.readAllBytes(Path.of(GlobalsTools.getBaseDir() + "/pjsip_parameters.json"));
        String json = new String(buff);
        return new ObjectMapper().readValue(json, TblSipParameter[].class);
    }

    public static String MD5(String stringToHash) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(stringToHash.getBytes());
            byte[] digest = messageDigest.digest();
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getRandomString() {
        return new Random().nextInt(999999) + "_" + System.currentTimeMillis();
    }

    // public static String getFileExtension(MultipartFile inFile) {
    // String fileExtension =
    // inFile.getOriginalFilename().substring(inFile.getOriginalFilename().lastIndexOf('.'));
    // return fileExtension;
    // }

    public static String getBaseDir() {
        return new File("").getAbsolutePath();
    }

}
