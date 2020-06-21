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

package org.sql.generation.implementation.grammar.manipulation;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.definition.table.TableConstraintDefinition;
import org.sql.generation.api.grammar.manipulation.AddTableConstraintDefinition;
import org.sql.generation.api.grammar.manipulation.AlterTableAction;
import org.sql.generation.implementation.grammar.common.SQLSyntaxElementBase;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class AddTableConstraintDefinitionImpl extends
        SQLSyntaxElementBase<AlterTableAction, AddTableConstraintDefinition>
        implements AddTableConstraintDefinition {

    private final TableConstraintDefinition _constraint;

    public AddTableConstraintDefinitionImpl(final SQLProcessorAggregator processor, final TableConstraintDefinition constraint) {
        this(processor, AddTableConstraintDefinition.class, constraint);
    }

    protected AddTableConstraintDefinitionImpl(final SQLProcessorAggregator processor,
                                               final Class<? extends AddTableConstraintDefinition> realImplementingType, final TableConstraintDefinition constraint) {
        super(processor, realImplementingType);

        NullArgumentException.validateNotNull("Constraint", constraint);

        this._constraint = constraint;
    }

    @Override
    protected boolean doesEqual(final AddTableConstraintDefinition another) {
        return this._constraint.equals(another.getConstraint());
    }

    @Override
    public TableConstraintDefinition getConstraint() {
        return this._constraint;
    }

}
