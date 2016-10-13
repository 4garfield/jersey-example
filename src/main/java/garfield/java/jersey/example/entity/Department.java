package garfield.java.jersey.example.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import garfield.java.jersey.example.common.util.ObjectUtil;

@Entity
@Table(name = "DEPARTMENT")
@NamedQueries({
    @NamedQuery(
        name  = "Department.findAll",
        query = "SELECT sub FROM Department sub",
        hints={ @QueryHint(name="org.hibernate.cacheable", value="true") }
    )
})
public class Department implements Serializable{
    
    /**
     * @since jersey-example 0.1.0-SNAPSHOT
     */
    private static final long serialVersionUID = 6614460790289888927L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME", nullable = false, unique = true)
    @NotNull
    private String name;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<SubDepartment> subDepartmentSet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubDepartment> getSubDepartmentSet() {
        return subDepartmentSet;
    }

    public void setSubDepartmentSet(Set<SubDepartment> subDepartmentSet) {
        this.subDepartmentSet = subDepartmentSet;
    }
    
    private static Object[] genarateFieldArray(Department department) {
        return new Object[]{ department.getName() };
    }
    
    @Override
    public int hashCode() {
        return ObjectUtil.hashCode(genarateFieldArray(this));
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        if(!(obj instanceof Department))
            return false;
        return ObjectUtil.equals(genarateFieldArray(this), genarateFieldArray((Department) obj));
    }
    
}
