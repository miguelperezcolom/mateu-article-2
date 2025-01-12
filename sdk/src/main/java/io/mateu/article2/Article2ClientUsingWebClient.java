package io.mateu.article2;

import io.mateu.article2.model.Booking;
import io.mateu.article2.model.BookingListRow;
import io.mateu.article2.model.Direction;
import io.mateu.article2.model.SearchRequest;
import io.mateu.article2.model.Sort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class Article2ClientUsingWebClient implements Article2Client {

    WebClient client = WebClient.create("https://article2.mateu.io");

    @Override
    public Mono<Void> create(Booking booking) {
        return client.post().uri("/booking/api/bookings")
                .bodyValue(booking)
                .retrieve().toBodilessEntity().then();
    }

    @Override
    public Mono<Void> update(Booking booking) {
        return client.put().uri("/booking/api/bookings/{bookingId}", booking.id())
                .bodyValue(booking)
                .retrieve().toBodilessEntity().then();
    }

    @Override
    public Mono<Void> cancel(String bookingId, BigDecimal newValue) {
        return client.post().uri("/booking/api/bookings/{bookingId}/cancel", bookingId)
                .bodyValue(newValue)
                .retrieve().toBodilessEntity().then();
    }

    @Override
    public Mono<Booking> findById(String bookingId) {
        return client.get().uri("/booking/api/bookings/{bookingId}", bookingId).retrieve().bodyToMono(Booking.class);
    }

    /*
{
    "content":[{"id":"0585665f-8809-4092-a159-035fbc05e7fc","customer":"Rosemary Cole","serviceDescription":"Hostal Box","startDate":"2025-01-25","endDate":"2025-02-05","status":"Confirmed","value":1935.498,"invoiced":500.000,"paid":null}],
    "pageable":{
        "pageNumber":0,
        "pageSize":20,
        "sort":{
            "unsorted":true,
            "sorted":false,
            "empty":true
        },
        "offset":0,
        "unpaged":false,
        "paged":true
    },
    "totalPages":1,
    "totalElements":1,
    "last":true,
    "numberOfElements":1,
    "size":20,
    "number":0,
    "sort":{
        "unsorted":true,
        "sorted":false,
        "empty":true
     },
     "first":true,
     "empty":false
}
*/

    @Override
    public Mono<Page<BookingListRow>> search(String text, Pageable pageable) {
        return
                client.post().uri("/booking/api/bookings/search")
                .bodyValue(new SearchRequest(text, new io.mateu.article2.model.Pageable(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        map(pageable.getSort())
                )))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<io.mateu.article2.model.Page<BookingListRow>>() {})
                        .map(p -> new PageImpl<>(
                                p.content(),
                                pageable,
                                p.totalElements()
                        ))
                ;
    }

    private Sort map(org.springframework.data.domain.Sort sort) {
        if (sort == null || sort.isEmpty()) {
            return null;
        }
        return sort.stream().findFirst().map(s -> new Sort(s.getProperty(), Direction.valueOf(s.getDirection().name()))).get();
    }
}
