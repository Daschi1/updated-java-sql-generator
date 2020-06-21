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

package org.sql.generation.api.grammar.query.joins;

import org.sql.generation.api.grammar.query.QueryExpressionBody;
import org.sql.generation.api.grammar.query.TableReference;

/**
 * This is common interface for joined tables.
 *
 * @author Stanislav Muhametsin
 * @see CrossJoinedTable
 * @see NaturalJoinedTable
 * @see QualifiedJoinedTable
 * @see UnionJoinedTable
 */
public interface JoinedTable
        extends QueryExpressionBody, TableReference {
    /**
     * Returns the table on the left side of the join.
     *
     * @return The table on the left side of the join.
     */
    TableReference getLeft();

    /**
     * Gets the table on the right side of the join.
     *
     * @return The table on the right side of the join.
     */
    TableReference getRight();

}
