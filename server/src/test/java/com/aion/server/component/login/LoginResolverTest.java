package com.aion.server.component.login;

import com.aion.server.controller.dto.Login;
import com.aion.server.database.infra.DBClient;
import com.aion.server.mock.MockDataBase;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

public class LoginResolverTest {

    @Test
    public void should_get_from_user_db() throws SQLException {
        DBClient dbClient = MockDataBase.mockDBClient();
        Login login = new Login("tigran", "tigran");
        LoginResolver loginResolver = new LoginResolver(dbClient, login);

        Assert.assertEquals(true, loginResolver.checkExist());

    }
}
