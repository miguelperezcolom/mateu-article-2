package io.mateu.article2.booking.infra.primary.ui;

import io.mateu.article2.booking.infra.primary.ui.bookings.BookingsCrud;
import io.mateu.core.domain.uidefinition.shared.annotations.MateuUI;
import io.mateu.core.domain.uidefinition.shared.annotations.MenuOption;

@MateuUI("/booking")
public class BookingUI {

    @MenuOption
    BookingsCrud bookings;


}
