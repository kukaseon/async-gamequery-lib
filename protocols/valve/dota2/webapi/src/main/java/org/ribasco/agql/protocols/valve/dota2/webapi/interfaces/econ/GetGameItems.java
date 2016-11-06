package org.ribasco.agql.protocols.valve.dota2.webapi.interfaces.econ;

import org.ribasco.agql.protocols.valve.dota2.webapi.Dota2ApiConstants;
import org.ribasco.agql.protocols.valve.dota2.webapi.requests.Dota2EconRequest;

public class GetGameItems extends Dota2EconRequest {
    public GetGameItems(int apiVersion, String language) {
        super(Dota2ApiConstants.DOTA2_METHOD_GETGAMEITEMS, apiVersion, language);
    }
}