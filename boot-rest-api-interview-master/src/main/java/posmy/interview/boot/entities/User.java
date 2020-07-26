package posmy.interview.boot.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import posmy.interview.boot.entities.enumeration.UserStatus;

@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
	@Column(length = 255, nullable = false)
	private String login;
	
	@NotNull
	@Column(length = 255, nullable = false)
	private String password;
	
	@NotNull
    @Column(length = 1, nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
	private Set<Role> roles;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BorrowEvent> borrowed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Set<BorrowEvent> getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Set<BorrowEvent> borrowed) {
		this.borrowed = borrowed;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", status=" + status + ", roles="
				+ roles + "]";
	}
}
