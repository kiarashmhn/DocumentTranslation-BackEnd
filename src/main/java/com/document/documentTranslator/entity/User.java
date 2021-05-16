package com.document.documentTranslator.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

    private String username;
    private String password;
    private String email;
    private Long level;
    private String phone;
    private String tempPassword;
    private Date tempPassCreationDate;

    @Override
    public Map<String, Object> map() {
        Map<String, Object> map = super.map();
        map.put("username", this.username);
        map.put("email", this.email);
        map.put("level", this.level);
        map.put("phone", this.phone);

        return map;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public Date getTempPassCreationDate() {
        return tempPassCreationDate;
    }

    public void setTempPassCreationDate(Date tempPassCreationDate) {
        this.tempPassCreationDate = tempPassCreationDate;
    }
}
