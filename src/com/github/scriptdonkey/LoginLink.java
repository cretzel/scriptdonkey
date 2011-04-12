package com.github.scriptdonkey;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class LoginLink extends ExternalLink {

    private static final long serialVersionUID = 1L;

    public LoginLink(final String id) {
        super(id, Login.getURL());
        add(new Label("label", new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                final boolean userLoggedIn = Login.isUserLoggedIn();
                return userLoggedIn ? "Logout ("
                        + Login.getCurrentUserSafe().getNickname() + ")"
                        : "Login";
            }
        }));
    }

}
