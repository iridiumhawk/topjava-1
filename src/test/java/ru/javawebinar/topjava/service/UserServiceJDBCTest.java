package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;


//@ActiveProfiles(Profiles.ACTIVE_DB)
@ActiveProfiles(profiles = {"jdbc","hsqldb"})
public class UserServiceJDBCTest extends UserTestBase {

}