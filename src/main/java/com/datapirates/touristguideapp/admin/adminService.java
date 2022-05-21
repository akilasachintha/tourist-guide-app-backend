package com.datapirates.touristguideapp.admin;

public interface adminService {
    String madeAdminSecreteKey(String username,String password);
    boolean checkTokenValidity(String token) throws Exception;
    adminEntity changeAdmin(adminEntity admin);
    String madeUserSecretKey(String email,String password);
}
