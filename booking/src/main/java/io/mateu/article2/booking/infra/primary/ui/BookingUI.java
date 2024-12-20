package io.mateu.article2.booking.infra.primary.ui;

import io.mateu.article2.booking.infra.primary.ui.bookings.BookingCrud;
import io.mateu.uidl.annotations.MateuUI;
import io.mateu.uidl.annotations.MenuOption;
import io.mateu.uidl.annotations.Submenu;

class BookingMenu {
    @MenuOption
    BookingCrud bookings;
}

@MateuUI("/booking")
public class BookingUI {

    @Submenu
    BookingMenu booking;

}
