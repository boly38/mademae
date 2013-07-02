package net.mademocratie.gae.server.json;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.server.domain.DbImport;
import net.mademocratie.gae.server.domain.JsonServiceResponse;
import net.mademocratie.gae.server.exception.MaDemocratieException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ImplementedBy(OpsService.class)
public interface IOpsService {
    javax.ws.rs.core.Response dbExport() throws MaDemocratieException;
    JsonServiceResponse dbImport(DbImport dbImport);
    JsonServiceResponse sendReport() throws MaDemocratieException;
}
