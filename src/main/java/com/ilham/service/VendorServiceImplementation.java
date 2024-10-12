package com.ilham.service;

import com.ilham.exception.NotFoundException;
import com.ilham.models.Vendor;
import com.ilham.repository.VendorRepository;
import com.ilham.utils.VendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VendorServiceImplementation implements VendorService{

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public List<Vendor> getAllVendor() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(int vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(
                        () -> new NotFoundException("Vendor with id " + vendorId + " not found")
                );
    }

    @Override
    public Vendor insertVendor(Vendor vendor) {

        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor updateVendor(int vendorId,Vendor vendor) {
        Vendor existingVendor = getVendorById(vendorId);

        Vendor newVendor = new Vendor();
        newVendor.setId(vendorId);
        newVendor.setName(vendor.getName());
        newVendor.setEmail(vendor.getEmail());
        newVendor.setNoHp(vendor.getNoHp());

        return vendorRepository.save(newVendor);
    }

    @Override
    public Vendor deleteVendor(int vendorId) {
        Vendor existingVendor = getVendorById(vendorId);

        vendorRepository.delete(existingVendor);

        return VendorMapper.vendorResponse(existingVendor);
    }
}
