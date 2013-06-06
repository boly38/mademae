package net.mademocratie.gae.server.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DbImport {
    private String dbcontent;

    public DbImport() {
    }

    public String getDbcontent() {
        return dbcontent;
    }

    public void setDbcontent(String dbcontent) {
        this.dbcontent = dbcontent;
    }
}
