package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStatus;
import io.mateu.article2.booking.domain.ports.output.EventBus;
import io.mateu.article2.booking.infra.secondary.events.DomainEventPoller;
import io.mateu.article2.booking.infra.secondary.events.FakeEventBus;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingCreationFormTest {

    @Autowired
    BookingCreationForm bookingCreationForm;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FakeEventBus eventBus;

    @Autowired
    DomainEventPoller domainEventPoller;

    @Test
    void bookingIsCreated() {
        // given
        var customerName = "Mateu";
        var serviceDescription = "1 double room at Hotel Colombo";
        var startDate = LocalDate.of(2023, 7, 23);
        var endDate = LocalDate.of(2023, 8, 3);

        // when
        bookingCreationForm.customerName = customerName;
        bookingCreationForm.serviceDescription = serviceDescription;
        bookingCreationForm.startDate = startDate;
        bookingCreationForm.endDate = endDate;
        bookingCreationForm.createBooking().block();
        domainEventPoller.run();

        // then
        var booking = bookingRepository.findById(new BookingId(bookingCreationForm.id)).block();
        assertNotNull(booking);
        assertEquals(customerName, booking.getState().customerName().name());
        assertEquals(serviceDescription, booking.getState().serviceDescription().description());
        assertEquals(startDate, booking.getState().startDate().date());
        assertEquals(endDate, booking.getState().endDate().date());
        assertEquals(BookingStatus.Confirmed, booking.getState().status());
        var events = eventBus.popAll();
        assertEquals(1, events.size());
        assertInstanceOf(BookingCreated.class, events.get(0));
        assertEquals(bookingCreationForm.id, ((BookingCreated)events.get(0)).bookingId());
    }

}