package com.springsecurity.springservice.service;

import com.springsecurity.springservice.entity.AppRole;
import com.springsecurity.springservice.entity.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    // Je lui donne en parametre un objet de type appRole et il va l'ajouté
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
