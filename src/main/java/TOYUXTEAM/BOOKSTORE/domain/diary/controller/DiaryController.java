package TOYUXTEAM.BOOKSTORE.domain.diary.controller;

import TOYUXTEAM.BOOKSTORE.domain.diary.dto.request.DiaryRequest;
import TOYUXTEAM.BOOKSTORE.domain.diary.dto.response.DiaryResponse;
import TOYUXTEAM.BOOKSTORE.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/home")  // page 2
    public ResponseEntity<Page<DiaryResponse>> getDiariesHome(@PathVariable("id") Long id, @RequestParam("page") int offset) {
        return ResponseEntity.ok().body(diaryService.findAll(id, offset, 2));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/list")  // page 9
    public ResponseEntity<Page<DiaryResponse>> getDiariesDateBefore(@PathVariable("id") Long id, @RequestParam("page") int offset) {
        return ResponseEntity.ok().body(diaryService.findAll(id, offset, 9));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/list/{month}/{day}/diary") // page 9
    public ResponseEntity<Page<DiaryResponse>> getDiariesForDate(@PathVariable("id") Long id,
                                            @PathVariable("month") Long month, @PathVariable("day") Long day, @RequestParam("page") int offset) {
        return ResponseEntity.ok().body(diaryService.findByDate(id, month, day, offset));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/diary")
    public ResponseEntity<DiaryResponse> getDiary(@RequestParam("diaryid") Long diaryId) {
        return ResponseEntity.ok().body(diaryService.find(diaryId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/new")
    public ResponseEntity<DiaryResponse> createDiary(@PathVariable("id") Long id, @RequestBody @Valid DiaryRequest diaryRequest) {
        return ResponseEntity.ok().body(diaryService.create(id, diaryRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/newfile")
    public ResponseEntity<DiaryResponse> createDiaryWithFile(@PathVariable("id") Long id, @RequestBody @Valid DiaryRequest diaryRequest) throws IOException {
        return ResponseEntity.ok().body(diaryService.createWithFile(id, diaryRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}/diary")
    public ResponseEntity<DiaryResponse> modifyDiary(@RequestParam("diaryid") Long diaryId,
                                @PathVariable("id") Long id, @RequestBody @Valid DiaryRequest diaryRequest) {
        return ResponseEntity.ok().body(diaryService.modify(id, diaryId, diaryRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/diary")
    public ResponseEntity<DiaryResponse> deleteDiary(@PathVariable("id") Long id, @RequestParam("diaryid") Long diaryId){
        return ResponseEntity.ok().body(diaryService.delete(id, diaryId));
    }
}
