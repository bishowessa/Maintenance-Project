package com.lms.service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import com.lms.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.lms.presentation.TwilioConfig;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {
    @Autowired
    private TwilioConfig twilioConfig;

    private static final Random RANDOM = new Random();
    private final Map<String, String> otpMap = new ConcurrentHashMap<>();
    private static final List<Pair<String, Optional<User>>> lessonRequest = Collections.synchronizedList(new ArrayList<>());

    public OtpResponseDto sendSMS(OtpRequest otpRequest, Optional<User> curr) {
        OtpResponseDto otpResponseDto;
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber()); // to
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from

            String otp = generateOTP();
            String otpMessage = "Dear Customer, Your OTP is " + otp + " for sending sms through Spring boot application. Thank You.";

            Message.creator(to, from, otpMessage).create();

            otpMap.put(otpRequest.getUsername(), otp);
            lessonRequest.add(Pair.of(otpRequest.getLessonName(), curr));

            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            log.error("Failed to send OTP SMS", e);
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    public String validateOtp(OtpValidationRequest otpValidationRequest, Optional<User> currentUser) {
        String username = otpValidationRequest.getUsername();
        String enteredOtp = otpValidationRequest.getOtpNumber();

        if (otpMap.containsKey(username) && otpMap.get(username).equals(enteredOtp)) {
            otpMap.remove(username);
            return "OTP is valid!";
        } else {
            currentUser.ifPresent(user ->
                    lessonRequest.removeIf(pair ->
                            pair.getRight().isPresent() && pair.getRight().get().equals(user)
                    )
            );
            return "OTP is invalid!";
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000").format(package com.lms.service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import com.lms.persistence.*;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.lms.presentation.TwilioConfig;

import lombok.extern.slf4j.Slf4j;

        @Service
        @Slf4j
        public class SmsService {

            private final TwilioConfig twilioConfig;

            private static final Random RANDOM = new Random();
            private final Map<String, String> otpMap = new ConcurrentHashMap<>();
            private static final List<Pair<String, Optional<User>>> lessonRequest = Collections.synchronizedList(new ArrayList<>());

            // Constructor Injection
            public SmsService(TwilioConfig twilioConfig) {
                this.twilioConfig = twilioConfig;
            }

            public OtpResponseDto sendSMS(OtpRequest otpRequest, Optional<User> curr) {
                OtpResponseDto otpResponseDto;
                try {
                    PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber()); // to
                    PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from

                    String otp = generateOTP();
                    String otpMessage = "Dear Customer, Your OTP is " + otp + " for sending sms through Spring boot application. Thank You.";

                    Message.creator(to, from, otpMessage).create();

                    otpMap.put(otpRequest.getUsername(), otp);
                    lessonRequest.add(Pair.of(otpRequest.getLessonName(), curr));

                    otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
                } catch (Exception e) {
                    log.error("Failed to send OTP SMS", e);
                    otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
                }
                return otpResponseDto;
            }

            public String validateOtp(OtpValidationRequest otpValidationRequest, Optional<User> currentUser) {
                String username = otpValidationRequest.getUsername();
                String enteredOtp = otpValidationRequest.getOtpNumber();

                if (otpMap.containsKey(username) && otpMap.get(username).equals(enteredOtp)) {
                    otpMap.remove(username);
                    return "OTP is valid!";
                } else {
                    currentUser.ifPresent(user ->
                            lessonRequest.removeIf(pair ->
                                    pair.getRight().isPresent() && pair.getRight().get().equals(user)
                            )
                    );
                    return "OTP is invalid!";
                }
            }

            private String generateOTP() {
                return new DecimalFormat("000000").format(RANDOM.nextInt(999999));
            }

            public static List<Pair<String, Optional<User>>> viewAttendance() {
                return lessonRequest;
            }
        }
        int i = RANDOM.nextInt (999999););
    }

    public static List<Pair<String, Optional<User>>> viewAttendance() {
        return lessonRequest;
    }
}
