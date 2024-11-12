package io.mateu.article2.booking.infra.primary.ui;

import io.mateu.article2.booking.infra.primary.ui.bookings.BookingsCrud;
import io.mateu.uidl.annotations.MateuUI;
import io.mateu.uidl.annotations.MenuOption;

@MateuUI("/booking")
public class BookingUI {

    @MenuOption
    BookingsCrud bookings;


}
