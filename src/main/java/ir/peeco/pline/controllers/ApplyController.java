package ir.peeco.pline.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ir.peeco.pline.pline.ApiResult;
import ir.peeco.pline.pline.fileGenratorTools.FileGenrator;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/configs")
public class ApplyController {

    private FileGenrator fileGenrator;

    @Autowired
    public void setFileGenrator(FileGenrator fileGenrator) {
        this.fileGenrator = fileGenrator;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResult> apply(@RequestBody Map<String, String> body) {
        fileGenrator.GratePjsip();

        ApiResult result = new ApiResult();
        result.setHasError(false);
        return ResponseEntity.ok(result);
    }

}
