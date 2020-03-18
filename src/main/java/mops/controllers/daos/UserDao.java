package mops.controllers.daos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDao {
    @Id
    @GeneratedValue
    private int id;
}
