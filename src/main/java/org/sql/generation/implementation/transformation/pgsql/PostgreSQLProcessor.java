/*
 * Copyright (c) 2010, Stanislav Muhametsin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.sql.generation.implementation.transformation.pgsql;

import org.atp.api.Typeable;
import org.sql.generation.api.grammar.booleans.BinaryPredicate;
import org.sql.generation.api.grammar.booleans.NotRegexpPredicate;
import org.sql.generation.api.grammar.booleans.RegexpPredicate;
import org.sql.generation.api.grammar.common.datatypes.BigInt;
import org.sql.generation.api.grammar.common.datatypes.SQLInteger;
import org.sql.generation.api.grammar.common.datatypes.SmallInt;
import org.sql.generation.api.grammar.common.datatypes.pgsql.Text;
import org.sql.generation.api.grammar.definition.table.ColumnDefinition;
import org.sql.generation.api.grammar.definition.table.TableCommitAction;
import org.sql.generation.api.grammar.definition.table.TableDefinition;
import org.sql.generation.api.grammar.definition.table.pgsql.PgSQLTableCommitAction;
import org.sql.generation.api.grammar.literals.TimestampTimeLiteral;
import org.sql.generation.api.grammar.manipulation.pgsql.PgSQLDropTableOrViewStatement;
import org.sql.generation.api.grammar.modification.pgsql.PgSQLInsertStatement;
import org.sql.generation.api.grammar.query.LimitSpecification;
import org.sql.generation.api.grammar.query.OffsetSpecification;
import org.sql.generation.api.grammar.query.QuerySpecification;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.implementation.transformation.BooleanExpressionProcessing.BinaryPredicateProcessor;
import org.sql.generation.implementation.transformation.ConstantProcessor;
import org.sql.generation.implementation.transformation.DefaultSQLProcessor;
import org.sql.generation.implementation.transformation.DefinitionProcessing.TableDefinitionProcessor;
import org.sql.generation.implementation.transformation.pgsql.DefinitionProcessing.PGColumnDefinitionProcessor;
import org.sql.generation.implementation.transformation.pgsql.LiteralExpressionProcessing.PGDateTimeLiteralProcessor;
import org.sql.generation.implementation.transformation.pgsql.ManipulationProcessing.PgSQLDropTableOrViewStatementProcessor;
import org.sql.generation.implementation.transformation.pgsql.ModificationProcessing.PgSQLInsertStatementProcessor;
import org.sql.generation.implementation.transformation.pgsql.QueryProcessing.PgSQLLimitSpecificationProcessor;
import org.sql.generation.implementation.transformation.pgsql.QueryProcessing.PgSQLOffsetSpecificationProcessor;
import org.sql.generation.implementation.transformation.pgsql.QueryProcessing.PgSQLQuerySpecificationProcessor;
import org.sql.generation.implementation.transformation.spi.SQLProcessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav Muhametsin
 */
public class PostgreSQLProcessor extends DefaultSQLProcessor {

    private static final Map<Class<? extends Typeable<?>>, SQLProcessor> _defaultProcessors;

    private static final Map<Class<? extends BinaryPredicate>, String> _defaultPgSQLBinaryOperators;

    static {
        final Map<Class<? extends BinaryPredicate>, String> binaryOperators =
                new HashMap<>(
                        DefaultSQLProcessor.getDefaultBinaryOperators());
        binaryOperators.put(RegexpPredicate.class, "~");
        binaryOperators.put(NotRegexpPredicate.class, "!~");
        _defaultPgSQLBinaryOperators = binaryOperators;

        final Map<Class<? extends Typeable<?>>, SQLProcessor> processors =
                new HashMap<>(
                        DefaultSQLProcessor.getDefaultProcessors());

        // Override default processor for date-time
        processors.put(TimestampTimeLiteral.class, new PGDateTimeLiteralProcessor());

        // Override default processor for column definition
        final Map<Class<?>, String> dataTypeSerials = new HashMap<>();
        dataTypeSerials.put(BigInt.class, "BIGSERIAL");
        dataTypeSerials.put(SQLInteger.class, "SERIAL");
        dataTypeSerials.put(SmallInt.class, "SMALLSERIAL");
        processors.put(ColumnDefinition.class,
                new PGColumnDefinitionProcessor(Collections.unmodifiableMap(dataTypeSerials)));

        // Add support for regexp comparing
        processors
                .put(
                        RegexpPredicate.class,
                        new BinaryPredicateProcessor(PostgreSQLProcessor._defaultPgSQLBinaryOperators
                                .get(RegexpPredicate.class)));
        processors.put(
                NotRegexpPredicate.class,
                new BinaryPredicateProcessor(PostgreSQLProcessor._defaultPgSQLBinaryOperators
                        .get(NotRegexpPredicate.class)));

        // Add support for PostgreSQL legacy LIMIT/OFFSET
        processors.put(QuerySpecification.class, new PgSQLQuerySpecificationProcessor());
        processors.put(OffsetSpecification.class, new PgSQLOffsetSpecificationProcessor());
        processors.put(LimitSpecification.class, new PgSQLLimitSpecificationProcessor());

        // Add support for "TEXT" data type
        processors.put(Text.class, new ConstantProcessor("TEXT"));

        // Add "DROP" table commit action
        final Map<TableCommitAction, String> commitActions = new HashMap<>(
                TableDefinitionProcessor.getDefaultCommitActions());
        commitActions.put(PgSQLTableCommitAction.DROP, "DROP");
        processors.put(TableDefinition.class,
                new TableDefinitionProcessor(TableDefinitionProcessor.getDefaultTableScopes(),
                        commitActions));

        // Add "IF EXISTS" functionality to DROP TABLE/VIEW statements
        processors.put(PgSQLDropTableOrViewStatement.class,
                new PgSQLDropTableOrViewStatementProcessor());

        // Add support for PostgreSQL-specific INSTERT statement RETURNING clause
        processors.put(PgSQLInsertStatement.class, new PgSQLInsertStatementProcessor());

        _defaultProcessors = processors;
    }

    public PostgreSQLProcessor(final SQLVendor vendor) {
        super(vendor, PostgreSQLProcessor._defaultProcessors);
    }

}
