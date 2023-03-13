package com.example.manageemployee.controller;

import com.example.manageemployee.dto.ProvinceDto;
import com.example.manageemployee.request.SearchProvinceRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/province")
public class ProvinceController {
    private final ProvinceService provinceService;

    @PostMapping()
    public ResponseResult add(@RequestBody ProvinceDto dto) {
        return provinceService.add(dto);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<ProvinceDto> getById(@RequestParam(value = "id") UUID id) {
        return provinceService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<ProvinceDto>> getAll() {
        List<ProvinceDto> result = provinceService.getAllProvince();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseResult deleteById(@RequestParam(value = "id") UUID id) {
        return provinceService.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProvinceDto>> search(@RequestParam(name = "id", required = false) UUID id,
                                                    @RequestParam(name = "name", required = false) String name) {
        SearchProvinceRequest searchProvinceRequest = SearchProvinceRequest.builder()
                .name(name)
                .id(id)
                .build();
        List<ProvinceDto> list1 = provinceService.search(searchProvinceRequest);
        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    @PostMapping("/addProDisCom")
    public ResponseResult addProDisCom(@RequestBody ProvinceDto dto) {
        return provinceService.addProDisCom(dto);
    }

    @PutMapping()
    public ResponseResult Update(@RequestBody ProvinceDto dto) {
        return provinceService.update(dto);
    }

    @PostMapping("/add")
    public ResponseResult add2(@RequestBody ProvinceDto dto) {
        return provinceService.add2(dto);
    }
}
