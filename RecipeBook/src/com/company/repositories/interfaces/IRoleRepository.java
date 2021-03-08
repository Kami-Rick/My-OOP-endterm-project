package com.company.repositories.interfaces;

import com.company.entities.Role;

import java.util.List;

public interface IRoleRepository {
    public Role getRole(int id);
    public Role getRole(String name);
    public List<Role> getRoles();
}
