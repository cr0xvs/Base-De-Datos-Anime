/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.anime.entidad;

import com.mycompany.anime.entidad.Estudio;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author crisd
 */
@Entity
@Table(name = "ANIME")
@NamedQueries({
    @NamedQuery(name = "Anime.findAll", query = "SELECT a FROM Anime a"),
    @NamedQuery(name = "Anime.findById", query = "SELECT a FROM Anime a WHERE a.id = :id"),
    @NamedQuery(name = "Anime.findByNombre", query = "SELECT a FROM Anime a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Anime.findByGenero", query = "SELECT a FROM Anime a WHERE a.genero = :genero"),
    @NamedQuery(name = "Anime.findByFechaLanzamiento", query = "SELECT a FROM Anime a WHERE a.fechaLanzamiento = :fechaLanzamiento"),
    @NamedQuery(name = "Anime.findByNumTemporadas", query = "SELECT a FROM Anime a WHERE a.numTemporadas = :numTemporadas"),
    @NamedQuery(name = "Anime.findByCapitulos", query = "SELECT a FROM Anime a WHERE a.capitulos = :capitulos"),
    @NamedQuery(name = "Anime.findByEstado", query = "SELECT a FROM Anime a WHERE a.estado = :estado"),
    @NamedQuery(name = "Anime.findByFavorito", query = "SELECT a FROM Anime a WHERE a.favorito = :favorito"),
    @NamedQuery(name = "Anime.findByImagen", query = "SELECT a FROM Anime a WHERE a.imagen = :imagen")})
public class Anime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "GENERO")
    private String genero;
    @Column(name = "FECHA_LANZAMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;
    @Column(name = "NUM_TEMPORADAS")
    private Integer numTemporadas;
    @Column(name = "CAPITULOS")
    private Integer capitulos;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FAVORITO")
    private Boolean favorito;
    @Column(name = "IMAGEN")
    private String imagen;
    @JoinColumn(name = "ESTUDIO", referencedColumnName = "ID")
    @ManyToOne
    private Estudio estudio;

    public Anime() {
    }

    public Anime(Integer id) {
        this.id = id;
    }

    public Anime(Integer id, String nombre, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Integer getNumTemporadas() {
        return numTemporadas;
    }

    public void setNumTemporadas(Integer numTemporadas) {
        this.numTemporadas = numTemporadas;
    }

    public Integer getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Integer capitulos) {
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

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
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
        return "com.mycompany.anime.entidad.Anime[ id=" + id + " ]";
    }
    
}
