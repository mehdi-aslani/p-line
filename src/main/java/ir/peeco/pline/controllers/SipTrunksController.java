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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ir.peeco.pline.models.TblSipTrunk;
import ir.peeco.pline.pline.ApiResult;
import ir.peeco.pline.repositories.SipTrunksRepository;

@RestController
@CrossOrigin(value = "*", maxAge = 300)
@RequestMapping("/sip-trunks")
public class SipTrunksController {

    private SipTrunksRepository sipTrunkRepository;

    @Autowired
    public void setSipTrunkRepository(SipTrunksRepository sipTrunkRepository) {
        this.sipTrunkRepository = sipTrunkRepository;
    }

    @GetMapping("/index")
    public ResponseEntity<Page<TblSipTrunk>> index(@RequestParam(required = false) Map<String, String> params) {
        // var id = HttpSession.getAttribute("user");
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
                params.get("fromUser") == null ? "" : params.get("fromUser"),
                params.get("fromDomain") == null ? "" : params.get("fromDomain"),
                params.get("proxy") == null ? "" : params.get("proxy"),
                pageable);
        return ResponseEntity.ok(table);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam(name = "id") Long id) {
        var data = sipTrunkRepository.findById(id);
        if (data.isEmpty())
            return ResponseEntity.ok(new TblSipTrunk());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResult> create(@Valid @RequestBody TblSipTrunk sipTrunk) {

        var result = new ApiResult();

        if (sipTrunk.getName().trim().isEmpty()) {
            result.addMessage("'Name' cannot be Empty");
        }

        if (sipTrunkRepository.countAllByName(sipTrunk.getName()) > 0) {
            result.addMessage("The name is duplicate");
        }

        if (result.getMessages().size() == 0) {
            sipTrunkRepository.save(sipTrunk);
        }

        result.setHasError(result.getMessages().size() > 0);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResult> update(@Valid @RequestBody TblSipTrunk sipTrunk) {

        var result = new ApiResult();

        if (sipTrunk.getName().trim().isEmpty()) {
            result.addMessage("'Name' cannot be Empty");
        }

        var data = sipTrunkRepository.findByName(sipTrunk.getName());
        if (data != null && data.getId() != sipTrunk.getId()) {
            result.addMessage("The name is duplicate");
        }

        if (result.getMessages().size() == 0) {
            sipTrunkRepository.save(sipTrunk);
        }

        result.setHasError(result.getMessages().size() > 0);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody Map<String, Long> body) {

        var result = new ApiResult();
        try {
            sipTrunkRepository.deleteById(body.get("id"));
        } catch (Exception ex) {
            result.addMessage("An error occurred while delete item. Contact the system administrator");
        }
        result.setHasError(result.getMessages().size() > 0);
        return ResponseEntity.ok(result);
    }
}
