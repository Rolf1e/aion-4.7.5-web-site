package com.aion.server.database.infra;

import com.aion.server.database.config.DataBaseConfiguration;
import com.aion.server.database.dto.Authentication;
import com.aion.server.mock.MockDataBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SQLConnectionBuilder.class)
public class PostgresClientTest {

    @Before
    public void init() throws SQLException {
        Authentication authentication = new Authentication("rolfie", "rolfie", "localhost", "8080");
        DataBaseConfiguration configuration = new DataBaseConfiguration("org.postgresql.Driver", "postgresql", "aion-web-site");
        PowerMockito.mockStatic(SQLConnectionBuilder.class);
        BDDMockito.given(SQLConnectionBuilder.getConnection(authentication, configuration))
                .willReturn(MockDataBase.mockSQLConnection());
    }

    @Test
    public void should_select_from_db() {

    }

    @Test
    public void should_insert_from_db() {

    }
}
