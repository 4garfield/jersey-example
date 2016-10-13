package garfield.java.jersey.example.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import garfield.java.jersey.example.common.util.ObjectUtil;

@Entity
@Table(name = "SUB_DEPARTMENT", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"DEPARTMENT_ID", "NAME"})
})
@NamedQueries({
    @NamedQuery(
        name  = "SubDepartment.findAllByDepartmentId",
        query = "SELECT sub FROM SubDepartment sub JOIN sub.department d WHERE d.id = :departmentId"
    ),
    @NamedQuery(
        name = "SubDepartment.findOne",
        query = "SELECT sub FROM SubDepartment sub JOIN sub.department d WHERE d.id = :departmentId AND sub.id = :id"
    ),
    @NamedQuery(
        name = "SubDepartment.exist",
        query = "SELECT COUNT(sub) FROM SubDepartment sub JOIN sub.department d WHERE d.id = :departmentId AND sub.id = :id AND 1=1"
    ),
    @NamedQuery(
        name = "SubDepartment.delete",
        query = "DELETE FROM SubDepartment sub WHERE sub.department.id = departmentId AND sub.id = :id"
    )
})
public class SubDepartment implements Serializable{
    
    /**
     * @since jersey-example 0.1.0-SNAPSHOT
     */
    private static final long serialVersionUID = 1415615712656900653L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    @JsonIgnore
    @NotNull
    private Department department;
    
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;
    
    @OneToMany(mappedBy = "subDepartment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Goods> goodsSet;
    
    public Long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Goods> getGoodsSet() {
        return goodsSet;
    }

    public void setGoodsSet(Set<Goods> goodsSet) {
        this.goodsSet = goodsSet;
    }

    private static Object[] genarateFieldArray(SubDepartment subDepartment) {
        return new Object[]{ subDepartment.getName(), subDepartment.getDepartment() };
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
        if(!(obj instanceof SubDepartment))
            return false;
        return ObjectUtil.equals(genarateFieldArray(this), genarateFieldArray((SubDepartment) obj));
    }
    
}
