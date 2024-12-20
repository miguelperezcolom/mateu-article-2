/*
 * This file is generated by jOOQ.
 */
package io.mateu.article2.financial.jooq.model.default_schema.tables;


import io.mateu.article2.financial.jooq.model.default_schema.DefaultSchema;
import io.mateu.article2.financial.jooq.model.default_schema.Keys;
import io.mateu.article2.financial.jooq.model.default_schema.tables.records.PaymentRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Payment extends TableImpl<PaymentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PAYMENT</code>
     */
    public static final Payment PAYMENT = new Payment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PaymentRecord> getRecordType() {
        return PaymentRecord.class;
    }

    /**
     * The column <code>PAYMENT.ID</code>.
     */
    public final TableField<PaymentRecord, String> ID = createField(DSL.name("ID"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>PAYMENT.BOOKING_ID</code>.
     */
    public final TableField<PaymentRecord, String> BOOKING_ID = createField(DSL.name("BOOKING_ID"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>PAYMENT._DATE</code>.
     */
    public final TableField<PaymentRecord, LocalDate> _DATE = createField(DSL.name("_DATE"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>PAYMENT._VALUE</code>.
     */
    public final TableField<PaymentRecord, BigDecimal> _VALUE = createField(DSL.name("_VALUE"), SQLDataType.DECIMAL(12, 3), this, "");

    private Payment(Name alias, Table<PaymentRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Payment(Name alias, Table<PaymentRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>PAYMENT</code> table reference
     */
    public Payment(String alias) {
        this(DSL.name(alias), PAYMENT);
    }

    /**
     * Create an aliased <code>PAYMENT</code> table reference
     */
    public Payment(Name alias) {
        this(alias, PAYMENT);
    }

    /**
     * Create a <code>PAYMENT</code> table reference
     */
    public Payment() {
        this(DSL.name("PAYMENT"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<PaymentRecord> getPrimaryKey() {
        return Keys.PK_PAYMENT;
    }

    @Override
    public Payment as(String alias) {
        return new Payment(DSL.name(alias), this);
    }

    @Override
    public Payment as(Name alias) {
        return new Payment(alias, this);
    }

    @Override
    public Payment as(Table<?> alias) {
        return new Payment(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Payment rename(String name) {
        return new Payment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Payment rename(Name name) {
        return new Payment(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Payment rename(Table<?> name) {
        return new Payment(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Payment where(Condition condition) {
        return new Payment(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Payment where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Payment where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Payment where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Payment where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Payment where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Payment where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Payment where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Payment whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Payment whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
