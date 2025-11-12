package com.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="manager")
public class Manager {
    @Id
    private  int  idmanager;
    private String  managerName;
    private String  managerPassword;
    // 无参构造函数
    public Manager() {
    }

    // 全参构造函数
    public Manager(int idmanager, String managerName, String managerPassword) {
        this.idmanager = idmanager;
        this.managerName = managerName;
        this.managerPassword = managerPassword;
    }

    // getter 和 setter 方法
    public int getIdmanager() {
        return idmanager;
    }

    public void setIdmanager(int idmanager) {
        this.idmanager = idmanager;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Manager{" +
                "idmanager=" + idmanager +
                ", managerName='" + managerName + '\'' +
                ", managerPassword='" + managerPassword + '\'' +
                '}';
    }
}
