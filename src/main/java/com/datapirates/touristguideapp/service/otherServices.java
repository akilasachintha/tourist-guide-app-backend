package com.datapirates.touristguideapp.service;

public interface otherServices {
    void sendMails(String toEmail,String subject,String body);
    boolean compareStatus(String state1,String state2,String state3);
    boolean compareStatus2(String state1,String state2,String state3);
}
