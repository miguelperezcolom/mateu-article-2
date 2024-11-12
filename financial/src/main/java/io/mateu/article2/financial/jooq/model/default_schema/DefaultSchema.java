/*
 * This file is generated by jOOQ.
 */
package io.mateu.article2.financial.jooq.model.default_schema;


import io.mateu.article2.financial.jooq.model.DefaultCatalog;
import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable;
import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable2;
import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable3;
import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable4;
import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable5;

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
     * The table <code>TEST_TABLE</code>.
     */
    public final TestTable TEST_TABLE = TestTable.TEST_TABLE;

    /**
     * The table <code>TEST_TABLE2</code>.
     */
    public final TestTable2 TEST_TABLE2 = TestTable2.TEST_TABLE2;

    /**
     * The table <code>TEST_TABLE3</code>.
     */
    public final TestTable3 TEST_TABLE3 = TestTable3.TEST_TABLE3;

    /**
     * The table <code>TEST_TABLE4</code>.
     */
    public final TestTable4 TEST_TABLE4 = TestTable4.TEST_TABLE4;

    /**
     * The table <code>TEST_TABLE5</code>.
     */
    public final TestTable5 TEST_TABLE5 = TestTable5.TEST_TABLE5;

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
            TestTable.TEST_TABLE,
            TestTable2.TEST_TABLE2,
            TestTable3.TEST_TABLE3,
            TestTable4.TEST_TABLE4,
            TestTable5.TEST_TABLE5
        );
    }
}
