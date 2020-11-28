/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entitybd;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos TCHIOZEM
 */
@Entity
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientsPK clientsPK;
    @Basic(optional = false)
    @Column(name = "Site")
    private String site;
    @Column(name = "CodeClient")
    private String codeClient;
    @Column(name = "Civilite")
    private String civilite;
    @Column(name = "Nom")
    private String nom;
    @Column(name = "Prenom")
    private String prenom;
    @Column(name = "CodePostale")
    private String codePostale;
    @Column(name = "Ville")
    private String ville;
    @Column(name = "Pays")
    private String pays;
    @Column(name = "Adresse")
    private String adresse;
    @Column(name = "Region")
    private String region;
    @Column(name = "Departement")
    private String departement;
    @Column(name = "Arrondissement")
    private String arrondissement;
    @Column(name = "FichierCniAvant")
    private String fichierCniAvant;
    @Column(name = "NumeroContribuable")
    private String numeroContribuable;
    @Column(name = "FichierCniArriere")
    private String fichierCniArriere;
    @Column(name = "SiteInternet")
    private String siteInternet;
    @Column(name = "Password")
    private String password;
    @Column(name = "Token")
    private String token;
    @Column(name = "District")
    private String district;
    @Column(name = "Telephone")
    private String telephone;
    @Column(name = "NomEntreprise")
    private String nomEntreprise;
    @Column(name = "RaisonSociale")
    private String raisonSociale;
    @Column(name = "TelephoneWhatsapp")
    private String telephoneWhatsapp;
    @Column(name = "Email")
    private String email;
    @Column(name = "Foto")
    private String foto;
    @Column(name = "PieceIdentite")
    private String pieceIdentite;
    @Column(name = "DateCreation")
    private String dateCreation;
    @Column(name = "IDDateCreation")
    private Integer iDDateCreation;
    @Column(name = "DateNaissance")
    private String dateNaissance;
    @Column(name = "LieuNaissance")
    private String lieuNaissance;
    @Column(name = "Archive")
    private Integer archive;
    @Column(name = "ArchiveIDDate")
    private Integer archiveIDDate;
    @Column(name = "Statut")
    private Integer statut;
    @Column(name = "IDDateNaissance")
    private Integer iDDateNaissance;
    @Column(name = "Sexe")
    private Integer sexe;
    @Column(name = "Synchronization")
    private String synchronization;
    @Column(name = "LastUpDateTime")
    private String lastUpDateTime;
    @Column(name = "ArchiveDate")
    private String archiveDate;
    @Column(name = "IDLastUpDate")
    private Integer iDLastUpDate;
    @Column(name = "CodeAgence")
    private String codeAgence;
    @Column(name = "DateTime")
    private String dateTime;
    @Column(name = "IDDateTime")
    private Integer iDDateTime;

    public Clients() {
    }

    public Clients(ClientsPK clientsPK) {
        this.clientsPK = clientsPK;
    }

    public Clients(ClientsPK clientsPK, String site) {
        this.clientsPK = clientsPK;
        this.site = site;
    }

    public Clients(int nEng, String indexe) {
        this.clientsPK = new ClientsPK(nEng, indexe);
    }

    public ClientsPK getClientsPK() {
        return clientsPK;
    }

    public void setClientsPK(ClientsPK clientsPK) {
        this.clientsPK = clientsPK;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public String getFichierCniAvant() {
        return fichierCniAvant;
    }

    public void setFichierCniAvant(String fichierCniAvant) {
        this.fichierCniAvant = fichierCniAvant;
    }

    public String getNumeroContribuable() {
        return numeroContribuable;
    }

    public void setNumeroContribuable(String numeroContribuable) {
        this.numeroContribuable = numeroContribuable;
    }

    public String getFichierCniArriere() {
        return fichierCniArriere;
    }

    public void setFichierCniArriere(String fichierCniArriere) {
        this.fichierCniArriere = fichierCniArriere;
    }

    public String getSiteInternet() {
        return siteInternet;
    }

    public void setSiteInternet(String siteInternet) {
        this.siteInternet = siteInternet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getTelephoneWhatsapp() {
        return telephoneWhatsapp;
    }

    public void setTelephoneWhatsapp(String telephoneWhatsapp) {
        this.telephoneWhatsapp = telephoneWhatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPieceIdentite() {
        return pieceIdentite;
    }

    public void setPieceIdentite(String pieceIdentite) {
        this.pieceIdentite = pieceIdentite;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Integer getIDDateCreation() {
        return iDDateCreation;
    }

    public void setIDDateCreation(Integer iDDateCreation) {
        this.iDDateCreation = iDDateCreation;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setArchive(Integer archive) {
        this.archive = archive;
    }

    public Integer getArchiveIDDate() {
        return archiveIDDate;
    }

    public void setArchiveIDDate(Integer archiveIDDate) {
        this.archiveIDDate = archiveIDDate;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public Integer getIDDateNaissance() {
        return iDDateNaissance;
    }

    public void setIDDateNaissance(Integer iDDateNaissance) {
        this.iDDateNaissance = iDDateNaissance;
    }

    public Integer getSexe() {
        return sexe;
    }

    public void setSexe(Integer sexe) {
        this.sexe = sexe;
    }

    public String getSynchronization() {
        return synchronization;
    }

    public void setSynchronization(String synchronization) {
        this.synchronization = synchronization;
    }

    public String getLastUpDateTime() {
        return lastUpDateTime;
    }

    public void setLastUpDateTime(String lastUpDateTime) {
        this.lastUpDateTime = lastUpDateTime;
    }

    public String getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(String archiveDate) {
        this.archiveDate = archiveDate;
    }

    public Integer getIDLastUpDate() {
        return iDLastUpDate;
    }

    public void setIDLastUpDate(Integer iDLastUpDate) {
        this.iDLastUpDate = iDLastUpDate;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getIDDateTime() {
        return iDDateTime;
    }

    public void setIDDateTime(Integer iDDateTime) {
        this.iDDateTime = iDDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientsPK != null ? clientsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.clientsPK == null && other.clientsPK != null) || (this.clientsPK != null && !this.clientsPK.equals(other.clientsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.payment.pay.entitybd.Clients[ clientsPK=" + clientsPK + " ]";
    }
    
}
