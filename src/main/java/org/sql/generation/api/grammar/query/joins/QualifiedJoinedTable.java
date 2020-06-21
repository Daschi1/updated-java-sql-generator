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

/**
 * This syntax element represents the qualified join ({@code JOIN} between two tables.
 *
 * @author Stanislav Muhametsin
 */
public interface QualifiedJoinedTable
        extends JoinedTable {
    /**
     * Returns the join type for this {@code JOIN}.
     *
     * @return The join type for this {@code JOIN}.
     * @see JoinType
     */
    JoinType getJoinType();

    /**
     * Returns the join specification for this {@code JOIN}.
     *
     * @return The join specification for this {@code JOIN}.
     * @see JoinSpecification
     */
    JoinSpecification getJoinSpecification();

}
