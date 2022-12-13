package ir.peeco.pline.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.peeco.pline.models.TblSipGlobals;
import ir.peeco.pline.models.TblSipSystems;
import ir.peeco.pline.pline.ApiResult;
import ir.peeco.pline.repositories.SipSystemsRepository;
import ir.peeco.pline.repositories.SipGlobalsRepository;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/settings")
public class SettingsController {

    private SipGlobalsRepository sipGlobalsRepository;
    private SipSystemsRepository sipSystemsRepository;

    @Autowired
    public void setSipGlobalsRepository(SipGlobalsRepository sipGlobalsRepository) {
        this.sipGlobalsRepository = sipGlobalsRepository;
    }

    @Autowired
    public void setSipSystemsRepository(SipSystemsRepository sipSystemsRepository) {
        this.sipSystemsRepository = sipSystemsRepository;
    }

    @GetMapping("/sip-globals")
    public TblSipGlobals sipGlobals() {
        var sip = sipGlobalsRepository.findAll();
        if (sip.size() == 0)
            return new TblSipGlobals();
        return sip.get(0);
    }

    @PostMapping("/save-sip-globals")
    public ResponseEntity<ApiResult> saveSipGlobals(@Valid @RequestBody TblSipGlobals tblSipGlobals) {
        ApiResult result = new ApiResult();
        try {
            sipGlobalsRepository.save(tblSipGlobals);
            result.setHasError(false);
        } catch (Exception e) {
            result.setHasError(true);
            result.addMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/system-sip-settings")
    public TblSipSystems systemSettings() {
        var sip = sipSystemsRepository.findAll();
        if (sip.size() == 0)
            return new TblSipSystems();
        return sip.get(0);

    }

    @PostMapping("/save-system-sip-settings")
    public Map<String, Object> saveSystemSettings(@Valid @RequestBody TblSipSystems tblSipSystems) {
        Map<String, Object> result = new HashMap<>();
        try {
            sipSystemsRepository.save(tblSipSystems);
        } catch (Exception e) {

        }
        return result;
    }

}
