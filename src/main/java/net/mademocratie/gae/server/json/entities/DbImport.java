package net.mademocratie.gae.server.json.entities;

import net.mademocratie.gae.server.entities.IDatabaseContent;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DbImport implements IDatabaseContent {
    private String importContent;

    public DbImport() {
    }

    public String getImportContent() {
        return importContent;
    }

    public String getContent() {
        return importContent;
    }

    public void setImportContent(String importContent) {
        this.importContent = importContent;
    }

    public String getSchemaVersion() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
