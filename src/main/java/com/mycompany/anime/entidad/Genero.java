/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.anime.entidad;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author 1DAW08
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "GENERO")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Genero.findAll", query = "SELECT g FROM Genero g"),
    @javax.persistence.NamedQuery(name = "Genero.findById", query = "SELECT g FROM Genero g WHERE g.id = :id"),
    @javax.persistence.NamedQuery(name = "Genero.findByNombre", query = "SELECT g FROM Genero g WHERE g.nombre = :nombre")})
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.OneToMany(mappedBy = "genero")
    private Collection<Anime> animeCollection;

    public Genero() {
    }

    public Genero(Integer id) {
        this.id = id;
    }

    public Genero(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Anime> getAnimeCollection() {
        return animeCollection;
    }

    public void setAnimeCollection(Collection<Anime> animeCollection) {
        this.animeCollection = animeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.anime.entities.Genero[ id=" + id + " ]";
    }
    
}
