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

package org.sql.generation.implementation.grammar.common.datatypes;

import org.atp.spi.TypeableImpl;
import org.sql.generation.api.grammar.common.datatypes.SQLDataType;
import org.sql.generation.api.grammar.common.datatypes.UserDefinedType;

/**
 * @author Stanislav Muhametsin
 */
public class UserDefinedTypeImpl extends TypeableImpl<SQLDataType, UserDefinedType>
        implements UserDefinedType {

    private final String _text;

    public UserDefinedTypeImpl(final String textContent) {
        this(UserDefinedType.class, textContent);
    }

    protected UserDefinedTypeImpl(final Class<? extends UserDefinedType> realImplementingType, final String textContent) {
        super(realImplementingType);

        this._text = textContent;
    }

    @Override
    protected boolean doesEqual(final UserDefinedType another) {
        return TypeableImpl.bothNullOrEquals(this._text, another.getTextualRepresentation());
    }

    @Override
    public String getTextualRepresentation() {
        return this._text;
    }
}
