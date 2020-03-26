package mops.infrastructure.interceptors;

import lombok.NoArgsConstructor;
import mops.utils.AccountUtil;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;

import javax.servlet.http.HttpServletRequest;

// https://stackoverflow.com/questions/9687084/using-a-handlerinterceptor-to-add-model-attributes-with-spring-web-flow

/**
 * Wir wollen in den Flows zu jeder Zeit das Attribut „account“ gesetzt haben, damit der Login angezeigt wird und wir
 * Zugriff auf den aktuell angemeldeten User haben. Idealerweise könnten wir also per Interceptor
 * in der Methode postHandle() das Attribut setzen. Web Flow 2 generiert aber kein ModelAndView mehr und gibt
 * stattdessen null zurück.
 *
 * Es ist möglich, den ServletExternalContext zu modifizieren, wie unten auskommentiert, dann steht das Attribut
 * zur Verfügung. Allerdings betrachet Web Flow das Attribut dann, wie man in FlowHandlerAdapter.handle() sieht,
 * nicht mehr als Input (der Input wird in einer anderen Mapt gespeichert), weshalb wir im Flow nicht
 *
 * <input name="account" type="mops.infrastructure.controllers.dtos.Account" required="true" />
 *
 * deklarieren können, um zu kennzeichnen, dass das Attribut übergeben wird. Ich habe deswegen
 * defaultCreateFlowExecutionInputMap() überschrieben. Jetzt kann man im Flow den obigen Tag deklarieren
 * und das Objekt wird beim Start des Flows in flowScope.account geschrieben.
 *
 * Achtung: defaultCreateFlowExecutionInputMap() wird nicht zwingend aufgerufen, wenn es andere Parameter gibt!
 * Das sieht man in FlowHandlerAdapter.getInputMap(). Falls das ein Problem werden sollte, gibt es immer noch
 * die Möglichkeit, die auskommentierte Lösung zu benutzen und im obigen Tag „required="true"“ zu entfernen.
 *
 * Eventuell gibt es eine Möglichkeit in AccountInterceptor.preHandle(), aber ich habe nicht herausgefunden,
 * ob und wie das geht.
 */

@NoArgsConstructor
public final class WebFlowHandlerAdapterAndInterceptor extends FlowHandlerAdapter {

    /*@Override
    protected ServletExternalContext createServletExternalContext(
            HttpServletRequest request,
            HttpServletResponse response) {

        ServletExternalContext context =
                super.createServletExternalContext(request, response);

        //context.getSessionMap().put("account",
        //        createAccountFromToken((KeycloakAuthenticationToken) request.getUserPrincipal()));

        return context;
    }*/

    @Override
    protected MutableAttributeMap<Object> defaultCreateFlowExecutionInputMap(HttpServletRequest request) {
        MutableAttributeMap<Object> inputMap = super.defaultCreateFlowExecutionInputMap(request);
        if (inputMap == null) {
            inputMap = new LocalAttributeMap<>(1, 1);
        }
        if (AccountUtil.isUserLogged()) {
            final KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
            inputMap.put("account",
                    AccountUtil.createAccountFromToken(token));
        }
        return inputMap;
    }
}
