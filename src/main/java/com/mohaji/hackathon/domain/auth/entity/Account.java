package com.mohaji.hackathon.domain.auth.entity;


import com.mohaji.hackathon.domain.Image.entity.Image;
import com.mohaji.hackathon.domain.wear.entity.ImageEntity;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails , ImageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  private String username;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable
  @Setter
  private List<Image> images;

  public void changePassword(String newPassword) {
    this.password = newPassword;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
}
