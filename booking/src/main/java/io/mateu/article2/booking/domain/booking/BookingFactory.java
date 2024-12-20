package io.mateu.article2.booking.domain.booking;

import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.shared.events.DomainEvent;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingFactory {

    private final ApplicationContext applicationContext;

    public BookingFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Booking create(BookingId id, CustomerName customerName, ServiceDescription serviceDescription, BookingStartDate startDate, BookingEndDate endDate, BookingValue value) {
        var booking = applicationContext.getBean(Booking.class);
        booking.setState(new BookingState(
                id,
                customerName,
                serviceDescription,
                startDate,
                endDate,
                value,
                BookingStatus.Confirmed
        ));
        booking.addEvent(new DomainEvent(
                UUID.randomUUID().toString(),
                "booking-" + id.id(),
                LocalDateTime.now(),
                new BookingCreated(
                        id.id(),
                        customerName.name(),
                        serviceDescription.description(),
                        startDate.date(),
                        endDate.date(),
                        value.value(),
                        BookingStatus.Confirmed.name())
        ));
        return booking;
    }

    public Booking ofState(BookingState bookingState) {
        var booking = applicationContext.getBean(Booking.class);
        booking.setState(bookingState);
        return booking;
    }
}
