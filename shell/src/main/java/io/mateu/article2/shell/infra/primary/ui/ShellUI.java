package io.mateu.article2.shell.infra.primary.ui;


import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.RemoteMenu;
import io.mateu.uidl.interfaces.HasLogo;
import io.mateu.uidl.interfaces.Icon;

@MateuUI("")                                        // (1)
@PageTitle("Mateu's booking system")
@Title("Home")
@AppTitle("My booking system")
@FavIcon("/mateu-favicon.png")
public class ShellUI extends Home implements HasLogo {


    @MenuOption                                     // (2)
    RemoteMenu booking = new RemoteMenu(
            "booking/mateu/v3",        // Api path
            "io.mateu.article2.booking." +
                    "infra.primary.ui.BookingUI",   // UI id
            "booking"                               // Menu id
    );


    @MenuOption                                     // (3)
    RemoteMenu financial = new RemoteMenu(
            "financial/mateu/v3",      // Api path
            "io.mateu.article2.financial.shared." +
                    "infra.primary.ui.FinancialUI", // UI id
            "financial"                             // Menu id
    );

    @Override
    public String getLogoUrl() {
        return "/mateu-favicon.png";
    }
}
