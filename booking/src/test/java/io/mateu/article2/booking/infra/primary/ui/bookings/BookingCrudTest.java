package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.infra.secondary.events.DomainEventPoller;
import io.mateu.article2.booking.infra.secondary.events.FakeEventBus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingCrudTest {

    @Autowired
    BookingCrud bookingCrud;

    @Autowired
    FakeEventBus eventBus;

    @Autowired
    DomainEventPoller domainEventPoller;

    @Test
    public void allBookingsAreFound() throws Throwable {
        // given
        createBookings();

        // when
        var page = bookingCrud.fetchRows("", new BookingFilters(), Pageable.ofSize(30)).block();

        // then
        assertEquals(3, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
        assertEquals(3, page.getContent().size());
        assertEquals(1, page.getContent().stream().filter(r -> r.customer().equals("Mateu")).count());
        assertEquals(1, page.getContent().stream().filter(r -> r.customer().equals("Antonia")).count());
        assertEquals(1, page.getContent().stream().filter(r -> r.customer().equals("Miguel")).count());
    }

    private void createBookings() throws Throwable {
        createBooking("Mateu", "1 double room at Hotel Colombo");
        createBooking("Antonia", "1 suite at Hotel Bahía Palma");
        createBooking("Miguel", "1 single room at Hotel Formentor");
    }

    private void createBooking(String customerName, String serviceDescription) throws Throwable {
        var bookingCreationForm = (BookingCreationForm) bookingCrud.getNewRecordForm();
        bookingCreationForm.customerName = customerName;
        bookingCreationForm.serviceDescription = serviceDescription;
        bookingCreationForm.startDate = LocalDate.now();
        bookingCreationForm.endDate = LocalDate.now();
        bookingCreationForm.createBooking().block();
        domainEventPoller.run();
    }

    @Test
    public void onlyBookingIsFound() throws Throwable {
        // given
        createBookings();

        // when
        var page = bookingCrud.fetchRows("mateu", new BookingFilters(), Pageable.ofSize(30)).block();

        // then
        assertEquals(1, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
        assertEquals(1, page.getContent().size());
        assertEquals(1, page.getContent().stream().filter(r -> r.customer().equals("Mateu")).count());
        assertEquals(0, page.getContent().stream().filter(r -> r.customer().equals("Antonia")).count());
        assertEquals(0, page.getContent().stream().filter(r -> r.customer().equals("Miguel")).count());
    }

}