package org.bte.orders.server.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class ContactInfo {
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String phone1;
    private String phone2;
    private String email;
}
