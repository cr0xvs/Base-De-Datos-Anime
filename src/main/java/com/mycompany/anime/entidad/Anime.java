/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.anime.entidad;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 1DAW08
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "ANIME")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Anime.findAll", query = "SELECT a FROM Anime a"),
    @javax.persistence.NamedQuery(name = "Anime.findById", query = "SELECT a FROM Anime a WHERE a.id = :id"),
    @javax.persistence.NamedQuery(name = "Anime.findByNombre", query = "SELECT a FROM Anime a WHERE a.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Anime.findByEstudio", query = "SELECT a FROM Anime a WHERE a.estudio = :estudio"),
    @javax.persistence.NamedQuery(name = "Anime.findByFechaLanzamiento", query = "SELECT a FROM Anime a WHERE a.fechaLanzamiento = :fechaLanzamiento"),
    @javax.persistence.NamedQuery(name = "Anime.findByNumTemporadas", query = "SELECT a FROM Anime a WHERE a.numTemporadas = :numTemporadas"),
    @javax.persistence.NamedQuery(name = "Anime.findByCapitulos", query = "SELECT a FROM Anime a WHERE a.capitulos = :capitulos"),
    @javax.persistence.NamedQuery(name = "Anime.findByEstado", query = "SELECT a FROM Anime a WHERE a.estado = :estado"),
    @javax.persistence.NamedQuery(name = "Anime.findByFavorito", query = "SELECT a FROM Anime a WHERE a.favorito = :favorito"),
    @javax.persistence.NamedQuery(name = "Anime.findByImagen", query = "SELECT a FROM Anime a WHERE a.imagen = :imagen")})
public class Anime implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ESTUDIO")
    private String estudio;
    @javax.persistence.Column(name = "FECHA_LANZAMIENTO")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaLanzamiento;
    @javax.persistence.Column(name = "NUM_TEMPORADAS")
    private Short numTemporadas;
    @javax.persistence.Column(name = "CAPITULOS")
    private Short capitulos;
    @javax.persistence.Column(name = "ESTADO")
    private String estado;
    @javax.persistence.Column(name = "FAVORITO")
    private Boolean favorito;
    @javax.persistence.Column(name = "IMAGEN")
    private String imagen;
    @javax.persistence.JoinColumn(name = "GENERO", referencedColumnName = "ID")
    @javax.persistence.ManyToOne
    private Genero genero;

    public Anime() {
    }

    public Anime(Integer id) {
        this.id = id;
    }

    public Anime(Integer id, String nombre, String estudio) {
        this.id = id;
        this.nombre = nombre;
        this.estudio = estudio;
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

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Short getNumTemporadas() {
        return numTemporadas;
    }

    public void setNumTemporadas(Short numTemporadas) {
        this.numTemporadas = numTemporadas;
    }

    public Short getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Short capitulos) {
        this.capitulos = capitulos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
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
        if (!(object instanceof Anime)) {
            return false;
        }
        Anime other = (Anime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.anime.entities.Anime[ id=" + id + " ]";
    }
    
}
