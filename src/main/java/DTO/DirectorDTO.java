/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Director;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class DirectorDTO {
    private Integer id;
    private String name;
    private String about;

    public DirectorDTO() {
    }

    public DirectorDTO(Director director) {
        this.id = director.getId();
        this.name = director.getName();
        this.about = director.getAbout();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "DirectorDTO{" + "id=" + id + ", name=" + name + ", about=" + about + '}';
    }
    
    
}
