package com.todo.config.security.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.todo.member.model.MemberDTO;

import lombok.ToString;
/**
* @packageName    : com.todo.config.security.dto
* @fileName        : CustomUserDetails.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@ToString
public class CustomUserDetails implements UserDetails  {

    private final MemberDTO memberDTO;

    public CustomUserDetails(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }
    
    // role값 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collection = new ArrayList<>();
		
		collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return memberDTO.getRole();
            }
        });
		
		return collection;
	}

	
  

	
	// password값을 반환
	@Override
	public String getPassword() {
		return memberDTO.getPassword();
	}

	// id를 반환
	@Override
	public String getUsername() {
		return memberDTO.getEmail();
	}
	
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
