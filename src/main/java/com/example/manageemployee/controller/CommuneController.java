package com.example.manageemployee.controller;

import com.example.manageemployee.dto.CommuneDto;
import com.example.manageemployee.request.SearchCommuneRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.CommuneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RestController
@RequestMapping("/api/v1/commune")
public class CommuneController {
    private final CommuneService communeService;

    public CommuneController(CommuneService communeService) {
        this.communeService = communeService;
    }

    @PostMapping
    public ResponseResult add(@RequestBody CommuneDto dto){
        return communeService.add(dto);
    }

    @GetMapping("/id")
    public ResponseEntity<CommuneDto> getById(@RequestParam(value = "id") UUID id) {
        return communeService.getById(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CommuneDto>> getAllCommune() {
        List<CommuneDto> result = communeService.getAllCommune();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add2")
    public ResponseResult add2(@RequestBody CommuneDto dto){
        return communeService.add2(dto);
    }

    @DeleteMapping()
    public ResponseResult deleteById(@RequestParam(value = "id") UUID id) {
        return communeService.deleteById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommuneDto>> search(@RequestParam(name = "id", required = false) UUID id,
                                                   @RequestParam(name = "name", required = false) String name) {
        SearchCommuneRequest searchCommuneRequest = SearchCommuneRequest.builder()
                .name(name)
                .id(id)
                .build();
        List<CommuneDto> list1 = communeService.search(searchCommuneRequest);
        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseResult Update(@RequestBody CommuneDto dto) {
        return communeService.update(dto);
    }
}
