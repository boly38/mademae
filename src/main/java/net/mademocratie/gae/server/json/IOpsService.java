package net.mademocratie.gae.server.json;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.json.entities.DbImport;
import net.mademocratie.gae.server.json.entities.JsonServiceResponse;
import net.mademocratie.gae.server.exception.MaDemocratieException;

@ImplementedBy(OpsService.class)
public interface IOpsService {
    javax.ws.rs.core.Response dbExport() throws MaDemocratieException;
    JsonServiceResponse dbImport(DbImport dbImport);
    JsonServiceResponse sendReport() throws MaDemocratieException;
}
