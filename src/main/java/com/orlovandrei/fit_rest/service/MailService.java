package com.orlovandrei.fit_rest.service;

public interface MailService {

    void sendRegistrationEmail(String email, String username);
}
