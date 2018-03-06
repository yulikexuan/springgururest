//: guru.springfamework.api.v1.model.VendorDTO.java


package guru.springfamework.api.v1.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class VendorDTO {

	private String name;
	private String vendorUrl;

	VendorDTO() {}

	private VendorDTO(String name, String vendorUrl) {
		this.name = name;
		this.vendorUrl = vendorUrl;
	}

	public static final class VendorDTOBuilder {

		private String name;
		private String vendorUrl;

		private VendorDTOBuilder() {
		}

		public static final VendorDTOBuilder getInstance() {
			return new VendorDTOBuilder();
		}

		public VendorDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public VendorDTOBuilder setVendorUrl(String vendorUrl) {
			this.vendorUrl = vendorUrl;
			return this;
		}

		public VendorDTO createVendorDTO() {
			return new VendorDTO(name, vendorUrl);
		}
	}

}///:~