package com.lms.presentation;

import com.lms.persistence.OtpRequest;
import com.lms.persistence.OtpResponseDto;
import com.lms.persistence.OtpValidationRequest;
import com.lms.persistence.User;
import com.lms.service.SmsService;
import com.lms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OtpController {

    private final UserService userService;
    private final SmsService smsService;

    public OtpController(UserService userService, SmsService smsService) {
        this.userService = userService;
        this.smsService = smsService;
    }

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
        Optional<User> currentUser = getCurrentUser();
        log.info("Inside sendOtp :: {}", otpRequest.getUsername());
        return smsService.sendSMS(otpRequest, currentUser);
    }

    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        Optional<User> currentUser = getCurrentUser();
        log.info("Inside validateOtp :: {} {}", otpValidationRequest.getUsername(), otpValidationRequest.getOtp());
        return smsService.validateOtp(otpValidationRequest, currentUser);
    }

    @GetMapping("/viewAttendance")
    public ResponseEntity<ArrayList<Pair<String, Optional<User>>>> getMediaForCourse() {
        if (getCurrentUser().isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(SmsService.viewAttendance());
    }

    private Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(currentUserDetails.getUsername());
    }
}
