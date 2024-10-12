package com.ilham.utils;

import com.ilham.models.Vendor;

public class VendorMapper {

    public static Vendor vendorResponse(Vendor vendor) {
        Vendor vendorResponse = new Vendor();
        vendorResponse.setId(vendor.getId());
        vendorResponse.setName(vendor.getName());
        vendorResponse.setEmail(vendor.getEmail());
        vendorResponse.setNoHp(vendor.getNoHp());

        return vendorResponse;
    }
}
