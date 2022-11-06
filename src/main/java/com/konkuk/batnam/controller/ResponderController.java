package com.konkuk.batnam.controller;

import com.konkuk.batnam.dto.request.responder.ResponderCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderUpdateDto;
import com.konkuk.batnam.dto.response.ResponderResponseDto;
import com.konkuk.batnam.service.ResponderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/responders", produces = "application/json; charset=UTF8")
public class ResponderController {

    private final ResponderService responderService;

    @PostMapping
    public ResponderResponseDto createResponder(@RequestBody ResponderCreateDto dto) {
        return responderService.createResponder(dto);
    }

    @GetMapping("/{airStripId}")
    public List<ResponderResponseDto> getResponderListByAirStripId(@PathVariable Long airStripId) {
        return responderService.findAllByStripId(airStripId);
    }

    @PutMapping
    public Long updateResponder(@RequestBody ResponderUpdateDto dto) {
        return responderService.updateResponder(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteResponder(@PathVariable Long id) {
        responderService.deleteResponder(id);
    }
}
