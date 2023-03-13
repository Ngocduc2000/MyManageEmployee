package com.example.manageemployee.service.serviceImpl;

import com.example.manageemployee.constant.EmployeeExcelHeaderConstant;
import com.example.manageemployee.domain.Commune;
import com.example.manageemployee.domain.District;
import com.example.manageemployee.domain.Employee;
import com.example.manageemployee.domain.Province;
import com.example.manageemployee.dto.EmployeeDto;
import com.example.manageemployee.mapper.EmployeeMapperImpl;
import com.example.manageemployee.repository.CommuneRepository;
import com.example.manageemployee.repository.DistrictRepository;
import com.example.manageemployee.repository.EmployeeRepository;
import com.example.manageemployee.repository.ProvinceRepository;
import com.example.manageemployee.request.SearchEmployeeRequest;
import com.example.manageemployee.response.ResponseResult;
import com.example.manageemployee.service.EmployeeService;
import com.example.manageemployee.spec.EmployeeSpec;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.manageemployee.validator.Constants.*;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, UUID> implements EmployeeService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    private final CommuneRepository communeRepository;

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapperImpl employeeMapperImpl;

    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(ProvinceRepository provinceRepository, DistrictRepository districtRepository, CommuneRepository communeRepository, EmployeeRepository employeeRepository, EmployeeMapperImpl employeeMapperImpl, ModelMapper modelMapperImpl, ModelMapper modelMapper) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapperImpl = employeeMapperImpl;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseResult add(EmployeeDto dto) {
        Employee entity = new Employee();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAge(dto.getAge());

        Optional<Province> provinceEntity = provinceRepository.findById(dto.getProvinceId());
        provinceEntity.ifPresent(entity::setProvince);

        Optional<District> districtEntity = districtRepository.findById(dto.getDistrictId());
        districtEntity.ifPresent(entity::setDistrict);

        Optional<Commune> communeEntity = communeRepository.findById(dto.getCommuneId());
        communeEntity.ifPresent(entity::setCommune);

        employeeRepository.save(entity);
        return new ResponseResult(true, "Data added successfully");
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> entities = employeeRepository.findAll();

        return entities.stream()
                .map(entity -> modelMapper.map(entity, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<EmployeeDto> getById(UUID id) {
        Optional<Employee> optionalEntity = employeeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Employee entity = optionalEntity.get();
            EmployeeDto dto = new EmployeeDto(entity);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<EmployeeDto> search(SearchEmployeeRequest searchEmployeeRequest) {
        List<Employee> entities = employeeRepository.findAll(new EmployeeSpec(searchEmployeeRequest));

        return employeeMapperImpl.mapList(entities);
    }

    @Override
    public ResponseResult deleteById(UUID id) {
        Optional<Employee> optionalEntity = employeeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            employeeRepository.deleteById(id);
            return new ResponseResult(true, "Delete successfully");
        } else {
            return new ResponseResult(false, "Employee not found");
        }
    }

    @Override
    public ResponseResult update(EmployeeDto dto) {
        ResponseResult result = checkValid(dto, -1);
        if (result.isValid()) {
            return edit(dto);
        } else {
            return result;
        }
    }

    @Override
    public ResponseResult add2(EmployeeDto dto) {
        ResponseResult result = checkValid(dto, -1);
        if (result.isValid()) {
            return add(dto);
        } else {
            return result;
        }
    }

    @Override
    public byte[] export() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employee");
        Row header = sheet.createRow(0);
        String[] sheetHeader = EmployeeExcelHeaderConstant.EXCEL_HEADER;
        for (int i = 0; i < sheetHeader.length; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(sheetHeader[i]);
        }
        List<Employee> employees = employeeRepository.findAll();
        for (int i = 0; i < employees.size(); i++) {
            Row row = sheet.createRow(i+1);
            Map<Integer, String> map = new HashMap<>();
            map.put(1,String.valueOf(i+1));
            map.put(2, employees.get(i).getName());
            map.put(3, employees.get(i).getCode());
            map.put(4, employees.get(i).getEmail());
            map.put(5, employees.get(i).getPhone());
            map.put(6, Integer.toString(employees.get(i).getAge()));
            map.put(7, employees.get(i).getProvince() == null ? null : String.valueOf(employees.get(i).getProvince().getId()));
            map.put(8, employees.get(i).getProvince() == null ? null : String.valueOf(employees.get(i).getProvince().getId()));
            map.put(9, employees.get(i).getProvince() == null ? null : String.valueOf(employees.get(i).getProvince().getId()));
            map.forEach((k, v) -> {
                Cell cell = row.createCell(k-1);
                cell.setCellValue(v);
            });

        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseResult importFromExcel(String path) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<EmployeeDto> employeeDtoList = new ArrayList<>(sheet.getLastRowNum());

            for (Row row : sheet) {
                EmployeeDto dto = new EmployeeDto();
                Iterator<Cell> cellIterator = row.cellIterator();

                dto.setCode(cellIterator.next().getStringCellValue());
                dto.setName(cellIterator.next().getStringCellValue());
                dto.setEmail(cellIterator.next().getStringCellValue());
                dto.setPhone(cellIterator.next().getStringCellValue());
                dto.setAge((int)cellIterator.next().getNumericCellValue());
                dto.setProvinceId(UUID.fromString(cellIterator.next().getStringCellValue()));
                dto.setDistrictId(UUID.fromString(cellIterator.next().getStringCellValue()));
                dto.setCommuneId(UUID.fromString(cellIterator.next().getStringCellValue()));

                ResponseResult result = checkValid(dto, row.getRowNum() + 1);
                if (!result.isValid()) {
                    return result;
                }

                employeeDtoList.add(dto);
            }

            for (EmployeeDto dto : employeeDtoList) {
                add(dto);
            }

            return new ResponseResult("Imported successfully");
        } catch (IOException e) {
            return new ResponseResult("Cannot open the excel file, please check the path or make sure that the excel file is xlsx");
        }
    }

    private ResponseResult checkValid(EmployeeDto dto, int line) {
        StringBuilder errorMsg = new StringBuilder();

        if (dto.getCode() == null) {
            errorMsg.append("Line " + line + ": code is required :(\n");
        } else if (!Pattern.compile(CODE_PATTERN).matcher(dto.getEmail()).matches()) {
            errorMsg.append("Line " + line + ": Invalid code\n");
        }

        if (dto.getName() == null) {
            errorMsg.append("Line " + line + ": name is required :(\n");
        }

        if (dto.getEmail() == null) {
            errorMsg.append("Line " + line + ": email is required :(\n");
        } else if (!Pattern.compile(EMAIL_PATTERN).matcher(dto.getEmail()).matches()) {
            errorMsg.append("Line " + line + ": email is not match the email pattern\n");
        }

        if (dto.getPhone() == null) {
            errorMsg.append("Line " + line + ": phone is required\n");
        } else if (!Pattern.compile(PHONE_PATTERN).matcher(dto.getPhone()).matches()) {
            errorMsg.append("Line " + line + ": Invalid phone");
        }

        if (dto.getAge() < 0) {
            errorMsg.append("Line " + line + ": age cannot be negative\n");
        }

        if (dto.getProvinceId() == null) {
            errorMsg.append("Line " + line + ": ProvinceId is required\n");
        }

        if (dto.getDistrictId() == null) {
            errorMsg.append("Line " + line + ": districtId is required\n");
        }

        if (dto.getCommuneId() == null) {
            errorMsg.append("Line " + line + ": communeId is required\n");
        }

        if (errorMsg.length() > 0) {
            return new ResponseResult(false, errorMsg.toString());
        }

        return checkValid2(dto, line);
    }

    private ResponseResult checkValid2(EmployeeDto dto, int line) {
        if (dto == null) {
            return new ResponseResult(false, "Line " + line + ": employee is null");
        }
        if (dto.getDistrictId() == null) {
            return new ResponseResult(false, "Line " + line + ": districtId is null");
        }
        if (dto.getCommuneId() == null) {
            return new ResponseResult(false, "Line " + line + ": communeId is null");
        }
        if (dto.getProvinceId() == null) {
            return new ResponseResult(false, "Line " + line + ": provinceId is null");
        }
        if (dto.getId() != null && employeeRepository.findById(dto.getId()).isPresent()) {
            return new ResponseResult(false, "Line " + line + ": employee's id was not found");
        }
        try {
            District districtEntity = districtRepository.getOne(dto.getDistrictId());
            Commune communeEntity = communeRepository.getOne(dto.getCommuneId());
            if (!Objects.equals(communeEntity.getDistrict().getId(), dto.getDistrictId())) {
                return new ResponseResult(false, "Line " + line + ": Commune must belong District");
            }
            if (!Objects.equals(districtEntity.getProvince().getId(), dto.getProvinceId())) {
                return new ResponseResult(false, "Line " + line + ": District must belong City");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseResult(false, "Line " + line + ": ProvinceId or districtId or communeId was not found");
        }
        return new ResponseResult(true);
    }

    private ResponseResult edit(EmployeeDto dto) {
        if (!employeeRepository.existsById(dto.getId())) {
            return new ResponseResult(false, "Employee's id was not found");
        }

        if (dto.getCode() != null && existsByCode(dto.getCode())) {
            return new ResponseResult(false, "Employee's code already exists");
        }

        Province provinceEntity = provinceRepository.getOne(dto.getProvinceId());
        District districtEntity = districtRepository.getOne(dto.getDistrictId());
        Commune communeEntity = communeRepository.getOne(dto.getCommuneId());

        if (!communeEntity.getDistrict().getId().equals(dto.getDistrictId())) {
            return new ResponseResult(false, "Commune must belong to District");
        }

        if (!districtEntity.getProvince().getId().equals(dto.getProvinceId())) {
            return new ResponseResult(false, "District must belong to Province");
        }

        Employee entity = employeeRepository.getOne(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAge(dto.getAge());
        entity.setProvince(provinceEntity);
        entity.setDistrict(districtEntity);
        entity.setCommune(communeEntity);

        employeeRepository.save(entity);
        return new ResponseResult(true, "Data added successfully");
    }

    private boolean existsByCode(String code) {
        try {
            employeeRepository.getByCode(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
