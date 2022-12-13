package ir.peeco.pline.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ir.peeco.pline.models.TblSipProfileDetails;
import ir.peeco.pline.repositories.SipProfileDetailsRepository;
import ir.peeco.pline.repositories.SipProfilesRepository;
import ir.peeco.pline.tools.GlobalsTools;

@RestController
@CrossOrigin(value = "*", maxAge = 300)
@RequestMapping("/sip-profile-details")
public class SipProfileDetailsController {

    SipProfileDetailsRepository sipProfileDetailsRepository;
    SipProfilesRepository sipProfilesRepository;

    @Autowired
    public void setSipProfileDetailsRepository(SipProfileDetailsRepository sipProfileDetailsRepository) {
        this.sipProfileDetailsRepository = sipProfileDetailsRepository;
    }

    @Autowired
    public void setSipProfilesRepository(SipProfilesRepository sipProfilesRepository) {
        this.sipProfilesRepository = sipProfilesRepository;
    }

    @GetMapping("/params")
    public ResponseEntity<Object> getParameters() throws Exception {
        return ResponseEntity.ok(GlobalsTools.getSipParameters().get("sip_profiles"));
    }

    @PostMapping("/save")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> save(@RequestBody Map<String, Object> body) {

        Long id = Long.valueOf(body.get("id").toString());
        body.remove("id");
        var sipProfile = sipProfilesRepository.findSipProfileById(id);
        sipProfileDetailsRepository.deleteBySipProfileId(id);
        for (var key : body.keySet()) {
            Map<String, Object> items = (Map<String, Object>) body.get(key);
            for (var item : items.keySet()) {
                var sipProDet = new TblSipProfileDetails();
                sipProDet.sipProfile = sipProfile;
                sipProDet.type = key;
                sipProDet.key = item;
                sipProDet.value = items.get(item).toString();
                sipProfileDetailsRepository.save(sipProDet);
            }
        }
        var result = new HashMap<String, Object>();
        result.put("error", false);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getData(@RequestParam(name = "id", required = true) Long id) {
        var details = sipProfileDetailsRepository.getBySipProfileId(id);
        Map<String, Object> endpoint = new HashMap<String, Object>();
        Map<String, Object> auth = new HashMap<String, Object>();
        Map<String, Object> transport = new HashMap<String, Object>();
        Map<String, Object> contact = new HashMap<String, Object>();
        Map<String, Object> aor = new HashMap<String, Object>();
        for (TblSipProfileDetails d : details) {
            switch (d.type) {
                case "endpoint": {
                    endpoint.put(d.key, d.value);
                }
                    break;
                case "auth": {
                    auth.put(d.key, d.value);
                }
                    break;
                case "transport": {
                    transport.put(d.key, d.value);
                }
                    break;
                case "contact": {
                    contact.put(d.key, d.value);
                }
                    break;
                case "aor": {
                    aor.put(d.key, d.value);
                }
                    break;
            }
        }
        var result = new HashMap<String, Object>();
        result.put("endpoint", endpoint);
        result.put("auth", auth);
        result.put("transport", transport);
        result.put("contact", contact);
        result.put("aor", aor);
        return ResponseEntity.ok(result);
    }
}
