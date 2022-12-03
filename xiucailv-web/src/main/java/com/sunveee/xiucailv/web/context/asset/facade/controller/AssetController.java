package com.sunveee.xiucailv.web.context.asset.facade.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AssetController {

    @RequestMapping(value = "/ping")
    public String ping() {
        return "ok";
    }

}