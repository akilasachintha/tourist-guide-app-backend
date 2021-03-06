package com.datapirates.touristguideapp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class adminController {

    @Autowired
    private adminService adminService;
    /**Security **/

    @GetMapping("/getKey")
    private String getKey(@RequestBody AdminEntity admin){
        return adminService.madeAdminSecreteKey(admin.getUsername(),admin.getPassword());
    }

    /** AddAdmin **/

    @PostMapping("/add")
    private String addAdmin(@RequestBody AdminEntity admin ){
        adminService.changeAdmin(admin);
        return "added successfully";
    }
}
