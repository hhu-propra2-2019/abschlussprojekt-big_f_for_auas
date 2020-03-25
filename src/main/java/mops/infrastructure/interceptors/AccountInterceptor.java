package mops.infrastructure.interceptors;

import lombok.NoArgsConstructor;
import mops.controllers.dtos.Account;
import mops.domain.models.user.UserId;
import mops.utils.AccountUtil;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Analog zu https://www.baeldung.com/spring-model-parameters-with-handler-interceptor
// ACHTUNG: Wenn der/die Nutzer*in noch nicht eingeloggt ist, MUSS Keycloak den/die Nutzer*in auf die Login-Seite
// verweisen, weil es sonst NullPointerExceptions im Controller und sonstwo gibt.
// Das ist zurzeit so konfiguriert und aus meiner Sicht spricht nichts dagegen, den Code zu zu lassen,
// aber wenn ein anderer Authentication-Provider verwendet werden soll,
// muss ein fehlendes Login-Token zu einem Redirect auf die Login-Seite führen.
@NoArgsConstructor
public final class AccountInterceptor extends HandlerInterceptorAdapter {

    private static final String ACCOUNT_ATTRIBUTE = "account";
    private static final String USERID_ATTRIBUTE = "userId";

    // Selbst, wenn die Nutzer nicht eingeloggt sind, werden die Attribute gesetzt,
    // damit man sich im Controller ohne required = false die Attribute injecten lassen kann.
    // Wenn die Attribute gar nicht gesetzt sind, kann Spring sonst die Anfrage nicht auf die entsprechende
    // Controller-Methode mappen.
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (AccountUtil.isUserLogged()) {
            final KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
            request.setAttribute(ACCOUNT_ATTRIBUTE, AccountUtil.createAccountFromToken(token));
            request.setAttribute(USERID_ATTRIBUTE, AccountUtil.createUserIdFromRequest(request));
        } else {
            request.setAttribute(ACCOUNT_ATTRIBUTE, new Account(null, null, null, null));
            request.setAttribute(USERID_ATTRIBUTE, new UserId(null));
        }
        return true;
    }

    /**
     * Nachdem der Controller aufgerufen wurde, wird das Attribut „account“ zum Modell hinzugefügt.
     * Wie im Styleguide erläutert, sorgt das dafür, dass die aktuellen Userinformationen unten links angezeigt werden.
     * @param request Die Request, in die in preHandle() das Account-Objekt hinzugefügt wurde
     * @param response ...
     * @param object ...
     * @param model Das Modell, in das das Account-Objekt übertragen wird.
     * @throws Exception ...
     */
    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object object,
                           ModelAndView model) throws Exception {
        if (model != null && request.getAttribute(ACCOUNT_ATTRIBUTE) != null) {
            model.getModel().put(ACCOUNT_ATTRIBUTE, request.getAttribute(ACCOUNT_ATTRIBUTE));
        }
    }
}
