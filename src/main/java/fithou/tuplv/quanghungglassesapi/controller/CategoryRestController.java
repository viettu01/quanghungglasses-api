package fithou.tuplv.quanghungglassesapi.controller;

import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CategoryRestController {
    final CategoryService categoryService;
    final PaginationMapper paginationMapper;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                    @RequestParam(value = "status", defaultValue = "", required = false) Boolean status,
                                    @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                    @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (status != null) {
            return ResponseEntity.ok().body(paginationMapper.mapToPaginationDTO(categoryService.findByNameContainingAndStatus(name, status, pageable)));
        }

        if (StringUtils.hasText(name))
            return ResponseEntity.ok().body(paginationMapper.mapToPaginationDTO(categoryService.findByNameContaining(name, pageable)));

        return ResponseEntity.ok().body(paginationMapper.mapToPaginationDTO(categoryService.findAll(pageable)));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok().body(categoryService.findBySlug(slug));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping({"/", ""})
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(categoryService.create(categoryRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"/", ""})
    public ResponseEntity<?> update(@Valid @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(categoryService.update(categoryRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping({"/", ""})
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {
        categoryService.deleteByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-status")
    public ResponseEntity<?> findByStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok().body(Map.of("countByStatus", categoryService.countByStatus(status)));
    }
}
