package com.springsecurity.springservice.service;

import com.springsecurity.springservice.entity.AppRole;
import com.springsecurity.springservice.entity.AppUser;
import com.springsecurity.springservice.repository.AppRoleRepository;
import com.springsecurity.springservice.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AcountServiceImpl implements  AccountService{

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AcountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    /**
     * Ici je met la logique d'encodage du mot de passe, car chaque fois
     * que j'ajoute un user, je passe par la méthode addNewUser
     * @param appUser
     * @return
     */
    @Override
    public AppUser addNewUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    /**
     * Pour ajouter le role à l'utilisateur, je recupère le user correspondant ensuite, le role
     * depuis la BDD, je recupère la collection de role depuis l'entité User et j'ajoute le role
     */
    public void addRoleToUser(String username, String roleName) {

        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
