package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.controller.user;

import java.util.HashMap;
import java.util.Iterator;

import org.springframework.lang.NonNull;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.BadRequest400Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.customExceptions.client400.Conflict409Exception;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.user.User;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.model.user.UserCollection;
import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.repository.UserRepository;

public class UserController {
	UserCollection users;
	UserRepository userDAO;
	
	public UserController(UserRepository userDAO) {
		this.userDAO = userDAO;
		this.users = new UserCollection(loadUsers());
	}
	
	public void addUser(@NonNull String name, @NonNull String login, @NonNull String password, @NonNull boolean isAdmin)
			throws Conflict409Exception {
		User user = new User(name, login, password, isAdmin);
		this.users.addUser(user);
		this.userDAO.save(user);
	}
	
	public boolean checkRegistered(@NonNull String login) {
		return users.checkRegistered(login);
	}
	
	public User getUserByLogin(@NonNull String login) throws BadRequest400Exception {
		return users.getUserByLogin(login);
	}
	
	private HashMap<String, User> loadUsers(){
		final String rootLogin = "admin";
		
		if (!userDAO.existsById(rootLogin)) {
			final User rootUser = new User("administrador", "admin", "admin", true);
			userDAO.save(rootUser);
		}
		
		Iterable<User> iterableUsers = this.userDAO.findAll();
		HashMap<String, User> mappedUsers = new HashMap<String, User>();
		Iterator<User> it = iterableUsers.iterator();
		while (it.hasNext()) {
			User user = it.next();
			mappedUsers.put(user.getLogin(), user);
		}
		return mappedUsers;
	}
}
