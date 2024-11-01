package io.mateu.article2.shell.infra.primary.ui;


import io.mateu.core.domain.uidefinition.core.interfaces.HasIcon;
import io.mateu.core.domain.uidefinition.core.interfaces.HasTitle;
import io.mateu.core.domain.uidefinition.core.interfaces.Icon;
import io.mateu.core.domain.uidefinition.shared.annotations.*;
import io.mateu.core.domain.uidefinition.shared.annotations.Home;

@MateuUI("")
@Caption("Mateu's booking system")
public class ShellUI implements HasTitle, HasIcon {


    @MenuOption
    String booking = "Hola booking!";


    @MenuOption
    String financial = "Hola financial!";

    @Home
    io.mateu.article2.shell.infra.primary.ui.Home home;


    @Override
    public String getTitle() {
        return "Booking system";
    }

    @Override
    public String getIcon() {
        return "vaadin:travel";
    }
}
