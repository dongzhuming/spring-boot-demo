package org.molecule.demo.springdataldap;

/**
 * @author Dong Zhuming
 */

import lombok.Data;


import javax.naming.Name;


import org.springframework.ldap.odm.annotations.Attribute;

import org.springframework.ldap.odm.annotations.DnAttribute;

import org.springframework.ldap.odm.annotations.Entry;

import org.springframework.ldap.odm.annotations.Id;


/**
 * {@link Person} object stored inside LDAP.
 *
 * @author Mark Paluch
 */

@Entry(base = "ou=people,dc=springframework,dc=org", objectClasses = "inetOrgPerson")

@Data

public class Person {


    private @Id
    Name id;

    private @DnAttribute(value = "uid", index = 3)
    String uid;

    private @Attribute(name = "cn")
    String fullName;

    private @Attribute(name = "sn")
    String lastname;

    private String userPassword;

}
