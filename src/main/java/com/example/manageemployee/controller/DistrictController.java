package com.example.manageemployee.controller;

import com.example.manageemployee.dto.DistrictDto;
import com.example.manageemployee.request.SearchDistrictRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/district")
public class DistrictController {
    private final DistrictService districtService;

    @PostMapping
    public ResponseResult add(@RequestBody DistrictDto dto) {
        return districtService.add(dto);
    }

    @GetMapping
    public ResponseEntity<List<DistrictDto>> getAllDistrict() {
        List<DistrictDto> result = districtService.getAllDistrict();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<DistrictDto> getById(@RequestParam(value = "id") UUID id) {
        return districtService.getById(id);
    }

    @DeleteMapping(value = "")
    public ResponseResult deleteById(@RequestParam(value = "id") UUID id) {
        return districtService.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DistrictDto>> search(@RequestParam(name = "id", required = false) UUID id,
                                                    @RequestParam(name = "name", required = false) String name) {
        SearchDistrictRequest searchDistrictRequest = SearchDistrictRequest.builder()
                .name(name)
                .id(id)
                .build();
        List<DistrictDto> list1 = districtService.search(searchDistrictRequest);
        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseResult Update(@RequestBody DistrictDto dto) {
        return districtService.update(dto);
    }

    @PostMapping("/add")
    public ResponseResult add2(@RequestBody DistrictDto dto) {
        return districtService.add2(dto);
    }
}
