package com.ilham.controller;

import com.ilham.models.Vendor;
import com.ilham.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class VendorController {

    @Autowired
    VendorService vendorService;

    @GetMapping("/api/vendor")
    public List<Vendor> listVendor() {
        return vendorService.getAllVendor();
    }

    @GetMapping("/api/vendor/{vendorId}")
    public Vendor getVendor(@PathVariable int vendorId) {
        return vendorService.getVendorById(vendorId);
    }

    @PostMapping("/api/vendor")
    public Vendor inserVendor(@RequestBody Vendor vendor) {
        return vendorService.insertVendor(vendor);
    }

    @PutMapping("/api/vendor/{vendorId}")
    public Vendor updateVendor(@PathVariable int vendorId, @RequestBody Vendor vendor) {
        return vendorService.updateVendor(vendorId, vendor);
    }

    @DeleteMapping("/api/vendor/{vendorId}")
    public Vendor deleteVendor(@PathVariable int vendorId) {
        return vendorService.deleteVendor(vendorId);
    }
}
