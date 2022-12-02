package ir.peeco.pline.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.peeco.pline.pline.InfoConfiguration;
import ir.peeco.pline.pline.PlineTools;
import ir.peeco.pline.repositories.SipProfileDetailsRepository;
import ir.peeco.pline.repositories.SipProfilesRepository;

@RestController
@CrossOrigin(value = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/configs")
public class ApplyController {

    private SipProfileDetailsRepository sipProfileDetailsRepository;
    private SipProfilesRepository profilesRepository;
    private PlineTools plineTools;

    @Autowired
    public void setPlineTools(PlineTools plineTools) {
        this.plineTools = plineTools;
    }

    @Autowired
    public void setSipProfileDetailsRepository(SipProfileDetailsRepository sipProfileDetailsRepository) {
        this.sipProfileDetailsRepository = sipProfileDetailsRepository;
    }

    @Autowired
    public void setProfilesRepository(SipProfilesRepository profilesRepository) {
        this.profilesRepository = profilesRepository;
    }

    @PostMapping("/apply")
    public ResponseEntity<Object> apply(@RequestBody Map<String, String> body) throws Exception {

        Map<String, Object> result = new HashMap<>();
        CreateSipProfile();
        return ResponseEntity.ok(result);
    }

    public void CreateSipProfile() {
        String[] elementPjSip = new String[] { "transport", "endpoint", "auth", "contact", "aor" };
        String folder = "sip-profiles";
        if (!folder.endsWith("/")) {
            folder = folder + "/";
        }
        String path = plineTools.getConfigPath() + folder;

        if (new File(path).exists()) {
            plineTools.deleteAllFileInFolder(path);
        }

        profilesRepository.findAll().forEach(p -> {
            ArrayList<InfoConfiguration> infoConfigurations = new ArrayList<>();

            for (String pjSip : elementPjSip) {

                InfoConfiguration configuration = new InfoConfiguration(pjSip + "-" + p.id);
                configuration.setBanner(true);

                if (pjSip == "transport") {
                    configuration.setDescription(
                            "Profile Name: " + p.name + "\n" + "Profile Description: " + p.description);
                } else {
                    configuration.setTemplate("!");
                }

                if (pjSip == "endpoint") {
                    configuration.addElement("disallow", "all");
                }

                sipProfileDetailsRepository.getBySipProfileIdAndType(p.id, pjSip).forEach(d -> {
                    String value = d.value;
                    if (value.startsWith("[") && value.endsWith("]")) {
                        value = value.replace("[", "").replace("]", "").replace("\"", "").replace(" ", "");
                    }
                    configuration.addElement(d.key, value);
                });
                infoConfigurations.add(configuration);

            }

            plineTools.writeinfoFile("sip-profiles", "sip-profile-" + p.id, infoConfigurations);
        });
    }

}
