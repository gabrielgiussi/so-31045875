package oss.gabrielgiussi.hiplay.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class EntityA {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Basic
  private String description;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "relations", joinColumns = {
    @JoinColumn(name = "id_a", nullable = false, updatable = false)
  }, inverseJoinColumns = {
    @JoinColumn(name = "id_b", nullable = false, updatable = false)
  })
  Set<EntityB> setOfB;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<EntityB> getSetOfB() {
    return setOfB;
  }

  public void setSetOfB(Set<EntityB> setOfB) {
    this.setOfB = setOfB;
  }

}
