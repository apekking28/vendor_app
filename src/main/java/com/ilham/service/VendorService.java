package com.ilham.service;

import com.ilham.models.Vendor;

import java.util.List;

public interface VendorService {

    public List<Vendor> getAllVendor();

    public Vendor getVendorById(int vendorId);

    public Vendor insertVendor(Vendor vendor);

    public Vendor updateVendor(int vendorId,Vendor vendor);

    public Vendor deleteVendor(int vendorId);
}
