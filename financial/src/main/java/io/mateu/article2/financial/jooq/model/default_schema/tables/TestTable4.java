/*
 * This file is generated by jOOQ.
 */
package io.mateu.article2.financial.jooq.model.default_schema.tables;


import io.mateu.article2.financial.jooq.model.default_schema.DefaultSchema;
import io.mateu.article2.financial.jooq.model.default_schema.Keys;
import io.mateu.article2.financial.jooq.model.default_schema.tables.records.TestTable4Record;

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
public class TestTable4 extends TableImpl<TestTable4Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>TEST_TABLE4</code>
     */
    public static final TestTable4 TEST_TABLE4 = new TestTable4();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TestTable4Record> getRecordType() {
        return TestTable4Record.class;
    }

    /**
     * The column <code>TEST_TABLE4.TEST_COLUMN</code>.
     */
    public final TableField<TestTable4Record, Integer> TEST_COLUMN = createField(DSL.name("TEST_COLUMN"), SQLDataType.INTEGER.nullable(false), this, "");

    private TestTable4(Name alias, Table<TestTable4Record> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private TestTable4(Name alias, Table<TestTable4Record> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>TEST_TABLE4</code> table reference
     */
    public TestTable4(String alias) {
        this(DSL.name(alias), TEST_TABLE4);
    }

    /**
     * Create an aliased <code>TEST_TABLE4</code> table reference
     */
    public TestTable4(Name alias) {
        this(alias, TEST_TABLE4);
    }

    /**
     * Create a <code>TEST_TABLE4</code> table reference
     */
    public TestTable4() {
        this(DSL.name("TEST_TABLE4"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<TestTable4Record> getPrimaryKey() {
        return Keys.PK_TEST_TABLE4;
    }

    @Override
    public TestTable4 as(String alias) {
        return new TestTable4(DSL.name(alias), this);
    }

    @Override
    public TestTable4 as(Name alias) {
        return new TestTable4(alias, this);
    }

    @Override
    public TestTable4 as(Table<?> alias) {
        return new TestTable4(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public TestTable4 rename(String name) {
        return new TestTable4(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TestTable4 rename(Name name) {
        return new TestTable4(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public TestTable4 rename(Table<?> name) {
        return new TestTable4(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TestTable4 where(Condition condition) {
        return new TestTable4(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TestTable4 where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TestTable4 where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TestTable4 where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TestTable4 where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TestTable4 where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TestTable4 where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public TestTable4 where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TestTable4 whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public TestTable4 whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}