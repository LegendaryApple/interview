package posmy.interview.boot.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 255)
    @Id
    @Column(length = 255)
    private String name;
    
    public Role() {
    	
    }
    
    public Role(@NotNull @Size(max = 255) String name) {
		super();
		this.name = name;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
            "name='" + name + '\'' +
            "}";
    }
}
