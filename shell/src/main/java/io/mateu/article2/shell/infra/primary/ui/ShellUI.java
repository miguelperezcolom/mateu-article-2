package io.mateu.article2.shell.infra.primary.ui;


import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.RemoteMenu;
import io.mateu.uidl.interfaces.HasLogo;
import io.mateu.uidl.interfaces.Icon;

@MateuUI("")                                        // (1)
@PageTitle("Mateu's booking system")
@Title("Home")
@AppTitle("My booking system")
@FavIcon("/images/mateu-favicon.png")
public class ShellUI extends Home implements HasLogo {


    @MenuOption(remote = true)                                     // (2)
    String booking = "booking";


    @MenuOption(remote = true)                                     // (2)
    String financial = "financial";

    @Override
    public String getLogoUrl() {
        return "/images/mateu-favicon.png";
    }
}
