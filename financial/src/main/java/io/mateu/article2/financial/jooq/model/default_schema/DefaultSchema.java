/*
 * This file is generated by jOOQ.
 */
package io.mateu.article2.financial.jooq.model.default_schema;


import io.mateu.article2.financial.jooq.model.DefaultCatalog;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Event;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Invoice;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Payment;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>BOOKING</code>.
     */
    public final Booking BOOKING = Booking.BOOKING;

    /**
     * The table <code>EVENT</code>.
     */
    public final Event EVENT = Event.EVENT;

    /**
     * The table <code>INVOICE</code>.
     */
    public final Invoice INVOICE = Invoice.INVOICE;

    /**
     * The table <code>PAYMENT</code>.
     */
    public final Payment PAYMENT = Payment.PAYMENT;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Booking.BOOKING,
            Event.EVENT,
            Invoice.INVOICE,
            Payment.PAYMENT
        );
    }
}
