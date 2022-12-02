package ir.peeco.pline.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ir.peeco.pline.models.TblSipTrunk;
import ir.peeco.pline.repositories.SipProfilesRepository;
import ir.peeco.pline.repositories.SipTrunkRepository;

@RestController
@CrossOrigin(value = "http://localhost:3000", maxAge = 300)
@RequestMapping("/sip-trunks")
public class SipTrunksController {

    private SipTrunkRepository sipTrunkRepository;
    private SipProfilesRepository sipProfilesRepository;
    private javax.servlet.http.HttpSession HttpSession;

    @Autowired
    public void setHttpSession(javax.servlet.http.HttpSession httpSession) {
        HttpSession = httpSession;
    }

    @Autowired
    public void setSipProfilesRepository(SipProfilesRepository sipProfilesRepository) {
        this.sipProfilesRepository = sipProfilesRepository;
    }

    @Autowired
    public void setSipTrunkRepository(SipTrunkRepository sipTrunkRepository) {
        this.sipTrunkRepository = sipTrunkRepository;
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

        var table = sipTrunkRepository.findAllBy(
                params.get("name") == null ? "" : params.get("name"),
                params.get("username") == null ? "" : params.get("username"),
                params.get("realm") == null ? "" : params.get("realm"),
                params.get("proxy") == null ? "" : params.get("proxy"),
                pageable);
        return ResponseEntity.ok(table);
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam String id) {
        Map<String, Object> result = new HashMap<>();
        if (id.trim().isEmpty()) {
            var profile = sipProfilesRepository.findAll();
            result.put("sipProfiles", profile);
            result.put("data", new TblSipTrunk());
            return ResponseEntity.ok(result);
        }
        var table = sipTrunkRepository.findById(Long.valueOf(id));
        var profile = sipProfilesRepository.findAll();
        result.put("sipProfiles", profile);
        result.put("data", table);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody TblSipTrunk sipTrunk) {

        Map<String, Object> result = new HashMap<>();
        ArrayList<String> errors = new ArrayList<>();

        if (sipTrunk.name.trim().isEmpty()) {
            errors.add("'Name' cannot be Empty");
        }

        if (sipTrunkRepository.countAllByName(sipTrunk.name) > 0) {
            errors.add("The name is duplicate");
        }

        if (errors.size() == 0) {
            sipTrunkRepository.save(sipTrunk);
        }

        result.put("error", errors.size() > 0);
        result.put("errorsDesc", errors);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@Valid @RequestBody TblSipTrunk sipTrunk) {

        Map<String, Object> result = new HashMap<>();
        ArrayList<String> errors = new ArrayList<>();

        if (sipTrunk.name.trim().isEmpty()) {
            errors.add("'Name' cannot be Empty");
        }

        var data = sipTrunkRepository.findByName(sipTrunk.name);
        if (data != null && data.id != sipTrunk.id) {
            errors.add("The name is duplicate");
        }

        if (errors.size() == 0) {
            sipTrunkRepository.save(sipTrunk);
        }

        result.put("error", errors.size() > 0);
        result.put("errorsDesc", errors);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody Map<String, Long> body) {

        Map<String, Object> result = new HashMap<>();
        ArrayList<String> errors = new ArrayList<>();
        try {
            sipTrunkRepository.deleteById(body.get("id"));
        } catch (Exception ex) {
            errors.add("An error occurred while delete item. Contact the system administrator");
        }
        result.put("error", errors.size() > 0);
        result.put("errorsDesc", errors);
        return ResponseEntity.ok(result);
    }
}
