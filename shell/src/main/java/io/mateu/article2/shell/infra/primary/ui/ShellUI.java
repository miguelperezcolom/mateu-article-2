package io.mateu.article2.shell.infra.primary.ui;


import io.mateu.uidl.annotations.Caption;
import io.mateu.uidl.annotations.MateuUI;
import io.mateu.uidl.annotations.MenuOption;
import io.mateu.uidl.data.RemoteMenu;
import io.mateu.uidl.interfaces.HasIcon;
import io.mateu.uidl.interfaces.HasTitle;

@MateuUI("")
@Caption("Mateu's booking system")
public class ShellUI implements HasTitle, HasIcon {


    @MenuOption
    RemoteMenu booking = new RemoteMenu("booking/mateu/v3", "io.mateu.article2.booking.infra.primary.ui.BookingUI", "bookings");


    @MenuOption
    String financial = "Hola financial!";

    @io.mateu.uidl.annotations.Home
    Home home;


    @Override
    public String getTitle() {
        return "Booking system";
    }

    @Override
    public String getIcon() {
        return "vaadin:travel";
    }
}
