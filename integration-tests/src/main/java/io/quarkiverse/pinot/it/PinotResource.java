/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.quarkiverse.pinot.it;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.apache.pinot.client.Connection;
import org.apache.pinot.client.Request;
import org.apache.pinot.client.ResultSet;
import org.apache.pinot.client.ResultSetGroup;

@Path("/pinot")
@ApplicationScoped
public class PinotResource {
    @Inject
    Connection pinotConnection;

    @GET
    public String hello() {
        String query = "select playerName, sum(runs) from baseballStats where yearID>=2000 group by playerName order by sum(runs) desc limit 10";

        Request pinotClientRequest = new Request("sql", query);
        ResultSetGroup pinotResultSetGroup = pinotConnection.execute(pinotClientRequest);
        ResultSet resultTableResultSet = pinotResultSetGroup.getResultSet(0);

        StringBuilder results = new StringBuilder();
        for (int i = 0; i < resultTableResultSet.getRowCount(); i++) {
            var year = resultTableResultSet.getString(i, 0);
            var count = resultTableResultSet.getString(i, 1);
            results.append(year).append(" | ").append(count).append('\n');
        }

        return results.toString();
    }
}
