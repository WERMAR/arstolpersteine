import {KeycloakService} from "keycloak-angular";

export function initializeKeycloak(
  keycloak: KeycloakService
) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'arStolpersteine',
        clientId: 'frontend-client'
      },
      initOptions: {
        pkceMethod: 'S256',
        // must match to the configured value in keycloak
        redirectUri: 'http://localhost:443/home',
        checkLoginIframe: false
      }
    });
}
