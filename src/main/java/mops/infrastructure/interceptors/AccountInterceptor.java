package mops.infrastructure.interceptors;

import lombok.NoArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static mops.infrastructure.interceptors.AccountFromTokenUtil.createAccountFromToken;
import static mops.infrastructure.interceptors.AccountFromTokenUtil.isUserLogged;

// Analog zu https://www.baeldung.com/spring-model-parameters-with-handler-interceptor
@NoArgsConstructor
public class AccountInterceptor extends HandlerInterceptorAdapter {

    /**
     * Wenn bei einer Request das Modell erstellt wurde, wird das Attribut „account“ hinzugefügt.
     * Wie im Styleguide erläutert, sorgt das dafür, dass die aktuellen Userinformationen unten links angezeigt werden.
     * @param request ...
     * @param response ...
     * @param object ...
     * @param model ...
     * @throws Exception ...
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object object,
                           ModelAndView model) throws Exception {
        if (model != null && !isRedirectView(model) && isUserLogged()) {
            addUserDetailsToModel(model, request);
        }
    }

    private void addUserDetailsToModel(ModelAndView model, HttpServletRequest request) {
        final KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        model.addObject("account", createAccountFromToken(token));
    }

    // Mit instanceof ist abgesichert, dass view nicht null ist
    @SuppressWarnings("PMD.LawOfDemeter")
    public static boolean isRedirectView(ModelAndView mv) {

        final String viewName = mv.getViewName();
        if (viewName != null && viewName.startsWith("redirect:/")) {
            return true;
        }

        final View view = mv.getView();
        return view instanceof SmartView && ((SmartView) view).isRedirectView();
    }
}
