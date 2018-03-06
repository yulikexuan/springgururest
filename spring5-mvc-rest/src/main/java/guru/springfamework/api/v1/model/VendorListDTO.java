//: guru.springfamework.api.v1.model.VendorListDTO.java


package guru.springfamework.api.v1.model;


import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VendorListDTO {

	private final List<VendorDTO> vendors = new ArrayList<>();

	public VendorListDTO(@NonNull List<VendorDTO> vendors) {
		this.vendors.addAll(vendors);
	}

	public final List<VendorDTO> getVendors() {
		return Collections.unmodifiableList(this.vendors);
	}

}///:~