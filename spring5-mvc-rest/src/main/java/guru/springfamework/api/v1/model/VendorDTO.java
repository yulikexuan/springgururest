//: guru.springfamework.api.v1.model.VendorDTO.java


package guru.springfamework.api.v1.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class VendorDTO {

    @ApiModelProperty(value = "The name of the vendor.", notes = "This peoperty is required.", required = true)
    private String name;

    @ApiModelProperty(value = "Vendor's URL.", notes = "The URL of the vendor.", required = false)
    private String vendorUrl;

    VendorDTO() {
    }

    private VendorDTO(String name, String vendorUrl) {
        this.name = name;
        this.vendorUrl = vendorUrl;
    }

    public String getName() {
        return this.name;
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