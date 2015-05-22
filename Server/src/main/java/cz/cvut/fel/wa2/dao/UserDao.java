package cz.cvut.fel.wa2.dao;

import cz.cvut.fel.wa2.entities.User;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class UserDao extends AbstractDao<User> {
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
