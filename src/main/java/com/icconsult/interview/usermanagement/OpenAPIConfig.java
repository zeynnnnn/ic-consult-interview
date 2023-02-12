/*
 * OpenAPIConfig.java
 *
 * (c) Copyright iC Consult GmbH, 2021
 * All Rights reserved.
 *
 * iC Consult GmbH
 * 45128 Essen
 * Germany
 *
 */

package com.icconsult.interview.usermanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Software Developer Coding Assignment",
                description = "A simple and intentionally flawed spring boot project for conducting coding interviews.",
                contact = @Contact(
                        name = "iC Consult GmbH",
                        url = "https://ic-consult.com/",
                        email = "carreer@ic-consult.com"
                ),
                version= "3.0" ),
        servers = @Server(url = "${springdoc.swagger-ui.server}", description = "Default Server URL")
)
@SecurityScheme(name = "swagger_oauth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}", tokenUrl = "${springdoc.oAuthFlow.tokenUrl}")))
public class OpenAPIConfig {
}
