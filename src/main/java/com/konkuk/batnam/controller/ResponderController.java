package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.responder.ResponderCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderUpdateDto;
import com.konkuk.batnam.dto.response.ResponderResponseDto;
import com.konkuk.batnam.service.ResponderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/responders", produces = "application/json; charset=UTF8")
public class ResponderController {

    private final ResponderService responderService;
    @Operation(summary = "비상 연락망 추가",description = "비상 연락망 추가 api")
    @PostMapping
    public ResponderResponseDto createResponder(@RequestBody ResponderCreateDto dto) {
        return responderService.createResponder(dto);
    }

    @Operation(summary = "활주로 별 비상 연락망 리스트 조회",description = "비상 연락망 조회 api")
    @GetMapping("/{airStripId}")
    public List<ResponderResponseDto> getResponderListByAirStripId(@PathVariable Long airStripId) {
        return responderService.findAllByStripId(airStripId);
    }

    @Operation(summary = "비상 연락망 수정",description = "비상 연락망 수정 api")
    @PutMapping
    public Long updateResponder(@RequestBody ResponderUpdateDto dto) {
        return responderService.updateResponder(dto);
    }

    @Operation(summary = "비상 연락망 삭제",description = "비상 연락망 삭제 api")
    @DeleteMapping("/{id}")
    public void deleteResponder(@PathVariable Long id) {
        responderService.deleteResponder(id);
    }
}
