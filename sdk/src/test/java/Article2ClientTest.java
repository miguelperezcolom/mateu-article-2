import io.mateu.article2.Article2Client;
import io.mateu.article2.Article2ClientUsingWebClient;
import io.mateu.article2.model.Booking;
import io.mateu.article2.model.BookingStatus;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Article2ClientTest {

    String testBookingId = "test-booking-id";

    @Test
    void testFind() {
        Article2Client client = new Article2ClientUsingWebClient();
        var bookingId = "0585665f-8809-4092-a159-035fbc05e7fc";
        var booking = client.findById(bookingId).block();
        System.out.println(booking);
    }

    @Test
    void testSearch() {
        Article2Client client = new Article2ClientUsingWebClient();
        var page = client.search("Rosemary", Pageable.ofSize(20)).block();
        System.out.println(page);
    }

    @Test
    void testCreate() {
        Article2Client client = new Article2ClientUsingWebClient();
        var page = client.create(new Booking(
                testBookingId,
                "Mr Test",
                "1 double room in Hotel Formentor",
                LocalDate.of(2025, 10, 2),
                LocalDate.of(2025, 10, 10),
                new BigDecimal(1200.32),
                BookingStatus.Confirmed
        )).block();
        System.out.println(page);
    }

    @Test
    void testUpdate() {
        Article2Client client = new Article2ClientUsingWebClient();
        var page = client.update(new Booking(
                testBookingId,
                "Mr Test 2",
                "Hotel Formentor",
                LocalDate.of(2025, 8, 2),
                LocalDate.of(2025, 8, 10),
                new BigDecimal(700.55),
                BookingStatus.Confirmed
        )).block();
        System.out.println(page);
    }

    @Test
    void testCancel() {
        Article2Client client = new Article2ClientUsingWebClient();
        var booking = client.cancel(testBookingId, new BigDecimal(50)).block();
        System.out.println(booking);
    }
}
