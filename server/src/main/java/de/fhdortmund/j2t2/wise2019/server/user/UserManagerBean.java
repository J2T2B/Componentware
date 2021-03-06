package de.fhdortmund.j2t2.wise2019.server.user;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.server.commons.util.HashUtil;
import de.fhdortmund.j2t2.wise2019.server.persistence.daos.UserDao;
import de.fhdortmund.j2t2.wise2019.server.user.login.LoginCredentials;
import de.fhdortmund.j2t2.wise2019.server.user.register.NewUserData;
import de.fhdortmund.j2t2.wise2019.server.user.register.UserAlreadyExistsException;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.PreDestroy;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@Named
@Dependent
public class UserManagerBean implements UserManagerRemote, UserManagerLocal {
    Map<String, User> users = new HashMap<>();

    @Inject
    private UserDao userDao;

    public List<Chat> getChatsForUser(String token) {
        User user = users.get(token);
        return user.getGames().stream().map(Game::getGameState).map(GameState::getOpenChats).flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public void createUser(NewUserData user) throws UserAlreadyExistsException, NoSuchAlgorithmException {
        if(users.containsKey(user.getUsername())){
            throw new UserAlreadyExistsException("User with name " + user.getUsername() + "already exists");
        }
        User createdUser = new DefaultUserImpl(user.getUsername(), hashUserData(user), new ArrayList<>());
        users.put(user.getUsername(), createdUser);
        userDao.persist(createdUser);
    }

    @Override
    public User getUser(String name) {
        return users.get(name);
    }

    @Override
    public boolean login(LoginCredentials credentials) throws UserDoesntExistException, NoSuchAlgorithmException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(!users.containsKey(credentials.getUsername())){
            try {
                users.put(credentials.getUsername(), userDao.get(credentials.getUsername()));
            } catch (EJBTransactionRolledbackException | IOException e){
                throw new UserDoesntExistException(credentials.getUsername());
            }

        }
        User target = users.get(credentials.getUsername());
        return hashUserData(credentials).equals(target.getHash());
    }

    private String hashUserData(Credentials user) throws NoSuchAlgorithmException {
        return HashUtil.createHash(ArrayUtils.addAll(user.getUsername().toCharArray(), user.getPassword()));
    }

    @PreDestroy
    public void deconstruct(){
        for(User user : users.values()){
            userDao.persist(user);
        }
    }
}
