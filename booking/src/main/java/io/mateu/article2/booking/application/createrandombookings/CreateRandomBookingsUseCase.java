package io.mateu.article2.booking.application.createrandombookings;

import com.github.javafaker.Faker;
import io.mateu.article2.booking.domain.booking.Booking;
import io.mateu.article2.booking.domain.booking.BookingFactory;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.booking.domain.ports.output.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class CreateRandomBookingsUseCase
{
    final BookingRepository bookingRepository;
    final EventBus eventBus;
    final BookingFactory bookingFactory;
    final Faker faker = new Faker();
    final List<String> serviceNames = List.of(
            "Hotel Cala Marçal",
            "Hotel Palma Bay",
            "Hotel Marivent",
            "Hostal Box",
            "Hotel Alcudiamar",
            "Hotel Tropicana",
            "Hotel Mediterraneo",
            "Transfer Hotel -> Airport",
            "Transfer Airport -> Hotel"
    );

    public CreateRandomBookingsUseCase(BookingRepository bookingRepository, EventBus eventBus, BookingFactory bookingFactory) {
        this.bookingRepository = bookingRepository;
        this.eventBus = eventBus;
        this.bookingFactory = bookingFactory;
    }

    public Mono<String> createBookings(CreateRandomBookingsRequest request) {
        long t0 = System.currentTimeMillis();
        return Flux.range(0, request.quantity())
                .map(this::createBooking)
                .flatMap(bookingRepository::save)
                .then(Mono.fromCallable(() -> "" + request.quantity() + " bookings created in " + (System.currentTimeMillis() - t0) + "ms."));
    }

    private Booking createBooking(Integer integer) {
        log.info("Creating random booking {}", integer);
        var startDate = LocalDate.now().plusDays(Math.abs(new Random().nextInt()) % 60);
        return bookingFactory.create(
                new BookingId(UUID.randomUUID().toString()),
                new CustomerName(faker.name().fullName()),
                new ServiceDescription(serviceNames.get(Math.abs(new Random().nextInt()) % serviceNames.size())),
                new BookingStartDate(startDate),
                new BookingEndDate(startDate.plusDays(1 + Math.abs(new Random().nextInt()) % 13)),
                new BookingValue(BigDecimal.valueOf(Math.abs(new Random().nextDouble(3000))))
        );
    }
}
