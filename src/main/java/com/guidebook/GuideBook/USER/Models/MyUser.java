package com.guidebook.GuideBook.USER.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.ADMIN.Models.College;
import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.MEETHOST.Model.Event;
import com.guidebook.GuideBook.MEETHOST.Model.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String authorities ="ROLE_USER"; // e.g., "ROLE_USER,ROLE_ADMIN"

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(authority -> (GrantedAuthority) () -> authority)
                .collect(Collectors.toList());
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

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date updatedOn;
    @Version
    @JsonIgnore
    private Integer version;

//    TICKET SYSTEM
    @ManyToMany
    @JoinTable(
            name = "user_ticket",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "ticketId")
    )
    private List<Ticket> ticketList;//owning side

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "eventId")
    )
    private List<Event> eventList;//owning side
}
