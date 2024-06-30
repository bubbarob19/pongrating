package me.bubbarob19.pongrating.controller;

import lombok.AllArgsConstructor;
import me.bubbarob19.pongrating.model.InviteCode;
import me.bubbarob19.pongrating.service.InviteCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/invite-code")
@AllArgsConstructor
public class InviteCodeController {

    private InviteCodeService inviteCodeService;

    @GetMapping("/generate")
    public ResponseEntity<InviteCode> generate() {
        return ResponseEntity.ok(inviteCodeService.generateInviteCode());
    }
}
