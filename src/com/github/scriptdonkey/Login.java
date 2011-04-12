package com.github.scriptdonkey;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Login {

    private static final UserService userService = UserServiceFactory
            .getUserService();

    public static String getURL() {

        // Get the URL of this app to redirect to it
        final Request request = RequestCycle.get().getRequest();
        final HttpServletRequest req = (HttpServletRequest) request
                .getContainerRequest();
        final StringBuffer requestURL = req.getRequestURL();
        final String scheme = "http://";
        String rest = requestURL.substring(scheme.length());
        final int slashIndex = rest.indexOf('/');
        if (slashIndex != -1) {
            rest = rest.substring(0, slashIndex);
        }
        final int queryIndex = rest.indexOf('?');
        if (queryIndex != -1) {
            rest = rest.substring(0, queryIndex);
        }
        final String url = scheme + rest;

        // Create the login/logout page URL with with redirect tho this app
        final String targetUrl = userService.isUserLoggedIn() ? logoutUrl(url)
                : loginUrl(url);

        return targetUrl;

    }

    private static String logoutUrl(final String appUrl) {
        return userService.createLogoutURL(appUrl);
    }

    private static String loginUrl(final String appUrl) {
        return userService.createLoginURL(appUrl);
    }

    public static boolean isUserLoggedIn() {
        return userService.isUserLoggedIn();
    }

    public static User getCurrentUserSafe() {
        if (isUserLoggedIn()) {
            return userService.getCurrentUser();
        }
        return null;
    }

}
