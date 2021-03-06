package com.gregorriegler.seamer.sqlite;

import com.gregorriegler.seamer.core.InvocationsShould;
import com.gregorriegler.seamer.core.Persistence;

public class SqliteInvocationsShould extends InvocationsShould {

    @Override
    protected Persistence createPersistence() {
        return new SqlitePersistence();
    }

}
