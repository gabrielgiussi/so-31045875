package oss.gabrielgiussi.hiplay.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class EntityB {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Basic
  private String description;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "setOfB")
  Set<EntityA> setOfA;

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

  public Set<EntityA> getSetOfA() {
    return setOfA;
  }

  public void setSetOfA(Set<EntityA> setOfA) {
    this.setOfA = setOfA;
  }

}
