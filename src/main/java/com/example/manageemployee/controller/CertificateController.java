package com.example.manageemployee.controller;

import com.example.manageemployee.dto.CertificateDto;
import com.example.manageemployee.request.SearchCertificateRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.CertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("api/v1/certificate")
public class CertificateController {
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping()
    public ResponseResult add(@RequestBody CertificateDto dto) {
        return certificateService.add(dto);
    }

    @GetMapping()
    public ResponseEntity<List<CertificateDto>> getAll() {
        List<CertificateDto> result = certificateService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<CertificateDto> getById(@RequestParam(value = "id") UUID id) {
        return certificateService.getById(id);
    }

    @DeleteMapping(value = "")
    public ResponseResult deleteById(@RequestParam(value = "id") UUID id) {
        return certificateService.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CertificateDto>> search(@RequestParam(name = "start_day", required = false) LocalDateTime startDay,
                                                       @RequestParam(name = "end_day", required = false) LocalDateTime endDay,
                                                       @RequestParam(name = "code", required = false) String code,
                                                       @RequestParam(name = "name", required = false) String name,
                                                       @RequestParam(name = "type", required = false) String type) {
        SearchCertificateRequest searchCertificateRequest = SearchCertificateRequest.builder()
                .code(code)
                .startDay(startDay)
                .endDay(endDay)
                .name(name)
                .type(type)
                .build();
        List<CertificateDto> list1 = certificateService.search(searchCertificateRequest);
        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseResult add2(@RequestBody CertificateDto dto) {
        return certificateService.add2(dto);
    }

    @PutMapping()
    public ResponseResult Update(@RequestBody CertificateDto dto) {
        return certificateService.update(dto);
    }

}
