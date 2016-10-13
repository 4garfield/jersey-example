package garfield.java.jersey.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import garfield.java.jersey.example.common.util.ObjectUtil;

@Entity
@Table(name = "GOODS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"SUB_DEPARTMENT_ID", "NAME"})
})
@NamedQueries({
    @NamedQuery(
        name  = "Goods.findAll",
        query = "SELECT goods FROM Goods goods JOIN goods.subDepartment sub WHERE sub.id = :subDepartmentId AND sub.department.id = :departmentId"
    ),
    @NamedQuery(
        name = "Goods.exist",
        query = "SELECT COUNT(goods) FROM Goods goods JOIN goods.subDepartment sub WHERE sub.id = :subDepartmentId AND sub.department.id = :departmentId AND "
            + "goods.id = :id AND 1=1"
    ),
    @NamedQuery(
        name = "Goods.findOne",
        query = "SELECT goods FROM Goods goods JOIN goods.subDepartment sub WHERE sub.id = :subDepartmentId AND sub.department.id = :departmentId AND "
            + "goods.id = :id"
    ),
    @NamedQuery(
        name = "Goods.delete",
        query = "DELETE FROM Goods goods WHERE goods = (SELECT goods FROM Goods goods JOIN goods.subDepartment sub WHERE sub.id = :subDepartmentId AND "
            + "sub.department.id = :departmentId AND goods.id = :id)"
    )
})
public class Goods implements Serializable{
    
    /**
     * @since jersey-example 0.1.0-SNAPSHOT
     */
    private static final long serialVersionUID = 7527865037229184361L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "SUB_DEPARTMENT_ID", nullable = false)
    @JsonIgnore
    @NotNull
    private SubDepartment subDepartment;
    
    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal price;
    
    @Column(name = "SUMMARY", nullable = false)
    @NotNull
    private String summary;
    
    @Column(name = "PRODUCT_DETAILS", nullable = false)
    @Lob
    @NotNull
    private String productDetails;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubDepartment getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(SubDepartment subDepartment) {
        this.subDepartment = subDepartment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    private static Object[] genarateFieldArray(Goods goods) {
        return new Object[]{ goods.getName(), goods.getSubDepartment(), goods.getPrice(), goods.getSummary(), goods.getProductDetails() };
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
        if(!(obj instanceof Goods))
            return false;
        return ObjectUtil.equals(genarateFieldArray(this), genarateFieldArray((Goods) obj));
    }
    
}
