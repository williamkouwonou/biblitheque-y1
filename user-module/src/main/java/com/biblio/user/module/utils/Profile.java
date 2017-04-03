/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.biblio.user.module.utils;

/**
 *
 * @author kouwonou
 */
public enum Profile {
    ADMIN,MEMBRE;
    
    @Override
    public String toString(){
        switch(this){
            case ADMIN: return "Admin";
            case  MEMBRE :return "Membre";
        }
        return "";
    }
}
