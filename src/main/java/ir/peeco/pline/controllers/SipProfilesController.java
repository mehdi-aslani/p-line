package ir.peeco.pline.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ir.peeco.pline.models.TblSipProfile;
import ir.peeco.pline.repositories.SipProfileDetailsRepository;
import ir.peeco.pline.repositories.SipProfilesRepository;

@RestController
@CrossOrigin(value = "*", maxAge = 300)
@RequestMapping("/sip-profiles")
public class SipProfilesController {

    private SipProfilesRepository sipProfilesRepository;
    private SipProfileDetailsRepository sipProfileDetailsRepository;

    @Autowired
    public void setSipProfilesRepository(SipProfilesRepository sipProfilesRepository) {
        this.sipProfilesRepository = sipProfilesRepository;
    }

    @Autowired
    public void setSipProfileDetailsRepository(SipProfileDetailsRepository sipProfileDetailsRepository) {
        this.sipProfileDetailsRepository = sipProfileDetailsRepository;
    }

    @PostMapping("/create")
    public Map<String, Object> saveProfile(@Valid @RequestBody TblSipProfile tblSipProfile) {
        Map<String, Object> result = new HashMap<String, Object>();
        ArrayList<String> errors = new ArrayList<>();
        if (sipProfilesRepository.existsByName(tblSipProfile.name)) {
            errors.add("The name is duplicate");
        } else
            this.sipProfilesRepository.save(tblSipProfile);
        result.put("error", errors.size() > 0);
        result.put("errorsDesc", errors);
        return result;
    }

    @GetMapping("/index")
    public ResponseEntity<Object> index(@RequestParam(required = false) Map<String, String> params) {
        int page = 0;
        if (params.get("page") != null) {
            page = Integer.parseInt(params.get("page"));
            params.remove("page");
        }

        int size = 10;
        if (params.get("size") != null) {
            size = Integer.parseInt(params.get("size"));
        }

        Sort sort = Sort.unsorted();
        if (params.get("sort") != null) {
            if (params.get("sort").startsWith("-"))
                sort = Sort.by(params.get("sort").substring(1)).descending();
            else
                sort = Sort.by(params.get("sort")).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        var table = sipProfilesRepository.findAllBy(
                params.get("name") == null ? "" : params.get("name"),
                params.get("description") == null ? "" : params.get("description"),
                pageable);
        return ResponseEntity.ok(table);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        var table = sipProfilesRepository.findById(id);
        return ResponseEntity.ok(table);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@Valid @RequestBody TblSipProfile tblSipProfile) {
        Map<String, Object> result = new HashMap<String, Object>();
        sipProfilesRepository.save(tblSipProfile);
        result.put("error", false);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody Map<String, Long> body) {
        ArrayList<String> error = new ArrayList<>();
        Long id = body.get("id");
        try {
            // if (sipProfileDetailsRepository.getBySipProfileId(id).size() > 0) {
            // error.add("This item is in use and cannot be deleted");
            // } else {
            sipProfileDetailsRepository.deleteBySipProfileId(id);
            sipProfilesRepository.deleteById(id);
            // }
        } catch (Exception ex) {
            error.add("This item is in use and cannot be deleted");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", error.size() > 0);
        result.put("errorsDesc", error);
        return ResponseEntity.ok(result);
    }

}
