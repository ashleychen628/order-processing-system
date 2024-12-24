public class BillingAddressValidator {
    public static class BillingAddressNotMatchException extends Exception {
        public BillingAddressNotMatchException(String message) {
            super(message);
        }
    }

    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zip;

        public Address(String street, String city, String state, String zip) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }

        public boolean equals(Address other) {
            if (other == null) return false;
            return street.equals(other.street) &&
                   city.equals(other.city) &&
                   state.equals(other.state) &&
                   zip.equals(other.zip);
        }
    }

    public static void validateAddress(Address submitted, Address registered) 
        throws BillingAddressNotMatchException {
        if (submitted == null || registered == null) {
            throw new BillingAddressNotMatchException("Addresses cannot be null");
        }
        
        if (!submitted.equals(registered)) {
            throw new BillingAddressNotMatchException(
                "Submitted address does not match registered address"
            );
        }
    }

    // Test method to demonstrate different scenarios
    public static boolean testAddressValidation(Address submitted, Address registered) {
        try {
            validateAddress(submitted, registered);
            return true;
        } catch (BillingAddressNotMatchException e) {
            return false;
        }
    }
}